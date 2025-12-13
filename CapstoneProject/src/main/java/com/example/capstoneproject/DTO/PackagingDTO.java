package com.example.capstoneproject.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackagingDTO {

    @NotEmpty(message = "package description cannot be empty")
    private String description;
    @NotEmpty(message = "mock type  cannot be empty")
    @Pattern(regexp = "^(box|bottle|label|pouch|bag)$",message = "the mock type must be box ,bottle, label, pouch or bag")
    private String mockupType;
    @NotEmpty(message = " color palette cannot be empty")
    private String colorPalette;
    @NotEmpty(message = " dimensions cannot be empty")
    private String dimensions;
}
