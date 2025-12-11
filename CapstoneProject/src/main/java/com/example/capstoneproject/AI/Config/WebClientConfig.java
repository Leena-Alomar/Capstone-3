package com.example.capstoneproject.AI.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient stabilityWebClient(@Value("${stability.api.key}") String apiKey) {

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(9 * 1024 * 1024) // 9 MB
                )
                .build();

        return WebClient.builder()
                .baseUrl("https://api.stability.ai")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .exchangeStrategies(strategies)
                .build();
    }


}
