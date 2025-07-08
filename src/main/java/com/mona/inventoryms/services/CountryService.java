package com.mona.inventoryms.services;

import com.mona.inventoryms.models.Country;
import com.mona.inventoryms.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> findAll(){
        return countryRepository.findAll();
    }

    public Country getCountry(int id){
        return countryRepository.findById(id).orElse(null);
    }

    public  Country save(Country country){
        return countryRepository.save(country);
    }

    public void delete(Integer id) {
         countryRepository.deleteById(id);
    }

    public Country getById(Integer id) {
        return countryRepository.findById(id).orElse(null);
    }

    public void update(Country country) {
        countryRepository.save(country);
    }

}
