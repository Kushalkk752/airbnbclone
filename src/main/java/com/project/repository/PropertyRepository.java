package com.project.repository;

import com.project.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;
import com.project.entity.Property;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByPropertyName(String propertyName);

    @Query("select p from Property p JOIN city c JOIN country co where c.cityName=:name or co.countryName=:name")
    List<Property> searchHotels(@Param("name") String name);
}