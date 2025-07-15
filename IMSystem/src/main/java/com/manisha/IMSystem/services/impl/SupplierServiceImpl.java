package com.manisha.IMSystem.services.impl;


import com.manisha.IMSystem.dto.Response;
import com.manisha.IMSystem.dto.SupplierDTO;
import com.manisha.IMSystem.exceptions.NotFoundException;

import com.manisha.IMSystem.models.Supplier;

import com.manisha.IMSystem.repositories.SupplierRepository;
import com.manisha.IMSystem.services.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;


    @Override
    public Response addSupplier(SupplierDTO supplierDTO) {

        Supplier supplierToSave = modelMapper.map(supplierDTO ,Supplier.class);
        supplierRepository.save(supplierToSave);

        return Response.builder()
                .status(200)
                .message("Supplier Saved Successfully")
                .build();
    }

    @Override
    public Response updateSupplier(Long id, SupplierDTO supplierDTO) {

        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Supplier Not Found"));

        if(supplierDTO.getName() != null) existingSupplier.setName(supplierDTO.getName());
        if(supplierDTO.getContactInfo() != null) existingSupplier.setContactInfo(supplierDTO.getContactInfo());
        if(supplierDTO.getAddress() != null) existingSupplier.setAddress(supplierDTO.getAddress());

        supplierRepository.save(existingSupplier);

        return Response.builder()
                .status(200)
                .message("Supplier updated Successfully")
                .build();
    }

    @Override
    public Response getAllSupplier() {
        List<Supplier> suppliers= supplierRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));

        List<SupplierDTO>supplierDTOList = modelMapper.map(suppliers,new TypeToken<List<SupplierDTO>>(){}.getType());

        return Response.builder()
                .status(200)
                .message("Success")
                .suppliers(supplierDTOList)
                .build();
    }

    @Override
    public Response getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Supplier Not Found"));

        SupplierDTO supplierDTO = modelMapper.map(supplier,SupplierDTO.class);

        return Response.builder()
                .status(200)
                .message("Success")
                .supplier(supplierDTO)
                .build();
    }

    @Override
    public Response deleteSupplier(Long id) {
        supplierRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Supplier Not Found"));

        supplierRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Supplier Deleted Successfully")
                .build();
    }
}
