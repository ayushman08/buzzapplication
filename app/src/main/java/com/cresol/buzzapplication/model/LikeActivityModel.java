package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 8/25/2016.
 */
public class LikeActivityModel {
    public String getIngredientName() {
        return ingredientName;
    }


    public String ingredientName;
    public Integer crossImage;

    public LikeActivityModel(String ingredientName, Integer crossImage) {
        this.ingredientName = ingredientName;
        this.crossImage = crossImage;

    }

}