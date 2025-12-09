package com.example.capstoneproject.Controller;

import com.example.capstoneproject.AI.Service.AiLogoService;
import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.Model.Logo;
import com.example.capstoneproject.Service.LogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/logo")
@RequiredArgsConstructor
public class LogoController
{
    private final LogoService logoService;
    private final AiLogoService aiLogoService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllLogos(){
        return ResponseEntity.ok(logoService.getAllLogos());
    }

    @PostMapping("/add/{project_id}")
    public ResponseEntity<?> addLogo(@PathVariable Integer project_id, @RequestParam("logo") MultipartFile logo) throws IOException {
        logoService.addLogo(project_id,logo);
        return ResponseEntity.status(200).body(new ApiResponse("logo added successfully"));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showLogo(@PathVariable Integer id){
        Logo l = logoService.getLogoImage(id);

        return ResponseEntity.status(200)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + l.getName() + "\"")
                .contentType(MediaType.parseMediaType(l.getType()))
                .body(l.getData());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLogo(@PathVariable Integer id){
        logoService.deleteLogoImage(id);
        return ResponseEntity.status(200).body(new ApiResponse("logo deleted successfully"));
    }

    @PostMapping("/generate-id/{project_id}")
    public ResponseEntity<?> generateLogo(@PathVariable Integer project_id,@RequestBody String description ) {
        byte[] logo = aiLogoService.generateImageTest(description);
        logoService.save(project_id,logo);
        return ResponseEntity.status(200)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"ai-logo.png\"")  // 👈 view in browser instead of download
                .contentType(MediaType.IMAGE_PNG)
                .body(logo);
    }

    @PostMapping("/test")
    public ResponseEntity<?> generateLogoTest(@RequestBody String description ) {
        return ResponseEntity.status(200).body(aiLogoService.generateImageTest(description));
    }
}
