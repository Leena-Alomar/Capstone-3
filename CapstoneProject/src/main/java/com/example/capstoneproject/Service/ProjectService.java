package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.DTO.ProjectDTO;
import com.example.capstoneproject.Model.Category;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Model.User;
import com.example.capstoneproject.Repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final com.example.capstoneproject.AI.Service.OpenaiService openaiService;

    private int count=0;

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public void addProject(Integer user_id, ProjectDTO project){
        User user = userRepository.findUserById(user_id);
        Category c = categoryRepository.findCategoryByName(project.getCategory());
        if (c == null){
            throw new ApiException("category not found");
        }
        if(user==null){
            throw new ApiException("user not found");
        }
        Project p = new Project();
        p.setName(project.getName());
        p.setUser(user);
        p.setCategory(c);
        p.setDescription(project.getDescription());
        p.setType(project.getType());
        projectRepository.save(p);
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

    public String feasibilityStudy(Integer id){
        Project project=projectRepository.findProjectById(id);
        String prompt = "You are a professional analyst. Prepare a feasibility study for the following project. " +
                "Provide clear evaluation of technical, financial, operational, and legal aspects, " +
                "highlight potential risks, benefits, and recommendations. " +
                "Project Name: " + project.getName() +
                "\nProject Description: " + project.getDescription();

        return openaiService.askAI(prompt);
    }

    public String checkFeasibiltySubsecrition(Integer id){
        Project project=projectRepository.findProjectById(id);
        if(project==null){
            throw new ApiException("the project is not found");
        }
        if (Boolean.FALSE.equals(project.getUser().getSubscription())){
            if(count>=2){
                throw new ApiException("Maximum free trials reached. Please subscribe for more");
            }
            count ++;
        }
        return feasibilityStudy(id);
    }
}
