package com.example.capstoneproject.Controller;

import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.Apify.DTO.ListMatchCountDto;
import com.example.capstoneproject.Apify.Service.ListAudienceService;
import com.example.capstoneproject.DTO.AudienceDTO;
import com.example.capstoneproject.Service.TargetAduinceService;
import com.example.capstoneproject.XApi.DTOs.AudienceSummaryDto;
import com.example.capstoneproject.XApi.Service.AudienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/audience")
public class TargetAudienceController {

    private final TargetAduinceService targetAduinceService;
    private final AudienceService audienceService;
    private final ListAudienceService listAudienceService;


    @GetMapping("/get")
    public ResponseEntity<?> getTargetAduinces(){
        return ResponseEntity.status(200).body(targetAduinceService.findAllUser());
    }

    @PostMapping("add/{campaign_id}")
    public ResponseEntity<?> addTargetAduince(@PathVariable Integer campaign_id,@RequestBody @Valid AudienceDTO targetaudience){
        targetAduinceService.addTargetAduince(campaign_id, targetaudience);
        return ResponseEntity.status(200).body(new ApiResponse("the Target Aduince is added"));
    }


    @PutMapping("update/{id}")
    public ResponseEntity<?> updateTargetAduince(@PathVariable Integer id,@RequestBody @Valid AudienceDTO targetAudience){
        targetAduinceService.updateTargetAduince(id, targetAudience);
        return ResponseEntity.status(200).body(new ApiResponse("the Target Aduince is updated"));
    }

    @DeleteMapping ("delete/{id}")
    public ResponseEntity<?> deleteTargetAduince(@PathVariable Integer id){
        targetAduinceService.deleteTargetAduince(id);
        return ResponseEntity.status(200).body(new ApiResponse("the Target Aduince is deleted"));
    }

    @GetMapping("/analyze")
    public ResponseEntity<?> analyze(
            @RequestParam String handle,
            @RequestParam(defaultValue = "100") int limit
    ) {
        int safeLimit = Math.min(limit, 300); // avoid abusing API
        Mono<AudienceSummaryDto> summary =  audienceService.analyzeHandle(handle, safeLimit);
        return ResponseEntity.status(200).body(summary);
    }

    @GetMapping("/match-count/{project_id}")
    public ResponseEntity<?> countMatchingFollowers(
            @PathVariable Integer project_id,
            @RequestParam(defaultValue = "100") Integer maxFollowers
    ) {
        return ResponseEntity.status(200).body(listAudienceService.getListFollowersCount(project_id, maxFollowers));
    }
    @GetMapping("/test-count/{project_id}")


    public ResponseEntity<?> test(
            @PathVariable Integer project_id,
            @RequestParam(defaultValue = "100") Integer maxFollowers
    ) {
        return ResponseEntity.status(200).body(listAudienceService.getListFollowersCount(project_id, maxFollowers));
    }
}
