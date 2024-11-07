package com.project.repository;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.project.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String cityName);
}