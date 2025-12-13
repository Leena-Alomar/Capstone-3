package com.example.capstoneproject.AI.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.Logo;
import com.example.capstoneproject.Model.User;
import com.example.capstoneproject.Repository.LogoRepository;
import com.example.capstoneproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
@RequiredArgsConstructor
public class AiLogoService {

    private final WebClient stabilityWebClient;
    public final UserRepository userRepository;



    public byte[] generateImageTest(Integer id,String prompt) {
        User u = userRepository.findUserById(id);
        if (u == null) {
            throw new ApiException("User not found");
        }
        if (!u.getSubscription()){
            throw new ApiException("this feature is enabled with subscription only");
        }
        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("Prompt must not be empty");
        }

        // multipart/form-data body
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("prompt", prompt);
        form.add("output_format", "jpeg");   // png or jpeg
        form.add("aspect_ratio", "1:1");    // e.g. 1:1, 16:9, 9:16
        // some examples add a dummy field, but it's optional:
        // form.add("none", "");

        try {
            return stabilityWebClient.post()
                    .uri("/v2beta/stable-image/generate/ultra")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    // IMPORTANT: Stability expects image/* or application/json
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
                    .block(); // OK for your synchronous controller

        } catch (WebClientResponseException e) {
            // This prints the real Stability error (which helped us before)
            System.out.println("=== Stability API ERROR ===");
            System.out.println("Status: " + e.getStatusCode());
            System.out.println("Body:   " + e.getResponseBodyAsString());
            throw e;
        }
    }
}
