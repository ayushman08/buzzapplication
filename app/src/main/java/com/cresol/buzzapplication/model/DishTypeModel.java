package com.cresol.buzzapplication.model;

/**
 * Created by Nitesh on 9/16/2016.
 */
public class DishTypeModel {
    public Integer DishID;
    public String DishTypeName;

    public Integer getDishID() {
        return DishID;
    }

    public void setDishID(Integer dishID) {
        DishID = dishID;
    }

    public String getDishTypeName() {
        return DishTypeName;
    }

    public void setDishTypeName(String dishTypeName) {
        DishTypeName = dishTypeName;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer Status;
}
