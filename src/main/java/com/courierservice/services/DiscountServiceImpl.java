package com.courierservice.services;

import com.courierservice.models.OrderResult;
import com.courierservice.models.Parcel;
import com.courierservice.models.ParcelType;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

public class DiscountServiceImpl implements DiscountService{
    @Override
    public OrderResult applyDiscount(OrderResult orderResult) {
        List<Parcel> parcelList = orderResult.getParcelList();
        int smalls = 0, mediums = 0, large = 0, xl = 0, heavy = 0;
        BigDecimal discount = BigDecimal.ZERO;
        for(Parcel parcel : parcelList) {
            if(parcel.getType() == ParcelType.SMALL) smalls++;
            else if(parcel.getType() == ParcelType.MEDIUM) mediums++;
            else if(parcel.getType() == ParcelType.LARGE) large++;
            else if(parcel.getType() == ParcelType.XL) xl++;
            else if(parcel.getType() == ParcelType.HEAVY) heavy++;
        }
        if(smalls > 3) {
            BigDecimal discountableItems = new BigDecimal( smalls/4);
            discount = discount.add(getDiscount(ParcelType.SMALL)).multiply(discountableItems);
        }
        if(mediums > 2) {
            BigDecimal discountableItems = new BigDecimal(mediums/3);
            discount = discount.add(getDiscount(ParcelType.MEDIUM)).multiply(discountableItems);
        }
        int mixed = large + xl + heavy + Math.floorMod(smalls, 4) + Math.floorMod(mediums, 3);
        if(mixed > 4) {
            BigDecimal discountableItems = new BigDecimal(mixed/5);
            BigDecimal value = getSmallestCost(Math.floorMod(smalls, 4), Math.floorMod(mediums, 3), large, xl, heavy);
            discount = discount.add(value).multiply(discountableItems);
        }
        orderResult.setDiscount(discount.negate());
        orderResult.setTotalCost(orderResult.getTotalCost().subtract(discount));
        return orderResult;
    }

    private BigDecimal getSmallestCost(int smalls, int mediums, int large, int xl, int heavy) {
        List<Integer> parcels = Lists.newArrayList(smalls, mediums, large, xl, heavy);
        for(int i = 0; i < parcels.size(); i++) {
            if(parcels.get(i) > 0) return getDiscount(ParcelType.values()[i]);
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal getDiscount(ParcelType parcelType) {
        switch(parcelType) {
            case SMALL: return new BigDecimal(3);
            case MEDIUM: return new BigDecimal(8);
            case LARGE: return new BigDecimal(15);
            case XL: return new BigDecimal(25);
            case HEAVY: return new BigDecimal(50);
            default: return BigDecimal.ZERO;
        }
    }
}
