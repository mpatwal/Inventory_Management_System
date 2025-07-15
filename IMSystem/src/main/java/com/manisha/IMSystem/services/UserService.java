package com.manisha.IMSystem.services;

import com.manisha.IMSystem.dto.LoginRequest;
import com.manisha.IMSystem.dto.RegisterRequest;
import com.manisha.IMSystem.dto.Response;
import com.manisha.IMSystem.dto.UserDTO;
import com.manisha.IMSystem.models.User;

public interface UserService {

    Response registerUser(RegisterRequest registerRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getCurrentLoggedInUser();

    Response getUserById(Long id);

    Response updateUSer(Long id, UserDTO userDTO);

    Response deleteUser(Long id);

    Response getUSerTransactions(Long id);

}
