package com.example.capstoneproject.XApi.WebClients;


import com.example.capstoneproject.XApi.DTOs.XUserDTO;
import com.example.capstoneproject.XApi.Response.XFollowersResponse;
import com.example.capstoneproject.XApi.Response.XUserResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class XClient {

    private final WebClient xWebClient;

    public XClient(WebClient xWebClient) {
        this.xWebClient = xWebClient;
    }

    public Mono<XUserDTO> getUserByHandle(String handle) {
        return xWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/by/username/{username}")
                        .queryParam("user.fields", "location,description")
                        .build(handle))
                .retrieve()
                .bodyToMono(XUserResponse.class)
                .map(XUserResponse::getData);
    }

    public Mono<List<XUserDTO>> getFollowersSample(String userId, int limit) {
        List<XUserDTO> collected = new ArrayList<>();
        return fetchFollowersPage(userId, null, limit, collected)
                .then(Mono.just(collected));
    }

    private Mono<Void> fetchFollowersPage(String userId, String paginationToken, int limit, List<XUserDTO> collected) {
        if (collected.size() >= limit) {
            return Mono.empty();
        }

        return xWebClient.get()
                .uri(uriBuilder -> {
                    var b = uriBuilder
                            .path("/users/{id}/list_memberships")
                            .queryParam("max_results", Math.min(1000, limit)) // X basic may limit this
                            .queryParam("user.fields", "location,description");
                    if (paginationToken != null) {
                        b.queryParam("pagination_token", paginationToken);
                    }
                    return b.build(userId);
                })
                .retrieve()
                .bodyToMono(XFollowersResponse.class)
                .flatMap(resp -> {
                    if (resp.getData() != null) {
                        for (XUserDTO u : resp.getData()) {
                            if (collected.size() >= limit) break;
                            collected.add(u);
                        }
                    }
                    String next = resp.getMeta() != null ? resp.getMeta().getNext_token() : null;
                    if (next != null && collected.size() < limit) {
                        return fetchFollowersPage(userId, next, limit, collected);
                    }
                    return Mono.empty();

                });
    }
}
