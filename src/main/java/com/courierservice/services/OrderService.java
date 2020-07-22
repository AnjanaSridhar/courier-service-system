package com.courierservice.services;

import com.courierservice.models.OrderResult;
import com.courierservice.models.Parcel;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    public BigDecimal totalCost(List<Parcel> result);

    public OrderResult applySpeedyShipping(boolean speedy, OrderResult orderResult);
}
