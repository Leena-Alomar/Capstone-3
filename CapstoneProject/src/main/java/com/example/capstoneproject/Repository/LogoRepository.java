package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Logo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoRepository extends JpaRepository<Logo,Integer> {

    @Query("select l from Logo l where l.id =:id")
    Logo findLogoById(Integer id);
}
