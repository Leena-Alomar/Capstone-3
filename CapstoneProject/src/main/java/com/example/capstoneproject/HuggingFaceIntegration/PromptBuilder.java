package com.example.capstoneproject.HuggingFaceIntegration;

import com.example.capstoneproject.DTO.CampaignContentDTO;
import com.example.capstoneproject.Model.GeneratedContent;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String generateNewContent(CampaignContentDTO dto) {

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

    public String summarizeContent(GeneratedContent content) {

        return """
        You are a content creator working for this project campaign.

        You created a social media content for a project campaign, you are now required to summarize it.
        Generate a JSON object for the summarized social media content according to the previous:

            Title: %s
            Content: %s
            Type: %s

        Requirements:
        - Return ONLY valid JSON.
        - JSON format:
          {"title":"...(summarized)","content":"...","type":"..."}

        - Title must be the same with (summarized) included.
        - Content must reflect the old content project campaign.
        - Type must be one of: Post, Caption, Script.

        Produce ONLY the JSON. No extra text.
        """.formatted(
                content.getTitle(),
                content.getContent(),
                content.getType()
        );
    }

    public String translateContent(GeneratedContent content, String language) {

        return """
        You are a content creator working for this project campaign.

        You created a social media content for a project campaign, you are now required to translate it to: %s.
        Generate a JSON object for a the summarized social media content according to the previous:

            Title: %s
            Content: %s
            Type: %s

        Requirements:
        - Return ONLY valid JSON.
        - JSON format:
          {"title":"...","content":"...","type":"..."}

        - Title must be short and simple (max 4 words).
        - Content must reflect the old content project campaign and fit the social media platform.
        - Type must be one of: Post, Caption, Script (not translated).

        Produce ONLY the JSON. No extra text.
        """.formatted(
                language,
                content.getTitle(),
                content.getContent(),
                content.getType()
        );
    }

}
