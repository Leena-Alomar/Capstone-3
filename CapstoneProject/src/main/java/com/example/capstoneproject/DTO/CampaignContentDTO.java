package com.example.capstoneproject.DTO;

import lombok.Data;

@Data
public class CampaignContentDTO {

    private String projectName;
    private String projectDescription;
    private String projectType;

    private String campaignName;
    private String campaignDescription;

    private String platform;
    private String goal;

    private Integer targetAudienceMinAge;
    private Integer targetAudienceMaxAge;

    private String targetAudienceGender;
    private String targetInterest;
    private String targetAudienceLocation;
    private String targetAudienceIncomeLevel;
}
