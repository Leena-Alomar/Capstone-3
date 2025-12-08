package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Campagin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaginRepository extends JpaRepository<Campagin, Integer> {

    Campagin findCampaginById(Integer id);
}
