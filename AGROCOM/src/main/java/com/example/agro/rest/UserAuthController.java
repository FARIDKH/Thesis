package com.example.agro.rest;

import com.example.agro.exceptions.UserAlreadyExistException;
import com.example.agro.message.request.UserRegister;
import com.example.agro.message.response.JwtResponse;
import com.example.agro.models.User;
import com.example.agro.models.UserLogin;
import com.example.agro.repositories.user.UserRepository;
import com.example.agro.security.CustomerDetails;
import com.example.agro.security.jwt.JwtTokenProvider;
import com.example.agro.service.UserAuthService;
import com.example.agro.service.user.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserAuthController {


    @Autowired
    private UserAuthService userAuthService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;



    @PostMapping("/user/registration")
    @ResponseBody
    public ResponseEntity registerUser(@RequestBody UserRegister user) {
        try{
            User newUser = userAuthService.registerNewUserAccount(user);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );

            return getAuthResponse(authentication);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(200).body(e.getMessage());
        }

    }

    private ResponseEntity getAuthResponse(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Long id = userRepository.findByEmail(userDetails.getUsername()).get().getId();

        JwtResponse jwtResponse = new JwtResponse(jwt,userDetails.getUsername(),id,userDetails.getAuthorities());


        return ResponseEntity.status(200).body(jwtResponse);
    }

    @PostMapping("/user/login")
    public ResponseEntity loginUser(@Valid @RequestBody UserLogin userLogin){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getUsername(),
                        userLogin.getPassword()
                )
        );

        return getAuthResponse(authentication);
    }


    @RequestMapping(value="/user", method = RequestMethod.GET)
    public CustomerDetails getUser(){
        CustomerDetails cs = (CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return cs;
    }


}
