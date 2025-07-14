package com.manisha.IMSystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manisha.IMSystem.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {

   @Positive(message = "field can't be empty")
   private Long productId;

    @Positive(message = "field can't be empty")
    private Integer quantity;


    private Long supplierId;

    private String description;
    private String note;


}
