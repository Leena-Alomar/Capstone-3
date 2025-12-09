package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Project findProjectById(Integer id);

    @Query("select p from Project p join p.logo l where l.id = :id")
    Project findProjectByLogoId(Integer id);
}
