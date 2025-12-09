package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaginRepository extends JpaRepository<campaign, Integer> {

    campaign findCampaginById(Integer id);
}
