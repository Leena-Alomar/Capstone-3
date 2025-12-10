package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    Campaign findCampaignById(Integer id);

    List<Campaign> findCampaignByEndDateAndStatus(LocalDate endDate, String status);

}

