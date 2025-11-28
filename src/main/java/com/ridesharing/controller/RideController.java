package com.ridesharing.controller;

import com.ridesharing.model.Ride;
import com.ridesharing.repository.RideRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideRepository rideRepo;

    public RideController(RideRepository rideRepo) {
        this.rideRepo = rideRepo;
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestRide(@RequestBody Ride ride) {

        if (ride.getPassengerName() == null || ride.getPickup() == null || ride.getDropoff() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Missing fields"));
        }

        ride.setStatus("REQUESTED");
        Ride saved = rideRepo.save(ride);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRide(@PathVariable Long id) {

        if (!rideRepo.existsById(id)) {
            return ResponseEntity.status(404)
                    .body(Map.of("error", "Ride not found"));
        }

        rideRepo.deleteById(id);

        return ResponseEntity.ok(Map.of("ok", true));
    }
}
