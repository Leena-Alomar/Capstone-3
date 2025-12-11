package com.example.capstoneproject.Controller;

import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.Model.Category;
import com.example.capstoneproject.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController
{
    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCategories()
    {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category)
    {
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("category added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody @Valid Category category){
        categoryService.updateCategory(id, category);
        return ResponseEntity.status(200).body(new ApiResponse("category updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return ResponseEntity.status(200).body(new ApiResponse("category deleted successfully"));
    }
}
