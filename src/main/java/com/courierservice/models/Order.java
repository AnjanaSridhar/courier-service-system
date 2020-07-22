package com.courierservice.models;

import java.util.List;

public class Order {
    private List<Parcel> parcelList;
    private String currency;
    private boolean speedyShipping;

    public Order(List<Parcel> parcelList, String currency) {
        this.parcelList = parcelList;
        this.currency = currency;
    }

    public boolean isSpeedyShipping() {
        return speedyShipping;
    }

    public void setSpeedyShipping(boolean speedyShipping) {
        this.speedyShipping = speedyShipping;
    }

    public List<Parcel> getParcelList() {
        return parcelList;
    }

    public void setParcelList(List<Parcel> parcelList) {
        this.parcelList = parcelList;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
