package com.cresol.buzzapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.model.AddIngredientModelOutput;
import com.cresol.buzzapplication.model.AllRecipeModel;
import com.cresol.buzzapplication.model.AllRestaurantModel;
import com.cresol.buzzapplication.model.LikeActivityModel;
import com.cresol.buzzapplication.model.ReadProfileInfo;
import com.cresol.buzzapplication.util.DataResource;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nitesh on 10/6/2016.
 */
public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_TIME = 1000;
    UserSessionManager sessionManger;
    api BuzzdApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManger = new UserSessionManager(this);

        BuzzdApi = GlobalMethods.GetBuzzdAPI(SplashActivity.this);

        GlobalMethods.ClearCacheData();
        RetroCallForRecipes();
        RetroCallForRestaurant();
        retroCallProfileData();
        RetroCallForGettingDisLikeIngredient();
        RetroCallForGettingLikeIngredient();

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent;
                if (sessionManger.isUserLoggedIn()) {
                    mainIntent = new Intent(SplashActivity.this,
                            HomeActivity.class);
                } else {
                    mainIntent = new Intent(SplashActivity.this,
                            SignupActivity.class);
                }
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
                handler.removeCallbacks(this);
            }
        }, SPLASH_DISPLAY_TIME);


    }
     //we create a method
    public void retroCallProfileData() {
        // if (sessionManager.isUserLoggedIn()) {
        //if (GlobalMethods.isNetworkAvailable(getActivity())) {

     //here we are calling for the peofile data according to the customerId
        Call<ReadProfileInfo> outputcall = BuzzdApi.GetProfileData(13);


        outputcall.enqueue(new Callback<ReadProfileInfo>() {
            @Override
            public void onResponse(Call<ReadProfileInfo> call, Response<ReadProfileInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //getting profile data from response.body
                    DataResource.cacheProfileInfo = response.body();
                }

            }

            @Override
            public void onFailure(Call<ReadProfileInfo> call, Throwable t) {

            }
        });

        // }


        //   }

    }
       //getting list of all recipies from the server database
      public void RetroCallForRecipes() {

        Call<List<AllRecipeModel>> recipeapi = BuzzdApi.GetAllRecipes();
        recipeapi.enqueue(new Callback<List<AllRecipeModel>>() {
            @Override
            public void onResponse(Call<List<AllRecipeModel>> call, Response<List<AllRecipeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DataResource.cacheAllRecipeData = response.body();


                }

            }

            @Override
            public void onFailure(Call<List<AllRecipeModel>> call, Throwable t) {

            }
        });

    }

        public void RetroCallForRestaurant() {

      //we get the all restaurant list with the details of restaurant
        Call<List<AllRestaurantModel>> restaurantcall = BuzzdApi.GetAllRestaurants();


        restaurantcall.enqueue(new Callback<List<AllRestaurantModel>>() {
            @Override
            public void onResponse(Call<List<AllRestaurantModel>> call, Response<List<AllRestaurantModel>> response) {
                //check that the response is successful and body is not null
                if (response.isSuccessful() && response.body() != null) {
                    DataResource.cacheAllRestaurantData = response.body();

                }

            }

            @Override
            public void onFailure(Call<List<AllRestaurantModel>> call, Throwable t) {

            }
        });
    }
    //getting the like ingredients
    public void RetroCallForGettingLikeIngredient() {
        if(sessionManger.isUserLoggedIn()) {
            if (GlobalMethods.isNetworkAvailable(this)) {
                // GlobalMethods.ShowProgressBar(this);

                Call<AddIngredientModelOutput> outputCall = BuzzdApi.GetLikeIngredient(sessionManger.GetUserId());

                //  GlobalMethods.ShowProgressBar(this);
                outputCall.enqueue(new Callback<AddIngredientModelOutput>() {
                    @Override
                    public void onResponse(Call<AddIngredientModelOutput> call, Response<AddIngredientModelOutput> response) {
                        if (response.isSuccessful() & response.body() != null) {
                            if (response.body().IngredientName != null) {

                                for (String ingredientName : response.body().IngredientName) {

                                    LikeActivityModel listOneData = new LikeActivityModel(ingredientName, R.drawable.cross);
                                    //Adding data to listOne obj
                                    DataResource.cacheLikeListObj.add(listOneData);
                                }

                            }
                        }
                        //   GlobalMethods.HideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<AddIngredientModelOutput> call, Throwable t) {
                        //  GlobalMethods.HideProgressBar();
                    }
                });
            }
        }else{
            GlobalMethods.ShowLoginMessage(this);
        }

    }

    public void RetroCallForGettingDisLikeIngredient() {
        if(sessionManger.isUserLoggedIn()) {
            if (GlobalMethods.isNetworkAvailable(this)) {
                // GlobalMethods.ShowProgressBar(this);

                Call<AddIngredientModelOutput> outputCall = BuzzdApi.GetDisLikeIngredient(sessionManger.GetUserId());

                outputCall.enqueue(new Callback<AddIngredientModelOutput>() {
                    @Override
                    public void onResponse(Call<AddIngredientModelOutput> call, Response<AddIngredientModelOutput> response) {
                        if (response.isSuccessful() & response.body() != null) {

                            if (response.body().IngredientName != null) {

                                //here we gwt the ingredient name by retrofit library
                                for (String ingredientName : response.body().IngredientName) {

                                    LikeActivityModel listOneData = new LikeActivityModel(ingredientName, R.drawable.cross);
                                    //Adding data to listOne obj
                                    DataResource.cacheDisLikedListObj.add(listOneData);
                                }
                            }

                        }
                        //  GlobalMethods.HideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<AddIngredientModelOutput> call, Throwable t) {
                        //   GlobalMethods.HideProgressBar();
                    }
                });
            }
        }
    else{
        GlobalMethods.ShowLoginMessage(this);
        }
    }



    public void GetLocation() {

    }

}
