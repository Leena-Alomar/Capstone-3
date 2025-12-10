package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.DTO.CampaignContentDTO;
import com.example.capstoneproject.Model.Campaign;
import com.example.capstoneproject.Model.GeneratedContent;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Model.TargetAudience;
import com.example.capstoneproject.Repository.CampaginRepository;
import com.example.capstoneproject.Repository.GeneratedContentRepository;
import com.example.capstoneproject.Repository.ProjectRepository;
import com.example.capstoneproject.Repository.TargetAduinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneratedContentService {

    private final GeneratedContentRepository generatedContentRepository;
    private final CampaginRepository campaignRepository;
    private final ProjectRepository projectRepository;
    private final TargetAduinceRepository targetAduinceRepository;
    private final AIService aiService;

    public List<GeneratedContent> getAllGeneratedContent(){
        return generatedContentRepository.findAll();
    }

    public void addContent(Integer campaign_id, GeneratedContent content){
        Campaign campaign = campaignRepository.findCampaginById(campaign_id);

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

        Campaign campaign = campaignRepository.findCampaginById(content.getCampaign().getId());

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
        Campaign campaign = campaignRepository.findCampaginById(campaign_id);
        if(campaign==null){
            throw new ApiException("campaign not found");
        }

        Project project = projectRepository.findProjectById(campaign.getProject().getId());
        TargetAudience audience = targetAduinceRepository.findTargetAduinceById(campaign.getTargetAudience().getId());
        if(project==null||audience==null){
            throw new ApiException("project or target audience not found");
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

        newContent.setCampaign(campaign);
        generatedContentRepository.save(newContent);
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

}
