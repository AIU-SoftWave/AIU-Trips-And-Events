-- Database Initialization Script for Performance Testing
-- Creates production-like dataset for load testing

-- Drop existing tables if they exist (for clean setup)
-- DO NOT run this in production!

-- Create events table with proper indexes for performance
CREATE TABLE IF NOT EXISTS events (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(50) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    location VARCHAR(255),
    price DECIMAL(10, 2),
    capacity INTEGER,
    image_url VARCHAR(500),
    status VARCHAR(50) NOT NULL,
    created_by_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for optimized queries
CREATE INDEX IF NOT EXISTS idx_events_status ON events(status);
CREATE INDEX IF NOT EXISTS idx_events_start_date ON events(start_date);
CREATE INDEX IF NOT EXISTS idx_events_type ON events(type);
CREATE INDEX IF NOT EXISTS idx_events_status_start_date ON events(status, start_date);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seed admin user
INSERT INTO users (name, email, password, role) 
VALUES ('Admin', 'admin@aiu.edu', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhkW', 'ADMIN')
ON CONFLICT (email) DO NOTHING;

-- Seed 100 test events for performance testing
-- Mix of upcoming, ongoing, and past events for realistic testing
INSERT INTO events (title, description, type, start_date, end_date, location, price, capacity, status, created_by_id)
SELECT 
    'Event ' || generate_series || ' - ' || 
    CASE (generate_series % 5)
        WHEN 0 THEN 'Tech Conference'
        WHEN 1 THEN 'Sports Day'
        WHEN 2 THEN 'Cultural Festival'
        WHEN 3 THEN 'Career Fair'
        ELSE 'Workshop'
    END as title,
    'This is a test event for performance testing. ' ||
    'It contains detailed description with multiple lines of text to simulate real-world data. ' ||
    'The event will feature various activities and attractions for students.' as description,
    CASE (generate_series % 2)
        WHEN 0 THEN 'EVENT'
        ELSE 'TRIP'
    END as type,
    '2025-01-01'::timestamp + (generate_series || ' days')::interval as start_date,
    '2025-01-01'::timestamp + ((generate_series + 1) || ' days')::interval as end_date,
    CASE (generate_series % 4)
        WHEN 0 THEN 'Main Auditorium'
        WHEN 1 THEN 'Sports Complex'
        WHEN 2 THEN 'Campus Grounds'
        ELSE 'Conference Hall'
    END as location,
    (generate_series % 100) + 10.00 as price,
    ((generate_series % 10) + 5) * 10 as capacity,
    CASE 
        WHEN generate_series <= 70 THEN 'ACTIVE'
        WHEN generate_series <= 90 THEN 'ACTIVE'
        ELSE 'COMPLETED'
    END as status,
    1 as created_by_id
FROM generate_series(1, 100);

-- Create statistics for query optimizer
ANALYZE events;
ANALYZE users;
