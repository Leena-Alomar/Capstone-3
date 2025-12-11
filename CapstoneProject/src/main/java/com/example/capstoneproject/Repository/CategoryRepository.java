package com.example.capstoneproject.Repository;

import com.example.capstoneproject.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>
{
    @Query("select c from Category c where c.id =:id")
    Category findCategoryById(Integer id);

    @Query("select c from Category c where c.name =:name")
    Category findCategoryByName(String name);

    @Query("select c from Category c join c.projects p where p.id = :id")
    Category findCategoryOfProjectById(@Param("id") Integer id);
}
