package com.mona.inventoryms.services;

import com.mona.inventoryms.exception.InvalidTokenException;
import com.mona.inventoryms.exception.UserAlreadyExistException;
import com.mona.inventoryms.mailing.AccountVerificationEmailContext;
import com.mona.inventoryms.mailing.EmailService;
import com.mona.inventoryms.models.User;
import com.mona.inventoryms.repository.UserRepository;
import com.mona.inventoryms.security.models.SecureToken;
import com.mona.inventoryms.security.repository.SecureTokenRepository;
import com.mona.inventoryms.security.services.SecureTokenService;
import jakarta.mail.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Value("${site.base.url.https}")
    private String baseURL;

    private final UserRepository userRepository;

    private final SecureTokenRepository secureTokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final SecureTokenService secureTokenService;

    private final EmailService emailService;

    public UserService(UserRepository userRepository, SecureTokenRepository secureTokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder, SecureTokenService secureTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.secureTokenRepository = secureTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.secureTokenService = secureTokenService;
        this.emailService = emailService;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user, Long id){
        return userRepository.save(user);
    }

    public User register(User user) {
        if(checkIfUserExist(user.getEmail())) {
            throw new UserAlreadyExistException("User already exists for this email");
        }
        user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        sendRegistrationConfirmationEmail(user);
        return newUser;
    }

    public boolean checkIfUserExist(String email){
        return userRepository.findByEmail(email) != null;
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void sendRegistrationConfirmationEmail(User user) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);

        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyUser(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()) {
            throw new InvalidTokenException("Token has expired or not valid");
        }

        User user = userRepository.getOne(secureToken.getUser().getId());
        if(Objects.isNull(user)){
            throw new InvalidTokenException("User does not exist");
        }

        user.setAccountVerified(true);
        userRepository.save(user);

        secureTokenService.removeToken(secureToken);
        return  true;
    }

}