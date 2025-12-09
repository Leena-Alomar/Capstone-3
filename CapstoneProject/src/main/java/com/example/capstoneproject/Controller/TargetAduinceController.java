package com.example.capstoneproject.Controller;

import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.DTO.AudienceDTO;
import com.example.capstoneproject.Model.TargetAudience;
import com.example.capstoneproject.Service.TargetAduinceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Aduince")
public class TargetAduinceController {

    private final TargetAduinceService targetAduinceService;


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
}
