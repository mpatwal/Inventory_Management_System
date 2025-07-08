package com.mona.inventoryms.models;

import com.fasterxml.jackson.annotation.*;
import com.mona.inventoryms.security.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "category")
@Data
public class Category extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 75)
    private String title;

    @Column(length = 100)
    private String metaTitle;

    @Column(nullable = false, length = 100)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    @JsonIgnore
    private List<com.mona.inventoryms.models.SubCategory> subCategories;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<com.mona.inventoryms.models.Product> products;

}
