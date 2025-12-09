package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.TargetAudience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetAduinceRepository extends JpaRepository<TargetAudience,Integer> {
    TargetAudience findTargetAduinceById(Integer id);
}
