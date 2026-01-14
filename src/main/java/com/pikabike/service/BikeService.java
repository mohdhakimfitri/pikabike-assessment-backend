package com.pikabike.service;

import com.pikabike.model.Bike;
import com.pikabike.repository.BikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {

    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    public Bike addBike(Bike bike) {
        return bikeRepository.save(bike);
    }
}