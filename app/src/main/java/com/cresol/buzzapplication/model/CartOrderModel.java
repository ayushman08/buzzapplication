package com.cresol.buzzapplication.model;

/**
 * Created by Nitesh on 9/25/2016.
 */
public class CartOrderModel {
    public String getDish_Id() {
        return Dish_Id;
    }

    public void setDish_Id(String dish_Id) {
        Dish_Id = dish_Id;
    }

    public String getDish_Image() {
        return Dish_Image;
    }

    public void setDish_Image(String dish_Image) {
        Dish_Image = dish_Image;
    }

    public String getDish_Name() {
        return Dish_Name;
    }

    public void setDish_Name(String dish_Name) {
        Dish_Name = dish_Name;
    }

    public String getDish_Price() {
        return Dish_Price;
    }

    public void setDish_Price(String dish_Price) {
        Dish_Price = dish_Price;
    }

    public String getDish_Quantity() {
        return Dish_Quantity;
    }

    public void setDish_Quantity(String dish_Quantity) {
        Dish_Quantity = dish_Quantity;
    }

    public String getRestaurant_Id() {
        return Restaurant_Id;
    }

    public void setRestaurant_Id(String restaurant_Id) {
        Restaurant_Id = restaurant_Id;
    }

    public String Dish_Name;
    public String Dish_Image;
    public String Dish_Price;
    public String Dish_Id;
    public String Dish_Quantity;
    public String Restaurant_Id;

    public CartOrderModel(String dish_Id, String dish_Name, String dish_Image, String dish_Quantity, String dish_Price, String restaurant_Id) {
        this.Dish_Id = dish_Id;
        this.Dish_Name = dish_Name;
        this.Dish_Image = dish_Image;
        this.Dish_Price = dish_Price;
        this.Dish_Quantity = dish_Quantity;
        this.Restaurant_Id = restaurant_Id;
    }

}
