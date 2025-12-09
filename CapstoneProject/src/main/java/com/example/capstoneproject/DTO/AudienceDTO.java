package com.example.capstoneproject.DTO;

import com.example.capstoneproject.Model.campaign;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AudienceDTO
{
    @NotNull(message = "The Min Age Cannot Be Null")
    @Min(value = 1, message = "the min age cannot be less than 1")
    private Integer min_age;
    @NotNull(message = "The Max Age Cannot Be Null")
    @Max(value = 90,message = "The Max age Cannot Be Greater Than 90")
    private Integer max_age;
    @NotEmpty(message = "The gender Cannot Be Empty")
    @Pattern(regexp = "^(?i)(female|male)$",message = "gender must be female or male")
    private String gender;
    @NotEmpty(message = "The Interest Cannot Be Empty")
    private String interest;
    @NotEmpty(message = "The Location Cannot Be Empty")
    @Size(min =2,message = "The Location Length Must Be at Least Value of 2")
    private String location;
    @NotEmpty(message = "The Income Level Cannot Be Empty")
    @Pattern(regexp = "^(?i)(high|medium|low)$",message = "The Income Level Must Be high,medium,low")
    private String income_level;

}
