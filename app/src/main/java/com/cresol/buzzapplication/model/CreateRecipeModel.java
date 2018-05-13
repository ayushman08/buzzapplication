package com.cresol.buzzapplication.model;

/**
 * Created by Nitesh on 9/24/2016.
 */
public class CreateRecipeModel {
    public String RecipieName;

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipieDescription() {
        return RecipieDescription;
    }

    public void setRecipieDescription(String recipieDescription) {
        RecipieDescription = recipieDescription;
    }

    public String getRecipieHowToMake() {
        return RecipieHowToMake;
    }

    public void setRecipieHowToMake(String recipieHowToMake) {
        RecipieHowToMake = recipieHowToMake;
    }

    public String getRecipieImage() {
        return RecipieImage;
    }

    public void setRecipieImage(String recipieImage) {
        RecipieImage = recipieImage;
    }

    public String getRecipieName() {
        return RecipieName;
    }

    public void setRecipieName(String recipieName) {
        RecipieName = recipieName;
    }

    public String getStartingPrice() {
        return StartingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        StartingPrice = startingPrice;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String RecipieImage;
    public String StartingPrice;
    public int UserID;
    public String RecipieDescription;
    public String ingredients;
    public String RecipieHowToMake;




}
