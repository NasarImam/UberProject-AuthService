package com.example.Uber_AuthService.service;
import com.example.Uber_AuthService.DTO.PassengerDto;
import com.example.Uber_AuthService.DTO.PassengerSignUpRequestDto;
import com.example.Uber_AuthService.modals.Passenger;
import com.example.Uber_AuthService.repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthService {
    private final PassengerRepository passengerRepository;

    // adding password encoder to hash the password
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.passengerRepository=passengerRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public PassengerDto signUpPassenger(PassengerSignUpRequestDto passengerSignUpRequestDto){
        Passenger passenger = Passenger.builder()
                .email(passengerSignUpRequestDto.getEmail())
                .name(passengerSignUpRequestDto.getName())
                .password(bCryptPasswordEncoder.encode(passengerSignUpRequestDto.getPassword())) // TODO: Encrypt password
                .phoneNumber(passengerSignUpRequestDto.getPhoneNumber())
                .build();
        Passenger newPassenger=passengerRepository.save(passenger);

        return PassengerDto.from(newPassenger);


    }

}
