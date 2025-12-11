package com.example.capstoneproject.Apify.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListMatchCountDto {
    private String listId;
    private Integer totalFollowersSampled;
    private Long matchingCount;


}
