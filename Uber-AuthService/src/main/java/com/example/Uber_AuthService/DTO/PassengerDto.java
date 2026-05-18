package com.example.Uber_AuthService.DTO;

import com.example.Uber_AuthService.modals.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Date createdAt;
    private  String phoneNumber;

    public static PassengerDto from(Passenger p){
        PassengerDto result= PassengerDto.builder()
                .id(p.getId())
                .createdAt(p.getCreatedAt())
                .name(p.getName())
                .email(p.getEmail())
                .password(p.getPassword())
                .phoneNumber(p.getPhoneNumber())
                .build();
        return result;


    }
}
