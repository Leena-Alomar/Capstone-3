package com.example.capstoneproject.Apify.Client;


import com.example.capstoneproject.Apify.Config.ApifyProperties;
import com.example.capstoneproject.Apify.DTO.ApifyFollowerItem;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;


import java.util.List;
import java.util.Map;

@Service
public class ApifyClient {

    private final WebClient webClient;
    private final ApifyProperties props;

    public ApifyClient(WebClient apifyWebClient, ApifyProperties props) {
        this.webClient = apifyWebClient;
        this.props = props;
    }

    public Mono<List<ApifyFollowerItem>> getListFollowers(String listId, int maxFollowers) {

        Map<String, Object> input = Map.of(
                "mode", "listFollowers",   // حسب ما يطلبه الـ actor
                "listId", listId,
                "maxFollowers", maxFollowers
        );

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/acts/{actorId}/runs")
                        .queryParam("token", props.getToken())
                        .queryParam("waitForFinish", 60)   // ننتظر لين يخلص
                        .build(props.getActorId()))        // مثال: "ammar-y~x-list-followers-scraper"
                .bodyValue(Map.of("input", input))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(json -> {
                    JsonNode dataNode = json.path("data");
                    String datasetId = dataNode.path("defaultDatasetId").asText(null);

                    if (datasetId == null || datasetId.isBlank()) {
                        return Mono.error(new IllegalStateException(
                                "Apify response does not contain defaultDatasetId. Response: " + json.toString()
                        ));
                    }

                    return getDatasetItems(datasetId);
                });
    }

    private Mono<List<ApifyFollowerItem>> getDatasetItems(String datasetId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/datasets/{datasetId}/items")
                        .queryParam("token", props.getToken())
                        .build(datasetId))
                .retrieve()
                .bodyToFlux(ApifyFollowerItem.class)
                .collectList();
    }
}