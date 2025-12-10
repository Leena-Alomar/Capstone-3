package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.Campaign;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Repository.CampaginRepository;
import com.example.capstoneproject.Repository.ProjectRepository;
import com.example.capstoneproject.Repository.TargetAduinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaginService {

    private final CampaginRepository campaginRepository;
    private final ProjectRepository projectRepository;
    private final TargetAduinceRepository targetAduinceRepository;

    public List<Campaign> getAllCampaigns(){
        return campaginRepository.findAll();
    }

    public void addCampaign(Integer project_id, Campaign campaign){
        Project project = projectRepository.findProjectById(project_id);
        if(project==null){
            throw new ApiException("project not found");
        }
        campaign.setStatus("Draft");
        campaign.setProject(project);
        campaginRepository.save(campaign);
    }

    public void updateCampaign(Integer campaign_id, Campaign campaign){

        Campaign oldCampaign = campaginRepository.findCampaginById(campaign_id);
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

        campaginRepository.save(oldCampaign);
    }

    public void deleteCampaign(Integer campaign_id){

        Campaign campaign = campaginRepository.findCampaginById(campaign_id);
        if(campaign==null) {
            throw new ApiException("campaign not found");
        }

        campaginRepository.delete(campaign);
    }


}
