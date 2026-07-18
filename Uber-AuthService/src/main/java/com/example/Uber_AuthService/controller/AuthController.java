package com.example.Uber_AuthService.controller;

import com.example.Uber_AuthService.DTO.AuthRequestDto;
import com.example.Uber_AuthService.DTO.PassengerDto;
import com.example.Uber_AuthService.DTO.PassengerSignUpRequestDto;
import com.example.Uber_AuthService.modals.Passenger;
import com.example.Uber_AuthService.service.AuthService;
import com.example.Uber_AuthService.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtService jwtService;

    @Value("${cookie.expiry}")
    private int cookieExpiry;

    private AuthService authService;
    private final AuthenticationManager authenticationManager;


    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService){
        this.authService=authService;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signup(@RequestBody PassengerSignUpRequestDto passengerSignUpRequestDto){
       PassengerDto response= authService.signUpPassenger(passengerSignUpRequestDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse httpServletResponse) {
        System.out.println("request recieved" + authRequestDto.getEmail() + " " + authRequestDto.getPassword());


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        String jwtToken = null;
        if (authentication.isAuthenticated()) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("email", authRequestDto.getEmail());
            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            jwtToken = jwtService.createToken(new HashMap<>(),
                    userDetails.getUsername());

        }
        ResponseCookie cookie =
                ResponseCookie.from("jwt", jwtToken)
                        .httpOnly(true)
                        .secure(false)       // true in production (HTTPS)
                        .path("/")
                        .maxAge(Duration.ofHours(1))
                        .sameSite("Strict")
                        .build();
        System.out.println("this is cookie" + cookie);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Login Successful");

    }
}

