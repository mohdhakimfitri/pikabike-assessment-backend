package com.pikabike.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Data
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "bike_id", nullable = true)
    private Bike bike;

    @Column(nullable = false)
    private int durationHours;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private String status = "PENDING";

    private LocalDateTime requestDate = LocalDateTime.now();

    private LocalDateTime approvedDate;

    private String paymentStatus = "PENDING";
}