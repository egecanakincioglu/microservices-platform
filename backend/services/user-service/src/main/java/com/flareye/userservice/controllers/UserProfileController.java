package com.flareye.userservice.controllers;

import com.flareye.userservice.dto.UpdateProfileRequest;
import com.flareye.userservice.dto.UserProfileDTO;
import com.flareye.userservice.models.UserProfile;
import com.flareye.userservice.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> getAll() {
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getById(@PathVariable Long id) {
        Optional<UserProfile> profile = userProfileService.findById(id);
        return profile.map(p -> ResponseEntity.ok(userProfileService.toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-auth/{authUserId}")
    public ResponseEntity<UserProfileDTO> getByAuthUserId(@PathVariable String authUserId) {
        Optional<UserProfile> profile = userProfileService.findByAuthUserId(authUserId);
        return profile.map(p -> ResponseEntity.ok(userProfileService.toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> update(@PathVariable Long id,
                                                  @RequestBody UpdateProfileRequest request) {
        Optional<UserProfile> existing = userProfileService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserProfile updated = userProfileService.update(existing.get(), request);
        return ResponseEntity.ok(userProfileService.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (userProfileService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userProfileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
