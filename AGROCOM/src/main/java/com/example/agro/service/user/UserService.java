package com.example.agro.service.user;


import com.example.agro.models.Order;
import com.example.agro.models.OrderItem;
import com.example.agro.models.User;
import com.example.agro.repositories.order.OrderRepository;
import com.example.agro.repositories.user.UserRepository;
import com.example.agro.service.order.OrderService;
import com.example.agro.service.orderItem.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private OrderItemService orderItemService;


    public List<User> getAllUsers(){
        return  this.userRepository.findAll();
    }

    public Optional<User> getUserByID(Long id){
        return this.userRepository.findById(id);
    }


    public Optional<User> getUserByEmail(String email) { return this.userRepository.findByEmail(email); }

    public User addUserRole(Long userId,Integer value){
        if ( getUserByID(userId).isEmpty() ) { return null; }
        User user = getUserByID(userId).get();
        if (value == 1){
            user.addRole("SELLER");
        } else if (value == 2) {
            user.addRole("ROLE_ADMIN");
        }
        userRepository.save(user);
        return user;
    }

    public User removeUserRole(Long userId,Integer value){
        if ( getUserByID(userId).isEmpty() ) { return null; }
        User user = getUserByID(userId).get();
        if (value == 1){
            user.removeRole("SELLER");
        } else if (value == 2) {
            user.removeRole("ROLE_ADMIN");
        }
        userRepository.save(user);
        return user;
    }




}
