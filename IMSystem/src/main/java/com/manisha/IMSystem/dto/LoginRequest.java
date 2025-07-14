package com.manisha.IMSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "field can't be empty")
    private String email;
    @NotBlank(message = "field can't be empty")
    private String password;
}
