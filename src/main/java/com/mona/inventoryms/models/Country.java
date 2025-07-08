package com.mona.inventoryms.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.mona.inventoryms.security.models.Auditable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "country")
@Data
public class Country extends Auditable<String> {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String code;
	private String capital;
	private String description;
	private String nationality;
	private String continent;
	
	@OneToMany(mappedBy="country")
	@JsonIgnore
	private List<com.mona.inventoryms.models.State> states;
}
