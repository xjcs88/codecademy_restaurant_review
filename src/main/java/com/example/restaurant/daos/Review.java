package com.example.restaurant.daos;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Table(name = "REVIEW")
@Getter
@Setter
public class Review {


    @Column(name = "NAME")
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "STATUS")
    private Status status;
}
