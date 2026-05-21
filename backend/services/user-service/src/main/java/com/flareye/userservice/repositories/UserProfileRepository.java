package com.flareye.userservice.repositories;

import com.flareye.userservice.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByEmail(String email);
    Optional<UserProfile> findByAuthUserId(String authUserId);
    boolean existsByEmail(String email);
}
