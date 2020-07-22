package com.courierservice.services;

import com.courierservice.models.OrderResult;
import com.courierservice.models.Parcel;
import com.courierservice.models.ParcelType;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final BigDecimal TWO = new BigDecimal(2);
    private static final BigDecimal SPEEDY_SHIPPING = TWO;
    private static final BigDecimal OVERWEIGHT_CHARGE = TWO;

    @Override
    public BigDecimal totalCost(List<Parcel> parcelList) {
        BigDecimal total = BigDecimal.ZERO;
        for(Parcel parcel : parcelList)
            total = total.add(parcel.getCost());
        BigDecimal charge = getOverWeightCharge(parcelList);
        return total.add(charge);
    }

    public OrderResult applySpeedyShipping(boolean speedy, OrderResult orderResult) {
        if(speedy) {
            orderResult.setSpeedyShipping(orderResult.getTotalCost());
            orderResult.setTotalCost(orderResult.getTotalCost().multiply(SPEEDY_SHIPPING));
        }
        return orderResult;
    }

    private BigDecimal getOverWeightCharge(List<Parcel> parcels){
        BigDecimal extra_charge = BigDecimal.ZERO;
        for(Parcel parcel : parcels) {
            int acceptableWeight = acceptableWeight(parcel.getType());
            if(parcel.getWeight() > acceptableWeight){
                extra_charge = new BigDecimal(parcel.getWeight() - acceptableWeight).multiply(OVERWEIGHT_CHARGE);
            }
        }
        return extra_charge;
    }

    private int acceptableWeight(ParcelType parcelType) {
        switch (parcelType) {
            case SMALL: return 1;
            case MEDIUM: return 3;
            case LARGE: return 6;
            case XL: return 10;
        }
        return 0;
    }
}
