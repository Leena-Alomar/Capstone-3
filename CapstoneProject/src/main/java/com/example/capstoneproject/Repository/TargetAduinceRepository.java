package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.TargetAduince;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetAduinceRepository extends JpaRepository<TargetAduince,Integer> {
    TargetAduince findTargetAduinceById(Integer id);
}
