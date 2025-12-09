package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Model.User;
import com.example.capstoneproject.Repository.ProjectRepository;
import com.example.capstoneproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public void addProject(Integer user_id, Project project){
        User user = userRepository.findUserById(user_id);
        if(user==null){
            throw new ApiException("user not found");
        }
        project.setUser(user);
        projectRepository.save(project);
    }

    public void updateProject(Integer project_id,Project project){
        Project oldProject = projectRepository.findProjectById(project_id);
        if(oldProject==null){
            throw new ApiException("project not found");
        }
        oldProject.setName(project.getName());
        oldProject.setDescription(project.getDescription());
        oldProject.setType(project.getType());
        projectRepository.save(oldProject);
    }

    public void deleteProject(Integer project_id){
        Project project = projectRepository.findProjectById(project_id);
        if(project==null) {
            throw new ApiException("project not found");
        }
        projectRepository.delete(project);
    }
}
