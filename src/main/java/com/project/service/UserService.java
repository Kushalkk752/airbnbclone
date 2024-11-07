package com.project.service;

import com.project.entity.AppUser;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.AppUserDto;
import com.project.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private AppUserRepository appUserRepository;
    private ModelMapper modelMapper;

    public UserService(AppUserRepository appUserRepository, ModelMapper modelMapper) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
    }

    public AppUserDto createUser(AppUserDto dto) {
        AppUser appUser = mapToEntity(dto);
        AppUser save = appUserRepository.save(appUser);
        AppUserDto appUserDto = mapToDto(save);
        return appUserDto;
    }

    private AppUserDto mapToDto(AppUser save) {
        AppUserDto appUserDto = modelMapper.map(save, AppUserDto.class);
        return appUserDto;
    }

    private AppUser mapToEntity(AppUserDto dto) {
        AppUser appUser = modelMapper.map(dto, AppUser.class);
        return appUser;
    }

    public List<AppUserDto> findAllUsers() {
        List<AppUser> appUsers = appUserRepository.findAll();
        List<AppUserDto> appUserDtos = appUsers.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
        return appUserDtos;
    }

    public void deleteUser(Long id) {
        appUserRepository.deleteById(id);
    }

    public AppUser updateUser(Long id, AppUser appUser) {
        AppUser user = appUserRepository.findById(id).get();
        user.setUsername(appUser.getUsername());
        user.setEmail(appUser.getEmail());
        user.setEmail(appUser.getEmail());
        user.setPassword(appUser.getPassword());
        AppUser save = appUserRepository.save(user);
        return save;
    }

    public AppUserDto findUserById(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Record not found")
        );
        AppUserDto appUserDto = mapToDto(appUser);
        return appUserDto;
    }
}
