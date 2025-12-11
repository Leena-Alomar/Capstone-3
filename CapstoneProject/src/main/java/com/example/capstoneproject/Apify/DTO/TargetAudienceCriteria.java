package com.example.capstoneproject.Apify.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetAudienceCriteria {
    private List<String> countries;
    private List<String> genders;
    private List<String> interests;
    private List<String> ageGroups;
}
