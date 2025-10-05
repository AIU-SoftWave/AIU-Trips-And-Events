package com.aiu.trips.repository;

import com.aiu.trips.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPasswordResetToken(String token);
    Optional<User> findByEmailVerificationToken(String token);
    boolean existsByEmail(String email);
}
