package com.example.capstoneproject.Service;

import com.example.capstoneproject.AI.Service.AiLogoService;
import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.Logo;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Model.User;
import com.example.capstoneproject.Repository.LogoRepository;
import com.example.capstoneproject.Repository.ProjectRepository;
import com.example.capstoneproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LogoService
{
    private final LogoRepository logoRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    public List<Logo> getAllLogos() {
        List<Logo> logos = logoRepository.findAll();
        if (logos.isEmpty()) {
            throw new ApiException("No logos found");
        }
        return logos;
    }

    public void save(Integer user_id,Integer id, byte[] logo) {
        Project project = projectRepository.findProjectById(id);
        if (!Objects.equals(project.getUser().getId(), user_id)) {
            throw new ApiException("this project doesnt belong to this user");
        }
        if(project == null){
            throw new ApiException("Project not found");
        }
        Logo l = new Logo();
        l.setName(project.getName()+"_logo");
        l.setType("image/jpeg");
        l.setData(logo);
        l.setProject(project);
        logoRepository.save(l);
    }

    public void addLogo(Integer project_id,MultipartFile file) throws ApiException, IOException {
        Project p = projectRepository.findProjectById(project_id);
        if (p == null) {
            throw new ApiException("Project not found");
        }
        Logo logo = new Logo();
        logo.setData(file.getBytes());
        logo.setName(file.getOriginalFilename());
        logo.setType(file.getContentType());
        logo.setProject(p);
        logoRepository.save(logo);
        p.setLogo(logo);
        projectRepository.save(p);
    }

    public Logo getLogoImage(Integer id) {
        return logoRepository.findLogoById(id);
    }

    public void deleteLogoImage(Integer id) {
        Logo img = logoRepository.findLogoById(id);
        if (img == null) {
            throw new ApiException("Logo with id " + id + " not found");
        }
        Project project = projectRepository.findProjectByLogoId(img.getId());
        project.setLogo(null);
        projectRepository.save(project);
        logoRepository.delete(img);
    }
}
