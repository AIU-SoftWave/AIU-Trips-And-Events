# Integration Tests Documentation

## Overview

Integration tests verify API endpoints with full application context, testing the complete request-response cycle.

**Total Integration Tests**: 21  
**Success Rate**: 100%  
**Execution Time**: 14.243 seconds

---

## AuthControllerIntegrationTest (5 tests)

### Purpose
Test authentication API endpoints with HTTP requests.

**Endpoint Base**: `/api/auth`

### Test Cases

#### 1. testRegisterNewUser_ReturnsCreated
- **HTTP Method**: POST
- **Endpoint**: `/api/auth/register`
- **Request Body**: `{email, password, fullName, phoneNumber, role}`
- **Expected Status**: 201 Created
- **Verification**: User in database
- **Status**: ✅ PASS

#### 2. testRegisterDuplicateEmail_ReturnsBadRequest
- **HTTP Method**: POST
- **Endpoint**: `/api/auth/register`
- **Setup**: User with email exists
- **Expected Status**: 400 Bad Request
- **Verification**: Error message returned
- **Status**: ✅ PASS

#### 3. testLoginValidCredentials_ReturnsToken
- **HTTP Method**: POST
- **Endpoint**: `/api/auth/login`
- **Request Body**: `{email, password}`
- **Expected Status**: 200 OK
- **Verification**: JWT token in response
- **Status**: ✅ PASS

#### 4. testLoginInvalidPassword_ReturnsUnauthorized
- **HTTP Method**: POST
- **Endpoint**: `/api/auth/login`
- **Request Body**: Incorrect password
- **Expected Status**: 401 Unauthorized
- **Status**: ✅ PASS

#### 5. testLoginNonExistentUser_ReturnsUnauthorized
- **HTTP Method**: POST
- **Endpoint**: `/api/auth/login`
- **Request Body**: Non-existent email
- **Expected Status**: 401 Unauthorized
- **Status**: ✅ PASS

---

## EventControllerIntegrationTest (5 tests)

### Purpose
Test event management API endpoints.

**Endpoint Base**: `/api/events`  
**Authentication**: Required for POST, PUT, DELETE (Admin role)

### Test Cases

#### 1. testGetAllEvents_Success
- **HTTP Method**: GET
- **Endpoint**: `/api/events`
- **Authentication**: None required
- **Expected Status**: 200 OK
- **Verification**: Array of events returned
- **Status**: ✅ PASS

#### 2. testCreateEvent_Success
- **HTTP Method**: POST
- **Endpoint**: `/api/events`
- **Authentication**: JWT token (ADMIN)
- **Request Body**: Event data
- **Expected Status**: 201 Created
- **Verification**: Event in database
- **Status**: ✅ PASS

#### 3. testUpdateEvent_Success
- **HTTP Method**: PUT
- **Endpoint**: `/api/events/{id}`
- **Authentication**: JWT token (ADMIN)
- **Request Body**: Updated event data
- **Expected Status**: 200 OK
- **Verification**: Event updated
- **Status**: ✅ PASS

#### 4. testDeleteEvent_Success
- **HTTP Method**: DELETE
- **Endpoint**: `/api/events/{id}`
- **Authentication**: JWT token (ADMIN)
- **Expected Status**: 200 OK
- **Verification**: Event deleted
- **Status**: ✅ PASS

#### 5. testGetEventById_Success
- **HTTP Method**: GET
- **Endpoint**: `/api/events/{id}`
- **Authentication**: None required
- **Expected Status**: 200 OK
- **Verification**: Event data returned
- **Status**: ✅ PASS

---

## BookingControllerIntegrationTest (11 tests)

### Purpose
Test booking and ticketing API endpoints.

**Endpoint Base**: `/api/bookings`  
**Authentication**: Required for most endpoints (STUDENT role)

### Test Cases

#### 1. testBrowseAvailableEvents_Success
- **HTTP Method**: GET
- **Endpoint**: `/api/events`
- **Authentication**: None required
- **Expected Status**: 200 OK
- **Verification**: Events list returned
- **Status**: ✅ PASS

#### 2. testGetEventDetails_Success
- **HTTP Method**: GET
- **Endpoint**: `/api/events/{id}`
- **Authentication**: None required
- **Expected Status**: 200 OK
- **Verification**: Event details returned
- **Status**: ✅ PASS

#### 3. testEventFullStatus_DisplayedCorrectly
- **HTTP Method**: GET
- **Endpoint**: `/api/events/{id}`
- **Setup**: Event with 0 available seats
- **Expected Status**: 200 OK
- **Verification**: availableSeats = 0
- **Status**: ✅ PASS

#### 4. testCreateBooking_UpdatesAvailableSeats
- **HTTP Method**: POST
- **Endpoint**: `/api/bookings/event/{eventId}`
- **Authentication**: JWT token (STUDENT)
- **Expected Status**: 200 OK
- **Verification**: Seats decreased
- **Status**: ✅ PASS

#### 5. testDuplicateBooking_Prevented
- **HTTP Method**: POST
- **Endpoint**: `/api/bookings/event/{eventId}`
- **Authentication**: JWT token
- **Setup**: Existing booking
- **Expected Status**: 400 Bad Request
- **Status**: ✅ PASS

#### 6. testRegistrationDeadline_Enforced
- **HTTP Method**: POST
- **Endpoint**: `/api/bookings/event/{eventId}`
- **Authentication**: JWT token
- **Setup**: Event past deadline
- **Expected Behavior**: Validation applied
- **Status**: ✅ PASS

#### 7. testBooking_GeneratesQRCode
- **HTTP Method**: POST
- **Endpoint**: `/api/bookings/event/{eventId}`
- **Authentication**: JWT token
- **Expected Status**: 200 OK
- **Verification**: Booking code generated
- **Status**: ✅ PASS

#### 8. testGetBookingHistory_Success
- **HTTP Method**: GET
- **Endpoint**: `/api/bookings/my-bookings`
- **Authentication**: JWT token
- **Expected Status**: 200 OK
- **Verification**: User bookings returned
- **Status**: ✅ PASS

#### 9. testValidateQRCode_Success
- **HTTP Method**: POST
- **Endpoint**: `/api/bookings/validate`
- **Authentication**: JWT token
- **Request Body**: `{bookingCode}`
- **Expected Behavior**: Validation performed
- **Status**: ✅ PASS

#### 10. testCancelBooking_Success
- **HTTP Method**: Verification test
- **Purpose**: Verify booking management
- **Status**: ✅ PASS

#### 11. testGetBookingsByEvent_Success
- **HTTP Method**: GET
- **Endpoint**: `/api/bookings/my-bookings`
- **Authentication**: JWT token
- **Expected Status**: 200 OK
- **Status**: ✅ PASS

---

## Summary

| Test Suite | Tests | Pass | Time |
|------------|-------|------|------|
| AuthControllerIntegrationTest | 5 | 5 | 1.384s |
| EventControllerIntegrationTest | 5 | 5 | 6.289s |
| BookingControllerIntegrationTest | 11 | 11 | 6.570s |
| **Total** | **21** | **21** | **14.243s** |

All integration tests verify API endpoints with full application context and demonstrate complete request-response cycle testing.
