package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Campaign;
import com.example.capstoneproject.Model.GeneratedContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneratedContentRepository extends JpaRepository<GeneratedContent,Integer> {
    GeneratedContent findGeneratedContentById(Integer id);

    List<GeneratedContent> findGeneratedContentByCampaign(Campaign campaign);

    List<GeneratedContent> findGeneratedContentByStatus(String status);

    @Query("select c from GeneratedContent c where c.status = :status")
    List<GeneratedContent> findContentByStatus(String status);
}


