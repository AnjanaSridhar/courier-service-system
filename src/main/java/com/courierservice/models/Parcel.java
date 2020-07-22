package com.courierservice.models;

import java.math.BigDecimal;

public class Parcel {
    private int dimension;
    private BigDecimal cost;
    private ParcelType type;
    private String name;

    public Parcel(int dimension, String name) {
        this.dimension = dimension;
        this.name = name;
        calculateType(this.dimension);
    }

    private void calculateType(int size) {
        if(size < 10) {
            this.setType(ParcelType.SMALL);
            this.setCost(new BigDecimal(3));
        }
        else if(size < 50) {
            this.setType(ParcelType.MEDIUM);
            this.setCost(new BigDecimal(8));
        }
        else if(size < 100) {
            this.setType(ParcelType.LARGE);
            this.setCost(new BigDecimal(15));
        }
        else {
            this.setType(ParcelType.XL);
            this.setCost(new BigDecimal(25));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public ParcelType getType() {
        return type;
    }

    public void setType(ParcelType type) {
        this.type = type;
    }
}
