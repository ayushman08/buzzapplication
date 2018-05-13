package com.cresol.buzzapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.adapter.RestaurantAdapter;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.model.AllRestaurantModel;
import com.cresol.buzzapplication.util.DataResource;
import com.cresol.buzzapplication.util.GlobalMethods;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saurabh on 7/28/2016.
 */
public class RestaurantActivity extends DrawerActivity {

    api buzzdApi;
    List<AllRestaurantModel> allRestaurantModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_restaurant, null, false);
        drawer.addView(contentView, 0);

        GlobalMethods.overrideFonts(this, contentView);

        showCartCountOnCart();

        // We make methods in static Global class in which GetBuzzdAPI(this),here this is context(means that class)
        buzzdApi = GlobalMethods.GetBuzzdAPI(this);
        //First check the Network is available,for that we make a method in GlobalMethods class
        if (GlobalMethods.isNetworkAvailable(this)) {

        }

        //RetroCall
        if (GlobalMethods.isNetworkAvailable(RestaurantActivity.this)) {
            //Access data of Restaurant from the class in which we save all server dat to thee list
           // static data that we have save during splash screen
            if (DataResource.cacheAllRestaurantData != null && !DataResource.cacheAllRestaurantData.isEmpty()) {
                allRestaurantModels = DataResource.cacheAllRestaurantData;
                BindData();
            } else {
                //gete the data from the server
                RetroCallForRestaurant();
            }
        }

    }

    //method to show cart count on the toolbar
    public void showCartCountOnCart() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_drawer_id);
        setSupportActionBar(toolbar);
        TextView toolbarCartTxt = (TextView) findViewById(R.id.toolbar_cart_text);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.toolbar_image_layout);
        //get the count of selected dishes fom the sqlite database
        int count = GlobalMethods.GetCount(this);
        toolbarCartTxt.setText(String.valueOf(count));

        //Remove apptitle from Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GlobalMethods.GetCount(getApplicationContext()) != 0) {
                    Intent i = new Intent(getApplicationContext(), YourOrderActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Cart is Empty.Please add product", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

      //make a method by which we get the Restaurant list
     public void RetroCallForRestaurant() {


        Call<List<AllRestaurantModel>> restaurantcall = buzzdApi.GetAllRestaurants();

        GlobalMethods.ShowProgressBar(this);
        restaurantcall.enqueue(new Callback<List<AllRestaurantModel>>() {
            @Override
            public void onResponse(Call<List<AllRestaurantModel>> call, Response<List<AllRestaurantModel>> response) {
                //check that the response is successful and body is not null
                if (response.isSuccessful() && response.body() != null) {
                    DataResource.cacheAllRestaurantData = response.body();
                    allRestaurantModels = response.body();
                    BindData();
                }
                GlobalMethods.HideProgressBar();
            }

            @Override
            public void onFailure(Call<List<AllRestaurantModel>> call, Throwable t) {
                GlobalMethods.HideProgressBar();
            }
        });
    }

//set the data to the list
      public void BindData() {

        RestaurantAdapter adapter = new RestaurantAdapter(RestaurantActivity.this, R.layout.restaurant_child_list, allRestaurantModels);


        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //allRestaurantModels have all restaurant list data
                //obj have  position by list
                AllRestaurantModel obj = allRestaurantModels.get(position);

                Intent i = new Intent(getApplicationContext(), DishesActivity.class);
                //Sending data to the other activity by putExtra
                i.putExtra("RestaurantId", String.valueOf(obj.RestaurantID));
                i.putExtra("RestaurantImage", String.valueOf(obj.getRestaurantImage()));
                i.putExtra("RestaurantName", String.valueOf(obj.getRestaurantName()));
                startActivity(i);
            }
        });

    }

}

