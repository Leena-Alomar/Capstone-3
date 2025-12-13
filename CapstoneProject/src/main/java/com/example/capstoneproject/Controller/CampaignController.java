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

    @GetMapping("/get/user/{id}")
    public ResponseEntity<?> getCampaignsByUser(@PathVariable Integer id){
        return ResponseEntity.status(200).body(campaignService.getCampaignsOfUser(id));
    }

    @GetMapping("/status/draft")
    public ResponseEntity<?> getCampaignsStatusDraft(){
        return ResponseEntity.status(200).body(campaignService.getCampaignDraft());
    }
    @GetMapping("/status/running")
    public ResponseEntity<?> getCampaignsStatusRunning(){
        return ResponseEntity.status(200).body(campaignService.getCampaignRunning());
    }
    @GetMapping("/status/completed")
    public ResponseEntity<?> getCampaignsStatusCompleted(){
        return ResponseEntity.status(200).body(campaignService.getCampaignCompleted());
    }

    @GetMapping("/platform/x")
    public ResponseEntity<?> getPlatformX(){
        return ResponseEntity.status(200).body(campaignService.getCampaignX());
    }
    @GetMapping("/platform/tiktok")
    public ResponseEntity<?> getPlatformTikTok(){
        return ResponseEntity.status(200).body(campaignService.getCampaignTikTok());
    }
    @GetMapping("/platform/instagram")
    public ResponseEntity<?> getPlatformInstagram(){
        return ResponseEntity.status(200).body(campaignService.getCampaignInstagram());
    }


}
