# Functional Requirements

This document details all functional requirements that have automated test coverage in the AIU Trips & Events Management System.

## FR-1: User Authentication & Authorization

### FR-1.1: User Registration
**Description**: The system shall allow new users to register with their information.

**Requirements**:
- Users must provide email, password, full name, and phone number
- Email must be unique in the system
- Password must meet strength requirements
- User role is assigned (STUDENT, STAFF, ADMIN)

**Test Coverage**: ✅ Complete
- TC_AUTH_003: Register New User
- TC_AUTH_004: Prevent Duplicate Email
- TC_AUTH_005: Password Strength Validation
- TC_INT_AUTH_001: Register New User (API)
- TC_INT_AUTH_002: Prevent Duplicate Email (API)

### FR-1.2: User Login
**Description**: The system shall authenticate users with email and password.

**Requirements**:
- Accept email and password credentials
- Validate credentials against database
- Generate JWT token on successful authentication
- Return appropriate error messages for invalid credentials

**Test Coverage**: ✅ Complete
- TC_AUTH_001: Login with Valid Credentials
- TC_AUTH_002: Login with Invalid Credentials
- TC_AUTH_007: Login Non-Existent User
- TC_AUTH_008: JWT Token Generation
- TC_INT_AUTH_003: Login with Valid Credentials (API)
- TC_INT_AUTH_004: Login with Invalid Password (API)
- TC_INT_AUTH_005: Login Non-Existent User (API)

### FR-1.3: Password Management
**Description**: The system shall provide password reset functionality.

**Requirements**:
- Users can request password reset via email
- System sends reset link to registered email
- Password must meet strength requirements

**Test Coverage**: ✅ Complete
- TC_AUTH_006: Password Reset
- TC_AUTH_009: Password Encryption

### FR-1.4: Role-Based Access Control
**Description**: The system shall enforce role-based access control.

**Requirements**:
- Three roles: STUDENT, STAFF, ADMIN
- Different permissions per role
- JWT token contains role information
- Endpoints protected based on role

**Test Coverage**: ✅ Complete
- Tested implicitly in all integration tests requiring authentication
- JWT tokens generated with role information
- Protected endpoints verified

---

## FR-2: Event Management

### FR-2.1: Event Creation
**Description**: The system shall allow authorized users to create events.

**Requirements**:
- Required fields: title, description, start date, location, capacity, price
- Event type: EVENT or TRIP
- Start date must be in the future
- Capacity must be positive
- Only ADMIN users can create events

**Test Coverage**: ✅ Complete
- TC_EVENT_001: Create Event with Valid Data
- TC_EVENT_003: Validate Past Date Prevention
- TC_EVENT_004: Validate Positive Capacity
- TC_INT_EVENT_002: Create Event (API)

### FR-2.2: Event Updates
**Description**: The system shall allow authorized users to update existing events.

**Requirements**:
- Update title, description, price, location
- Cannot update past events
- Only ADMIN users can update events
- Existing bookings are preserved

**Test Coverage**: ✅ Complete
- TC_EVENT_005: Update Existing Event
- TC_INT_EVENT_003: Update Event (API)

### FR-2.3: Event Deletion
**Description**: The system shall allow authorized users to delete events.

**Requirements**:
- Only ADMIN users can delete events
- System handles associated bookings appropriately
- Soft delete or hard delete based on business rules

**Test Coverage**: ✅ Complete
- TC_EVENT_006: Delete Event
- TC_INT_EVENT_004: Delete Event (API)

### FR-2.4: Event Retrieval
**Description**: The system shall provide event listing and detail views.

**Requirements**:
- List all events (public)
- Get event by ID (public)
- Filter by type, date, availability
- Display complete event information

**Test Coverage**: ✅ Complete
- TC_EVENT_010: Get All Events
- TC_EVENT_011: Get Event By ID
- TC_EVENT_012: Event Not Found
- TC_INT_EVENT_001: Get All Events (API)
- TC_INT_EVENT_005: Get Event By ID (API)

### FR-2.5: Event Capacity Management
**Description**: The system shall manage event capacity and seat availability.

**Requirements**:
- Track total capacity
- Track available seats
- Update available seats on booking
- Prevent overbooking

**Test Coverage**: ✅ Complete
- TC_EVENT_008: Registration Capacity Management
- TC_BOOKING_004: Check Seat Availability
- TC_BOOKING_009: Update Available Seats

### FR-2.6: Event Categorization
**Description**: The system shall support event categorization.

**Requirements**:
- Two types: EVENT and TRIP
- Different handling per type
- Type-specific fields (for trips: destination, transport mode)

**Test Coverage**: ✅ Complete
- TC_EVENT_007: Event Categorization

### FR-2.7: Event Image Upload
**Description**: The system shall support event image uploads.

**Requirements**:
- Upload event images
- Store image paths
- Display images in event details

**Test Coverage**: ✅ Complete
- TC_EVENT_002: Upload Event Image

### FR-2.8: Registration Deadline
**Description**: The system shall enforce registration deadlines.

**Requirements**:
- Events have registration deadline (end date)
- Bookings not allowed after deadline
- Clear indication of deadline status

**Test Coverage**: ✅ Complete
- TC_EVENT_009: Registration Deadline
- TC_BOOKING_006: Enforce Registration Deadline

---

## FR-3: Booking & Ticketing System

### FR-3.1: Browse Events
**Description**: Users shall be able to browse available events.

**Requirements**:
- Display all upcoming events
- Show event details (title, date, location, price, availability)
- Filter by availability
- Public access (no authentication required)

**Test Coverage**: ✅ Complete
- TC_BOOKING_001: Browse Available Events
- TC_INT_BOOKING_001: Browse Events (API)

### FR-3.2: Event Details
**Description**: Users shall be able to view detailed event information.

**Requirements**:
- Display complete event information
- Show current seat availability
- Display pricing information
- Show registration status

**Test Coverage**: ✅ Complete
- TC_BOOKING_002: Display Event Details
- TC_INT_BOOKING_002: Get Event Details (API)

### FR-3.3: Create Booking
**Description**: Authenticated users shall be able to book events.

**Requirements**:
- User must be authenticated
- Event must have available seats
- Event must not have passed registration deadline
- User cannot book same event twice
- Generate unique booking code
- Update available seats

**Test Coverage**: ✅ Complete
- TC_BOOKING_007: Generate Unique QR Codes
- TC_BOOKING_008: Create Digital Tickets
- TC_BOOKING_009: Update Available Seats
- TC_INT_BOOKING_004: Create Booking (API)

### FR-3.4: Duplicate Booking Prevention
**Description**: The system shall prevent users from booking the same event multiple times.

**Requirements**:
- Check for existing booking before creating new one
- Same user cannot book same event twice
- Clear error message if duplicate attempted

**Test Coverage**: ✅ Complete
- TC_BOOKING_005: Prevent Duplicate Bookings
- TC_INT_BOOKING_005: Prevent Duplicate Bookings (API)

### FR-3.5: Seat Availability
**Description**: The system shall enforce seat availability constraints.

**Requirements**:
- Check available seats before booking
- Prevent booking when event is full
- Real-time seat count updates
- Clear indication of full events

**Test Coverage**: ✅ Complete
- TC_BOOKING_003: Booking Disabled When Full
- TC_BOOKING_004: Check Seat Availability
- TC_INT_BOOKING_003: Display Event Full Status (API)

### FR-3.6: QR Code Generation
**Description**: The system shall generate unique QR codes for each booking.

**Requirements**:
- Generate unique booking code
- Create QR code for ticket
- Store QR code path/data
- QR code contains booking reference

**Test Coverage**: ✅ Complete
- TC_BOOKING_007: Generate Unique QR Codes
- TC_INT_BOOKING_007: Generate QR Code (API)

### FR-3.7: Digital Tickets
**Description**: The system shall create digital tickets for bookings.

**Requirements**:
- Generate ticket with booking details
- Include QR code
- Include event information
- Provide download/email functionality

**Test Coverage**: ✅ Complete
- TC_BOOKING_008: Create Digital Tickets

### FR-3.8: Booking History
**Description**: Users shall be able to view their booking history.

**Requirements**:
- Display all user bookings
- Show booking status
- Include event details
- Filter by status (upcoming, past, cancelled)

**Test Coverage**: ✅ Complete
- TC_BOOKING_010: Maintain Booking History
- TC_INT_BOOKING_008: Get Booking History (API)

### FR-3.9: QR Code Validation
**Description**: The system shall validate QR codes at event entry.

**Requirements**:
- Scan QR code
- Validate booking code
- Mark ticket as validated
- Prevent duplicate entry
- Log validation time and validator

**Test Coverage**: ✅ Complete
- TC_BOOKING_011: Validate QR Codes
- TC_INT_BOOKING_009: Validate QR Code (API)

---

## Requirements Coverage Summary

| Requirement Category | Total Requirements | Tested | Coverage |
|---------------------|-------------------|--------|----------|
| FR-1: Authentication | 4 | 4 | 100% |
| FR-2: Event Management | 8 | 8 | 100% |
| FR-3: Booking & Ticketing | 9 | 9 | 100% |
| **Total** | **21** | **21** | **100%** |

---

## Requirements Not Covered (Future Work)

The following functional requirements exist in the system but do not have automated test coverage yet:

### FR-4: Notification Management
- Event announcements
- Booking confirmations
- Cancellation notifications
- Reminder emails
- Custom messages

### FR-5: Reports & Analytics
- Participant reports
- Revenue tracking
- Statistics dashboard
- Export functionality (PDF, CSV, JSON)
- Attendance tracking
- Performance metrics

These features will be added in future testing iterations.

---

## Test-to-Requirement Traceability

Every automated test is mapped to at least one functional requirement, ensuring complete traceability and verification that all critical requirements are tested.

See [Test_Coverage_Mapping.md](Test_Coverage_Mapping.md) for detailed traceability matrix.
