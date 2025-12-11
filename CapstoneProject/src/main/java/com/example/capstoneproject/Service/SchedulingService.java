package com.example.capstoneproject.Service;
import com.example.capstoneproject.Model.Campaign;
import com.example.capstoneproject.Model.GeneratedContent;
import com.example.capstoneproject.Repository.CampaignRepository;
import com.example.capstoneproject.Repository.GeneratedContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final GeneratedContentRepository generatedContentRepository;
    private final CampaignRepository campaignRepository;

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void removeRejected(){
        List<GeneratedContent>contents= generatedContentRepository.findGeneratedContentByStatus("Rejected");

        for(int i=0;i<contents.size();i++){
            GeneratedContent content = contents.get(i);
            if(content.getStatusChange().isBefore(LocalDateTime.now().plusDays(3))){
                generatedContentRepository.delete(content);
            }
        }
    }
    @Scheduled(fixedRate = 60000)
    public void completeCampaign(){
        List<Campaign>campaigns= campaignRepository.findCampaignByStatus("Running");

        for(int i=0;i<campaigns.size();i++){
            Campaign campaign = campaigns.get(i);
            if(campaign.getEndDate().isBefore(LocalDate.now())){
                campaign.setStatus("Completed");
            }
        }
    }





}
