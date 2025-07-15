package com.manisha.IMSystem.services.impl;

import com.manisha.IMSystem.dto.LoginRequest;
import com.manisha.IMSystem.dto.RegisterRequest;
import com.manisha.IMSystem.dto.Response;
import com.manisha.IMSystem.dto.UserDTO;
import com.manisha.IMSystem.enums.UserRole;
import com.manisha.IMSystem.exceptions.InvalidCredentialsException;
import com.manisha.IMSystem.exceptions.NotFoundException;
import com.manisha.IMSystem.models.User;
import com.manisha.IMSystem.repositories.UserRepository;
import com.manisha.IMSystem.security.JwtUtils;
import com.manisha.IMSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole role = UserRole.MANAGER;
        if(registerRequest.getRole()!=null){
            role = registerRequest.getRole();
        }
        User userToSave = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(role)
                .build();
        userRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message(("user was successfully registered"))
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
    User user =userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(()->new NotFoundException("Email Not registerd"));
    if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
        throw new InvalidCredentialsException("Password does not matched");
    }
    String token = jwtUtils.generateToken(user.getEmail());
    return Response.builder()
            .status(200)
            .message("Logged in Successfully")
                    .role(user.getRole())
                    .token(token)
                    .expirationTime("6 months")
                    .build();
    }

    @Override
    public Response getAllUsers() {
         List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
         users.forEach(user->user.setTransactions(null));
         List<UserDTO>userDTOS =modelMapper.map(users,new TypeToken<List<UserDTO>>(){}.getType());

         return Response.builder()
                 .status(200)
                 .message("Success")
                 .users(userDTOS)
                 .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("user Not Found"));
        user.setTransactions(null);

        return  user;
    }
 @Override
    public Response getUserById(Long id) {
     User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("User Not Found"));

     UserDTO userDTO = modelMapper.map(user,UserDTO.class);
     userDTO.setTransactions(null);

     return Response.builder()
             .status(200)
             .message("Success")
             .user(userDTO)
             .build();
    }

    @Override
    public Response updateUSer(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElseThrow(()->new NotFoundException("User Not Found"));

        if (userDTO.getEmail() != null)existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPhoneNumber() != null)existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getName() != null)existingUser.setName(userDTO.getName());
        if (userDTO.getRole() != null)existingUser.setRole(userDTO.getRole());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        }
        userRepository.save(existingUser);

        return Response.builder()
                .status(200)
                .message("User Successfully Updated")
                .build();

    }

    @Override
    public Response deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(()->new NotFoundException("User Not Found"));
        userRepository.deleteById(id);
        return Response.builder()
                .status(200)
                .message("User Successfully Deleted")
                .build();
    }

    @Override
    public Response getUSerTransactions(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new NotFoundException("USer Not Found"));
        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        userDTO.getTransactions().forEach(transactionDTO -> {
            transactionDTO.setUser(null);
            transactionDTO.setSupplier(null);
        });
        return Response.builder()
                .status(200)
                .message(" Success")
                .user(userDTO)
                .build();
    }
}
