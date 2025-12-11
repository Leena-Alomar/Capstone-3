package com.example.capstoneproject.AI.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class OpenaiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .build();

    public String askAI(String prompt) {

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );


        Map<String, Object> response = webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();


        if (response != null && response.get("choices") instanceof List choicesList && !choicesList.isEmpty()) {
            Map<String, Object> choice = (Map<String, Object>) choicesList.get(0);
            if (choice.get("message") instanceof Map messageMap) {
                Object content = messageMap.get("content");
                if (content != null) {
                    return content.toString().replaceAll("\\s+\\n", "\n").trim();
                }
            }
        }

        return "No response from AI.";
    }
}
