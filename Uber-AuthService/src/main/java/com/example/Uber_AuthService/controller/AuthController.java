package com.example.Uber_AuthService.controller;

import com.example.Uber_AuthService.DTO.AuthRequestDto;
import com.example.Uber_AuthService.DTO.PassengerDto;
import com.example.Uber_AuthService.DTO.PassengerSignUpRequestDto;
import com.example.Uber_AuthService.modals.Passenger;
import com.example.Uber_AuthService.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;
    private final AuthenticationManager authenticationManager;


    public AuthController(AuthService authService, AuthenticationManager authenticationManager){
        this.authService=authService;
        this.authenticationManager=authenticationManager;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signup(@RequestBody PassengerSignUpRequestDto passengerSignUpRequestDto){
       PassengerDto response= authService.signUpPassenger(passengerSignUpRequestDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto){
        System.out.println("request recieved"+ authRequestDto.getEmail() + " "+ authRequestDto.getPassword());



        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword()));
        if (authentication.isAuthenticated()){
            return new ResponseEntity<>("Authenticated successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Auth not successfull", HttpStatus.OK);

    }
}

