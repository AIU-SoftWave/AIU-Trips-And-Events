package com.aiu.trips.repository;

import com.aiu.trips.model.User;
import com.aiu.trips.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByEmailVerificationToken(String token);
    Optional<User> findByPasswordResetToken(String token);
    List<User> findByRole(UserRole role);
    List<User> findByEmailContainingOrFullNameContaining(String email, String fullName);
}
