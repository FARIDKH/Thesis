package com.example.agro.message.request;

import javax.validation.constraints.NotNull;

public class RateRequest {

    @NotNull
    private Long sellerId;

    @NotNull
    private Long raterId;

    @NotNull
    private Long value;

    public Long getRaterId() {
        return raterId;
    }

    public void setRaterId(Long raterId) {
        this.raterId = raterId;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "RateRequest{" +
                "sellerId=" + sellerId +
                ", raterId=" + raterId +
                ", value=" + value +
                '}';
    }
}
