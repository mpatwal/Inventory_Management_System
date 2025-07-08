package com.mona.inventoryms.models;

import com.mona.inventoryms.security.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "customer")
@Data
public class Customer extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String middleName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 15)
    private String mobile;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String line1;

    @Column(length = 50)
    private String line2;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String province;

    @Column(length = 50)
    private String country;

    // Getters and Setters
}

