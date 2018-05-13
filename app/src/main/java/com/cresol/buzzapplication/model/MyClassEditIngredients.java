package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 8/2/2016.
 */
public class MyClassEditIngredients {
    public String getIngredientName() {
        return ingredientName;
    }



    public String ingredientName;
    public Integer crossImage;

    public MyClassEditIngredients(String ingredientName,Integer crossImage)
    {
       this.ingredientName=ingredientName;
        this.crossImage=crossImage;

    }

}
