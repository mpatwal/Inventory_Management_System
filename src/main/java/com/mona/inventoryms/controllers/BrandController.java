package com.mona.inventoryms.controllers;


import com.mona.inventoryms.services.BrandService;
import com.mona.inventoryms.models.Brand;
import com.mona.inventoryms.models.BrandStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public List<Brand> getBrands(){
        return brandService.getAllBrands();
    }

    @GetMapping("/brand/{id}")
    public Brand getBrand(@PathVariable("id") Long id){
        return brandService.getBrandById(id);
    }

    @PutMapping("/brand/{id}")
    public Brand updateBrand(@RequestBody() Brand brand, @PathVariable("id") Long id){
        return brandService.save(brand);
    }

    @PostMapping("/brands")
    public Brand addNew(@RequestBody() Brand brand){
        return brandService.save(brand);
    }

    @DeleteMapping("/brand/{id}")
    public void deleteBrand(@PathVariable("id") Long id){
        brandService.deleteBrand(id);
    }

    @GetMapping("/brand/stats")
    public ResponseEntity<BrandStats> getBrandStats() {
        BrandStats stats = brandService.getBrandStats();
        return ResponseEntity.ok(stats);
    }
}
