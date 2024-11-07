package com.project.service;

import com.project.entity.Country;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.CountryDto;
import com.project.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountriService {

    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    public CountriService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    public CountryDto createCountry(CountryDto dto) {
        Country country = mapToEntity(dto);
        Country save = countryRepository.save(country);
        CountryDto countryDto = mapToDto(save);
        return countryDto;
    }

    private CountryDto mapToDto(Country save) {
        CountryDto countryDto = modelMapper.map(save, CountryDto.class);
        return countryDto;
    }

    private Country mapToEntity(CountryDto dto) {
        Country country = modelMapper.map(dto, Country.class);
        return country;
    }

    public List<CountryDto> findAllCountries() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDto> countryDtos = countries.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return countryDtos;
    }

    @Transactional
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }

    public Country update(Long id, Country country) {
        Country coun = countryRepository.findById(id).get();
        coun.setCountryName(country.getCountryName());
        Country save = countryRepository.save(coun);
        return save;
    }

    public CountryDto getCountryById(Long id) {
        Country country = countryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Record not found")
        );
        CountryDto countryDto = mapToDto(country);
        return countryDto;
    }
}
