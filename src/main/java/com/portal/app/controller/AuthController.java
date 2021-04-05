package com.portal.app.controller;

import com.portal.app.exception.AppException;
import com.portal.app.model.Role;
import com.portal.app.model.RoleName;
import com.portal.app.model.User;
import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.JwtAuthenticationResponse;
import com.portal.app.payload.request.LoginRequest;
import com.portal.app.payload.request.SignUpRequest;
import com.portal.app.repository.RoleRepository;
import com.portal.app.repository.UserRepository;
import com.portal.app.security.JwtTokenProvider;
import com.portal.app.service.VerificationTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        if (tokenProvider.isActiveUser(authentication)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);
            String expiration = tokenProvider.getExpirationFromJWT(token);
            String hasRole = tokenProvider.getUserRole(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token, expiration, hasRole));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "User does not exist or active"), HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @ModelAttribute LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        if (tokenProvider.isActiveUser(authentication)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);
            String expiration = tokenProvider.getExpirationFromJWT(token);
            String hasRole = tokenProvider.getUserRole(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token, expiration, hasRole));
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "User does not exist or active"), HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (StringUtils.isNotBlank(signUpRequest.getUsername()) && userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByContact(signUpRequest.getContact())) {
            return new ResponseEntity<>(new ApiResponse(false, "Phone number already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getContact()
                , signUpRequest.getCity(), signUpRequest.getAddress());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        verificationTokenService.createVerification(user.getEmail());
        verificationTokenService.createOTPVerification(user.getContact());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
