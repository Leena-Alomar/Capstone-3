package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    @Query("select p from Project p where p.id = :id")
    Project findProjectById(@Param("id")Integer id);

    @Query("select p from Project p join p.logo l where l.id = :id")
    Project findProjectByLogoId(Integer id);
}
