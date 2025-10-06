package com.aiu.trips.service;

import com.aiu.trips.constants.AppConstants;
import com.aiu.trips.dto.RegisterRequest;
import com.aiu.trips.exception.ResourceNotFoundException;
import com.aiu.trips.exception.ValidationException;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerUser(RegisterRequest userData) {
        if (userRepository.existsByEmail(userData.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        User user = new User();
        user.setEmail(userData.getEmail());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        user.setFullName(userData.getFullName());
        user.setPhoneNumber(userData.getPhoneNumber());
        user.setFaculty(userData.getFaculty());
        user.setAcademicYear(userData.getAcademicYear());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateProfile(Long userId, RegisterRequest profileData) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));

        user.setFullName(profileData.getFullName());
        user.setPhoneNumber(profileData.getPhoneNumber());
        user.setFaculty(profileData.getFaculty());
        user.setAcademicYear(profileData.getAcademicYear());

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));
        
        userRepository.delete(user);
        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + email));
    }
}
