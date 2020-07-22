package com.courierservice.controllers;

import com.courierservice.models.Currency;
import com.courierservice.models.Order;
import com.courierservice.models.OrderResult;
import com.courierservice.models.Parcel;
import com.courierservice.models.ParcelType;
import com.courierservice.services.DiscountServiceImpl;
import com.courierservice.services.OrderServiceImpl;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourierControllerTest {
    @InjectMocks
    private CourierController courierController;
    @Mock private OrderServiceImpl orderService;
    @Mock private DiscountServiceImpl discountService;

    private static final Gson GSON = new Gson();

    @Before
    public void setUp() {
        courierController = new CourierController(orderService, discountService);
    }

    @Test
    public void makeOrder_Without_Speedy_Shipping_No_Overweight() {
        //Given
        String currency = "USD";
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(11, "parcel2", 3);
        Order order = new Order((Lists.newArrayList(parcel1, parcel2)), currency);
        String body = GSON.toJson(order);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2), BigDecimal.TEN);
        when(orderService.totalCost(anyList())).thenReturn(BigDecimal.TEN);
        when(orderService.applySpeedyShipping(anyBoolean(), any())).thenReturn(orderResult);
        when(discountService.applyDiscount(any())).thenReturn(orderResult);

        //When
        ResponseEntity<String> response = courierController.makeOrder(body);

        //Then
        OrderResult result = GSON.fromJson(response.getBody(), OrderResult.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ParcelType.SMALL, result.getParcelList().get(0).getType());
        assertEquals(ParcelType.MEDIUM, result.getParcelList().get(1).getType());
        assertNotNull(result.getTotalCost());
        assertNull(result.getSpeedyShipping());
        Parcel parcel = result.getParcelList().get(0);
        assertEquals("SMALL parcel: $3. Total cost: $10",
                parcel.getType().name() + " parcel: " + Currency.valueOf(currency).getCurrency() + parcel.getCost() +
                        ". Total cost: " + Currency.valueOf(currency).getCurrency() +  result.getTotalCost());
    }

    @Test
    public void makeOrder_Without_Speedy_Shipping_With_Overweight() {
        //Given
        String currency = "USD";
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(11, "parcel2", 3);
        Order order = new Order((Lists.newArrayList(parcel1, parcel2)), currency);
        String body = GSON.toJson(order);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2), BigDecimal.TEN);
        when(orderService.totalCost(anyList())).thenReturn(BigDecimal.TEN);
        when(orderService.applySpeedyShipping(anyBoolean(), any())).thenReturn(orderResult);
        when(discountService.applyDiscount(any())).thenReturn(orderResult);

        //When
        ResponseEntity<String> response = courierController.makeOrder(body);

        //Then
        OrderResult result = GSON.fromJson(response.getBody(), OrderResult.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ParcelType.SMALL, result.getParcelList().get(0).getType());
        assertEquals(ParcelType.MEDIUM, result.getParcelList().get(1).getType());
        assertNotNull(result.getTotalCost());
        assertNull(result.getSpeedyShipping());
        Parcel parcel = result.getParcelList().get(0);
        assertEquals("SMALL parcel: $3. Total cost: $10",
                parcel.getType().name() + " parcel: " + Currency.valueOf(currency).getCurrency() + parcel.getCost() +
                        ". Total cost: " + Currency.valueOf(currency).getCurrency() +  result.getTotalCost());
    }

    @Test
    public void makeOrder_With_Speedy_Shipping() {
        //Given
        String currency = "USD";
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(11, "parcel2", 3);
        Order order = new Order((Lists.newArrayList(parcel1, parcel2)), currency);
        order.setSpeedyShipping(true);
        String body = GSON.toJson(order);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2), BigDecimal.TEN);
        orderResult.setSpeedyShipping(BigDecimal.TEN);
        orderResult.setTotalCost(new BigDecimal(20));
        when(orderService.applySpeedyShipping(anyBoolean(), any(OrderResult.class))).thenReturn(orderResult);
        when(discountService.applyDiscount(any())).thenReturn(orderResult);

        //When
        ResponseEntity<String> response = courierController.makeOrder(body);

        //Then
        OrderResult result = GSON.fromJson(response.getBody(), OrderResult.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ParcelType.SMALL, result.getParcelList().get(0).getType());
        assertEquals(ParcelType.MEDIUM, result.getParcelList().get(1).getType());
        assertNotNull(result.getTotalCost());
        Parcel parcel = result.getParcelList().get(0);
        assertEquals("SMALL parcel: $3. Speedy Shipping: $10. Total cost: $20",
                parcel.getType().name() + " parcel: " + Currency.valueOf(currency).getCurrency() + parcel.getCost() +
                        ". Speedy Shipping: " + Currency.valueOf(currency).getCurrency() + result.getSpeedyShipping() +
                        ". Total cost: " + Currency.valueOf(currency).getCurrency() +  result.getTotalCost());

    }

    @Test
    public void makeOrder_With_Speedy_Shipping_With_Overweight() {
        //Given
        String currency = "USD";
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(11, "parcel2", 3);
        Order order = new Order((Lists.newArrayList(parcel1, parcel2)), currency);
        order.setSpeedyShipping(true);
        String body = GSON.toJson(order);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2), BigDecimal.TEN);
        orderResult.setSpeedyShipping(BigDecimal.TEN);
        orderResult.setTotalCost(new BigDecimal(20));
        when(orderService.applySpeedyShipping(anyBoolean(), any(OrderResult.class))).thenReturn(orderResult);
        when(discountService.applyDiscount(any())).thenReturn(orderResult);

        //When
        ResponseEntity<String> response = courierController.makeOrder(body);

        //Then
        OrderResult result = GSON.fromJson(response.getBody(), OrderResult.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ParcelType.SMALL, result.getParcelList().get(0).getType());
        assertEquals(ParcelType.MEDIUM, result.getParcelList().get(1).getType());
        assertNotNull(result.getTotalCost());
        Parcel parcel = result.getParcelList().get(0);
        assertEquals("SMALL parcel: $3. Speedy Shipping: $10. Total cost: $20",
                parcel.getType().name() + " parcel: " + Currency.valueOf(currency).getCurrency() + parcel.getCost() +
                        ". Speedy Shipping: " + Currency.valueOf(currency).getCurrency() + result.getSpeedyShipping() +
                        ". Total cost: " + Currency.valueOf(currency).getCurrency() +  result.getTotalCost());

    }

    @Test
    public void makeOrder_With_Speedy_Shipping_With_Discount() {
        //Given
        String currency = "USD";
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(11, "parcel2", 3);
        Order order = new Order((Lists.newArrayList(parcel1, parcel2)), currency);
        order.setSpeedyShipping(true);
        String body = GSON.toJson(order);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2), BigDecimal.TEN);
        orderResult.setSpeedyShipping(BigDecimal.TEN);
        orderResult.setTotalCost(new BigDecimal(20));
        orderResult.setDiscount(new BigDecimal(-8));
        when(orderService.applySpeedyShipping(anyBoolean(), any(OrderResult.class))).thenReturn(orderResult);
        when(discountService.applyDiscount(any())).thenReturn(orderResult);

        //When
        ResponseEntity<String> response = courierController.makeOrder(body);

        //Then
        OrderResult result = GSON.fromJson(response.getBody(), OrderResult.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ParcelType.SMALL, result.getParcelList().get(0).getType());
        assertEquals(ParcelType.MEDIUM, result.getParcelList().get(1).getType());
        assertNotNull(result.getTotalCost());
        Parcel parcel = result.getParcelList().get(0);
        assertEquals("SMALL parcel: $3. Speedy Shipping: $10. Total cost: $20. Discount: -8",
                parcel.getType().name() + " parcel: " + Currency.valueOf(currency).getCurrency() + parcel.getCost() +
                        ". Speedy Shipping: " + Currency.valueOf(currency).getCurrency() + result.getSpeedyShipping() +
                        ". Total cost: " + Currency.valueOf(currency).getCurrency() +  result.getTotalCost() +
                        ". Discount: "  +  result.getDiscount());

    }
}

