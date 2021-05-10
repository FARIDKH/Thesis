package com.example.agro.models;


import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    private String roles = "CUSTOMER"; // , SELLER, ADMIN


    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private String img_path;


    public Boolean getSeller() {
        return isSeller;
    }

    public void setSeller(Boolean seller) {
        isSeller = seller;
    }

    @Nullable
    private Boolean isSeller;


    @OneToMany(mappedBy = "rater")
    private Set<Ratings> ratings;


    public String getRoles() {
        roles = roles.replaceAll("\\s+","");
        System.out.println(roles);
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void addRole(String role){
        if (!this.roles.contains(role)){
            this.roles =  role + ", " + this.roles;
        }

    }

    public void removeRole(String role){
        this.roles = this.roles.replaceAll(role,"");
        this.roles = this.roles.replaceFirst(",","");
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }





    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles='" + roles + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
