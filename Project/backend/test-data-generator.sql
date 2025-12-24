-- Test Data Generator for Production-like Dataset
-- Creates realistic data volume to detect "tail latency" issues

-- Ensure database is ready
\c tripsdb_test;

-- Create tables (will be created by Hibernate, but ensuring they exist)
-- The actual schema will be created by Spring Boot JPA

-- Generate a production-sized dataset
-- This script will be executed after Hibernate creates the schema

-- Function to generate test data (will be called after application starts)
-- For now, we just ensure the database is initialized

-- Create indexes for performance (if not already created by Hibernate)
-- These will be created by Spring Boot, but we document the expected indexes:

-- Expected Indexes (created by JPA/Hibernate):
-- CREATE INDEX idx_user_email ON users(email);
-- CREATE INDEX idx_event_date ON events(event_date);
-- CREATE INDEX idx_booking_user ON bookings(user_id);
-- CREATE INDEX idx_booking_event ON bookings(event_id);

-- Note: Actual data population will be done through the application
-- or a separate data generation script after the schema is ready

SELECT 'Database initialized for testing' as status;
