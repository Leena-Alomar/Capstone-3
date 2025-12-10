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
    private final CampaignRepository campaignRepository;
    private final GeneratedContentRepository generatedContentRepository;

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void checkCampaignRunDate(){

        List<Campaign>campaigns=campaignRepository.findCampaignByEndDateAndStatus(LocalDate.now(),"Running");

        if(!campaigns.isEmpty()){
            for(int i=0;i<campaigns.size();i++){
                Campaign campaign = campaigns.get(i);
                campaign.setStatus("Completed");
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    public void removeRejectedContents(){

        List<GeneratedContent>contents= generatedContentRepository.findGeneratedContentByStatus("Rejected");

        if(!contents.isEmpty()){
            for(int i=0;i<contents.size();i++){
                GeneratedContent content = contents.get(i);
                if(LocalDateTime.now().isAfter(content.getStatusChanged().plusDays(3))){
                    generatedContentRepository.delete(content);
                }
            }
        }
    }



}
