package com.pikabike.controller;

import com.pikabike.model.Bike;
import com.pikabike.repository.BikeRepository;
import com.pikabike.service.BikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/bikes")
@CrossOrigin(origins = "http://localhost:3000")
public class BikeController {

    private final BikeService bikeService;
    private final BikeRepository bikeRepository;

    public BikeController(BikeService bikeService, BikeRepository bikeRepository) {
        this.bikeService = bikeService;
        this.bikeRepository = bikeRepository;
    }

    @GetMapping
    public List<Bike> getAll() {
        return bikeService.getAllBikes();
    }

    @PostMapping
    public Bike addBike(@RequestBody Bike bike) {
        return bikeService.addBike(bike);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bike> updateBike(@PathVariable Long id, @RequestBody Bike updatedBike) {
        Bike bike = bikeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bike tidak dijumpai dengan ID: " + id));

        bike.setName(updatedBike.getName());
        bike.setType(updatedBike.getType());
        bike.setPricePerHour(updatedBike.getPricePerHour());
        bike.setAvailable(updatedBike.isAvailable());

        if (updatedBike.getImageUrl() != null) {
            bike.setImageUrl(updatedBike.getImageUrl());
        }

        Bike savedBike = bikeRepository.save(bike);
        return ResponseEntity.ok(savedBike);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
        if (!bikeRepository.existsById(id)) {
            throw new RuntimeException("Bike tidak dijumpai dengan ID: " + id);
        }
        bikeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}