package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.DTO.PackagingDTO;
import com.example.capstoneproject.Model.Packaging;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Repository.PackagingRepository;
import com.example.capstoneproject.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackagingService {

    private final PackagingRepository packagingRepository;
    private final ProjectRepository projectRepository;

    public List<Packaging> findAllPackaging(){

        return packagingRepository.findAll();
    }

    public Packaging getPackageByID(Integer id){
        Packaging packaging=packagingRepository.findPackagingById(id);
        if(packaging==null){
            throw new ApiException("Packaging not found");
        }
        return packaging;
    }

    public void savePackageInfo(Integer projectId, byte[] imageBytes, PackagingDTO packagingDTO) {
        Project project = projectRepository.findProjectById(projectId);
        Packaging packaging = new Packaging();
        if (project == null){
            throw new ApiException("Project not found");
        }

        packaging.setDescription(packagingDTO.getDescription());
        packaging.setMockupType(packaging.getMockupType());
        packaging.setColorPalette(packaging.getColorPalette());
        packaging.setDimensions(packaging.getDimensions());
        packaging.setData(imageBytes);
        packaging.setType("image/png");
        packaging.setProject(project);

        packagingRepository.save(packaging);
    }


    public void addPackaging(Integer project_id,MultipartFile multipartFile) throws IOException {
        Project project=projectRepository.findProjectById(project_id);
        Packaging packaging=new Packaging();
        if(project ==null){
            throw new ApiException("the project is not found");
        }
        packaging.setMockupType(packaging.getMockupType());
        packaging.setColorPalette(packaging.getColorPalette());
        packaging.setDimensions(packaging.getDimensions());
        packaging.setDescription(packaging.getDescription());
        packaging.setProject(project);
        packaging.setData(multipartFile.getBytes());
        packaging.setType(multipartFile.getContentType());
        projectRepository.save(project);
    }

    public void updatePackaging(Integer project_id,Integer id, byte[] imageBytes,PackagingDTO packagingDTO) throws IOException {
        Project project =projectRepository.findProjectById(project_id);
        Packaging oldPackaging=packagingRepository.findPackagingById(id);
        if(project ==null|| oldPackaging==null){
            throw new ApiException("the project or package is not found");
        }
        oldPackaging.setMockupType(packagingDTO.getMockupType());
        oldPackaging.setColorPalette(packagingDTO.getColorPalette());
        oldPackaging.setDimensions(packagingDTO.getDimensions());
        oldPackaging.setDescription(packagingDTO.getDescription());
        oldPackaging.setProject(project);
        oldPackaging.setData(imageBytes);
        oldPackaging.setType("image/png");

        packagingRepository.save(oldPackaging);
    }

    public void deletePackaging(Integer id){
        Packaging packaging=packagingRepository.findPackagingById(id);
        Project project=projectRepository.findProjectByPackagingId(packaging.getId());
        if(packaging==null || project==null){
            throw new ApiException("Packaging or project is not found");
        }
        project.setPackaging(null);
        projectRepository.save(project);
        packagingRepository.delete(packaging);
    }


}
