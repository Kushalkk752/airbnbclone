package com.project.payload;

import com.project.entity.AppUser;
import com.project.entity.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Integer rating;
    private String description;
    private AppUser appUser;
    private Property property;
}
