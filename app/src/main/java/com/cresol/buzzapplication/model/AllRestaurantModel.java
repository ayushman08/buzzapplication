package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 7/28/2016.
 */
public class AllRestaurantModel {


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRestaurantDistance() {
        return RestaurantDistance;
    }

    public void setRestaurantDistance(String restaurantDistance) {
        RestaurantDistance = restaurantDistance;
    }

    public Integer getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        RestaurantID = restaurantID;
    }


    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getRestaurantOpeningStatus() {
        return RestaurantOpeningStatus;
    }

    public void setRestaurantOpeningStatus(String restaurantOpeningStatus) {
        RestaurantOpeningStatus = restaurantOpeningStatus;
    }

    public String getRestaurantImage() {
        return RestaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        RestaurantImage = restaurantImage;
    }

    public String RestaurantImage;
    public String RestaurantName;
    public Integer RestaurantID;
    public String Description;
    public String RestaurantOpeningStatus;
    public String RestaurantDistance;


}
