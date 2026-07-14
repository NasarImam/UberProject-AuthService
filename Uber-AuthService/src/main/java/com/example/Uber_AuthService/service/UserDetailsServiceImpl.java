package com.example.Uber_AuthService.service;
import com.example.Uber_AuthService.helper.AuthPassengerDetail;
import com.example.Uber_AuthService.modals.Passenger;
import com.example.Uber_AuthService.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
// this class is responsible for loading the user in the form of UserDetails object for Auth
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private  final PassengerRepository passengerRepository;

    public UserDetailsServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger=passengerRepository.findPassesngerByEmail(email);
        if (passenger.isPresent()){
            return new AuthPassengerDetail(passenger.get());

        }
        else {
            throw new UsernameNotFoundException("Passenger not found By given email");
        }
    }
}
