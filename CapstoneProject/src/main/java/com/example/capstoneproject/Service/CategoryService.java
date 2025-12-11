package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.Category;
import com.example.capstoneproject.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories()
    {
        List<Category> c = categoryRepository.findAll();
        if (c.isEmpty())
        {
            throw new ApiException("no category found");
        }
        return c;
    }

    public void addCategory(Category category)
    {
        categoryRepository.save(category);
    }

    public void updateCategory(Integer id, Category category){
        Category c = categoryRepository.findCategoryById(id);
        if (c == null){
            throw new ApiException("category not found");
        }
        c.setName(category.getName());
        c.setList_id(category.getList_id());
        categoryRepository.save(c);
    }

    public void deleteCategory(Integer id)
    {
        Category c = categoryRepository.findCategoryById(id);
        if (c == null){
            throw new ApiException("category not found");
        }
        categoryRepository.deleteById(id);
    }
}
