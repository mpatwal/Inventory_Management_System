package com.manisha.IMSystem.dto;

import com.manisha.IMSystem.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "field can't be empty")
    private String name;

    @NotBlank(message = "field can't be empty")
    private String email;

    @NotBlank(message = "field can't be empty")
    private String password;

    private UserRole role;
}
