package com.courierservice.controllers;

import com.courierservice.models.Currency;
import com.courierservice.models.Order;
import com.courierservice.models.OrderResult;
import com.courierservice.models.Parcel;
import com.courierservice.models.ParcelType;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CourierControllerTest {
    private CourierController courierController;

    private static final Gson GSON = new Gson();

    @Before
    public void setUp() {
        courierController = new CourierController();
    }

    @Test
    public void makeOrder() {
        //Given
        String currency = "USD";
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1");
        Parcel parcel2 = new Parcel(11, "parcel2");
        Order order = new Order((Lists.newArrayList(parcel1, parcel2)), currency);
        String body = GSON.toJson(order);

        //When
        ResponseEntity<String> response = courierController.makeOrder(body);

        //Then
        OrderResult result = GSON.fromJson(response.getBody(), OrderResult.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ParcelType.SMALL, result.getParcelList().get(0).getType());
        assertEquals(ParcelType.MEDIUM, result.getParcelList().get(1).getType());
        assertNotNull(result.getTotalCost());
        Parcel parcel = result.getParcelList().get(0);
        assertEquals("SMALL parcel: $3. Total cost: $11",
                parcel.getType().name() + " parcel: " + Currency.valueOf(currency).getCurrency() + parcel.getCost() +
                        ". Total cost: " + Currency.valueOf(currency).getCurrency() +  result.getTotalCost());
    }
}
