package com.example.capstoneproject.Controller;

import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.Model.Campagin;
import com.example.capstoneproject.Service.CampaginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/campagin")
public class CampaginController {

    private final CampaginService campaignService;

    @GetMapping("/get")
    public ResponseEntity<?> getCampaigns(){
        return ResponseEntity.ok(campaignService.getAllCampaigns());
    }

    @PostMapping("/add/{projectid}")
    public ResponseEntity<?>addCampaign(@PathVariable Integer projectid, @RequestBody @Valid Campagin campaign){
        campaignService.addCampaign(projectid, campaign);
        return ResponseEntity.ok(new ApiResponse("campaign added successfully"));
    }

    @PutMapping("/update/{campaignid}")
    public ResponseEntity<?>updateCampaign(@PathVariable Integer campaignid, @RequestBody @Valid Campagin campaign){
        campaignService.addCampaign(campaignid, campaign);
        return ResponseEntity.ok(new ApiResponse("campaign updated successfully"));
    }

    @DeleteMapping("/delete/{campaignid}")
    public ResponseEntity<?>deleteCampaign(@PathVariable Integer campaignid){
        campaignService.deleteCampaign(campaignid);
        return ResponseEntity.ok(new ApiResponse("campaign deleted successfully"));
    }
}
