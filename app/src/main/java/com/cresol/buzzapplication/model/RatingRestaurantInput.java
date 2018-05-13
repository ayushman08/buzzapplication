package com.cresol.buzzapplication.model;

import java.util.List;

/**
 * Created by Saurabh on 9/23/2016.
 */
public class RatingRestaurantInput {
    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    public List<RestaurantRateTypeWithRating> getRatingItems() {
        return RatingItems;
    }

    public void setRatingItems(List<RestaurantRateTypeWithRating> ratingItems) {
        RatingItems = ratingItems;
    }

    public Integer getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        RestaurantID = restaurantID;
    }

    Integer CustomerID;
    Integer RestaurantID;
    List<RestaurantRateTypeWithRating> RatingItems;
}
