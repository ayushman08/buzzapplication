package com.cresol.buzzapplication.model;

/**
 * Created by Nitesh on 9/15/2016.
 */
public class RecipeNutritionModel {

    public String getNutritionName() {
        return NutritionName;
    }

    public void setNutritionName(String nutritionName) {
        NutritionName = nutritionName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String NutritionName;
    public String Quantity;


}
