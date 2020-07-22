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

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DiscountServiceImplTest {

    @InjectMocks
    private DiscountServiceImpl discountService;

    @Before
    public void setUp() {
        discountService = new DiscountServiceImpl();
    }

    @Test
    public void applyDiscount_zero_discount() {
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(11, "parcel2", 3);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2), new BigDecimal(11));

        //When
        OrderResult result = discountService.applyDiscount(orderResult);

        //Then
        assertEquals(BigDecimal.ZERO, result.getDiscount());
    }

    @Test
    public void applyDiscount_with_small_discount() {
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(1, "parcel2", 1);
        Parcel parcel3 = new Parcel(1, "parcel3", 1);
        Parcel parcel4 = new Parcel(1, "parcel4", 1);
        Parcel parcel5 = new Parcel(1, "parcel5", 1);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2, parcel3, parcel4,
                parcel5), new BigDecimal(15));

        //When
        OrderResult result = discountService.applyDiscount(orderResult);

        //Then
        assertEquals(new BigDecimal(-3), result.getDiscount());
    }

    @Test
    public void applyDiscount_with_medium_discount() {
        //Given
        Parcel parcel1 = new Parcel(11, "parcel1", 3);
        Parcel parcel2 = new Parcel(11, "parcel2", 3);
        Parcel parcel3 = new Parcel(11, "parcel3", 3);
        Parcel parcel4 = new Parcel(11, "parcel4", 3);
        Parcel parcel5 = new Parcel(11, "parcel5", 3);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2, parcel3, parcel4,
                parcel5), new BigDecimal(40));

        //When
        OrderResult result = discountService.applyDiscount(orderResult);

        //Then
        assertEquals(new BigDecimal(-8), result.getDiscount());
    }

    @Test
    public void applyDiscount_with_mixed_bag_discount_case1() {
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(1, "parcel2", 1);
        Parcel parcel3 = new Parcel(1, "parcel3", 1);
        Parcel parcel4 = new Parcel(11, "parcel4", 3);
        Parcel parcel5 = new Parcel(11, "parcel5", 3);
        Parcel parcel6 = new Parcel(51, "parcel6", 6);
        Parcel parcel7 = new Parcel(51, "parcel7", 6);
        Parcel parcel8 = new Parcel(101, "parcel8", 10);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2, parcel3, parcel4,
                parcel5, parcel6, parcel7, parcel8), new BigDecimal(86));

        //When
        OrderResult result = discountService.applyDiscount(orderResult);

        //Then
        assertEquals(new BigDecimal(-3), result.getDiscount());
    }

    @Test
    public void applyDiscount_with_mixed_bag_discount_case2() {
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(1, "parcel2", 1);
        Parcel parcel3 = new Parcel(1, "parcel3", 1);
        Parcel parcel4 = new Parcel(1, "parcel4", 3);
        Parcel parcel5 = new Parcel(11, "parcel5", 3);
        Parcel parcel6 = new Parcel(51, "parcel6", 6);
        Parcel parcel7 = new Parcel(51, "parcel7", 6);
        Parcel parcel8 = new Parcel(101, "parcel8", 10);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2, parcel3, parcel4,
                parcel5, parcel6, parcel7, parcel8), new BigDecimal(86));

        //When
        OrderResult result = discountService.applyDiscount(orderResult);

        //Then
        assertEquals(new BigDecimal(-3), result.getDiscount());
    }

    @Test
    public void applyDiscount_with_mixed_bag_discount_case3() {
        //Given
        Parcel parcel1 = new Parcel(1, "parcel1", 1);
        Parcel parcel2 = new Parcel(1, "parcel2", 1);
        Parcel parcel3 = new Parcel(1, "parcel3", 1);
        Parcel parcel4 = new Parcel(1, "parcel4", 3);
        Parcel parcel5 = new Parcel(11, "parcel5", 3);
        Parcel parcel6 = new Parcel(51, "parcel6", 6);
        Parcel parcel7 = new Parcel(51, "parcel7", 6);
        Parcel parcel8 = new Parcel(101, "parcel8", 10);
        Parcel parcel9 = new Parcel(101, "parcel9", 10);
        OrderResult orderResult = new OrderResult(Lists.newArrayList(parcel1, parcel2, parcel3, parcel4,
                parcel5, parcel6, parcel7, parcel8, parcel9), new BigDecimal(111));

        //When
        OrderResult result = discountService.applyDiscount(orderResult);

        //Then
        assertEquals(new BigDecimal(-11), result.getDiscount());
    }

}
