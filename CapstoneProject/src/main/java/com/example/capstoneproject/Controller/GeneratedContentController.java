package com.example.capstoneproject.Controller;

import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.DTO.InputDTO;
import com.example.capstoneproject.Model.GeneratedContent;
import com.example.capstoneproject.Service.GeneratedContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/generated-content")
public class GeneratedContentController {

    private final GeneratedContentService generatedContentService;

    @GetMapping("/get")
    public ResponseEntity<?> getContent(){
        return ResponseEntity.ok(generatedContentService.getAllGeneratedContent());
    }

    @PostMapping("/add/{campaignid}")
    public ResponseEntity<?>addContent(@PathVariable Integer campaignid, @RequestBody @Valid GeneratedContent content){
        generatedContentService.addContent(campaignid, content);
        return ResponseEntity.ok(new ApiResponse("content added successfully"));
    }

    @PutMapping("/update/{contentid}")
    public ResponseEntity<?>updateContent(@PathVariable Integer contentid, @RequestBody @Valid GeneratedContent content){
        generatedContentService.updateContent(contentid, content);
        return ResponseEntity.ok(new ApiResponse("content updated successfully"));
    }

    @DeleteMapping("/delete/{contentid}")
    public ResponseEntity<?>deleteContent(@PathVariable Integer contentid){
        generatedContentService.deleteContent(contentid);
        return ResponseEntity.ok(new ApiResponse("content deleted successfully"));
    }

    @PostMapping("/generate-new/{campaignid}")
    public ResponseEntity<?>generateNewContent(@PathVariable Integer campaignid){
        generatedContentService.generateNewContent(campaignid);
        return ResponseEntity.ok(new ApiResponse("content generated successfully"));
    }

    @PostMapping("/summarize/{contentid}")
    public ResponseEntity<?>summarizeContent(@PathVariable Integer contentid){
        generatedContentService.summarizeContent(contentid);
        return ResponseEntity.ok(new ApiResponse("content summarized successfully"));
    }

    @PostMapping("/translate/{contentid}")
    public ResponseEntity<?> trendGeneratingContent(@PathVariable Integer contentid, @RequestBody InputDTO language){
        generatedContentService.translateContent(contentid, language);
        return ResponseEntity.ok(new ApiResponse("content translated successfully"));
    }

    @PutMapping("/approve/{contentid}/{campaignid}")
    public ResponseEntity<?>approveContent(@PathVariable Integer contentid,@PathVariable Integer campaignid){
        generatedContentService.approveContent(contentid, campaignid);
        return ResponseEntity.ok(new ApiResponse("content approved successfully"));
    }

    @PutMapping("/reject/{contentid}")
    public ResponseEntity<?>rejectContent(@PathVariable Integer contentid){
        generatedContentService.rejectContent(contentid);
        return ResponseEntity.ok(new ApiResponse("content has been rejected and will be deleted after 3 days"));
    }

    @PostMapping("/trend/{campaignid}")
    public ResponseEntity<?> languageByNameGeneratingContent(@PathVariable Integer campaignid){
        generatedContentService.contentLanguageBasedOnUserName(campaignid);
        return ResponseEntity.ok(new ApiResponse("content generated successfully"));
    }

    @PostMapping("/trend/{campaignid}")
    public ResponseEntity<?> trendGeneratingContent(@PathVariable Integer campaignid){
        generatedContentService.trendGenerateContent(campaignid);
        return ResponseEntity.ok(new ApiResponse("content generated successfully"));
    }

    @GetMapping("/evaluate/{contentid}/{campaignid}")
    public ResponseEntity<?>evaluateContent(@PathVariable Integer contentid,@PathVariable Integer campaignid){
        return ResponseEntity.ok(generatedContentService.evaluate(contentid,campaignid));
    }

    @GetMapping("/check-culture/{contentid}")
    public ResponseEntity<?>checkContentCulture(@PathVariable Integer contentid,@RequestBody InputDTO culture){
        return ResponseEntity.ok(generatedContentService.checkCulture(contentid,culture));
    }

    @GetMapping("/engagement-score/{content_id}")
    public ResponseEntity<?> engagementScore(@PathVariable Integer content_id){
        return ResponseEntity.status(200).body(generatedContentService.calculateEngagement(content_id));
    }

    @GetMapping("/evaluate-content/{content_id}")
    public ResponseEntity<?> evaluateContent(@PathVariable Integer content_id){
        return ResponseEntity.ok(generatedContentService.evaluateContent(content_id));
    }

    @GetMapping("/compair-first/{first_id}/to-second/{second}")
    public ResponseEntity<?> compairContent(@PathVariable Integer first_id, @PathVariable Integer second_id){
        return ResponseEntity.ok(generatedContentService.compairContent(first_id,second_id));
    }

    @GetMapping("/status/draft")
    public ResponseEntity<?>statusDraft(){
        return ResponseEntity.status(200).body(generatedContentService.getDraftContent());
    }
    @GetMapping("/status/approved")
    public ResponseEntity<?>statusApproved(){
        return ResponseEntity.status(200).body(generatedContentService.getApprovedContent());
    }
    @GetMapping("/status/rejected")
    public ResponseEntity<?>statusRejected(){
        return ResponseEntity.status(200).body(generatedContentService.getRejectedContent());
    }
}
