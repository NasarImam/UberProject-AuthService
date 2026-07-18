package com.example.Uber_AuthService.configuration;

import com.example.Uber_AuthService.repositories.PassengerRepository;
import com.example.Uber_AuthService.service.JwtService;
import com.example.Uber_AuthService.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration

public class SecurityConfig  {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    private final PassengerRepository passengerRepository;

    public SecurityConfig(PassengerRepository passengerRepository, UserDetailsService userDetailsService, JwtService jwtService) {
        this.passengerRepository = passengerRepository;
        this.userDetailsService=userDetailsService;
        this.jwtService=jwtService;
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new UserDetailsServiceImpl(passengerRepository);
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors-> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/signup/passenger").permitAll()
                        .requestMatchers("/api/v1/auth/signin/passenger").permitAll()
                        .anyRequest().authenticated()
                );


        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
