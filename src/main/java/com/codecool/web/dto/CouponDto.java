package com.codecool.web.dto;

import com.codecool.web.model.Coupon;
import com.codecool.web.model.Shop;

import java.util.List;

public final class CouponDto {

    private final Coupon coupon;
    private final List<Shop> couponShops;
    private final List<Shop> allShops;

    public CouponDto(Coupon coupon, List<Shop> couponShops, List<Shop> allShops) {
        this.coupon = coupon;
        this.couponShops = couponShops;
        this.allShops = allShops;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public List<Shop> getCouponShops() {
        return couponShops;
    }

    public List<Shop> getAllShops() {
        return allShops;
    }
}
