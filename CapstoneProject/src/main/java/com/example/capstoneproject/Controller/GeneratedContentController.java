package com.example.capstoneproject.Controller;

import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.Model.GeneratedContent;
import com.example.capstoneproject.Repository.GeneratedContentRepository;
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

    @PostMapping("/translate/{contentid}/{language}")
    public ResponseEntity<?>translateContent(@PathVariable Integer contentid,@PathVariable String language){
        generatedContentService.translateContent(contentid, language);
        return ResponseEntity.ok(new ApiResponse("content translated successfully"));
    }

    @PutMapping("/approve/{contentid}/{campaignid}")
    public ResponseEntity<?>approveContent(@PathVariable Integer contentid,@PathVariable Integer campaignid){
        generatedContentService.approveContent(contentid, campaignid);
        return ResponseEntity.ok(new ApiResponse("content approved successfully"));
    }

    @PutMapping("/reject/{contentid}/")
    public ResponseEntity<?>rejectContent(@PathVariable Integer contentid){
        generatedContentService.rejectContent(contentid);
        return ResponseEntity.ok(new ApiResponse("content will be deleted in 3 days"));
    }

}
