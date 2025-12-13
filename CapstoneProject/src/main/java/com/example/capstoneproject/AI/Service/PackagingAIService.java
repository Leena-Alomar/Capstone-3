package com.example.capstoneproject.AI.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class PackagingAIService {

    private final WebClient stabilityWebClient;

    public PackagingAIService(WebClient stabilityWebClient) {
        this.stabilityWebClient = stabilityWebClient;
    }

    public byte[] generateImageTest(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("Prompt must not be empty");
        }


        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("prompt", prompt);
        form.add("output_format", "jpeg");
        form.add("aspect_ratio", "1:1");
        try {
            return stabilityWebClient.post()
                    .uri("/v2beta/stable-image/generate/ultra")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.ACCEPT, "image/*")
                    .body(BodyInserters.fromMultipartData(form))
                    .retrieve()
                    .onStatus(
                            s -> s.is4xxClientError() || s.is5xxServerError(),
                            resp -> resp.bodyToMono(String.class).flatMap(body ->
                                    Mono.error(new RuntimeException("Stability error " +
                                            resp.statusCode() + ": " + body))
                            )
                    )
                    .bodyToMono(byte[].class)
                    .block();

        } catch (WebClientResponseException e) {
            System.out.println("=== Stability API ERROR ===");
            System.out.println("Status: " + e.getStatusCode());
            System.out.println("Body:   " + e.getResponseBodyAsString());
            throw e;
        }
    }
}
