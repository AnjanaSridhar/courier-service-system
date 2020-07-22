package com.courierservice.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class CourierControllerTest {
    private CourierController courierController;

    @Before
    public void setUp() {
        courierController = new CourierController();
    }

    @Test
    public void makeOrder() {
        //When
        ResponseEntity response = courierController.makeOrder();

        //Given
        Assert.assertEquals(HttpStatus.NOT_IMPLEMENTED, response.getStatusCode());
    }
}
