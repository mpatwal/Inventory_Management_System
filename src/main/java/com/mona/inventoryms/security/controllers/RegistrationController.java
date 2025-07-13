package com.mona.inventoryms.security.controllers;

import com.mona.inventoryms.exception.InvalidTokenException;
import com.mona.inventoryms.models.User;
import com.mona.inventoryms.security.services.AuthenticationService;
import com.mona.inventoryms.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class RegistrationController {

    Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private final AuthenticationService authenticationService;
    private UserService userService;

    @Autowired
    public RegistrationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = userService.register(user);
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

    @GetMapping("/register/verify")
    public void verifyUser(@RequestParam(required = false) String token, HttpServletResponse response) throws IOException {
        LOGGER.info("Token was received: {}", token);
        if(StringUtils.isEmpty(token)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Token is not valid");
        }
        try{
            userService.verifyUser(token);
            response.sendRedirect("http://localhost:3000/#/verificationSuccessful");
        } catch (InvalidTokenException e){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

}