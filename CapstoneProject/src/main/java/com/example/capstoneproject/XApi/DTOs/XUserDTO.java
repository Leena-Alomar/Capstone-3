package com.example.capstoneproject.XApi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class XUserDTO
{
    private String id;
    private String name;
    private String username;
    private String location;
    private String description;
    private Integer max_age;
    private Integer min_age;
}
