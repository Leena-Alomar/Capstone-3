package com.example.capstoneproject.Controller;

import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.DTO.ProjectDTO;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/ask/{id}")
    public ResponseEntity<?> askAI(@PathVariable Integer id){
        String answer = projectService.feasibilityStudy(id);
        return ResponseEntity.status(200).body(answer);

    }

    @GetMapping("/get")
    public ResponseEntity<?> getProjects(){
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping("/add/{userid}")
    public ResponseEntity<?>addProjects(@PathVariable Integer userid, @RequestBody @Valid ProjectDTO project){
        projectService.addProject(userid, project);
        return ResponseEntity.ok(new ApiResponse("project added successfully"));
    }

    @PutMapping("/update/{projectid}")
    public ResponseEntity<?>updateProjects(@PathVariable Integer projectid, @RequestBody @Valid Project project){
        projectService.updateProject(projectid, project);
        return ResponseEntity.ok(new ApiResponse("project updated successfully"));
    }

    @DeleteMapping("/delete/{project_id}")
    public ResponseEntity<?>deleteProjects(@PathVariable Integer project_id){
        projectService.deleteProject(project_id);
        return ResponseEntity.ok(new ApiResponse("project deleted successfully"));
    }

}
