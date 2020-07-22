package com.courierservice.models;

import java.math.BigDecimal;
import java.util.List;

public class OrderResult {
    private List<Parcel> parcelList;
    private BigDecimal totalCost;
    private String currency;
    private BigDecimal speedyShipping;

    public OrderResult(List<Parcel> parcelList, BigDecimal totalCost) {
        this.parcelList = parcelList;
        this.totalCost = totalCost;
    }

    public BigDecimal getSpeedyShipping() {
        return speedyShipping;
    }

    public void setSpeedyShipping(BigDecimal speedyShipping) {
        this.speedyShipping = speedyShipping;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Parcel> getParcelList() {
        return parcelList;
    }

    public void setParcelList(List<Parcel> parcelList) {
        this.parcelList = parcelList;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
