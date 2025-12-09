package com.example.capstoneproject.XApi.Service;

import com.example.capstoneproject.XApi.WebClients.XClient;
import com.example.capstoneproject.XApi.DTOs.AudienceSummaryDto;
import com.example.capstoneproject.XApi.DTOs.EnrichedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AudienceService {

    private final XClient xClient;
    private final EnrichmentService enrichmentService;



    public Mono<AudienceSummaryDto> analyzeHandle(String handle, int limit) {
        return xClient.getUserByHandle(handle)
                .flatMap(user -> xClient.getFollowersSample(user.getId(), limit))
                .map(followers -> {
                    List<EnrichedUser> enriched = followers.stream()
                            .map(enrichmentService::enrich)
                            .collect(Collectors.toList());

                    AudienceSummaryDto dto = new AudienceSummaryDto();
                    dto.setHandle(handle);
                    dto.setTotalSampled(enriched.size());
                    dto.setGenderDistribution(countBy(enriched, EnrichedUser::getGender));
                    dto.setAgeDistribution(countBy(enriched, EnrichedUser::getAgeGroup));
                    dto.setCountries(countBy(enriched, EnrichedUser::getCountry));
                    dto.setInterests(countInterests(enriched));

                    return dto;
                });
    }

    private Map<String, Long> countBy(List<EnrichedUser> list, Function<EnrichedUser, String> classifier) {
        return list.stream()
                .collect(Collectors.groupingBy(classifier, Collectors.counting()));
    }

    private Map<String, Long> countInterests(List<EnrichedUser> list) {
        return list.stream()
                .flatMap(u -> u.getInterests().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    }
}
