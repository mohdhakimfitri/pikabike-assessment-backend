package com.pikabike.service;

import com.pikabike.dto.RentalRequestDTO;
import com.pikabike.model.Bike;
import com.pikabike.model.Rental;
import com.pikabike.model.User;
import com.pikabike.repository.BikeRepository;
import com.pikabike.repository.RentalRepository;
import com.pikabike.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BikeRepository bikeRepository;
    private final UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository,
                         BikeRepository bikeRepository,
                         UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.bikeRepository = bikeRepository;
        this.userRepository = userRepository;
    }

    public Rental createRental(RentalRequestDTO dto) {
        if (dto.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID diperlukan");
        }
        if (dto.getBikeId() == null) {
            throw new IllegalArgumentException("Bike ID diperlukan");
        }

        User customer = userRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer tidak dijumpai dengan ID: " + dto.getCustomerId()));

        Bike bike = bikeRepository.findById(dto.getBikeId())
                .orElseThrow(() -> new RuntimeException("Bike tidak dijumpai dengan ID: " + dto.getBikeId()));

        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setBike(bike);
        rental.setDurationHours(dto.getDurationHours());
        rental.setTotalPrice(dto.getTotalPrice());

        rental.setStatus("PENDING");
        rental.setRequestDate(LocalDateTime.now());

        return rentalRepository.save(rental);
    }

    public List<Rental> getPendingRentals() {
        return rentalRepository.findByStatus("PENDING");
    }

    public Rental updateStatus(Long id, String status) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental tidak dijumpai dengan ID: " + id));

        rental.setStatus(status);

        if ("APPROVED".equals(status)) {
            rental.setApprovedDate(LocalDateTime.now());
        }

        return rentalRepository.save(rental);
    }
}