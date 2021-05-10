package com.example.agro.models;


import javax.persistence.*;

@Entity
public class Ratings {
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getRater() {
        return rater;
    }

    public void setRater(User rater) {
        this.rater = rater;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="seller_id", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name="rater_id", nullable = false)
    private User rater;

    private float value;

}
