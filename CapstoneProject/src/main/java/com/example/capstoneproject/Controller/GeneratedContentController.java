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
}
