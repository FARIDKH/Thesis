package com.example.agro.rest.user;


import com.example.agro.models.OrderItem;
import com.example.agro.models.User;
import com.example.agro.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/getAll")
    @ResponseBody
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }



    @PutMapping("/public/userID/{userID}/addRole/{roleValue}")
    @ResponseBody
    public User addRole(
            @PathVariable Long userID,
            @PathVariable Integer roleValue
    ){
        return this.userService.addUserRole(userID,roleValue);
    }

    @DeleteMapping("/public/userID/{userID}/deleteRole/{roleValue}")
    @ResponseBody
    public User deleteRole(
            @PathVariable Long userID,
            @PathVariable Integer roleValue
    ){
        return this.userService.removeUserRole(userID,roleValue);
    }

    @GetMapping("/public/userID/{userID}/getSellerStatus")
    public Boolean getStatus(
            @PathVariable Long userID
    ){
        return this.userService.getUserByID(userID).get().getSeller();
    }


    
}
