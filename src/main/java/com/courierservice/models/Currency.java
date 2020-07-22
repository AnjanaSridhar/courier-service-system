package com.courierservice.models;

public enum Currency {
    USD("$");
    private String currency;
    private Currency(String currency) {
        this.currency = currency;
    }
    public String getCurrency() {
        return this.currency;
    }
}
