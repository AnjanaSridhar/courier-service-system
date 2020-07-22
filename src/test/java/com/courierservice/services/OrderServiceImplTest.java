package com.courierservice.services;

import com.courierservice.models.OrderResult;
import com.courierservice.models.Parcel;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    private static final BigDecimal ELEVEN = new BigDecimal(11);

    @Before
    public void setUp() {
        orderService = new OrderServiceImpl();
    }

    private void assertNotNull(BigDecimal totalCost) {
    }

    @Test
    public void totalCost_Without_OverWeight() {
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(11, "parcel2", 3);

        //When
        BigDecimal result = orderService.totalCost(Lists.newArrayList(parcel1, parcel2));

        //Then
        assertNotNull(result);
        assertEquals(new BigDecimal(11), result);
    }

    @Test
    public void totalCost_With_OverWeight() {
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1", 2);
        Parcel parcel2 = new Parcel(11, "parcel2", 4);

        //When
        BigDecimal result = orderService.totalCost(Lists.newArrayList(parcel1, parcel2));

        //Then
        assertNotNull(result);
        assertEquals(new BigDecimal(13), result);
    }

    @Test
    public void buildOrder_With_SpeedyShipping_Without_Overweight() {
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(11, "parcel2", 3);
        BigDecimal totalCost = BigDecimal.TEN;
        boolean isSpeedy = true;
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2), totalCost);

        //When
        OrderResult result = orderService.applySpeedyShipping(isSpeedy, orderResult);

        //Then
        assertNotNull(result.getSpeedyShipping());
        assertEquals(result.getSpeedyShipping().multiply(new BigDecimal(2)), result.getTotalCost());
    }

    @Test
    public void buildOrder_With_SpeedyShipping_With_Overweight() {
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1", 2);
        Parcel parcel2 = new Parcel(11, "parcel2", 4);
        BigDecimal totalCost = BigDecimal.TEN;
        boolean isSpeedy = true;
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2), totalCost);

        //When
        OrderResult result = orderService.applySpeedyShipping(isSpeedy, orderResult);

        //Then
        assertNotNull(result.getSpeedyShipping());
        assertEquals(result.getSpeedyShipping().multiply(new BigDecimal(2)), result.getTotalCost());
    }
}
