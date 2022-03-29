package com.example.restaurant.daos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "REVIEW")
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NAME")
    private String name;
    @Column(name = "ID")
    private Long id;
    @Column(name = "PEANUT", nullable = true)
    private Double peanut;
    @Column(name = "EGG", nullable = true)
    private Double egg;
    @Column(name = "DAIRY", nullable = true)
    private Double dairy;
    @Column(name = "COMMENTARY", nullable = true)
    private String commentary;

}
