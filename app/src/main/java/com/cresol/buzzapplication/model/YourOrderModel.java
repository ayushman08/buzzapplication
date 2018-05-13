package com.cresol.buzzapplication.model;

import java.util.List;

/**
 * Created by Saurabh on 9/22/2016.
 */
public class YourOrderModel {
    public List<OrderDishList> getDish() {
        return Dish;
    }

    public void setDish(List<OrderDishList> dish) {
        Dish = dish;
    }

    List<OrderDishList> Dish;
    Integer UserID;
    float VatTax;
    float ServiceCharge;
    float ServiceTax;
    float SwatchBharatCess;

    public Integer getNumberOfGuests() {
        return NumberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        NumberOfGuests = numberOfGuests;
    }

    public float getServiceCharge() {
        return ServiceCharge;
    }

    public void setServiceCharge(float serviceCharge) {
        ServiceCharge = serviceCharge;
    }

    public float getServiceTax() {
        return ServiceTax;
    }

    public void setServiceTax(float serviceTax) {
        ServiceTax = serviceTax;
    }

    public float getSwatchBharatCess() {
        return SwatchBharatCess;
    }

    public void setSwatchBharatCess(float swatchBharatCess) {
        SwatchBharatCess = swatchBharatCess;
    }

    public Integer getTableNumber() {
        return TableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        TableNumber = tableNumber;
    }

    public Integer getUserID() {
        return UserID;
    }

    public void setUserID(Integer userID) {
        UserID = userID;
    }

    public float getVatTax() {
        return VatTax;
    }

    public void setVatTax(float vatTax) {
        VatTax = vatTax;
    }

    Integer NumberOfGuests;
    Integer TableNumber;

}
