package com.example.capstoneproject.XApi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrichedUser {
    private String id;
    private String name;
    private String username;
    private String gender;
    private String ageGroup;
    private String country;
    private String city;
    private List<String> interests;

    // getters & setters
}
