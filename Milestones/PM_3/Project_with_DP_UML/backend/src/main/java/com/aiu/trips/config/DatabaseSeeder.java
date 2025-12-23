package com.aiu.trips.config;

import com.aiu.trips.enums.*;
import com.aiu.trips.model.*;
import com.aiu.trips.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Database Seeder - Populates the database with sample data for testing and development
 * Updated to match After DP Data_Layer diagram structure with Activity/Event/Trip hierarchy
 */
@Component
@Order(2)
@Profile({"dev", "docker"})
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired(required = false)
    private TripRepository tripRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            if (userRepository.count() > 1) {
                System.out.println("Database already seeded. Skipping...");
                return;
            }

            System.out.println("Starting database seeding...");

            // Create users
            List<User> users = createUsers();
            
            // Create events and trips
            List<Event> events = createEvents(users);
            
            // Create bookings
            createBookings(users, events);
            
            // Create feedback
            createFeedback(users, events);
            
            // Create notifications
            createNotifications(users);

            System.out.println("Database seeding completed successfully!");
            System.out.println("Sample credentials:");
            System.out.println("  Admin: admin@aiu.edu / admin123");
            System.out.println("  Student: john.doe@aiu.edu / password123");
            System.out.println("  Student: jane.smith@aiu.edu / password123");
            System.out.println("  Organizer: organizer@aiu.edu / password123");
        } catch (Exception e) {
            System.err.println("Error during database seeding: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        try {
            // Create student users
            User student1 = new User();
            student1.setEmail("john.doe@aiu.edu");
            student1.setPassword(passwordEncoder.encode("password123"));
            student1.setFullName("John Doe");
            student1.setPhoneNumber("555-0101");
            student1.setRole(UserRole.STUDENT);
            users.add(userRepository.save(student1));

            User student2 = new User();
            student2.setEmail("jane.smith@aiu.edu");
            student2.setPassword(passwordEncoder.encode("password123"));
            student2.setFullName("Jane Smith");
            student2.setPhoneNumber("555-0102");
            student2.setRole(UserRole.STUDENT);
            users.add(userRepository.save(student2));

            User student3 = new User();
            student3.setEmail("mike.johnson@aiu.edu");
            student3.setPassword(passwordEncoder.encode("password123"));
            student3.setFullName("Mike Johnson");
            student3.setPhoneNumber("555-0103");
            student3.setRole(UserRole.STUDENT);
            users.add(userRepository.save(student3));

            User student4 = new User();
            student4.setEmail("sarah.williams@aiu.edu");
            student4.setPassword(passwordEncoder.encode("password123"));
            student4.setFullName("Sarah Williams");
            student4.setPhoneNumber("555-0104");
            student4.setRole(UserRole.STUDENT);
            users.add(userRepository.save(student4));

            // Create organizer (using ADMIN role)
            User organizer = new User();
            organizer.setEmail("organizer@aiu.edu");
            organizer.setPassword(passwordEncoder.encode("password123"));
            organizer.setFullName("Event Organizer");
            organizer.setPhoneNumber("555-0201");
            organizer.setRole(UserRole.ADMIN);
            users.add(userRepository.save(organizer));

            System.out.println("Created " + users.size() + " users");
        } catch (Exception e) {
            System.err.println("Error creating users: " + e.getMessage());
            e.printStackTrace();
        }
        
        return users;
    }

    private List<Event> createEvents(List<User> users) {
        List<Event> events = new ArrayList<>();
        if (users.isEmpty()) {
            System.out.println("No users available to create events");
            return events;
        }

        try {
            User organizer = users.get(users.size() - 1); // The organizer user (last one created)
            String organizerId = organizer.getId() != null ? organizer.getId().toString() : "1";

            // Event 1 - Tech Conference
            Event event1 = new Event();
            event1.setName("AI and Machine Learning Conference 2025");
            event1.setDescription("Join us for an exciting conference exploring the latest in AI and ML technologies. Network with industry experts and learn about cutting-edge research.");
            event1.setActivityDate(LocalDateTime.now().plusDays(30));
            event1.setLocation("Main Auditorium, AIU Campus");
            event1.setPrice(new BigDecimal("50.00"));
            event1.setCapacity(200);
            event1.setAvailableSeats(200);
            event1.setOrganizerId(organizerId);
            event1.setCategory(ActivityCategory.CONFERENCE);
            event1.setStatus(ActivityStatus.UPCOMING);
            event1.setTopic("Artificial Intelligence and Machine Learning");
            event1.setVenue("Main Auditorium, AIU Campus");
            event1.setAgenda("Registration, Keynote Speech, Technical Sessions, Networking");
            event1.setSpeakers(Arrays.asList("Dr. AI Expert", "Prof. ML Specialist"));
            events.add(eventRepository.save(event1));

            // Event 2 - Career Fair
            Event event2 = new Event();
            event2.setName("Annual Career Fair 2025");
            event2.setDescription("Meet with top employers and explore career opportunities. Bring your resume and be ready to network!");
            event2.setActivityDate(LocalDateTime.now().plusDays(45));
            event2.setLocation("Student Center, Hall A");
            event2.setPrice(BigDecimal.ZERO);
            event2.setCapacity(300);
            event2.setAvailableSeats(300);
            event2.setOrganizerId(organizerId);
            event2.setCategory(ActivityCategory.SEMINAR);
            event2.setStatus(ActivityStatus.UPCOMING);
            event2.setTopic("Career Development and Networking");
            event2.setVenue("Student Center, Hall A");
            event2.setAgenda("Company Booths, Resume Reviews, Interview Workshops");
            events.add(eventRepository.save(event2));

            // Event 3 - Workshop
            Event event3 = new Event();
            event3.setName("Web Development Workshop");
            event3.setDescription("Learn modern web development with React, Node.js, and TypeScript. Hands-on coding sessions included.");
            event3.setActivityDate(LocalDateTime.now().plusDays(20));
            event3.setLocation("Computer Lab 301");
            event3.setPrice(new BigDecimal("75.00"));
            event3.setCapacity(30);
            event3.setAvailableSeats(30);
            event3.setOrganizerId(organizerId);
            event3.setCategory(ActivityCategory.SEMINAR);
            event3.setStatus(ActivityStatus.UPCOMING);
            event3.setTopic("Modern Web Development");
            event3.setVenue("Computer Lab 301");
            event3.setAgenda("React Basics, Node.js Backend, TypeScript Best Practices");
            events.add(eventRepository.save(event3));

            // Event 4 - Cultural Festival
            Event event4 = new Event();
            event4.setName("International Cultural Festival");
            event4.setDescription("Celebrate diversity with food, music, and performances from around the world. Free admission!");
            event4.setActivityDate(LocalDateTime.now().plusDays(10));
            event4.setLocation("Campus Green");
            event4.setPrice(BigDecimal.ZERO);
            event4.setCapacity(500);
            event4.setAvailableSeats(500);
            event4.setOrganizerId(organizerId);
            event4.setCategory(ActivityCategory.CONCERT);
            event4.setStatus(ActivityStatus.UPCOMING);
            event4.setTopic("Cultural Diversity Celebration");
            event4.setVenue("Campus Green");
            event4.setAgenda("Cultural Performances, Food Stalls, Art Exhibitions");
            events.add(eventRepository.save(event4));

            System.out.println("Created " + events.size() + " events");
        } catch (Exception e) {
            System.err.println("Error creating events: " + e.getMessage());
            e.printStackTrace();
        }
        
        return events;
    }

    private void createBookings(List<User> users, List<Event> events) {
        if (users.isEmpty() || events.isEmpty()) {
            System.out.println("Not enough users or events to create bookings");
            return;
        }

        try {
            List<Booking> bookings = new ArrayList<>();

            // Student 1 books event 1
            Booking booking1 = new Booking();
            booking1.setUser(users.get(0));
            booking1.setEvent(events.get(0));
            booking1.setBookingCode("BK-" + System.currentTimeMillis() + "-1");
            booking1.setStatus(BookingStatus.CONFIRMED);
            booking1.setAmountPaid(events.get(0).getPrice().doubleValue());
            bookings.add(bookingRepository.save(booking1));

            // Update available seats
            Event event = events.get(0);
            event.setAvailableSeats(event.getAvailableSeats() - 1);
            eventRepository.save(event);

            // Student 2 books event 2
            if (events.size() > 1) {
                Booking booking2 = new Booking();
                booking2.setUser(users.get(1));
                booking2.setEvent(events.get(1));
                booking2.setBookingCode("BK-" + System.currentTimeMillis() + "-2");
                booking2.setStatus(BookingStatus.CONFIRMED);
                booking2.setAmountPaid(events.get(1).getPrice().doubleValue());
                bookings.add(bookingRepository.save(booking2));

                event = events.get(1);
                event.setAvailableSeats(event.getAvailableSeats() - 1);
                eventRepository.save(event);
            }

            // Student 3 books event 3
            if (users.size() > 2 && events.size() > 2) {
                Booking booking3 = new Booking();
                booking3.setUser(users.get(2));
                booking3.setEvent(events.get(2));
                booking3.setBookingCode("BK-" + System.currentTimeMillis() + "-3");
                booking3.setStatus(BookingStatus.CONFIRMED);
                booking3.setAmountPaid(events.get(2).getPrice().doubleValue());
                bookings.add(bookingRepository.save(booking3));

                event = events.get(2);
                event.setAvailableSeats(event.getAvailableSeats() - 1);
                eventRepository.save(event);
            }

            System.out.println("Created " + bookings.size() + " bookings");
        } catch (Exception e) {
            System.err.println("Error creating bookings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createFeedback(List<User> users, List<Event> events) {
        if (users.isEmpty() || events.isEmpty()) {
            System.out.println("Not enough users or events to create feedback");
            return;
        }

        try {
            List<Feedback> feedbackList = new ArrayList<>();

            // Feedback 1
            Feedback feedback1 = new Feedback();
            feedback1.setUser(users.get(0));
            feedback1.setEvent(events.get(0));
            feedback1.setRating(5);
            feedback1.setComment("Excellent conference! Learned a lot about AI and ML. The speakers were very knowledgeable.");
            feedbackList.add(feedbackRepository.save(feedback1));

            // Feedback 2
            if (users.size() > 1 && events.size() > 1) {
                Feedback feedback2 = new Feedback();
                feedback2.setUser(users.get(1));
                feedback2.setEvent(events.get(1));
                feedback2.setRating(5);
                feedback2.setComment("Amazing career fair! Met with several companies and got two job interviews.");
                feedbackList.add(feedbackRepository.save(feedback2));
            }

            // Feedback 3
            if (users.size() > 2 && events.size() > 2) {
                Feedback feedback3 = new Feedback();
                feedback3.setUser(users.get(2));
                feedback3.setEvent(events.get(2));
                feedback3.setRating(4);
                feedback3.setComment("Great workshop! Very practical and hands-on. Would love more advanced sessions.");
                feedbackList.add(feedbackRepository.save(feedback3));
            }

            System.out.println("Created " + feedbackList.size() + " feedback entries");
        } catch (Exception e) {
            System.err.println("Error creating feedback: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createNotifications(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users available to create notifications");
            return;
        }

        try {
            List<Notification> notifications = new ArrayList<>();

            // Welcome notifications for students (skip the organizer)
            for (int i = 0; i < Math.min(4, users.size()); i++) {
                Notification notification = new Notification();
                notification.setUser(users.get(i));
                notification.setMessage("Welcome to AIU Trips and Events! Explore upcoming events and trips.");
                notification.setType("INFO");
                notification.setIsRead(false);
                notifications.add(notificationRepository.save(notification));
            }

            // Event reminder notification for first user
            if (!users.isEmpty()) {
                Notification reminder = new Notification();
                reminder.setUser(users.get(0));
                reminder.setMessage("Reminder: AI and Machine Learning Conference starts in 30 days!");
                reminder.setType("INFO");
                reminder.setIsRead(false);
                notifications.add(notificationRepository.save(reminder));
            }

            System.out.println("Created " + notifications.size() + " notifications");
        } catch (Exception e) {
            System.err.println("Error creating notifications: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


        // Event 2 - Mountain Trip
        Event event2 = new Event();
        event2.setTitle("Mountain Hiking Adventure");
        event2.setDescription("Experience the beauty of nature with a guided hiking trip to the nearby mountains. Suitable for all fitness levels.");
        event2.setType(EventType.TRIP);
        event2.setStartDate(LocalDateTime.now().plusDays(15));
        event2.setEndDate(LocalDateTime.now().plusDays(17));
        event2.setLocation("Blue Ridge Mountains");
        event2.setPrice(150.0);
        event2.setCapacity(50);
        event2.setAvailableSeats(50);
        event2.setImageUrl("/images/mountain-trip.jpg");
        event2.setCreatedBy(organizer);
        event2.setStatus(EventStatus.ACTIVE);
        events.add(eventRepository.save(event2));

        // Event 3 - Career Fair
        Event event3 = new Event();
        event3.setTitle("Annual Career Fair 2025");
        event3.setDescription("Meet with top employers and explore career opportunities. Bring your resume and be ready to network!");
        event3.setType(EventType.EVENT);
        event3.setStartDate(LocalDateTime.now().plusDays(45));
        event3.setEndDate(LocalDateTime.now().plusDays(45).plusHours(6));
        event3.setLocation("Student Center, Hall A");
        event3.setPrice(0.0);
        event3.setCapacity(300);
        event3.setAvailableSeats(300);
        event3.setImageUrl("/images/career-fair.jpg");
        event3.setCreatedBy(organizer);
        event3.setStatus(EventStatus.ACTIVE);
        events.add(eventRepository.save(event3));

        // Event 4 - Beach Trip
        Event event4 = new Event();
        event4.setTitle("Summer Beach Getaway");
        event4.setDescription("Relax and unwind at the beautiful coastal beaches. Includes accommodation, meals, and beach activities.");
        event4.setType(EventType.TRIP);
        event4.setStartDate(LocalDateTime.now().plusDays(60));
        event4.setEndDate(LocalDateTime.now().plusDays(63));
        event4.setLocation("Sunny Beach Resort");
        event4.setPrice(250.0);
        event4.setCapacity(40);
        event4.setAvailableSeats(40);
        event4.setImageUrl("/images/beach-trip.jpg");
        event4.setCreatedBy(organizer);
        event4.setStatus(EventStatus.ACTIVE);
        events.add(eventRepository.save(event4));

        // Event 5 - Workshop
        Event event5 = new Event();
        event5.setTitle("Web Development Workshop");
        event5.setDescription("Learn modern web development with React, Node.js, and TypeScript. Hands-on coding sessions included.");
        event5.setType(EventType.EVENT);
        event5.setStartDate(LocalDateTime.now().plusDays(20));
        event5.setEndDate(LocalDateTime.now().plusDays(20).plusHours(4));
        event5.setLocation("Computer Lab 301");
        event5.setPrice(75.0);
        event5.setCapacity(30);
        event5.setAvailableSeats(30);
        event5.setImageUrl("/images/web-workshop.jpg");
        event5.setCreatedBy(organizer);
        event5.setStatus(EventStatus.ACTIVE);
        events.add(eventRepository.save(event5));

        // Event 6 - Cultural Festival
        Event event6 = new Event();
        event6.setTitle("International Cultural Festival");
        event6.setDescription("Celebrate diversity with food, music, and performances from around the world. Free admission!");
        event6.setType(EventType.EVENT);
        event6.setStartDate(LocalDateTime.now().plusDays(10));
        event6.setEndDate(LocalDateTime.now().plusDays(10).plusHours(8));
        event6.setLocation("Campus Green");
        event6.setPrice(0.0);
        event6.setCapacity(500);
        event6.setAvailableSeats(500);
        event6.setImageUrl("/images/cultural-festival.jpg");
        event6.setCreatedBy(organizer);
        event6.setStatus(EventStatus.ACTIVE);
        events.add(eventRepository.save(event6));

        System.out.println("Created " + events.size() + " events");
        return events;
    }

    private void createBookings(List<User> users, List<Event> events) {
        List<Booking> bookings = new ArrayList<>();

        // Student 1 bookings
        Booking booking1 = new Booking();
        booking1.setUser(users.get(0));
        booking1.setEvent(events.get(0));
        booking1.setBookingCode("BK-" + System.currentTimeMillis() + "-1");
        booking1.setStatus(BookingStatus.CONFIRMED);
        booking1.setAmountPaid(events.get(0).getPrice());
        bookings.add(bookingRepository.save(booking1));

        // Update available seats
        Event event = events.get(0);
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        // Student 2 bookings
        Booking booking2 = new Booking();
        booking2.setUser(users.get(1));
        booking2.setEvent(events.get(1));
        booking2.setBookingCode("BK-" + System.currentTimeMillis() + "-2");
        booking2.setStatus(BookingStatus.CONFIRMED);
        booking2.setAmountPaid(events.get(1).getPrice());
        bookings.add(bookingRepository.save(booking2));

        event = events.get(1);
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        // Student 3 bookings
        Booking booking3 = new Booking();
        booking3.setUser(users.get(2));
        booking3.setEvent(events.get(2));
        booking3.setBookingCode("BK-" + System.currentTimeMillis() + "-3");
        booking3.setStatus(BookingStatus.CONFIRMED);
        booking3.setAmountPaid(events.get(2).getPrice());
        bookings.add(bookingRepository.save(booking3));

        event = events.get(2);
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepository.save(event);

        System.out.println("Created " + bookings.size() + " bookings");
    }

    private void createFeedback(List<User> users, List<Event> events) {
        List<Feedback> feedbackList = new ArrayList<>();

        // Feedback 1
        Feedback feedback1 = new Feedback();
        feedback1.setUser(users.get(0));
        feedback1.setEvent(events.get(0));
        feedback1.setRating(5);
        feedback1.setComment("Excellent conference! Learned a lot about AI and ML. The speakers were very knowledgeable.");
        feedbackList.add(feedbackRepository.save(feedback1));

        // Feedback 2
        Feedback feedback2 = new Feedback();
        feedback2.setUser(users.get(1));
        feedback2.setEvent(events.get(1));
        feedback2.setRating(4);
        feedback2.setComment("Great hiking experience. The views were breathtaking. Would recommend to everyone!");
        feedbackList.add(feedbackRepository.save(feedback2));

        // Feedback 3
        Feedback feedback3 = new Feedback();
        feedback3.setUser(users.get(2));
        feedback3.setEvent(events.get(2));
        feedback3.setRating(5);
        feedback3.setComment("Amazing career fair! Met with several companies and got two job interviews.");
        feedbackList.add(feedbackRepository.save(feedback3));

        System.out.println("Created " + feedbackList.size() + " feedback entries");
    }

    private void createNotifications(List<User> users) {
        List<Notification> notifications = new ArrayList<>();

        // Welcome notifications for students
        for (int i = 0; i < 4; i++) {
            Notification notification = new Notification();
            notification.setUser(users.get(i));
            notification.setMessage("Welcome to AIU Trips and Events! Explore upcoming events and trips.");
            notification.setType("INFO");
            notification.setIsRead(false);
            notifications.add(notificationRepository.save(notification));
        }

        // Event reminder notification
        Notification reminder = new Notification();
        reminder.setUser(users.get(0));
        reminder.setMessage("Reminder: AI and Machine Learning Conference starts in 30 days!");
        reminder.setType("INFO");
        reminder.setIsRead(false);
        notifications.add(notificationRepository.save(reminder));

        System.out.println("Created " + notifications.size() + " notifications");
    }
}
