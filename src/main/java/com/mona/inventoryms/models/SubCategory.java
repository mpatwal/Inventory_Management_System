package com.mona.inventoryms.models;

import com.fasterxml.jackson.annotation.*;

import com.mona.inventoryms.security.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Table(name = "sub_category")
@Data
@JsonIgnoreProperties()
public class SubCategory extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "categoryid", insertable = false, updatable = false)
    private Category category;
    private Long categoryid;

}
