package com.courierservice.services;

import com.courierservice.models.OrderResult;

public interface DiscountService {
    public OrderResult applyDiscount(OrderResult orderResult) ;
}
