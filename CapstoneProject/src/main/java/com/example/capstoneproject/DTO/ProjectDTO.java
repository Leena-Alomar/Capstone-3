package com.example.capstoneproject.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO
{
    @NotEmpty(message = "name is required")
    private String name;
    @NotEmpty(message = "description is required")
    private String description;
    @NotEmpty(message = "type is required")
    private String type;
    @NotEmpty(message = "category is required")
    @Pattern(regexp = "^(?i)(sport|food|learning|beauty|safety|others)")
    private String category;
}
