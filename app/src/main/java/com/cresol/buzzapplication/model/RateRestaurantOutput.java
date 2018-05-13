package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 9/23/2016.
 */
public class RateRestaurantOutput {


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public  Boolean status;

    public String message;
}
