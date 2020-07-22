package com.courierservice.services;

import com.courierservice.models.OrderResult;
import com.courierservice.models.Parcel;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static BigDecimal SPEEDY_SHIPPING = new BigDecimal(2);

    @Override
    public BigDecimal totalCost(List<Parcel> parcelList) {
        BigDecimal total = BigDecimal.ZERO;
        for(Parcel parcel : parcelList)
            total = total.add(parcel.getCost());
        return total;
    }

    public OrderResult applySpeedyShipping(boolean speedy, OrderResult orderResult) {
        if(speedy) {
            orderResult.setSpeedyShipping(orderResult.getTotalCost());
            orderResult.setTotalCost(orderResult.getTotalCost().multiply(SPEEDY_SHIPPING));
        }
        return orderResult;
    }
}
