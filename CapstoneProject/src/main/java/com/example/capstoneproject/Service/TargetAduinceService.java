package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.DTO.AudienceDTO;
import com.example.capstoneproject.Model.TargetAudience;
import com.example.capstoneproject.Model.Campaign;
import com.example.capstoneproject.Repository.CampaignRepository;
import com.example.capstoneproject.Repository.TargetAduinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TargetAduinceService {

    private final TargetAduinceRepository targetAduinceRepository;
    private final CampaignRepository campaignRepository;

    public List<TargetAudience> findAllUser(){
        return targetAduinceRepository.findAll();
    }

    public void addTargetAduince(Integer campaign_id, AudienceDTO targetAudience){
        Campaign c = campaignRepository.findCampaignById(campaign_id);
        if (c == null){
            throw new ApiException("campaign not found");
        }
        TargetAudience t = new TargetAudience();
        t.setCampaign(c);
        t.setGender(targetAudience.getGender());
        t.setIncomeLevel(targetAudience.getIncome_level());
        t.setLocation(targetAudience.getLocation());
        t.setInterest(targetAudience.getInterest());
        t.setMinAge(targetAudience.getMin_age());
        t.setMaxAge(targetAudience.getMax_age());
        targetAduinceRepository.save(t);
    }

    public void updateTargetAduince(Integer id , AudienceDTO targetaudience){
        TargetAudience oldTargetAudience =targetAduinceRepository.findTargetAduinceById(id);
        if(oldTargetAudience ==null){
            throw new ApiException("Target Aduince is not found");
        }
        oldTargetAudience.setMinAge(targetaudience.getMin_age());
        oldTargetAudience.setMaxAge(targetaudience.getMax_age());
        oldTargetAudience.setGender(targetaudience.getGender());
        oldTargetAudience.setInterest(targetaudience.getInterest());
        oldTargetAudience.setLocation(targetaudience.getLocation());
        oldTargetAudience.setIncomeLevel(targetaudience.getIncome_level());
        targetAduinceRepository.save(oldTargetAudience);
    }

    public void deleteTargetAduince(Integer id){
        TargetAudience targetaudience =targetAduinceRepository.findTargetAduinceById(id);
        if(targetaudience ==null){
            throw new ApiException("Target Aduince is not found");
        }
        targetAduinceRepository.delete(targetaudience);
        Campaign c = campaignRepository.findCampaignById(id);
        c.setTargetAudience(null);
        campaignRepository.save(c);
    }
}
