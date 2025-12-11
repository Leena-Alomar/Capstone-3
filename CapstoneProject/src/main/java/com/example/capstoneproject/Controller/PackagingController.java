package com.example.capstoneproject.Controller;

import com.example.capstoneproject.AI.Service.PackagingAIService;
import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.DTO.PackagingDTO;
import com.example.capstoneproject.Model.Packaging;
import com.example.capstoneproject.Service.PackagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/packaging")
public class PackagingController {

    private final PackagingService packagingService;
    private final PackagingAIService packagingAIService;

    @GetMapping("/get/package/{id}")
    public ResponseEntity<?> getPackaging(@PathVariable Integer id){
        Packaging packaging = packagingService.getPackageByID(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(packaging.getType())).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"package.png\"").body(packaging.getData());
    }



    @GetMapping("/get")
    public ResponseEntity<?> getPackagings(){
        return ResponseEntity.status(200).body(packagingService.findAllPackaging());
    }


    @PostMapping("/generate-id/{project_id}")
    public ResponseEntity<?> generatePackage(@PathVariable Integer project_id,@RequestBody PackagingDTO packagingDTO ) {
        String prompt=String.format(  "Generate a realistic %s packaging with %s color palette, dimensions %s. Description: %s",
                packagingDTO.getDescription(),
                packagingDTO.getColorPalette(),
                packagingDTO.getMockupType(),
                packagingDTO.getDimensions());

        byte[] packageImage =packagingAIService.generateImageTest(prompt);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"ai-package.png\"").body(packageImage);
    }

    @PostMapping("/view")
    public ResponseEntity<byte[]> generatePackageTest(@RequestBody String description ) {
        byte[] image = packagingAIService.generateImageTest(description);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"ai-image.jpeg\"").body(image);
    }

    @PutMapping("/update/package/{project_id}/{id}")
    public ResponseEntity<?> updatePackage(@PathVariable Integer project_id, @PathVariable Integer id,@RequestBody PackagingDTO packagingDTO) throws IOException {
        byte[] image = packagingAIService.generateImageTest(
                String.format(
                        "Generate a realistic %s packaging with %s color palette, dimensions %s. Description: %s",
                        packagingDTO.getDescription(),
                        packagingDTO.getColorPalette(),
                        packagingDTO.getMockupType(),
                        packagingDTO.getDimensions()
                )
        );

        packagingService.updatePackaging(project_id,id,image,packagingDTO);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"ai-package.png\"").body(image);
    }

    @DeleteMapping ("delete/{id}")
    public ResponseEntity<?> deletePackaging(@PathVariable Integer id){
        packagingService.deletePackaging(id);
        return ResponseEntity.status(200).body(new ApiResponse("the Packaging is deleted"));
    }


}
