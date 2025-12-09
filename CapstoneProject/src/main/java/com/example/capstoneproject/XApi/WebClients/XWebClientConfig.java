package com.example.capstoneproject.XApi.WebClients;

import com.example.capstoneproject.XApi.Properties.XApiProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class XWebClientConfig {

    @Bean
    public WebClient xWebClient(XApiProperties props) {
        return WebClient.builder()
                .baseUrl(props.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + props.getBearerToken())
                .build();
    }
}
