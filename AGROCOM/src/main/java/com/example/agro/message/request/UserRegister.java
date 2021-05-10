package com.example.agro.message.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UserRegister {

    @Valid
    @NotNull
    private String firstName;

    @Valid
    @NotNull
    private String lastName;

    @Valid
    @NotNull
    private String email;


    @Valid
    @NotNull
    private String password;


    @Valid
    private Boolean sellerValue;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSellerValue() {
        return sellerValue;
    }

    public void setSellerValue(Boolean sellerValue) {
        this.sellerValue = sellerValue;
    }


}
