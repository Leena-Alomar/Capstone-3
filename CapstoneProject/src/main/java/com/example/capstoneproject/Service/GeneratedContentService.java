package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.Campagin;
import com.example.capstoneproject.Model.GeneratedContent;
import com.example.capstoneproject.Repository.CampaginRepository;
import com.example.capstoneproject.Repository.GeneratedContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneratedContentService {

    private final GeneratedContentRepository generatedContentRepository;
    private final CampaginRepository campaignRepository;

    public List<GeneratedContent> getAllGeneratedContent(){
        return generatedContentRepository.findAll();
    }

    public void addContent(Integer campaign_id, GeneratedContent content){
        Campagin campaign = campaignRepository.findCampaginById(campaign_id);

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

        Campagin campaign = campaignRepository.findCampaginById(content.getCampaign().getId());

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
}
