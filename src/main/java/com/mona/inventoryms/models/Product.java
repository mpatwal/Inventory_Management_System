package com.mona.inventoryms.models;

import com.mona.inventoryms.security.Auditable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 75)
    private String title;

    @Column(columnDefinition = "TINYTEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "subcategoryid")
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;
}
