package com.manisha.IMSystem.services;
import com.manisha.IMSystem.dto.Response;
import com.manisha.IMSystem.dto.SupplierDTO;

public interface SupplierService {

    Response addSupplier(SupplierDTO supplierDTO);
    Response updateSupplier(Long id, SupplierDTO supplierDTO);
    Response getAllSupplier();
    Response getSupplierById(Long id);
    Response deleteSupplier(Long id);
}
