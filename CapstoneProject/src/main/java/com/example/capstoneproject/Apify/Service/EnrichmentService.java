package com.example.capstoneproject.Apify.Service;

import com.example.capstoneproject.Apify.DTO.ApifyFollowerItem;
import com.example.capstoneproject.Apify.DTO.EnrichedUser;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EnrichmentService {

    // ... your previous inferGender, inferAgeGroup, inferCountry, inferCity, inferInterests ...

    public EnrichedUser enrichFromApify(ApifyFollowerItem follower) {
        EnrichedUser eu = new EnrichedUser();
        eu.setId(follower.getId());
        eu.setName(follower.getName());
        eu.setUsername(follower.getUsername());

        String bio = Optional.ofNullable(follower.getDescription()).orElse("");
        String location = Optional.ofNullable(follower.getLocation()).orElse("");

        String lowerBio = bio.toLowerCase();

        eu.setGender(inferGender(follower.getName()));
        eu.setAgeGroup(inferAgeGroup(lowerBio));
        eu.setCountry(inferCountry(location));
        eu.setCity(inferCity(location));
        eu.setInterests(inferInterests(lowerBio));

        return eu;
    }

    private String inferGender(String fullName) {
        if (fullName == null || fullName.isBlank()) return "unknown";

        String first = fullName.split("\\s+")[0].toLowerCase(Locale.ROOT);

        // You can extend these lists with Arabic names, etc.
        Set<String> maleNames = Set.of(
                "mohamed","mohammad","mohammed","ahmed","ahmad","ali","omar","khalid","yousef","yusuf"
        );
        Set<String> femaleNames = Set.of(
                "sara","sarah","maryam","fatima","fatimah","noor","nour","reem","reem","raneem"
        );

        if (maleNames.contains(first)) return "male";
        if (femaleNames.contains(first)) return "female";
        return "unknown";
    }

    /** Try to infer rough age group from numbers in bio (very rough). */
    private String inferAgeGroup(String lowerBio) {
        if (lowerBio == null) return "unknown";

        // Look for things like "24yo", "23 years old"
        Pattern p = Pattern.compile("(\\d{2})\\s*(yo|yrs|years)");
        Matcher m = p.matcher(lowerBio);
        if (m.find()) {
            int age = Integer.parseInt(m.group(1));
            return groupAge(age);
        }

        // Look for birth year like 1998, 2001
        Pattern yearPattern = Pattern.compile("19\\d{2}|20\\d{2}");
        Matcher yearMatcher = yearPattern.matcher(lowerBio);
        if (yearMatcher.find()) {
            int year = Integer.parseInt(yearMatcher.group());
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int age = currentYear - year;
            if (age > 0 && age < 100) {
                return groupAge(age);
            }
        }

        return "unknown";
    }

    private String groupAge(int age) {
        if (age < 18) return "<18";
        if (age <= 24) return "18-24";
        if (age <= 34) return "25-34";
        if (age <= 44) return "35-44";
        if (age <= 54) return "45-54";
        return "55+";
    }

    /** Very rough country inference from location string. */
    private String inferCountry(String location) {
        if (location == null) return "unknown";
        String l = location.toLowerCase(Locale.ROOT);

        if (l.contains("saudi") || l.contains("ksa") || l.contains("riyadh") || l.contains("jeddah")) {
            return "Saudi Arabia";
        }
        if (l.contains("uae") || l.contains("dubai") || l.contains("abu dhabi")) {
            return "UAE";
        }
        if (l.contains("egypt") || l.contains("cairo") || l.contains("alex")) {
            return "Egypt";
        }

        // add more rules as you like
        return "unknown";
    }

    /** Rough city extraction (for now, just pick some known ones). */
    private String inferCity(String location) {
        if (location == null) return "unknown";
        String l = location.toLowerCase(Locale.ROOT);

        if (l.contains("riyadh")) return "Riyadh";
        if (l.contains("jeddah") || l.contains("jiddah")) return "Jeddah";
        if (l.contains("dammam")) return "Dammam";
        if (l.contains("dubai")) return "Dubai";
        if (l.contains("cairo")) return "Cairo";

        return "unknown";
    }

    /** Simple keyword based interest detection from bio. */
    private List<String> inferInterests(String lowerBio) {
        List<String> interests = new ArrayList<>();
        if (lowerBio == null || lowerBio.isBlank()) {
            return interests;
        }

        if (lowerBio.contains("football") || lowerBio.contains("soccer") ||
                lowerBio.contains("futbol") || lowerBio.contains("ريال مدريد") ||
                lowerBio.contains("برشلونة") || lowerBio.contains("padel")) {
            interests.add("Sports");
        }

        if (lowerBio.contains("developer") || lowerBio.contains("programmer") ||
                lowerBio.contains("coding") || lowerBio.contains("java") ||
                lowerBio.contains("spring boot") || lowerBio.contains("software engineer")) {
            interests.add("Programming");
        }

        if (lowerBio.contains("marketing") || lowerBio.contains("digital marketing") ||
                lowerBio.contains("social media")) {
            interests.add("Marketing");
        }

        if (lowerBio.contains("design") || lowerBio.contains("ux") || lowerBio.contains("ui")) {
            interests.add("Design");
        }

        // you can add more categories as needed

        return interests;
    }
}
