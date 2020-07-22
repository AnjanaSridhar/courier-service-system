package com.courierservice.controllers;

import com.courierservice.models.Order;
import com.courierservice.models.OrderResult;
import com.courierservice.models.Parcel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class CourierController {
    private static final Gson GSON = new Gson();

    @RequestMapping(
            value = "/couriers",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makeOrder(
            @RequestBody(required = false) String body
    ) {
        Order order = GSON.fromJson(body, Order.class);
        List<Parcel> parcelList = order.getParcelList();
        OrderResult orderResult = new OrderResult(parcelList, totalCost(parcelList), order.getCurrency());
        return ResponseEntity.status(HttpStatus.CREATED).body(GSON.toJson(orderResult));
    }

    private BigDecimal totalCost(List<Parcel> parcels) {
        BigDecimal total = BigDecimal.ZERO;
        for(Parcel parcel : parcels)
            total = total.add(parcel.getCost());
        return total;
    }

    private List<Parcel> parcels(String body) {
        Type type = new TypeToken<List<Parcel>>() {
        }.getType();
        return new GsonBuilder().create().fromJson(body, type);
    }

}
