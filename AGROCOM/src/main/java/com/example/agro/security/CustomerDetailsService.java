package com.example.agro.security;

import com.example.agro.models.User;
import com.example.agro.service.user.UserService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailsService implements UserDetailsService {


    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> user = userService.getUserByEmail(s);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("Email not found!");
        }
        System.out.println("loadUserByUsername : " + user.get().toString());
        return user.map(CustomerDetails::new).get();
    }
}
