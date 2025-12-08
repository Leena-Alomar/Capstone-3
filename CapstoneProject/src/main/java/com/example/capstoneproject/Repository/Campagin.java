package com.example.capstoneproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Campagin extends JpaRepository<Campagin,Integer> {

    Campagin findCampaginById(Integer id);
}
