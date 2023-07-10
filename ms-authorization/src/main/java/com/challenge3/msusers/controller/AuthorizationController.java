package com.challenge3.msusers.controller;

import com.challenge3.msusers.exception.JwtTokenMalformedException;
import com.challenge3.msusers.exception.JwtTokenMissingException;
import com.challenge3.msusers.payload.LoginDto;
import com.challenge3.msusers.payload.TokenDto;
import com.challenge3.msusers.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class AuthorizationController {

    private UserService userService;

    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestBody TokenDto token) throws JwtTokenMalformedException, JwtTokenMissingException {
        userService.validateToken(token.getToken());
        return ResponseEntity.noContent().build();
    }
}
