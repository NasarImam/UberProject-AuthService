package com.example.Uber_AuthService.DTO;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class PassengerSignUpRequestDto {
    private String email;
   private String password;
    private String phoneNumber;
    private String name;
}
