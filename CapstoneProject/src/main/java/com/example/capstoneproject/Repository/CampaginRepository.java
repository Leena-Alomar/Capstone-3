package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaginRepository extends JpaRepository<Campaign, Integer> {

    Campaign findCampaginById(Integer id);
}
