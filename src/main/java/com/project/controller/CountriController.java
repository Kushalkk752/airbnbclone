package com.project.controller;

import com.project.entity.City;
import com.project.entity.Country;
import com.project.payload.CityDto;
import com.project.payload.CountryDto;
import com.project.repository.CityRepository;
import com.project.repository.CountryRepository;
import com.project.service.CityService;
import com.project.service.CountriService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Country")
public class CountriController {
    private CountriService countriService;
    private CountryRepository countryRepository;

    public CountriController(CountriService countriService, CountryRepository countryRepository) {
        this.countriService = countriService;
        this.countryRepository = countryRepository;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<?> createCountry(
            @RequestBody CountryDto dto
    ){
        Optional<Country> opCountryName = countryRepository.findByCountryName(dto.getCountryName());
        if(opCountryName.isPresent()){
            return new ResponseEntity<>("Country already present", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CountryDto countryDto = countriService.createCountry(dto);
        return new ResponseEntity<>(countryDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries(){
        List<CountryDto> countryDtos = countriService.findAllCountries();
        return new ResponseEntity<>(countryDtos,HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<String> deleteCountry(
            @RequestParam Long id
    ){
        countriService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(
            @PathVariable Long id,
            @RequestBody Country country
    ){
        Country coun = countriService.update(id,country);
        return new ResponseEntity<>(coun,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCOuntryById(
            @PathVariable Long id
    ){
        CountryDto countryDto = countriService.getCountryById(id);
        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }
}
