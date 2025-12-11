package com.example.capstoneproject.Service;

import com.example.capstoneproject.DTO.CampaignContentDTO;
import com.example.capstoneproject.DTO.EvaluateDTO;
import com.example.capstoneproject.HuggingFaceIntegration.AIResponseParser;
import com.example.capstoneproject.HuggingFaceIntegration.HuggingFaceClient;
import com.example.capstoneproject.HuggingFaceIntegration.PromptBuilder;
import com.example.capstoneproject.Model.GeneratedContent;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

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

    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/responses")
            .build();

    public String askAI(String prompt){
        Map<String, Object> message = Map.of(
                "role", "user",
                "content", prompt
        );

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4o-mini",
                "input", List.of(message)
        );

        String result = webClient.post()
                .uri("https://api.openai.com/v1/responses")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("\n\n================ RAW AI RESPONSE ================\n");
        System.out.println(result);
        System.out.println("\n=================================================\n");

        return result;
    }

    public GeneratedContent trendGenerateContent(CampaignContentDTO dto){
        String prompt = promptBuilder.trendBasedContentGeneration(dto);
        String response = askAI(prompt);
        return parser.generateContentOpenAI(response);
    }

    public EvaluateDTO evaluateContent(GeneratedContent content, CampaignContentDTO dto){
        String prompt = promptBuilder.evaluateContent(content, dto);
        String response = askAI(prompt);
        return parser.evaluateContentOpenAI(response);
    }
    public EvaluateDTO checkForCulture(GeneratedContent content, String culture){
        String prompt = promptBuilder.checkForCulture(content, culture);
        String response = askAI(prompt);
        return parser.evaluateContentOpenAI(response);
    }

}
