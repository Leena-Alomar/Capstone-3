package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.DTO.CampaignContentDTO;
import com.example.capstoneproject.DTO.EvaluationDTO;
import com.example.capstoneproject.Model.*;
import com.example.capstoneproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneratedContentService {

    private final GeneratedContentRepository generatedContentRepository;
    private final CampaignRepository campaignRepository;
    private final ProjectRepository projectRepository;
    private final TargetAduinceRepository targetAduinceRepository;
    private final AIService aiService;
    private final UserRepository userRepository;

    public List<GeneratedContent> getAllGeneratedContent(){
        return generatedContentRepository.findAll();
    }

    public void addContent(Integer campaign_id, GeneratedContent content){
        Campaign campaign = campaignRepository.findCampaignById(campaign_id);

        if(campaign==null){
            throw new ApiException("campaign not found");
        }
        content.setCampaign(campaign);
        campaignRepository.save(campaign);
    }

    public void updateContent(Integer content_id, GeneratedContent content){

        GeneratedContent oldContent = generatedContentRepository.findGeneratedContentById(content_id);
        if(oldContent==null) {
            throw new ApiException("content not found");
        }

        Campaign campaign = campaignRepository.findCampaignById(content.getCampaign().getId());

        if(campaign==null) {
            throw new ApiException("campaign not found");
        }

        oldContent.setTitle(content.getTitle());
        oldContent.setContent(content.getContent());
        oldContent.setType(content.getType());
        oldContent.setCampaign(campaign);

        generatedContentRepository.save(oldContent);
    }

    public void deleteContent(Integer content_id){
        GeneratedContent content = generatedContentRepository.findGeneratedContentById(content_id);
        if(content==null) {
            throw new ApiException("content not found");
        }
        generatedContentRepository.delete(content);
    }

    public void generateNewContent(Integer campaign_id){
        Campaign campaign = campaignRepository.findCampaignById(campaign_id);
        if(campaign==null){
            throw new ApiException("campaign not found");
        }

        Project project = projectRepository.findProjectById(campaign.getProject().getId());
        TargetAudience audience = targetAduinceRepository.findTargetAduinceById(campaign.getTargetAudience().getId());
        if(project==null||audience==null){
            throw new ApiException("project or target audience not found");
        }

        User user = userRepository.findUserById(project.getUser().getId());
        if(user==null){
            throw new ApiException("user not found");
        }

        if(!user.getSubscription()){
            if(user.getCreatedCounter()>5){
                throw new ApiException("you need to subscribe for more content");
            }
        }

        CampaignContentDTO dto= new CampaignContentDTO();
        dto.setProjectName(project.getName());
        dto.setProjectDescription(project.getDescription());
        dto.setProjectType(project.getType());
        dto.setCampaignName(campaign.getName());
        dto.setCampaignDescription(campaign.getDescription());
        dto.setPlatform(campaign.getPlatform());
        dto.setGoal(campaign.getGoal());
        dto.setTargetAudienceMinAge(audience.getMinAge());
        dto.setTargetAudienceMaxAge(audience.getMaxAge());
        dto.setTargetAudienceGender(audience.getGender());
        dto.setTargetInterest(audience.getInterest());
        dto.setTargetAudienceLocation(audience.getLocation());
        dto.setTargetAudienceIncomeLevel(audience.getIncomeLevel());

        GeneratedContent newContent = aiService.generateContent(dto);

        newContent.setStatus("Draft");
        newContent.setStatusChange(LocalDateTime.now());
        generatedContentRepository.save(newContent);
        user.setCreatedCounter(user.getCreatedCounter()+1);
        userRepository.save(user);
    }

    public void summarizeContent(Integer content_id){
        GeneratedContent content = generatedContentRepository.findGeneratedContentById(content_id);
        if(content==null){
            throw new ApiException("content not found");
        }

        GeneratedContent summarizedContent = aiService.summarizedContent(content);
        generatedContentRepository.save(summarizedContent);
    }

    public void translateContent(Integer content_id, String language){
        GeneratedContent content = generatedContentRepository.findGeneratedContentById(content_id);
        if(content==null){
            throw new ApiException("content not found");
        }

        GeneratedContent translatedContent = aiService.translateContent(content, language);
        generatedContentRepository.save(translatedContent);
    }

    public void approveContent(Integer content_id,Integer campaign_id){
        GeneratedContent content = generatedContentRepository.findGeneratedContentById(content_id);
        Campaign campaign = campaignRepository.findCampaignById(campaign_id);

        if(campaign==null||content==null){
            throw new ApiException("campaign or content not found");
        }

        if(content.getStatus().equals("Approved")){
            throw new ApiException("this content is already approved");
        }
        content.setStatus("Approved");
        content.setStatusChange(LocalDateTime.now());
        content.setCampaign(campaign);
        generatedContentRepository.save(content);
    }

    public void rejectContent(Integer content_id){
        GeneratedContent content = generatedContentRepository.findGeneratedContentById(content_id);

        if(content==null){
            throw new ApiException("content not found");
        }

        content.setStatus("Rejected");
        content.setStatusChange(LocalDateTime.now());
        generatedContentRepository.save(content);
    }

    public void trendGenerateContent(Integer campaign_id){
        Campaign campaign = campaignRepository.findCampaignById(campaign_id);
        if(campaign==null){
            throw new ApiException("campaign not found");
        }

        Project project = projectRepository.findProjectById(campaign.getProject().getId());
        TargetAudience audience = targetAduinceRepository.findTargetAduinceById(campaign.getTargetAudience().getId());
        if(project==null||audience==null){
            throw new ApiException("project or target audience not found");
        }

        User user = userRepository.findUserById(project.getUser().getId());
        if(user==null){
            throw new ApiException("user not found");
        }

        if(!user.getSubscription()){
            if(user.getCreatedCounter()>5){
                throw new ApiException("you need to subscribe for more content");
            }
        }

        CampaignContentDTO dto= new CampaignContentDTO();
        dto.setProjectName(project.getName());
        dto.setProjectDescription(project.getDescription());
        dto.setProjectType(project.getType());
        dto.setCampaignName(campaign.getName());
        dto.setCampaignDescription(campaign.getDescription());
        dto.setPlatform(campaign.getPlatform());
        dto.setGoal(campaign.getGoal());
        dto.setTargetAudienceMinAge(audience.getMinAge());
        dto.setTargetAudienceMaxAge(audience.getMaxAge());
        dto.setTargetAudienceGender(audience.getGender());
        dto.setTargetInterest(audience.getInterest());
        dto.setTargetAudienceLocation(audience.getLocation());
        dto.setTargetAudienceIncomeLevel(audience.getIncomeLevel());

        GeneratedContent newContent = aiService.trendGenerateContent(dto);

        newContent.setStatus("Draft");
        newContent.setStatusChange(LocalDateTime.now());
        generatedContentRepository.save(newContent);
        user.setCreatedCounter(user.getCreatedCounter()+1);
        userRepository.save(user);
    }

    public EvaluationDTO evaluate(Integer content_id,Integer campaign_id){
        GeneratedContent content = generatedContentRepository.findGeneratedContentById(content_id);
        Campaign campaign = campaignRepository.findCampaignById(campaign_id);
        if(content==null){
            throw new ApiException("content not found");
        }
        if(campaign == null){
            throw new ApiException("campaign not found");
        }

        Project project = projectRepository.findProjectById(campaign.getProject().getId());
        TargetAudience audience = targetAduinceRepository.findTargetAduinceById(campaign.getTargetAudience().getId());
        if(project==null||audience==null){
            throw new ApiException("project or target audience not found");
        }

        User user = userRepository.findUserById(project.getUser().getId());
        if(user==null){
            throw new ApiException("user not found");
        }

        if(!user.getSubscription()){
            if(user.getCreatedCounter()>5){
                throw new ApiException("you need to subscribe to open this feature");
            }
        }

        CampaignContentDTO dto= new CampaignContentDTO();
        dto.setProjectName(project.getName());
        dto.setProjectDescription(project.getDescription());
        dto.setProjectType(project.getType());
        dto.setCampaignName(campaign.getName());
        dto.setCampaignDescription(campaign.getDescription());
        dto.setPlatform(campaign.getPlatform());
        dto.setGoal(campaign.getGoal());
        dto.setTargetAudienceMinAge(audience.getMinAge());
        dto.setTargetAudienceMaxAge(audience.getMaxAge());
        dto.setTargetAudienceGender(audience.getGender());
        dto.setTargetInterest(audience.getInterest());
        dto.setTargetAudienceLocation(audience.getLocation());
        dto.setTargetAudienceIncomeLevel(audience.getIncomeLevel());

        EvaluationDTO evaluationDTO= aiService.evaluate(content,dto);
        if(evaluationDTO==null){
            throw new ApiException("something went wrong unable to evaluate");
        }
        return evaluationDTO;
    }

    public EvaluationDTO checkCulture(Integer content_id, String culture){
        GeneratedContent content = generatedContentRepository.findGeneratedContentById(content_id);
        if(content==null){
            throw new ApiException("content not found");
        }

        EvaluationDTO evaluationDTO= aiService.checkCulture(content,culture);
        if(evaluationDTO==null){
            throw new ApiException("something went wrong unable to check");
        }
        return evaluationDTO;
    }
}
