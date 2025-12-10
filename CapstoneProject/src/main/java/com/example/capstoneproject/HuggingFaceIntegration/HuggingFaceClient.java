package com.example.capstoneproject.HuggingFaceIntegration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HuggingFaceClient {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${}")
    private String apiKey;

    @Value("${huggingface.model.name}")
    private String modelName;

    private final String API_URL = "https://router.huggingface.co/v1/chat/completions";

    public String generateText(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        String body = """
            {
              "model": "%s",
              "messages": [
                { "role": "user", "content": %s }
              ],
              "max_tokens": 512
            }
            """.formatted(modelName, toJsonString(prompt));

        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response.getBody();
    }

    private String toJsonString(String text) {
        return "\"" + text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                + "\"";
    }


}
