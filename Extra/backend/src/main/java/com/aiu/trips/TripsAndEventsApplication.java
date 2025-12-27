package com.aiu.trips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TripsAndEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripsAndEventsApplication.class, args);
    }
}
