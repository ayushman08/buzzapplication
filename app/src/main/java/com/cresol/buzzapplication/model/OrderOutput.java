package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 9/22/2016.
 */
public class OrderOutput {
    public  String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int OrderID;
}
