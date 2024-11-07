package com.project.payload;
import com.project.entity.City;
import com.project.entity.Country;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDto {
    private String propertyName;
    private Integer no_of_guests;
    private Integer no_of_rooms;
    private Integer no_of_bathrooms;
    private Integer no_of_beds;
    private City city;
    private Country country;
}
