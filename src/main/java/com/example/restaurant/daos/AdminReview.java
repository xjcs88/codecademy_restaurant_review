package com.example.restaurant.daos;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "ADMINREVIEW")
@Getter
@Setter
public class AdminReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCEPTREVIEW")
    private boolean acceptReview;
}
