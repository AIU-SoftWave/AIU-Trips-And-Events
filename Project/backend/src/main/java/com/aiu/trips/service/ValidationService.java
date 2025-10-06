package com.aiu.trips.service;

import com.aiu.trips.model.Event;
import com.aiu.trips.model.Booking;
import com.aiu.trips.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class ValidationService {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10,15}$");
    
    /**
     * Validate event data
     */
    public boolean validateEventData(Event event) {
        if (event.getTitle() == null || event.getTitle().trim().isEmpty()) {
            throw new ValidationException("Event title is required");
        }
        
        if (event.getStartDate() == null) {
            throw new ValidationException("Event start date is required");
        }
        
        if (event.getStartDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Event start date cannot be in the past");
        }
        
        if (event.getEndDate() != null && event.getEndDate().isBefore(event.getStartDate())) {
            throw new ValidationException("Event end date cannot be before start date");
        }
        
        if (event.getCapacity() == null || event.getCapacity() <= 0) {
            throw new ValidationException("Event capacity must be greater than 0");
        }
        
        if (event.getPrice() == null || event.getPrice() < 0) {
            throw new ValidationException("Event price cannot be negative");
        }
        
        if (event.getRegistrationDeadline() != null && 
            event.getRegistrationDeadline().isAfter(event.getStartDate())) {
            throw new ValidationException("Registration deadline must be before event start date");
        }
        
        return true;
    }
    
    /**
     * Validate booking data
     */
    public boolean validateBookingData(Booking booking) {
        if (booking.getUser() == null) {
            throw new ValidationException("User is required for booking");
        }
        
        if (booking.getEvent() == null) {
            throw new ValidationException("Event is required for booking");
        }
        
        Event event = booking.getEvent();
        
        // Check if event has available seats
        if (event.getAvailableSeats() <= 0) {
            throw new ValidationException("Event is fully booked");
        }
        
        // Check if registration deadline has passed
        if (event.getRegistrationDeadline() != null && 
            LocalDateTime.now().isAfter(event.getRegistrationDeadline())) {
            throw new ValidationException("Registration deadline has passed");
        }
        
        // Check if event has already started
        if (event.getStartDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Cannot book past events");
        }
        
        return true;
    }
    
    /**
     * Validate email format
     */
    public boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format");
        }
        
        return true;
    }
    
    /**
     * Validate phone number format
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return true; // Phone number is optional
        }
        
        String cleanedNumber = phoneNumber.replaceAll("[\\s-()]", "");
        
        if (!PHONE_PATTERN.matcher(cleanedNumber).matches()) {
            throw new ValidationException("Invalid phone number format");
        }
        
        return true;
    }
    
    /**
     * Validate password strength
     */
    public boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters long");
        }
        
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecialChar = password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(ch) >= 0);
        
        if (!hasUpperCase) {
            throw new ValidationException("Password must contain at least one uppercase letter");
        }
        
        if (!hasLowerCase) {
            throw new ValidationException("Password must contain at least one lowercase letter");
        }
        
        if (!hasDigit) {
            throw new ValidationException("Password must contain at least one digit");
        }
        
        if (!hasSpecialChar) {
            throw new ValidationException("Password must contain at least one special character");
        }
        
        return true;
    }
    
    /**
     * Validate event capacity update
     */
    public boolean validateCapacityUpdate(Event event, int newCapacity) {
        long bookedSeats = event.getCapacity() - event.getAvailableSeats();
        
        if (newCapacity < bookedSeats) {
            throw new ValidationException(
                String.format("Cannot reduce capacity below %d (current bookings)", bookedSeats));
        }
        
        return true;
    }
}
