package com.example.capstoneproject.Apify.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListFollowersCountDto {
    private Integer max_age;
    private Integer min_age;
    private String interset;
    private Long count;

}

