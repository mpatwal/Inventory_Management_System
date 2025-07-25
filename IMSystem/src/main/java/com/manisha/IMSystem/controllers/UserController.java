package com.manisha.IMSystem.controllers;

import com.manisha.IMSystem.dto.LoginRequest;
import com.manisha.IMSystem.dto.RegisterRequest;
import com.manisha.IMSystem.dto.Response;
import com.manisha.IMSystem.dto.UserDTO;
import com.manisha.IMSystem.models.User;
import com.manisha.IMSystem.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response>getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response>updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUSer(id, userDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response>deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/transaction/{userId}")
    public ResponseEntity<Response>getUserAndTransactions(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUSerTransactions(userId));
    }

    @GetMapping("/current")
    public ResponseEntity<User>getCurrentUSer(){
        return ResponseEntity.ok(userService.getCurrentLoggedInUser());
    }
}
