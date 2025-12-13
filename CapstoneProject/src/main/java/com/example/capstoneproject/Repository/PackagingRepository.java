package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Packaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PackagingRepository extends JpaRepository<Packaging,Integer> {
    @Query("select p from Packaging p where p.id=:id")
    Packaging findPackagingById(Integer id);
}
