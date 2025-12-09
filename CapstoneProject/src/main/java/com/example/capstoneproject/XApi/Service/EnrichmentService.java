package com.example.capstoneproject.XApi.Service;


import com.example.capstoneproject.XApi.DTOs.EnrichedUser;
import com.example.capstoneproject.XApi.DTOs.XUserDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnrichmentService {

    public EnrichedUser enrich(XUserDTO user) {
        EnrichedUser eu = new EnrichedUser();

        eu.setId(user.getId());
        eu.setName(user.getName());
        eu.setUsername(user.getUsername());

        String bio = Optional.ofNullable(user.getDescription()).orElse("").toLowerCase();
        String location = Optional.ofNullable(user.getLocation()).orElse("");

        eu.setGender(inferGender(user.getName()));
        eu.setAgeGroup(inferAgeGroup(bio));
        eu.setCountry(inferCountry(location));
        eu.setCity(inferCity(location));
        eu.setInterests(inferInterests(bio));

        return eu;
    }

    private String inferGender(String name) {
        if (name == null) return "unknown";
        String firstName = name.split("\\s+")[0].toLowerCase();

        // simple examples; you can expand with Arabic names, etc.
        Set<String> maleNames = Set.of("ammar", "yousef", "mohammed", "ahmed", "ali");
        Set<String> femaleNames = Set.of("sarah", "murooj", "areej", "raneem", "fatimah");

        if (maleNames.contains(firstName)) return "male";
        if (femaleNames.contains(firstName)) return "female";
        return "unknown";
    }

    private String inferAgeGroup(String bio) {
        if (bio.contains("student") || bio.contains("undergrad")) {
            return "18-24";
        }
        if (bio.contains("engineer") || bio.contains("developer") || bio.contains("manager")) {
            return "25-34";
        }
        if (bio.contains("10+ years") || bio.contains("father") || bio.contains("mother")) {
            return "35plus";
        }
        return "unknown";
    }

    private String inferCountry(String location) {
        String lower = location.toLowerCase();
        if (lower.contains("ksa") || lower.contains("saudi")) return "Saudi Arabia";
        if (lower.contains("riyadh")) return "Saudi Arabia";
        if (lower.contains("jeddah")) return "Saudi Arabia";
        if (lower.contains("uae") || lower.contains("dubai") || lower.contains("abu dhabi")) return "United Arab Emirates";
        // add more rules as needed
        return "Unknown";
    }

    private String inferCity(String location) {
        String lower = location.toLowerCase();
        if (lower.contains("riyadh")) return "Riyadh";
        if (lower.contains("jeddah")) return "Jeddah";
        if (lower.contains("dubai")) return "Dubai";
        return "Unknown";
    }

    private List<String> inferInterests(String bio) {
        List<String> interests = new ArrayList<>();

        if (containsAny(bio, "spring", "java", "backend", "developer", "software", "coding")) {
            interests.add("Programming");
        }
        if (containsAny(bio, "football", "soccer", "padel", "gym", "fitness", "workout")) {
            interests.add("Sports");
        }
        if (containsAny(bio, "ps5", "xbox", "gamer", "gaming", "fortnite", "valorant", "lol")) {
            interests.add("Gaming");
        }
        if (containsAny(bio, "startup", "entrepreneur", "founder", "ceo", "marketing", "business")) {
            interests.add("Business");
        }
        if (containsAny(bio, "bitcoin", "crypto", "blockchain", "defi")) {
            interests.add("Crypto");
        }

        if (interests.isEmpty()) {
            interests.add("Other");
        }

        return interests;
    }

    private boolean containsAny(String text, String... keywords) {
        return Arrays.stream(keywords).anyMatch(text::contains);
    }
}
