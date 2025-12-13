package com.example.capstoneproject.Controller;

import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.Model.Campaign;
import com.example.capstoneproject.Service.CampaignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/campaign")
public class CampaignController {

    private final CampaignService campaignService;


    @GetMapping("/audience/{id}")
    public ResponseEntity<?> campaginAudince(@PathVariable Integer id){
        String answer = campaignService.campaginCheckAudience(id);
        return ResponseEntity.status(200).body(answer);

    }

    @GetMapping("/check-campaginaudince/{project_id}/{id}")
    public ResponseEntity<?> checkcampgainAudience(@PathVariable Integer project_id,@PathVariable Integer id){
        String answer = campaignService.checkCampaginCheckAudience(project_id,id);
        return ResponseEntity.status(200).body(answer);
    }

    @GetMapping("/suggestion/{id}")
    public ResponseEntity<?> askAI(@PathVariable Integer id){
        String answer = campaignService.suggestAudience(id);
        return ResponseEntity.status(200).body(answer);

    }

    @GetMapping("/check-audince/{project_id}/{id}")
    public ResponseEntity<?> checkAudience(@PathVariable Integer project_id,@PathVariable Integer id){
        String answer = campaignService.checkAudienceSubsecrition(project_id,id);
        return ResponseEntity.status(200).body(answer);
    }

    @GetMapping("/engagement/{id}")
    public ResponseEntity<?> campaginEngagement(@PathVariable Integer id){
        String answer = campaignService.campaginEngagementPredictions(id);
        return ResponseEntity.status(200).body(answer);

    }

    @GetMapping("/check-engagement/{project_id}/{id}")
    public ResponseEntity<?> checkEngagament(@PathVariable Integer project_id,@PathVariable Integer id){
        String answer = campaignService.checkEngagment(project_id,id);
        return ResponseEntity.status(200).body(answer);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCampaigns(){
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @PostMapping("/add/{projectid}")
    public ResponseEntity<?>addCampaign(@PathVariable Integer projectid, @RequestBody @Valid Campaign campaign){
        campaignService.addCampaign(projectid, campaign);
        return ResponseEntity.ok(new ApiResponse("campaign added successfully"));
    }

    @PutMapping("/update/{campaignid}")
    public ResponseEntity<?>updateCampaign(@PathVariable Integer campaignid, @RequestBody @Valid Campaign campaign){
        campaignService.updateCampaign(campaignid, campaign);
        return ResponseEntity.ok(new ApiResponse("campaign updated successfully"));
    }

    @DeleteMapping("/delete/{campaignid}")
    public ResponseEntity<?>deleteCampaign(@PathVariable Integer campaignid){
        campaignService.deleteCampaign(campaignid);
        return ResponseEntity.ok(new ApiResponse("campaign deleted successfully"));
    }

    @PutMapping("/run/{campaignid}")
    public ResponseEntity<?>runCampaign(@PathVariable Integer campaignid){
        campaignService.approveCampaign(campaignid);
        return ResponseEntity.ok(new ApiResponse("campaign updated successfully"));
    }

    @PutMapping("/cancel/{campaignid}")
    public ResponseEntity<?>cancelCampaign(@PathVariable Integer campaignid){
        campaignService.rejectCampaign(campaignid);
        return ResponseEntity.ok(new ApiResponse("campaign canceled successfully"));
    }

    @GetMapping("/expectations/{campaign_id}")
    public ResponseEntity<?>expectations(@PathVariable Integer campaign_id){
        return ResponseEntity.status(200).body(campaignService.expectationsOfCampaign(campaign_id));
    }

}
