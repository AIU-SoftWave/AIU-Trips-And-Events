# Unit Tests Documentation

## Overview

Unit tests verify individual components in isolation, focusing on business logic without external dependencies. All dependencies are mocked using Mockito.

**Total Unit Tests**: 39  
**Success Rate**: 100%  
**Execution Time**: 1.608 seconds

---

## AuthServiceTest (9 tests)

### Purpose
Test authentication and user management business logic.

### Test Cases

#### 1. testLogin_WithValidCredentials_ReturnsToken
- **Purpose**: Verify successful login with correct credentials
- **Setup**: User with valid email and encrypted password
- **Action**: Login with correct credentials
- **Assertion**: JWT token generated
- **Status**: ✅ PASS

#### 2. testLogin_WithInvalidPassword_ThrowsException
- **Purpose**: Verify authentication failure with wrong password
- **Setup**: User exists with password
- **Action**: Login with incorrect password
- **Assertion**: Authentication exception thrown
- **Status**: ✅ PASS

#### 3. testRegister_WithValidData_CreatesUser
- **Purpose**: Verify user registration with valid data
- **Setup**: Valid registration data provided
- **Action**: Submit registration
- **Assertion**: User created in database
- **Status**: ✅ PASS

#### 4. testRegister_WithDuplicateEmail_ThrowsException
- **Purpose**: Verify duplicate email prevention
- **Setup**: User with email exists
- **Action**: Register with same email
- **Assertion**: Duplicate email exception thrown
- **Status**: ✅ PASS

#### 5. testPasswordValidation_WithWeakPassword_ThrowsException
- **Purpose**: Verify password strength requirements
- **Setup**: Weak password provided
- **Action**: Attempt registration
- **Assertion**: Validation exception thrown
- **Status**: ✅ PASS

#### 6. testPasswordReset_WithValidEmail_SendsResetLink
- **Purpose**: Verify password reset functionality
- **Setup**: User with email exists
- **Action**: Request password reset
- **Assertion**: Reset process initiated
- **Status**: ✅ PASS

#### 7. testLogin_WithNonExistentUser_ThrowsException
- **Purpose**: Verify handling of non-existent user
- **Setup**: User does not exist
- **Action**: Attempt login
- **Assertion**: User not found exception thrown
- **Status**: ✅ PASS

#### 8. testLogin_GeneratesValidJwtToken
- **Purpose**: Verify JWT token generation
- **Setup**: Valid user credentials
- **Action**: Perform login
- **Assertion**: Valid JWT token returned
- **Status**: ✅ PASS

#### 9. testPasswordEncryption
- **Purpose**: Verify password encryption
- **Setup**: User registration with plain password
- **Action**: Save user
- **Assertion**: Password encrypted using BCrypt
- **Status**: ✅ PASS

---

## EventServiceTest (15 tests)

### Purpose
Test event management business logic including CRUD operations and validations.

### Test Cases

#### 1. testCreateEvent_WithValidData_ReturnsEvent
- **Purpose**: Verify event creation with all required fields
- **Setup**: Valid event data provided
- **Action**: Call createEvent
- **Assertion**: Event saved and returned
- **Status**: ✅ PASS

#### 2. testEventImageUpload_SavesImagePath
- **Purpose**: Verify event image upload
- **Setup**: Event exists
- **Action**: Upload image file
- **Assertion**: Image path saved
- **Status**: ✅ PASS

#### 3. testCreateEvent_WithPastDate_ThrowsException
- **Purpose**: Verify past date validation
- **Setup**: Event with past start date
- **Action**: Attempt creation
- **Assertion**: Validation exception thrown
- **Status**: ✅ PASS

#### 4. testCreateEvent_WithNegativeCapacity_ThrowsException
- **Purpose**: Verify capacity validation
- **Setup**: Event with negative capacity
- **Action**: Attempt creation
- **Assertion**: Validation exception thrown
- **Status**: ✅ PASS

#### 5. testUpdateEvent_WithValidData_UpdatesEvent
- **Purpose**: Verify event updates
- **Setup**: Existing event
- **Action**: Update event fields
- **Assertion**: Changes saved
- **Status**: ✅ PASS

#### 6. testDeleteEvent_RemovesEventFromDatabase
- **Purpose**: Verify event deletion
- **Setup**: Event exists
- **Action**: Delete event
- **Assertion**: Event no longer exists
- **Status**: ✅ PASS

#### 7. testEventCategories_DistinguishEventAndTrip
- **Purpose**: Verify event categorization
- **Setup**: Events with different types
- **Action**: Create events
- **Assertion**: Types preserved correctly
- **Status**: ✅ PASS

#### 8. testEventRegistration_StopsWhenFull
- **Purpose**: Verify capacity management
- **Setup**: Event with capacity of 1
- **Action**: Fill capacity
- **Assertion**: availableSeats = 0
- **Status**: ✅ PASS

#### 9. testRegistrationDeadline_EnforcedCorrectly
- **Purpose**: Verify deadline enforcement
- **Setup**: Event with deadline
- **Action**: Book before/after deadline
- **Assertion**: Deadline enforced
- **Status**: ✅ PASS

#### 10-15. Additional Event Tests
- Get all events
- Get event by ID
- Event not found handling
- Capacity validation
- Date validation
- Event preservation on update
- **Status**: ✅ ALL PASS

---

## BookingServiceTest (15 tests)

### Purpose
Test booking and ticketing business logic including seat management and QR codes.

### Test Cases

#### 1. testBrowseEvents_ReturnsAvailableEvents
- **Purpose**: Verify event browsing
- **Setup**: Multiple events exist
- **Action**: Browse events
- **Assertion**: Events returned
- **Status**: ✅ PASS

#### 2. testGetEventDetails_ReturnsCompleteInformation
- **Purpose**: Verify complete event details
- **Setup**: Event exists
- **Action**: Request details
- **Assertion**: All fields present
- **Status**: ✅ PASS

#### 3. testBooking_DisabledWhenEventFull
- **Purpose**: Verify booking prevention when full
- **Setup**: Event with 0 available seats
- **Action**: Attempt booking
- **Assertion**: Booking rejected
- **Status**: ✅ PASS

#### 4. testCheckSeatAvailability_ReturnsCorrectCount
- **Purpose**: Verify seat count accuracy
- **Setup**: Event with bookings
- **Action**: Check availability
- **Assertion**: Accurate count returned
- **Status**: ✅ PASS

#### 5. testDuplicateBooking_Prevented
- **Purpose**: Verify duplicate prevention
- **Setup**: User has booking
- **Action**: Attempt duplicate
- **Assertion**: Second booking rejected
- **Status**: ✅ PASS

#### 6. testRegistrationDeadline_Enforced
- **Purpose**: Verify deadline enforcement
- **Setup**: Event past deadline
- **Action**: Attempt booking
- **Assertion**: Booking rejected
- **Status**: ✅ PASS

#### 7. testQRCodeGeneration_CreatesUniqueCode
- **Purpose**: Verify QR code uniqueness
- **Setup**: Multiple bookings
- **Action**: Create bookings
- **Assertion**: Unique codes generated
- **Status**: ✅ PASS

#### 8. testDigitalTicket_Created
- **Purpose**: Verify ticket creation
- **Setup**: Booking created
- **Action**: Check ticket
- **Assertion**: Ticket information stored
- **Status**: ✅ PASS

#### 9. testAvailableSeats_UpdatedOnBooking
- **Purpose**: Verify seat updates
- **Setup**: Event with seats
- **Action**: Create booking
- **Assertion**: Seats decreased by 1
- **Status**: ✅ PASS

#### 10. testBookingHistory_MaintainedPerUser
- **Purpose**: Verify booking history
- **Setup**: User with multiple bookings
- **Action**: Retrieve history
- **Assertion**: All bookings present
- **Status**: ✅ PASS

#### 11. testQRCodeValidation_ValidatesCorrectly
- **Purpose**: Verify QR validation
- **Setup**: Booking with QR code
- **Action**: Validate code
- **Assertion**: Validation succeeds
- **Status**: ✅ PASS

#### 12-15. Additional Booking Tests
- Create booking
- Cancel booking
- Get bookings by event
- Handle no available seats
- **Status**: ✅ ALL PASS

---

## Summary

| Test Suite | Tests | Pass | Time |
|------------|-------|------|------|
| AuthServiceTest | 9 | 9 | 0.358s |
| EventServiceTest | 15 | 15 | 0.067s |
| BookingServiceTest | 15 | 15 | 1.183s |
| **Total** | **39** | **39** | **1.608s** |

All unit tests demonstrate excellent coverage of business logic with fast execution times and 100% success rate.
