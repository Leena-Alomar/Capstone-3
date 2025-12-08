package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.TargetAduince;
import com.example.capstoneproject.Model.User;
import com.example.capstoneproject.Repository.TargetAduinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TargetAduinceService {

    private final TargetAduinceRepository targetAduinceRepository;

    public List<TargetAduince> findAllUser(){
        return targetAduinceRepository.findAll();
    }

    public void addTargetAduince(TargetAduince targetAduince){
        targetAduinceRepository.save(targetAduince);
    }

    public void updateTargetAduince(Integer id , TargetAduince targetAduince){
        TargetAduince oldTargetAduince=targetAduinceRepository.findTargetAduinceById(id);

        if(oldTargetAduince==null){
            throw new ApiException("Target Aduince is not found");
        }
        oldTargetAduince.setMinAge(targetAduince.getMinAge());
        oldTargetAduince.setMaxAge(targetAduince.getMaxAge());
        oldTargetAduince.setGender(targetAduince.getGender());
        oldTargetAduince.setInterset(targetAduince.getInterset());
        oldTargetAduince.setLocation(targetAduince.getLocation());
        oldTargetAduince.setIncomeLevel(targetAduince.getIncomeLevel());
        targetAduinceRepository.save(oldTargetAduince);
    }

    public void deleteTargetAduince(Integer id){
        TargetAduince targetAduince=targetAduinceRepository.findTargetAduinceById(id);
        if(targetAduince==null){
            throw new ApiException("Target Aduince is not found");
        }
        targetAduinceRepository.delete(targetAduince);
    }
}
