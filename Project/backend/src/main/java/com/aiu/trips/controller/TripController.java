package com.aiu.trips.controller;

import com.aiu.trips.dto.TripDTO;
import com.aiu.trips.enums.EventType;
import com.aiu.trips.model.Trip;
import com.aiu.trips.model.User;
import com.aiu.trips.repository.UserRepository;
import com.aiu.trips.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@RequestBody TripDTO tripDTO, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Trip trip = new Trip();
        trip.setTitle(tripDTO.getTitle());
        trip.setDescription(tripDTO.getDescription());
        trip.setType(EventType.TRIP);
        trip.setStartDate(tripDTO.getStartDate());
        trip.setEndDate(tripDTO.getEndDate());
        trip.setLocation(tripDTO.getLocation());
        trip.setPrice(tripDTO.getPrice());
        trip.setCapacity(tripDTO.getCapacity());
        trip.setImageUrl(tripDTO.getImageUrl());
        trip.setCreatedBy(user);
        
        trip.setDestination(tripDTO.getDestination());
        trip.setDuration(tripDTO.getDuration());
        trip.setItinerary(tripDTO.getItinerary());
        trip.setIncludedServices(tripDTO.getIncludedServices());
        trip.setTransportationType(tripDTO.getTransportationType());

        Trip savedTrip = tripService.createTrip(trip);
        return ResponseEntity.ok(convertToDTO(savedTrip));
    }

    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(convertToDTO(trip));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDTO> updateTrip(@PathVariable Long id, @RequestBody TripDTO tripDTO) {
        Trip trip = new Trip();
        trip.setTitle(tripDTO.getTitle());
        trip.setDescription(tripDTO.getDescription());
        trip.setStartDate(tripDTO.getStartDate());
        trip.setEndDate(tripDTO.getEndDate());
        trip.setLocation(tripDTO.getLocation());
        trip.setPrice(tripDTO.getPrice());
        trip.setCapacity(tripDTO.getCapacity());
        trip.setImageUrl(tripDTO.getImageUrl());
        
        trip.setDestination(tripDTO.getDestination());
        trip.setDuration(tripDTO.getDuration());
        trip.setItinerary(tripDTO.getItinerary());
        trip.setIncludedServices(tripDTO.getIncludedServices());
        trip.setTransportationType(tripDTO.getTransportationType());

        Trip updatedTrip = tripService.updateTrip(id, trip);
        return ResponseEntity.ok(convertToDTO(updatedTrip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<TripDTO>> searchTrips(@RequestParam String keyword) {
        List<Trip> trips = tripService.searchTrips(keyword);
        return ResponseEntity.ok(trips.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }

    private TripDTO convertToDTO(Trip trip) {
        TripDTO dto = new TripDTO();
        dto.setId(trip.getId());
        dto.setTitle(trip.getTitle());
        dto.setDescription(trip.getDescription());
        dto.setType(trip.getType());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setLocation(trip.getLocation());
        dto.setPrice(trip.getPrice());
        dto.setCapacity(trip.getCapacity());
        dto.setAvailableSeats(trip.getAvailableSeats());
        dto.setImageUrl(trip.getImageUrl());
        dto.setStatus(trip.getStatus());
        
        dto.setDestination(trip.getDestination());
        dto.setDuration(trip.getDuration());
        dto.setItinerary(trip.getItinerary());
        dto.setIncludedServices(trip.getIncludedServices());
        dto.setTransportationType(trip.getTransportationType());
        
        if (trip.getCreatedBy() != null) {
            dto.setCreatedById(trip.getCreatedBy().getId());
            dto.setCreatedByName(trip.getCreatedBy().getFullName());
        }
        
        return dto;
    }
}
