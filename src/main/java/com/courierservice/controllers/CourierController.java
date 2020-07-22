package com.courierservice.controllers;

import com.courierservice.models.Order;
import com.courierservice.models.OrderResult;
import com.courierservice.models.Parcel;
import com.courierservice.services.DiscountService;
import com.courierservice.services.OrderService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourierController {
    private static final Gson GSON = new Gson();

    private OrderService orderService;
    private DiscountService discountService;

    @Autowired
    public CourierController(OrderService orderService, DiscountService discountService) {
        this.orderService = orderService;
        this.discountService = discountService;
    }

    @RequestMapping(
            value = "/couriers",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makeOrder(
            @RequestBody(required = false) String body
    ) {
        //Read input
        Order order = GSON.fromJson(body, Order.class);
        List<Parcel> parcelList = order.getParcelList();
        //Get total cost for order
        OrderResult orderResult = new OrderResult(parcelList, orderService.totalCost(parcelList));
        //Check if discounts are applicable and apply them
        orderResult = discountService.applyDiscount(orderResult);
        //Check if speedy shipping was selected
        orderResult = orderService.applySpeedyShipping(order.isSpeedyShipping(), orderResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(GSON.toJson(orderResult));
    }

}
