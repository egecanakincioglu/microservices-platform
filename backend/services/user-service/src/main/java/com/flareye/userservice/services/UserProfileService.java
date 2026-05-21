package com.flareye.userservice.services;

import com.flareye.userservice.dto.UpdateProfileRequest;
import com.flareye.userservice.dto.UserProfileDTO;
import com.flareye.userservice.models.UserProfile;
import com.flareye.userservice.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Optional<UserProfile> findById(Long id) {
        return userProfileRepository.findById(id);
    }

    public Optional<UserProfile> findByEmail(String email) {
        return userProfileRepository.findByEmail(email);
    }

    public Optional<UserProfile> findByAuthUserId(String authUserId) {
        return userProfileRepository.findByAuthUserId(authUserId);
    }

    public List<UserProfileDTO> findAll() {
        return userProfileRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserProfile save(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    public UserProfile update(UserProfile profile, UpdateProfileRequest request) {
        if (request.getFirstName() != null) profile.setFirstName(request.getFirstName());
        if (request.getLastName() != null) profile.setLastName(request.getLastName());
        if (request.getPhoneNumber() != null) profile.setPhoneNumber(request.getPhoneNumber());
        if (request.getAvatarUrl() != null) profile.setAvatarUrl(request.getAvatarUrl());
        if (request.getBio() != null) profile.setBio(request.getBio());
        return userProfileRepository.save(profile);
    }

    public void deleteById(Long id) {
        userProfileRepository.deleteById(id);
    }

    public UserProfileDTO toDTO(UserProfile profile) {
        return UserProfileDTO.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .email(profile.getEmail())
                .phoneNumber(profile.getPhoneNumber())
                .avatarUrl(profile.getAvatarUrl())
                .bio(profile.getBio())
                .createdAt(profile.getCreatedAt())
                .build();
    }
}
