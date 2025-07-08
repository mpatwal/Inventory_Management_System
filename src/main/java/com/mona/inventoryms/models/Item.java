package com.mona.inventoryms.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import com.mona.inventoryms.security.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "item")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Item extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "productid", insertable = false, updatable = false)
    private com.mona.inventoryms.models.Product product;
    private Long productid;

    @ManyToOne()
    @JoinColumn(name = "brandid", insertable = false, updatable = false)
    private Brand brand;
    private Long brandid;

    @ManyToOne
    @JoinColumn(name = "supplierid", insertable = false, updatable = false)
    private com.mona.inventoryms.models.Supplier supplier;
    private Long supplierid;

    @Column(length = 100)
    private String sku;

    private Float mrp = 0.0f;

    private Float discount = 0.0f;

    private Float price = 0.0f;

    private Short quantity = 0;

    private Short sold = 0;

    private Short available = 0;

    private Short defective = 0;

}
