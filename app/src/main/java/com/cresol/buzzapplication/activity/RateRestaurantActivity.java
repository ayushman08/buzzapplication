package com.cresol.buzzapplication.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.adapter.RateRestaurantAdapter;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.model.RateRestaurantOutput;
import com.cresol.buzzapplication.model.RatingRestaurantInput;
import com.cresol.buzzapplication.model.RestaurantRateTypeWithRating;
import com.cresol.buzzapplication.util.GlobalMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;


/**
 * Created by Saurabh on 7/12/2016.
 */
public class RateRestaurantActivity extends DrawerActivity {

    //Using for saving rating
    public static List<RestaurantRateTypeWithRating> saveRating = new ArrayList<>();
    int restaurantId;
    TextView restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_rate_restaurant, null, false);
        drawer.addView(contentView, 0);


        GlobalMethods.overrideFonts(this, contentView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_drawer_id);
        setSupportActionBar(toolbar);

        //Remove apptitle from Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        //
        TextView continueButton = (TextView) findViewById(R.id.continue_txt_id);
        restaurantName = (TextView) findViewById(R.id.restaurant_name_id);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetroCallForRateRestaurant();
            }
        });


        //Here we get the restaurant id by the intent and value
        String recipeIdVar = getIntent().getExtras().getString("RestaurantId");
        String restaurantNameData = getIntent().getExtras().getString("RestaurantName");
        restaurantName.setText(restaurantNameData);

        if (!recipeIdVar.isEmpty()) {
            restaurantId = Integer.valueOf(recipeIdVar);

        }

       //take rating with value
        List<RestaurantRateTypeWithRating> obj = new ArrayList<>();
            //add type and value to the list of this class type  RestaurantRateTypeWithRating
        RestaurantRateTypeWithRating data = new RestaurantRateTypeWithRating();
        data.setRatingID("Taste");
        data.setRatingValue("1.0");

        RestaurantRateTypeWithRating data1 = new RestaurantRateTypeWithRating();
        data1.setRatingID("Hygiene");
        data1.setRatingValue("2.0");

        RestaurantRateTypeWithRating data2 = new RestaurantRateTypeWithRating();
        data2.setRatingID("Comfort");
        data2.setRatingValue("3.0");

        RestaurantRateTypeWithRating data3 = new RestaurantRateTypeWithRating();
        data3.setRatingID("Waiter Service");
        data3.setRatingValue("4.0");

        RestaurantRateTypeWithRating data4 = new RestaurantRateTypeWithRating();
        data4.setRatingID("Price");
        data4.setRatingValue("5.0");

       //Add all to the list
        obj.add(data);
        obj.add(data1);
        obj.add(data2);
        obj.add(data3);
        obj.add(data4);
      //save it in another list
        saveRating = obj;


        RateRestaurantAdapter adapter = new RateRestaurantAdapter(RateRestaurantActivity.this, R.layout.rate_restaurant_child_list, obj);


        ListView list = (ListView) findViewById(R.id.rate_List);
        list.setAdapter(adapter);

    }

//for rating,we are getting data from the server
     public void RetroCallForRateRestaurant() {

        //       if (sessionManager.isUserLoggedIn()) {

        if (GlobalMethods.isNetworkAvailable(this)) {

            api buzzdAPI = GlobalMethods.GetBuzzdAPI(this);

            GlobalMethods.ShowProgressBar(this);


            List<RestaurantRateTypeWithRating> listObj = new ArrayList<RestaurantRateTypeWithRating>();

            listObj = saveRating;

            //here we are setting rating according to the restaurant
            RatingRestaurantInput ratingRestaurantInput = new RatingRestaurantInput();

            ratingRestaurantInput.setCustomerID(13);

            ratingRestaurantInput.setRestaurantID(restaurantId);
            ratingRestaurantInput.setRatingItems(listObj);


            Call<RateRestaurantOutput> ratRestaurantResponse = buzzdAPI.rateRestaurant(ratingRestaurantInput);
            ratRestaurantResponse.enqueue(new Callback<RateRestaurantOutput>() {
                @Override
                public void onResponse(Call<RateRestaurantOutput> call, Response<RateRestaurantOutput> response) {
                    if (!response.isSuccessful()) {
                        try {
                            String a = response.errorBody().string();
                            Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    GlobalMethods.HideProgressBar();
                    Toast.makeText(RateRestaurantActivity.this, "Rating" + String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<RateRestaurantOutput> call, Throwable t) {
                    Toast.makeText(RateRestaurantActivity.this, "Fail", Toast.LENGTH_LONG).show();

                }
            });

        }


        //  }

    }
}