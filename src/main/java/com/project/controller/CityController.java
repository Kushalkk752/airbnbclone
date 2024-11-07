package com.project.controller;

import com.project.entity.City;
import com.project.payload.CityDto;
import com.project.repository.CityRepository;
import com.project.service.CityService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private CityService cityService;
    private CityRepository cityRepository;

    public CityController(CityService cityService, CityRepository cityRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
    }

    @PostMapping("/addCity")
    public ResponseEntity<?> createCity(
            @RequestBody CityDto dto
    ){
        Optional<City> opCityName = cityRepository.findByCityName(dto.getCityName());
        if(opCityName.isPresent()){
            return new ResponseEntity<>("City already present", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CityDto cityDto = cityService.createCity(dto);
        return new ResponseEntity<>(cityDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities(){
        List<CityDto> cityDtos = cityService.findAllCities();
        return new ResponseEntity<>(cityDtos,HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> deleteCity(
            @RequestParam Long id
    ){
        cityService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(
            @PathVariable Long id,
            @RequestBody City city
    ){
        City citi = cityService.update(id,city);
        return new ResponseEntity<>(citi,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(
            @PathVariable Long id
    ){
        CityDto cityDto = cityService.getCityById(id);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

}
