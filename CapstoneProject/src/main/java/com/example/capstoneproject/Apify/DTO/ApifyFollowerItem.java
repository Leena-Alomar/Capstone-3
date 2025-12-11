package com.example.capstoneproject.Apify.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApifyFollowerItem {
    private String id;
    private String username;
    private String name;
    private String description; // bio
    private String location;

}
