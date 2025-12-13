package com.example.capstoneproject.Service;

import com.example.capstoneproject.AI.OpenAi.OpenAiClient;
import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.Campaign;
import com.example.capstoneproject.Model.GeneratedContent;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Model.User;
import com.example.capstoneproject.Repository.CampaignRepository;
import com.example.capstoneproject.Repository.GeneratedContentRepository;
import com.example.capstoneproject.Repository.ProjectRepository;
import com.example.capstoneproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final GeneratedContentRepository generatedContentRepository;


    private final OpenAiClient openAiClient;
    private int count=0;


    public List<Campaign> getAllCampaigns(){
        return campaignRepository.findAll();
    }

    public void addCampaign(Integer project_id, Campaign campaign){
        Project project = projectRepository.findProjectById(project_id);
        if(project==null){
            throw new ApiException("project not found");
        }
        campaign.setStatus("Draft");
        campaign.setProject(project);
        campaignRepository.save(campaign);
    }

    public void updateCampaign(Integer campaign_id, Campaign campaign){

        Campaign oldCampaign = campaignRepository.findCampaignById(campaign_id);
        if(oldCampaign==null) {
            throw new ApiException("campaign not found");
        }

        oldCampaign.setName(campaign.getName());
        oldCampaign.setDescription(campaign.getDescription());
        oldCampaign.setGoal(campaign.getGoal());
        oldCampaign.setPlatform(campaign.getPlatform());
        oldCampaign.setBudget(campaign.getBudget());
        oldCampaign.setStartDate(campaign.getStartDate());
        oldCampaign.setEndDate(campaign.getEndDate());
        oldCampaign.setStatus(campaign.getStatus());
        oldCampaign.setTargetAudience(campaign.getTargetAudience());

        campaignRepository.save(oldCampaign);
    }

    public void deleteCampaign(Integer campaign_id){

        Campaign campaign = campaignRepository.findCampaignById(campaign_id);
        if(campaign==null) {
            throw new ApiException("campaign not found");
        }

        campaignRepository.delete(campaign);
    }

    public void approveCampaign(Integer campaign_id){
        Campaign campaign = campaignRepository.findCampaignById(campaign_id);

        if(campaign==null) {
            throw new ApiException("campaign not found");
        }

        List<GeneratedContent>contents=generatedContentRepository.findGeneratedContentByCampaign(campaign);

        if(contents.isEmpty()){
            throw new ApiException("there is no approved content available for this campaign");
        }

        campaign.setStartDate(LocalDate.now());
        campaign.setStatus("Running");
        campaign.setEndDate(LocalDate.now().plusDays(campaign.getDurationDays()));
        campaignRepository.save(campaign);
    }

    public void rejectCampaign(Integer campaign_id){

        Campaign campaign = campaignRepository.findCampaignById(campaign_id);

        if(campaign==null) {
            throw new ApiException("campaign not found");
        }

        Project project = projectRepository.findProjectById(campaign.getProject().getId());
        User user = userRepository.findUserById(project.getUser().getId());

        if(project==null || user==null) {
            throw new ApiException("project or user not found");
        }

        if(!user.getSubscription()){
            throw new ApiException("you need to subscribe to get new campaign");
        }

        List<GeneratedContent>campaignContent= generatedContentRepository.findGeneratedContentByCampaign(campaign);

        for(int i=0; i<campaignContent.size();i++){
            generatedContentRepository.delete(campaignContent.get(i));
        }

        campaignRepository.delete(campaign);
    }
    public String suggestAudience(Integer id){

        Campaign campaign = campaignRepository.findCampaignById(id);
        if(campaign==null) {
            throw new ApiException("campaign not found");
        }

        String prompt = "You are a friendly marketing assistant. Suggest an ideal target audience " +
                "for this campaign:\n" +
                "Name: " + campaign.getName() + "\n" +
                "Description: " + campaign.getDescription() + "\n" +
                "Platform: " + campaign.getPlatform() + "\n" +
                "Goal: " + campaign.getGoal() + "\n" +
                "Budget: " + campaign.getBudget() + "\n\n" +
                "Provide: age range, gender, interests, income level, location.";

        return openaiService.askAI(prompt);
    }


    public String checkAudienceSubsecrition(Integer project_id,Integer id){
        Project project=projectRepository.findProjectById(project_id);
        if(project==null){
            throw new ApiException("the project is not found");
        }
        if (Boolean.FALSE.equals(project.getUser().getSubscription())) {
            if(count>=2){
                throw new ApiException("Maximum free trials reached. Please subscribe for more");
            }
            count ++;
        }
        return suggestAudience(id);
    }

    public String campaginEngagementPredictions(Integer id){
        Campaign campaign = campaignRepository.findCampaignById(id);
        if(campaign==null) {
            throw new ApiException("campaign not found");
        }

        String prompt = "You are a friendly marketing assistant. Predict engagement for this campaign:\n" +
                "Name: " + campaign.getName() + "\n" +
                "Description: " + campaign.getDescription() + "\n" +
                "Platform: " + campaign.getPlatform() + "\n" +
                "Goal: " + campaign.getGoal() + "\n" +
                "Budget: " + campaign.getBudget() + "\n" +
                "Provide estimated reach, likes, shares, and impressions.";

        return openaiService.askAI(prompt);
    }


    public String checkEngagment(Integer project_id,Integer id){
        Project project=projectRepository.findProjectById(project_id);
        if(project==null){
            throw new ApiException("the project is not found");
        }
        if (Boolean.FALSE.equals(project.getUser().getSubscription())) {
            if(count>=2){
                throw new ApiException("Maximum free trials reached. Please subscribe for more");
            }
            count ++;
        }
        return campaginEngagementPredictions(id);
    }

    public String campaginCheckAudience(Integer id){
        Campaign campaign = campaignRepository.findCampaignById(id);
        if(campaign==null||campaign.getTargetAudience().getId()==null) {
            throw new ApiException("campaign or audience not found");
        }
        String prompt = "You are a friendly marketing assistant. Evaluate whether the target audience " +
                "is a good fit for this campaign. Suggest improvements if necessary.\n\n" +
                "Campaign Details:\n" +
                "Name: " + campaign.getName() + "\n" +
                "Description: " + campaign.getDescription() + "\n" +
                "Platform: " + campaign.getPlatform() + "\n" +
                "Goal: " + campaign.getGoal() + "\n" +
                "Budget: " + campaign.getBudget() + "\n\n" +
                "Target Audience:\n" +
                "Age Range: " + campaign.getTargetAudience().getMinAge() + " - " + campaign.getTargetAudience().getMaxAge() + "\n" +
                "Gender: " + campaign.getTargetAudience().getGender() + "\n" +
                "Interests: " + campaign.getTargetAudience().getInterest() + "\n" +
                "Location: " + campaign.getTargetAudience().getLocation() + "\n" +
                "Income Level: " + campaign.getTargetAudience().getIncomeLevel() + "\n\n" +
                "Instructions: \n" +
                "- Tell if this audience fits the campaign.\n" +
                "- Suggest adjustments if needed (age, gender, interests, location, income level).\n" +
                "- Provide recommendations clearly and concisely.";

        return openaiService.askAI(prompt);
    }


    public String checkCampaginCheckAudience(Integer project_id,Integer id){
        Project project=projectRepository.findProjectById(project_id);
        if(project==null){
            throw new ApiException("the project is not found");
        }
        if (Boolean.FALSE.equals(project.getUser().getSubscription())) {
            if(count>=2){
                throw new ApiException("Maximum free trials reached. Please subscribe for more");
            }
            count ++;
        }
        return campaginCheckAudience(id);
    }


    public String expectationsOfCampaignAmmar(Integer campaign_id){
        Campaign campaign = campaignRepository.findCampaignById(campaign_id);
        if(campaign==null) {
            throw new ApiException("campaign not found");
        }
        return openAiClient.campaignExpectations(campaign.getDescription(),campaign.getDurationDays(),campaign.getName(),campaign.getPlatform(),campaign.getBudget());
    }
}
