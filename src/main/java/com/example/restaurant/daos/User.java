package com.example.restaurant.daos;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User {
    @Column(name = "NAME")
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIPCODE")
    private String zipcode;

    @Column(name = "PEANUT")
    private Boolean carePeanut;

    @Column(name = "EGG")
    private Boolean careEgg;

    @Column(name = "DAIRY")
    private Boolean careDairy;
}
