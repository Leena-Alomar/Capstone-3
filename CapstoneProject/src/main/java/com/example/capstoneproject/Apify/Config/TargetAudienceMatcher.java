package com.example.capstoneproject.Apify.Config;

import com.example.capstoneproject.Apify.DTO.EnrichedUser;
import com.example.capstoneproject.Apify.DTO.TargetAudienceCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TargetAudienceMatcher {

    public boolean matches(EnrichedUser user, TargetAudienceCriteria c) {
        if (c.getCountries() != null && !c.getCountries().isEmpty()) {
            if (user.getCountry() == null || !c.getCountries().contains(user.getCountry())) {
                return false;
            }
        }
        if (c.getGenders() != null && !c.getGenders().isEmpty()) {
            if (user.getGender() == null || !c.getGenders().contains(user.getGender())) {
                return false;
            }
        }
        if (c.getAgeGroups() != null && !c.getAgeGroups().isEmpty()) {
            if (user.getAgeGroup() == null || !c.getAgeGroups().contains(user.getAgeGroup())) {
                return false;
            }
        }
        if (c.getInterests() != null && !c.getInterests().isEmpty()) {
            List<String> interests = user.getInterests();
            if (interests == null || interests.stream().noneMatch(c.getInterests()::contains)) {
                return false;
            }
        }
        return true;
    }
}
