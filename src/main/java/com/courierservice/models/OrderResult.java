package com.courierservice.models;

import java.math.BigDecimal;
import java.util.List;

public class OrderResult {
    private List<Parcel> parcelList;
    private BigDecimal totalCost;
    private String currency;

    public OrderResult(List<Parcel> parcelList, BigDecimal totalCost, String currency) {
        this.parcelList = parcelList;
        this.totalCost = totalCost;
        this.currency = currency;
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
