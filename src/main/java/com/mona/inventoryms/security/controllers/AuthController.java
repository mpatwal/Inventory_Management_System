package com.mona.inventoryms.security.controllers;

import com.mona.inventoryms.models.User;
import com.mona.inventoryms.security.models.UserPrincipal;
import com.mona.inventoryms.security.services.AuthenticationService;
import com.mona.inventoryms.security.models.LoginRequest;
import com.mona.inventoryms.security.services.UserPrivilegeAssignmentService;
import com.mona.inventoryms.services.UserService;
import com.mona.inventoryms.security.services.TokenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    private final UserPrivilegeAssignmentService userPrivilegeAssignmentService;
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    public AuthController(UserService userService, UserPrivilegeAssignmentService userPrivilegeAssignmentService, TokenService tokenService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.userPrivilegeAssignmentService = userPrivilegeAssignmentService;
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            boolean isAuthenticated = authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            if (isAuthenticated) {
                User user = userService.getUserByUsername(loginRequest.getUsername());
                UserPrincipal principal = new UserPrincipal(userPrivilegeAssignmentService, user);
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, principal.getAuthorities());
                String jwtToken = tokenService.generateToken(authentication);
                session.setAttribute("user", loginRequest.getUsername());
                return ResponseEntity.ok(jwtToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();  // Invalidate the session
        return ResponseEntity.ok("Logout successful");
    }

}