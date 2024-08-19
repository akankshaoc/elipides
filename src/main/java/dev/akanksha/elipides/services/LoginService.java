package dev.akanksha.elipides.services;

import dev.akanksha.elipides.models.LoginRequest;
import dev.akanksha.elipides.models.LoginResponse;
import dev.akanksha.elipides.security.JWTUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final AuthenticationManager manager;
    private final JWTUtility jwtUtility;

    public LoginResponse logUserIn(@Valid LoginRequest loginRequest) {
        Authentication authentication
                = manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()) {
            return new LoginResponse(jwtUtility.issueToken(loginRequest.getUsername()));
        } else {
            return new LoginResponse("NO");
        }
    }
}
