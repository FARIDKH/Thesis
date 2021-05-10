package com.example.agro.models;


import com.fasterxml.jackson.annotation.JsonCreator;

public enum Constant {
    KG("KG"),
    MG("MG")

    ;


    private String title;

    Constant(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @JsonCreator
    public static Constant create (String value) {
        if(value == null) {
            throw new IllegalArgumentException();
        }
        for(Constant v : values()) {
            if(value.equals(v.getTitle())) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getTitle() {
        return title;
    }



}
