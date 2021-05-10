package com.example.agro.service;

import com.example.agro.exceptions.UserAlreadyExistException;
import com.example.agro.message.request.UserRegister;
import com.example.agro.models.User;
import com.example.agro.models.UserLogin;
import com.example.agro.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;





@Service
public class UserAuthService  {

    @Autowired
    private UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;


    private String responseText = "";

    public User registerNewUserAccount(UserRegister user)
        throws UserAlreadyExistException {
        if(repository.findByEmail(user.getEmail()).isEmpty() == false ){
            System.out.println(repository.findByEmail(user.getEmail()).toString());
            throw new UserAlreadyExistException("There is an account with that email address");
        }
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getSellerValue()){
            newUser.addRole("ROLE_SELLER");
        }
        repository.save(newUser);
        return newUser;
    }



    public String getResponseText() {
        return responseText;
    }
}
