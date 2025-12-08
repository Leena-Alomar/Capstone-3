package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.Campagin;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Repository.CampaginRepository;
import com.example.capstoneproject.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaginService {

    private final CampaginRepository campaginRepository;
    private final ProjectRepository projectRepository;

    public List<Campagin> getAllCampaigns(){
        return campaginRepository.findAll();
    }

    public void addCampaign(Integer project_id, Campagin campagin){
        Project project = projectRepository.findProjectById(project_id);

        if(project==null){
            throw new ApiException("project not found");
        }

        campagin.setProject(project);

        campaginRepository.save(campagin);
    }

    public void updateCampaign(Integer campaign_id,Campagin campagin){

        Campagin oldCampaign = campaginRepository.findCampaginById(campaign_id);
        if(oldCampaign==null) {
            throw new ApiException("campaign not found");
        }

        Project project = projectRepository.findProjectById(campagin.getProject().getId());
        if(project==null) {
            throw new ApiException("project not found");
        }

        oldCampaign.setName(campagin.getName());
        oldCampaign.setDescription(campagin.getDescription());
        oldCampaign.setGoal(campagin.getGoal());
        oldCampaign.setPlatform(campagin.getPlatform());
        oldCampaign.setBudget(campagin.getBudget());
        oldCampaign.setStartDate(campagin.getStartDate());
        oldCampaign.setEndDate(campagin.getEndDate());
        oldCampaign.setStatus(campagin.getStatus());
        oldCampaign.setTargetAudience(campagin.getTargetAudience());
        oldCampaign.setProject(project);

        campaginRepository.save(oldCampaign);
    }

    public void deleteCampaign(Integer campaign_id){

        Campagin campaign = campaginRepository.findCampaginById(campaign_id);
        if(campaign==null) {
            throw new ApiException("campaign not found");
        }

        campaginRepository.delete(campaign);
    }
}
