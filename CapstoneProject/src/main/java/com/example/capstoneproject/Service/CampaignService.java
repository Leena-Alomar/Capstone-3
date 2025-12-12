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

    public String expectationsOfCampaign(Integer campaign_id){
        Campaign campaign = campaignRepository.findCampaignById(campaign_id);
        if(campaign==null) {
            throw new ApiException("campaign not found");
        }
        return openAiClient.campaignExpectations(campaign.getDescription(),campaign.getDurationDays(),campaign.getName(),campaign.getPlatform(),campaign.getBudget());
    }
}
