package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 8/1/2016.
 */
public class MyClassYourOrder {
    public Integer getDishID() {
        return DishID;
    }

    public void setDishID(Integer dishID) {
        DishID = dishID;
    }

    public String getDishImage() {
        return DishImage;
    }

    public void setDishImage(String dishImage) {
        DishImage = dishImage;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public Integer getDishTypeID() {
        return DishTypeID;
    }

    public void setDishTypeID(Integer dishTypeID) {
        DishTypeID = dishTypeID;
    }

    public Integer getMenuTypeID() {
        return MenuTypeID;
    }

    public void setMenuTypeID(Integer menuTypeID) {
        MenuTypeID = menuTypeID;
    }

    public Integer getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        RestaurantID = restaurantID;
    }

    public String getStartingPrice() {
        return StartingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        StartingPrice = startingPrice;
    }

    public String DishImage;
    public String DishName;
    public Integer RestaurantID;
    public String StartingPrice;
    public Integer DishID;
    public Integer MenuTypeID;
    public Integer DishTypeID;

}


