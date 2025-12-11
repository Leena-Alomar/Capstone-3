package com.example.capstoneproject.Apify.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "apify")
public class ApifyProperties {
    private String baseUrl;
    private String token;
    private String actorId;

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getActorId() { return actorId; }
    public void setActorId(String actorId) { this.actorId = actorId; }
}
