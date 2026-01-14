package com.pikabike.controller;

import com.pikabike.dto.RentalRequestDTO;
import com.pikabike.model.Rental;
import com.pikabike.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody RentalRequestDTO dto) {
        Rental createdRental = rentalService.createRental(dto);
        return ResponseEntity.ok(createdRental);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Rental>> getPending() {
        return ResponseEntity.ok(rentalService.getPendingRentals());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Rental> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(rentalService.updateStatus(id, status));
    }
}