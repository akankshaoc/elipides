package dev.akanksha.elipides.controllers;

import dev.akanksha.elipides.models.LoginRequest;
import dev.akanksha.elipides.models.LoginResponse;
import dev.akanksha.elipides.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> logUserIn(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(
                loginService.logUserIn(loginRequest)
                , HttpStatus.OK);
    }
}
