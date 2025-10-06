package com.aiu.trips.service.interfaces;

import com.aiu.trips.dto.RegisterRequest;
import com.aiu.trips.model.User;

public interface IUserService {
    User registerUser(RegisterRequest userData);
    User updateProfile(Long userId, RegisterRequest profileData);
    User getUserById(Long userId);
    boolean deleteUser(Long userId);
    User getUserByEmail(String email);
}
