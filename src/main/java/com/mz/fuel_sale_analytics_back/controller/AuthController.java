package com.mz.fuel_sale_analytics_back.controller;

import com.mz.fuel_sale_analytics_back.payload.JwtAuthResponse;
import com.mz.fuel_sale_analytics_back.payload.SignInRequest;
import com.mz.fuel_sale_analytics_back.security.JwtCreator;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(description = "Autenticação")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtCreator jwtCreator;

    public AuthController(AuthenticationManager authenticationManager, JwtCreator jwtCreator) {
        this.authenticationManager = authenticationManager;
        this.jwtCreator = jwtCreator;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticate(@Valid @RequestBody SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtCreator.create(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(jwt));
    }

}
