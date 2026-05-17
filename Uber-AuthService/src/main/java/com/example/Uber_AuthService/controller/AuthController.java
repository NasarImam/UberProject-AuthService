package com.example.Uber_AuthService.controller;

import com.example.Uber_AuthService.DTO.PassengerDto;
import com.example.Uber_AuthService.DTO.PassengerSignUpRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signup(@RequestBody PassengerSignUpRequestDto passengerSignUpRequestDto){
        return new ResponseEntity<>();
    }
}
