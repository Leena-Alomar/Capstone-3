package com.example.capstoneproject.Apify.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Apify.Client.ApifyClient;
import com.example.capstoneproject.Apify.DTO.ListFollowersCountDto;
import com.example.capstoneproject.Model.Campaign;
import com.example.capstoneproject.Model.Project;
import com.example.capstoneproject.Repository.CampaignRepository;
import com.example.capstoneproject.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class ListAudienceService {

    private final ApifyClient apifyClient;
    private final ProjectRepository projectRepository;
    private final EnrichmentService enrichmentService;
    private final CampaignRepository campaignRepository;


    public Mono<ListFollowersCountDto> getListFollowersCount(Integer id, int maxFollowers) {
        Project p = projectRepository.findProjectById(id);
        Campaign c = campaignRepository.findCampaignByProjectId(id);
        if (p == null) {
            throw new ApiException("Project not found");
        }
        if (p.getCategory() == null) {
            throw new ApiException("this project does not have a category");
        }

        String listId = p.getCategory().getList_id();

        return apifyClient.getListFollowers(listId, maxFollowers)
                .map(list -> {
                    ListFollowersCountDto dto = new ListFollowersCountDto();
                    dto.setInterset(p.getCategory().getName());
                    dto.setMin_age(c.getTargetAudience().getMinAge());
                    dto.setMax_age(c.getTargetAudience().getMaxAge());
                    dto.setCount((long) list.size());
                    return dto;
                });
    }
}
