package com.example.capstoneproject.XApi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudienceSummaryDto {
    private String handle;
    private int totalSampled;
    private Map<String, Long> genderDistribution;
    private Map<String, Long> ageDistribution;
    private Map<String, Long> countries;
    private Map<String, Long> interests;

    // getters & setters
}
