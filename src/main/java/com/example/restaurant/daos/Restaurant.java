package com.example.restaurant.daos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TEST")
@Getter
@Setter
public class Restaurant
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PEANUT")
    private Double peanut;

    @Column(name = "EGG")
    private Double egg;

    @Column(name = "DAIRY")
    private Double dairy;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public Double getPeanut() {
//        return peanut;
//    }
//
//    public void setPeanut(Double peanut) {
//        this.peanut = peanut;
//    }
//
//    public Double getEgg() {
//        return egg;
//    }
//
//    public void setEgg(Double egg) {
//        this.egg = egg;
//    }
//
//    public Double getDairy() {
//        return dairy;
//    }
//
//    public void setDairy(Double dairy) {
//        this.dairy = dairy;
//    }
}
