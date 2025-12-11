package com.example.capstoneproject.HuggingFaceIntegration;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.DTO.EvaluationDTO;
import com.example.capstoneproject.Model.GeneratedContent;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class AIResponseParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public GeneratedContent generateContent(String content) {

        GeneratedContent newContent = new GeneratedContent();

        try {
            JsonNode root = mapper.readTree(content);
            JsonNode jsonContent;


            if (root.has("choices")) {
                String text = root.get("choices")
                        .get(0)
                        .get("message")
                        .get("content")
                        .asText();

                jsonContent = mapper.readTree(text);
            }

            else if (root.isObject()) {
                jsonContent = root;
            }
            else {
                throw new ApiException("AI response format not recognized");
            }


            newContent.setTitle(jsonContent.has("title") ? jsonContent.get("title").asText() : null);
            newContent.setContent(jsonContent.has("content") ? jsonContent.get("content").asText() : null);
            newContent.setType(jsonContent.has("type") ? jsonContent.get("type").asText() : null);

            return newContent;

        } catch (Exception e) {
            throw new ApiException("Error parsing AI response: " + e.getMessage());
        }
    }

    public GeneratedContent generatedContentOpenAi(String content) {

        GeneratedContent newContent = new GeneratedContent();

        try {
            JsonNode root = mapper.readTree(content);
            JsonNode jsonContent;


            if (root.has("output")) {
                JsonNode output = root.get("output");

                String text = output.get(0)
                        .get("content")
                        .get(0)
                        .get("text")
                        .asText();

                String clean = text
                        .replace("```json", "")
                        .replace("```", "")
                        .trim();

                jsonContent = mapper.readTree(clean);
            }


            else if (root.has("choices")) {
                String text = root.get("choices")
                        .get(0)
                        .get("message")
                        .get("content")
                        .asText();

                jsonContent = mapper.readTree(text);
            }


            else if (root.isObject()) {
                jsonContent = root;
            }

            else {
                throw new ApiException("AI response format not recognized");
            }


            newContent.setTitle(jsonContent.has("title") ? jsonContent.get("title").asText() : null);
            newContent.setContent(jsonContent.has("content") ? jsonContent.get("content").asText() : null);
            newContent.setType(jsonContent.has("type") ? jsonContent.get("type").asText() : null);

            return newContent;

        } catch (Exception e) {
            throw new ApiException("Error parsing AI response: " + e.getMessage());
        }
    }

    public EvaluationDTO evaluation(String evaluated) {

        EvaluationDTO evaluationDTO = new EvaluationDTO();

        try {
            JsonNode root = mapper.readTree(evaluated);
            JsonNode jsonContent;


            if (root.has("output")) {
                JsonNode output = root.get("output");

                String text = output.get(0)
                        .get("content")
                        .get(0)
                        .get("text")
                        .asText();


                String clean = text
                        .replace("```json", "")
                        .replace("```", "")
                        .trim();

                jsonContent = mapper.readTree(clean);
            }


            else if (root.has("choices")) {
                String text = root.get("choices")
                        .get(0)
                        .get("message")
                        .get("content")
                        .asText();

                jsonContent = mapper.readTree(text);
            }


            else if (root.isObject()) {
                jsonContent = root;
            }

            else {
                throw new ApiException("AI response format not recognized");
            }


            evaluationDTO.setSuitability(jsonContent.has("suitability") ? jsonContent.get("suitability").asText() : null);
            evaluationDTO.setReason(jsonContent.has("reason") ? jsonContent.get("reason").asText() : null);
            evaluationDTO.setSuggestions(jsonContent.has("suggestions") ? jsonContent.get("suggestions").asText() : null);

            return evaluationDTO;

        } catch (Exception e) {
            throw new ApiException("Error parsing AI response: " + e.getMessage());
        }
    }

}
