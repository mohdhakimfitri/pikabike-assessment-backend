package com.pikabike.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bikes")
@Data
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String type;

    @Column(nullable = false)
    private double pricePerHour;

    private boolean available = true;

    private String description;

    private String imageUrl;
}