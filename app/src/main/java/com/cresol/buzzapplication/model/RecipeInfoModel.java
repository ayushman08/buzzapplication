package com.cresol.buzzapplication.model;

import java.util.List;

/**
 * Created by Nitesh on 9/15/2016.
 */
 public class RecipeInfoModel {
    public int RecipeID;
    public int RestaurantID;
    public int RecipeType;
    public int MenuID;
    public String RecipeName;
    public Float StartingPrice;
    public Float MaxPrice;
    public Float LeastPrice;
    public String RecipeImage;
    public String ChefName;
    public String RecipeDescription;
    public Float IngredientsQuantity;

    public int getCaloriesCount() {
        return CaloriesCount;
    }

    public void setCaloriesCount(int caloriesCount) {
        CaloriesCount = caloriesCount;
    }

    public int getIngredientCount() {
        return IngredientCount;
    }

    public void setIngredientCount(int ingredientCount) {
        IngredientCount = ingredientCount;
    }

    public List<String> getIngredientList() {
        return IngredientList;
    }

    public void setIngredientList(List<String> ingredientList) {
        IngredientList = ingredientList;
    }

   // public List<RecipeNutritionModel> getNutritionList() {
     //   return NutritionList;
   // }

  //  public void setNutritionList(List<RecipeNutritionModel> nutritionList) {
    //    NutritionList = nutritionList;
    //}

    public Float getOverallRatingCount() {
        return OverallRatingCount;
    }

    public void setOverallRatingCount(Float overallRatingCount) {
        OverallRatingCount = overallRatingCount;
    }

    public Float getRatingUserCount() {
        return RatingUserCount;
    }

    public void setRatingUserCount(Float ratingUserCount) {
        RatingUserCount = ratingUserCount;
    }

    public List<String> IngredientList;
   // public List<RecipeNutritionModel> NutritionList;
    public int IngredientCount;
    public int CaloriesCount;
    public Float RatingUserCount;
    public Float OverallRatingCount;

    public List<String> getRecipeHowToMake() {
        return RecipeHowToMake;
    }

    public void setRecipeHowToMake(List<String> recipeHowToMake) {
        RecipeHowToMake = recipeHowToMake;
    }

    public List<String> RecipeHowToMake;
    public String RecipeCokingTime;
    public String RecipeCreateTime;
    public int RecipeStatus;


    public String getChefName() {
        return ChefName;
    }

    public void setChefName(String chefName) {
        ChefName = chefName;
    }

    public Float getIngredientsQuantity() {
        return IngredientsQuantity;
    }

    public void setIngredientsQuantity(Float ingredientsQuantity) {
        IngredientsQuantity = ingredientsQuantity;
    }

    public Float getLeastPrice() {
        return LeastPrice;
    }

    public void setLeastPrice(Float leastPrice) {
        LeastPrice = leastPrice;
    }

    public Float getMaxPrice() {
        return MaxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        MaxPrice = maxPrice;
    }

    public int getMenuID() {
        return MenuID;
    }

    public void setMenuID(int menuID) {
        MenuID = menuID;
    }

    public String getRecipeCokingTime() {
        return RecipeCokingTime;
    }

    public void setRecipeCokingTime(String recipeCokingTime) {
        RecipeCokingTime = recipeCokingTime;
    }

    public String getRecipeCreateTime() {
        return RecipeCreateTime;
    }

    public void setRecipeCreateTime(String recipeCreateTime) {
        RecipeCreateTime = recipeCreateTime;
    }

    public String getRecipeDescription() {
        return RecipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        RecipeDescription = recipeDescription;
    }

    public int getRecipeID() {
        return RecipeID;
    }

    public void setRecipeID(int recipeID) {
        RecipeID = recipeID;
    }

    public String getRecipeImage() {
        return RecipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        RecipeImage = recipeImage;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public int getRecipeStatus() {
        return RecipeStatus;
    }

    public void setRecipeStatus(int recipeStatus) {
        RecipeStatus = recipeStatus;
    }

    public int getRecipeType() {
        return RecipeType;
    }

    public void setRecipeType(int recipeType) {
        RecipeType = recipeType;
    }

    public int getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        RestaurantID = restaurantID;
    }

    public Float getStartingPrice() {
        return StartingPrice;
    }

    public void setStartingPrice(Float startingPrice) {
        StartingPrice = startingPrice;
    }


}
