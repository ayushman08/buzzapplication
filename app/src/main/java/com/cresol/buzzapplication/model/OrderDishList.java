package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 9/22/2016.
 */
public class OrderDishList {
   public int  ItemID;
    public int  Quantity;
    public Float Price;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }



    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }



    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }



}
