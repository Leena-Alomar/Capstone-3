package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.GeneratedContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratedContentRepository extends JpaRepository<GeneratedContent,Integer> {
    GeneratedContent findGeneratedContentById(Integer id);
}
