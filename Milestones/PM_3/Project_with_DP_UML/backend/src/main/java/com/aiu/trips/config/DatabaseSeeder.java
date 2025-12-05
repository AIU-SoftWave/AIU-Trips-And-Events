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
 * This runs after DataInitializer to add comprehensive sample data
 */
@Component
@Order(2)
@Profile({"dev", "docker"})
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
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
        if (userRepository.count() > 1) {
            System.out.println("Database already seeded. Skipping...");
            return;
        }

        System.out.println("Starting database seeding...");

        // Create users
        List<User> users = createUsers();
        
        // Create events and trips
        List<Activity> activities = createActivities(users);
        
        // Create bookings
        createBookings(users, activities);
        
        // Create feedback
        createFeedback(users, activities);
        
        // Create notifications
        createNotifications(users);

        System.out.println("Database seeding completed successfully!");
        System.out.println("Sample credentials:");
        System.out.println("  Admin: admin@aiu.edu / admin123");
        System.out.println("  Student: john.doe@aiu.edu / password123");
        System.out.println("  Student: jane.smith@aiu.edu / password123");
        System.out.println("  Organizer: organizer@aiu.edu / password123");
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

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
        return users;
    }

    private List<Activity> createActivities(List<User> users) {
        List<Activity> activities = new ArrayList<>();
        User organizer = users.get(4); // The organizer user

        // Event 1 - AI Conference
        Event event1 = new Event();
        event1.setName("AI and Machine Learning Conference 2025");
        event1.setDescription("Join us for an exciting conference exploring the latest in AI and ML technologies. Network with industry experts and learn about cutting-edge research.");
        event1.setActivityDate(LocalDateTime.now().plusDays(30));
        event1.setLocation("Main Auditorium, AIU Campus");
        event1.setPrice(new BigDecimal("50.00"));
        event1.setCapacity(200);
        event1.setAvailableSeats(200);
        event1.setCategory(ActivityCategory.CONFERENCE);
        event1.setStatus(ActivityStatus.UPCOMING);
        event1.setOrganizer(organizer);
        event1.setTopic("Artificial Intelligence and Machine Learning");
        event1.setVenue("Main Auditorium");
        event1.setSpeakers(Arrays.asList("Dr. John Smith", "Prof. Emily Chen", "Dr. Robert Johnson"));
        event1.setAgenda("9:00 AM - Registration\n10:00 AM - Keynote Speech\n12:00 PM - Lunch Break\n2:00 PM - Panel Discussion\n5:00 PM - Closing Remarks");
        activities.add(eventRepository.save(event1));

        // Trip 1 - Mountain Hiking
        Trip trip1 = new Trip();
        trip1.setName("Mountain Hiking Adventure");
        trip1.setDescription("Experience the beauty of nature with a guided hiking trip to the nearby mountains. Suitable for all fitness levels.");
        trip1.setActivityDate(LocalDateTime.now().plusDays(15));
        trip1.setLocation("Blue Ridge Mountains");
        trip1.setPrice(new BigDecimal("150.00"));
        trip1.setCapacity(50);
        trip1.setAvailableSeats(50);
        trip1.setCategory(ActivityCategory.ADVENTURE_TRIP);
        trip1.setStatus(ActivityStatus.UPCOMING);
        trip1.setOrganizer(organizer);
        trip1.setDestination("Blue Ridge Mountains");
        trip1.setDurationDays(3);
        trip1.setTransportMode("Bus");
        trip1.setStartLocation("AIU Campus");
        trip1.setEndLocation("Blue Ridge Mountains");
        trip1.setItinerary("Day 1: Travel and setup\nDay 2: Mountain hiking\nDay 3: Return journey");
        activities.add(tripRepository.save(trip1));

        // Event 2 - Career Fair
        Event event2 = new Event();
        event2.setName("Annual Career Fair 2025");
        event2.setDescription("Meet with top employers and explore career opportunities. Bring your resume and be ready to network!");
        event2.setActivityDate(LocalDateTime.now().plusDays(45));
        event2.setLocation("Student Center, Hall A");
        event2.setPrice(new BigDecimal("0.00"));
        event2.setCapacity(300);
        event2.setAvailableSeats(300);
        event2.setCategory(ActivityCategory.SEMINAR);
        event2.setStatus(ActivityStatus.UPCOMING);
        event2.setOrganizer(organizer);
        event2.setTopic("Career Development and Job Opportunities");
        event2.setVenue("Student Center, Hall A");
        event2.setSpeakers(Arrays.asList("HR Manager - Tech Corp", "Recruiter - Finance Inc"));
        event2.setAgenda("10:00 AM - Opening\n11:00 AM - Company Booths Open\n3:00 PM - Interview Sessions\n6:00 PM - Closing");
        activities.add(eventRepository.save(event2));

        // Trip 2 - Beach Getaway
        Trip trip2 = new Trip();
        trip2.setName("Summer Beach Getaway");
        trip2.setDescription("Relax and unwind at the beautiful coastal beaches. Includes accommodation, meals, and beach activities.");
        trip2.setActivityDate(LocalDateTime.now().plusDays(60));
        trip2.setLocation("Sunny Beach Resort");
        trip2.setPrice(new BigDecimal("250.00"));
        trip2.setCapacity(40);
        trip2.setAvailableSeats(40);
        trip2.setCategory(ActivityCategory.FIELD_TRIP);
        trip2.setStatus(ActivityStatus.UPCOMING);
        trip2.setOrganizer(organizer);
        trip2.setDestination("Sunny Beach Resort");
        trip2.setDurationDays(4);
        trip2.setTransportMode("Bus");
        trip2.setStartLocation("AIU Campus");
        trip2.setEndLocation("Sunny Beach Resort");
        trip2.setItinerary("Day 1: Travel to resort\nDay 2-3: Beach activities\nDay 4: Return journey");
        activities.add(tripRepository.save(trip2));

        // Event 3 - Web Development Workshop
        Event event3 = new Event();
        event3.setName("Web Development Workshop");
        event3.setDescription("Learn modern web development with React, Node.js, and TypeScript. Hands-on coding sessions included.");
        event3.setActivityDate(LocalDateTime.now().plusDays(20));
        event3.setLocation("Computer Lab 301");
        event3.setPrice(new BigDecimal("75.00"));
        event3.setCapacity(30);
        event3.setAvailableSeats(30);
        event3.setCategory(ActivityCategory.SEMINAR);
        event3.setStatus(ActivityStatus.UPCOMING);
        event3.setOrganizer(organizer);
        event3.setTopic("Modern Web Development");
        event3.setVenue("Computer Lab 301");
        event3.setSpeakers(Arrays.asList("Senior Developer - WebTech"));
        event3.setAgenda("1:00 PM - Introduction\n2:00 PM - React Basics\n3:00 PM - Node.js Overview\n4:00 PM - Hands-on Project");
        activities.add(eventRepository.save(event3));

        // Event 4 - Cultural Festival
        Event event4 = new Event();
        event4.setName("International Cultural Festival");
        event4.setDescription("Celebrate diversity with food, music, and performances from around the world. Free admission!");
        event4.setActivityDate(LocalDateTime.now().plusDays(10));
        event4.setLocation("Campus Green");
        event4.setPrice(new BigDecimal("0.00"));
        event4.setCapacity(500);
        event4.setAvailableSeats(500);
        event4.setCategory(ActivityCategory.CULTURAL_VISIT);
        event4.setStatus(ActivityStatus.UPCOMING);
        event4.setOrganizer(organizer);
        event4.setTopic("Cultural Diversity and Heritage");
        event4.setVenue("Campus Green");
        event4.setSpeakers(Arrays.asList("Cultural Ambassador", "International Students Association"));
        event4.setAgenda("12:00 PM - Opening Ceremony\n1:00 PM - Food Stalls\n3:00 PM - Cultural Performances\n8:00 PM - Closing");
        activities.add(eventRepository.save(event4));

        System.out.println("Created " + activities.size() + " activities (events and trips)");
        return activities;
    }

    private void createBookings(List<User> users, List<Activity> activities) {
        List<Booking> bookings = new ArrayList<>();

        // Student 1 bookings
        Booking booking1 = new Booking();
        booking1.setUser(users.get(0));
        booking1.setActivity(activities.get(0));
        booking1.setBookingCode("BK-" + System.currentTimeMillis() + "-1");
        booking1.setStatus(BookingStatus.CONFIRMED);
        booking1.setAmountPaid(activities.get(0).getPrice().doubleValue());
        bookings.add(bookingRepository.save(booking1));

        // Update available seats
        Activity activity = activities.get(0);
        activity.setAvailableSeats(activity.getAvailableSeats() - 1);
        activityRepository.save(activity);

        // Student 2 bookings
        Booking booking2 = new Booking();
        booking2.setUser(users.get(1));
        booking2.setActivity(activities.get(1));
        booking2.setBookingCode("BK-" + System.currentTimeMillis() + "-2");
        booking2.setStatus(BookingStatus.CONFIRMED);
        booking2.setAmountPaid(activities.get(1).getPrice().doubleValue());
        bookings.add(bookingRepository.save(booking2));

        activity = activities.get(1);
        activity.setAvailableSeats(activity.getAvailableSeats() - 1);
        activityRepository.save(activity);

        // Student 3 bookings
        Booking booking3 = new Booking();
        booking3.setUser(users.get(2));
        booking3.setActivity(activities.get(2));
        booking3.setBookingCode("BK-" + System.currentTimeMillis() + "-3");
        booking3.setStatus(BookingStatus.CONFIRMED);
        booking3.setAmountPaid(activities.get(2).getPrice().doubleValue());
        bookings.add(bookingRepository.save(booking3));

        activity = activities.get(2);
        activity.setAvailableSeats(activity.getAvailableSeats() - 1);
        activityRepository.save(activity);

        System.out.println("Created " + bookings.size() + " bookings");
    }

    private void createFeedback(List<User> users, List<Activity> activities) {
        List<Feedback> feedbackList = new ArrayList<>();

        // Feedback 1
        Feedback feedback1 = new Feedback();
        feedback1.setUser(users.get(0));
        feedback1.setActivity(activities.get(0));
        feedback1.setRating(5);
        feedback1.setComment("Excellent conference! Learned a lot about AI and ML. The speakers were very knowledgeable.");
        feedbackList.add(feedbackRepository.save(feedback1));

        // Feedback 2
        Feedback feedback2 = new Feedback();
        feedback2.setUser(users.get(1));
        feedback2.setActivity(activities.get(1));
        feedback2.setRating(4);
        feedback2.setComment("Great hiking experience. The views were breathtaking. Would recommend to everyone!");
        feedbackList.add(feedbackRepository.save(feedback2));

        // Feedback 3
        Feedback feedback3 = new Feedback();
        feedback3.setUser(users.get(2));
        feedback3.setActivity(activities.get(2));
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
            notification.setType(NotificationType.NEW_EVENT);
            notification.setIsRead(false);
            notifications.add(notificationRepository.save(notification));
        }

        // Event reminder notification
        Notification reminder = new Notification();
        reminder.setUser(users.get(0));
        reminder.setMessage("Reminder: AI and Machine Learning Conference starts in 30 days!");
        reminder.setType(NotificationType.REMINDER);
        reminder.setIsRead(false);
        notifications.add(notificationRepository.save(reminder));

        System.out.println("Created " + notifications.size() + " notifications");
    }
}
