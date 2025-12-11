package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    Campaign findCampaignById(Integer id);

    @Query("select c from Campaign c join c.project p where p.id =:id order by p.id desc ")
    Campaign findCampaignByProjectId(@Param("id") Integer id);

    List<Campaign> findCampaignByStatus(String status);

}
