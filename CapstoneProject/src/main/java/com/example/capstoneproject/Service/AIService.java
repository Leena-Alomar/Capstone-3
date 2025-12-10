package com.example.capstoneproject.Service;

import com.example.capstoneproject.DTO.CampaignContentDTO;
import com.example.capstoneproject.HuggingFaceIntegration.AIResponseParser;
import com.example.capstoneproject.HuggingFaceIntegration.HuggingFaceClient;
import com.example.capstoneproject.HuggingFaceIntegration.PromptBuilder;
import com.example.capstoneproject.Model.GeneratedContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {
    private final PromptBuilder promptBuilder;
    private final HuggingFaceClient huggingFaceClient;
    private final AIResponseParser parser;

    public GeneratedContent generateContent(CampaignContentDTO dto){
        String prompt = promptBuilder.generateNewContent(dto);
        String response = huggingFaceClient.generateText(prompt);
        return parser.generateContent(response);
    }

    public GeneratedContent summarizedContent(GeneratedContent content){
        String prompt = promptBuilder.summarizeContent(content);
        String response = huggingFaceClient.generateText(prompt);
        return parser.generateContent(response);
    }

    public GeneratedContent translateContent(GeneratedContent content, String language){
        String prompt = promptBuilder.translateContent(content,language);
        String response = huggingFaceClient.generateText(prompt);
        return parser.generateContent(response);
    }
}
