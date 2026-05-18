package com.example.Uber_AuthService.controller;

import com.example.Uber_AuthService.DTO.PassengerDto;
import com.example.Uber_AuthService.DTO.PassengerSignUpRequestDto;
import com.example.Uber_AuthService.modals.Passenger;
import com.example.Uber_AuthService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;


    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signup(@RequestBody PassengerSignUpRequestDto passengerSignUpRequestDto){
       PassengerDto response= authService.signUpPassenger(passengerSignUpRequestDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    @GetMapping("/signin")
    public ResponseEntity<?> signIn(){

        return new ResponseEntity<>(100,HttpStatus.CREATED);

    }
}

