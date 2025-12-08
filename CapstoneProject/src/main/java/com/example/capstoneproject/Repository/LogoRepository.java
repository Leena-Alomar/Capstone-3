package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Logo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoRepository extends JpaRepository<Logo,Integer> {
    Logo findLogoById(Integer id);
}
