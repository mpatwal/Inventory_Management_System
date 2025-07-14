package com.manisha.IMSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.manisha.IMSystem.enums.TransactionStatus;
import com.manisha.IMSystem.enums.TransactionType;
import com.manisha.IMSystem.models.Product;
import com.manisha.IMSystem.models.Supplier;
import com.manisha.IMSystem.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class TransactionDTO {

    private Long id;

    private Integer totalProducts;

    private BigDecimal totalPrice;


    private TransactionType transactionType;//purchase,sale,return_to_supplier


    private TransactionStatus status; //pending,completed,processing,cancelled

    private String description;
    private String note;

    private  LocalDateTime createdAt ;
    private LocalDateTime updatedAt;


    private ProductDTO product;


    private UserDTO user;


    private SupplierDTO supplier;


}
