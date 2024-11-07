package com.project.service;

import com.project.entity.City;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.CityDto;
import com.project.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public CityDto createCity(CityDto dto) {
        City city = mapToEntity(dto);
        City save = cityRepository.save(city);
        CityDto cityDto = mapToDto(save);
        return cityDto;
    }

    private CityDto mapToDto(City save) {
        CityDto cityDto = modelMapper.map(save,CityDto.class);
        return cityDto;
    }

    private City mapToEntity(CityDto dto) {
        City city = modelMapper.map(dto, City.class);
        return city;
    }

    public List<CityDto> findAllCities() {
        List<City> cities = cityRepository.findAll();
        List<CityDto> cityDtos = cities.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return cityDtos;
    }

    @Transactional
    public void delete(Long id) {
        cityRepository.deleteById(id);
    }

    public City update(Long id, City city) {
        City citi = cityRepository.findById(id).get();
        citi.setCityName(city.getCityName());
        City save = cityRepository.save(citi);
        return save;
    }

    public CityDto getCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Record not found")
        );
        CityDto cityDto = mapToDto(city);
        return cityDto;
    }
}
