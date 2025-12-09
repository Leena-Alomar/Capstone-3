package com.example.capstoneproject.HuggingFaceIntegration;

import com.example.capstoneproject.DTO.CampaignContentDTO;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildPrompt(CampaignContentDTO dto) {

        String minAge = dto.getTargetAudienceMinAge() == null ? "" : dto.getTargetAudienceMinAge().toString();
        String maxAge = dto.getTargetAudienceMaxAge() == null ? "" : dto.getTargetAudienceMaxAge().toString();

        return """
        You are a content creator working for this project campaign.

        Generate a JSON object for a social media content for a project campaign according to the following details:

            Project Name: %s
            Project Description: %s
            Project Type: %s
            Campaign Name: %s
            Campaign Description: %s
            Platform: %s
            Goal: %s
            Target Audience Minimum Age: %s
            Target Audience Maximum Age: %s
            Target Audience Gender: %s
            Target Interest: %s
            Target Audience Location: %s
            Target Audience IncomeLevel: %s

        Requirements:
        - Return ONLY valid JSON.
        - JSON format:
          {"title":"...","content":"...","type":"..."}

        - Title must be short and simple (max 4 words).
        - Content must reflect the project campaign and fit the social media platform.
        - Type must be one of: Post, Caption, Script.

        Produce ONLY the JSON. No extra text.
        """.formatted(
                dto.getProjectName(),
                dto.getProjectDescription(),
                dto.getProjectType(),
                dto.getCampaignName() == null ? "" : dto.getCampaignName(),
                dto.getCampaignDescription(),
                dto.getPlatform(),
                dto.getGoal(),
                minAge,
                maxAge,
                dto.getTargetAudienceGender(),
                dto.getTargetInterest(),
                dto.getTargetAudienceLocation(),
                dto.getTargetAudienceIncomeLevel()
        );
    }

}
