package com.cresol.buzzapplication.api;


import com.cresol.buzzapplication.model.AddIngredientModelInput;
import com.cresol.buzzapplication.model.AddIngredientModelOutput;
import com.cresol.buzzapplication.model.AllRecipeModel;
import com.cresol.buzzapplication.model.AllRestaurantModel;
import com.cresol.buzzapplication.model.CreateRecipeModel;
import com.cresol.buzzapplication.model.DishTypeModel;
import com.cresol.buzzapplication.model.DishesModel;
import com.cresol.buzzapplication.model.MenuTypeModel;
import com.cresol.buzzapplication.model.OrderOutput;
import com.cresol.buzzapplication.model.ProfileInput;
import com.cresol.buzzapplication.model.ProfileOutput;
import com.cresol.buzzapplication.model.RateRestaurantOutput;
import com.cresol.buzzapplication.model.RatingRestaurantInput;
import com.cresol.buzzapplication.model.ReadProfileInfo;
import com.cresol.buzzapplication.model.RecipeInfoModel;
import com.cresol.buzzapplication.model.RecipeNutritionModel;
import com.cresol.buzzapplication.model.StandardOutput;
import com.cresol.buzzapplication.model.YourOrderModel;
import com.cresol.buzzapplication.model.apimodel.UserRegistrationModel.Register_Response;
import com.cresol.buzzapplication.model.apimodel.UserRegistrationModel.UserInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Nitesh on 9/10/2016.
 */
public interface api {
    //register the user,in response we get the userId
    @POST("add_users")
    Call<Register_Response> Register(@Body UserInformation obj);

    @GET("Allrecipe")
        // GetAllRecipes()method,which type of data it will give <List<AllRecipeModel>> have chefname,recipeimage,recipeid,etc
    Call<List<AllRecipeModel>> GetAllRecipes();

    @GET("recipe")
    Call<RecipeInfoModel> GetRecipeInfo(@Query("id") int id);

    @GET("recipenutritioninfo")
    Call<List<RecipeNutritionModel>> GetRecipeNutritionInfo(@Query("id") int id);

    //method to get the All Restaurant list
    @GET("Allrestaurants")
    Call<List<AllRestaurantModel>> GetAllRestaurants();

    @GET("recipesbyRestaurentsID")
    Call<List<DishesModel>> GetRestaurantDishes(@Query("id") int id);

    @GET("dishestype")
    Call<List<DishTypeModel>> GetDishesType();

    @GET("menutype")
    Call<List<MenuTypeModel>> GetMenuType();

    //Ingredient

    //Adding Like Ingredient
    @POST("userAddLikeIngredient")
    Call<AddIngredientModelOutput> AddLikeIngredient(@Body AddIngredientModelInput obj);

    //Adding dislike Ingredient
    @POST("userAddDisLikeIngredient")
    Call<AddIngredientModelOutput> AddDisLikeIngredient(@Body AddIngredientModelInput obj);


    //Removing Like Ingredient
    @POST("userRemoveLikeIngredient")
    Call<AddIngredientModelOutput> RemoveLikeIngredient(@Body AddIngredientModelInput obj);


    //Removing dislike Ingredient
    @POST("userRemoveDisLikeIngredient")
    Call<AddIngredientModelOutput> RemoveDisLikeIngredient(@Body AddIngredientModelInput obj);


    //Removing dislike Ingredient
    @GET("userLikeIngredientListing")
    Call<AddIngredientModelOutput> GetLikeIngredient(@Query("userId") int userId);

    //Removing dislike Ingredient
    @GET("usersDislikeIngredientsListing")
    Call<AddIngredientModelOutput> GetDisLikeIngredient(@Query("userId") int userId);


    //For order

    //Removing dislike Ingredient
    @POST("create_order")
    Call<OrderOutput> PostOrder(@Body YourOrderModel obj);

    //RateRestaurant

    @POST("AddRestaurantRating")
    Call<RateRestaurantOutput> rateRestaurant(@Body RatingRestaurantInput rateRestaurantObj);

    //Save profile Information
    @POST("update_users")
    Call<ProfileOutput> updateProfile(@Body ProfileInput profileObj);

    //Save profile Information
    @GET("getcustomerinfo")
    Call<ReadProfileInfo> GetProfileData(@Query("CustomerId") int customerId);

    //Recipe adding
    @POST("create_recipe")
    Call<StandardOutput> CreateRecipe(@Body CreateRecipeModel obj);

}
