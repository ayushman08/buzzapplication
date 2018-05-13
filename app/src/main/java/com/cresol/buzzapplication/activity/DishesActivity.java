//package Name
package com.cresol.buzzapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.adapter.DishesAdapter;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.model.CartCount;
import com.cresol.buzzapplication.model.DishTypeModel;
import com.cresol.buzzapplication.model.DishesModel;
import com.cresol.buzzapplication.model.InterfaceDishToRating;
import com.cresol.buzzapplication.model.MenuTypeModel;
import com.cresol.buzzapplication.util.GlobalMethods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saurabh on 7/29/2016.
 */
//Extending DRAwerActivity to every Activity By which Drawer will show on Every Activity
//Implements interface methods to send cartcount to the Activity Toolbar
public class DishesActivity extends DrawerActivity implements InterfaceDishToRating, CartCount {

    // we created a List in which we save the list Data after clicking on addicon
    public static List<Integer> addedDishData = new ArrayList<Integer>();

    //Array list by which we can take definite amount of Data
    ArrayList<String> items = new ArrayList<String>();
    //Access to whole class,Spinner declare as instance variables
    RatingBar ratingBar;
    Spinner menuSpinner;
    Spinner dishSpinner;
    int restaurantId;
    String restaurantImage = "";
    String restaurantName = "";
    api BuzzdApi;
    static List<DishesModel> dishData;
    List<MenuTypeModel> menuTypeData;
    List<DishTypeModel> dishTypeData;
    TextView toolbarCartTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //----------------------
        //adding drawer to the activity view by add view method in DrawerActivity class
        //Taking  Layout inflater class
        //By getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // INflating  activity will give view of activity_choose_items
        View contentView = inflater.inflate(R.layout.activity_choose_items, null, false);
        //taking drawer object(donot declare in this class as it is already declare  in DrawerActivity class),By addView(contentView, 0);method
        //here contentView is View object of activity_choose_items
        drawer.addView(contentView, 0);

        GlobalMethods.overrideFonts(this, contentView);

        showCartCountOnCart();


        BuzzdApi = GlobalMethods.GetBuzzdAPI(this);

        //Here we get the restaurant id by the intent and value
        String recipeIdVar = getIntent().getExtras().getString("RestaurantId");



        if (!recipeIdVar.isEmpty()) {
            restaurantId = Integer.valueOf(recipeIdVar);

            if (GlobalMethods.isNetworkAvailable(this)) {
                //according to restaurant,we get the dish
                RetroCallForDish(restaurantId);
            }
        }


        dishSpinner = (Spinner) findViewById(R.id.spinner2);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

       //check if there is any image,than clear it

        List<DishesModel> obj = new ArrayList<>();

    }


    //onclick method declare in xml
    public void orderNow(View v) {


        //on clicking,we move from this Activity to another Activity
        if (GlobalMethods.GetCount(getApplicationContext()) != 0) {
            Intent i = new Intent(getApplicationContext(), YourOrderActivity.class);
            startActivity(i);
        }else {
            Toast.makeText(getApplicationContext(), "Cart is Empty.Please add product", Toast.LENGTH_LONG).show();
        }
    }

    //created method to get dishes according to the restaurant id
    public void RetroCallForDish(int restaurantId) {
        //call by the retrofit library
        Call<List<DishesModel>> dishesCall = BuzzdApi.GetRestaurantDishes(restaurantId);

        GlobalMethods.ShowProgressBar(this);
        dishesCall.enqueue(new Callback<List<DishesModel>>() {
            @Override
            public void onResponse(Call<List<DishesModel>> call, Response<List<DishesModel>> response) {
                GlobalMethods.HideProgressBar();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() != 0) {
                        dishData = response.body();

                        BindData(dishData);
                        RetroCallForMenuType();
                        RetroCallForDishType();
                    } else {
                        GlobalMethods.HideProgressBar();
                        Toast.makeText(getApplicationContext(), "No Dish available", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(DishesActivity.this, RestaurantActivity.class);
                        getApplicationContext().startActivity(i);

                    }
                }

            }

            @Override
            public void onFailure(Call<List<DishesModel>> call, Throwable t) {
                GlobalMethods.HideProgressBar();
            }
        });
    }


    public void BindData(List<DishesModel> obj) {

        // GlobalMethods.ShowProgressBar(this);
        DishesAdapter adapter = new DishesAdapter(DishesActivity.this, R.layout.choose_items_child_list, obj);


        //Accessing ListView
        ListView list = (ListView) findViewById(R.id.list);
        //set adapter in ListView ref by setAdapter method,passing Adapter obj(adapter) to setAdapter method
        list.setAdapter(adapter);
        //GlobalMethods.HideProgressBar();
    }


    public void RetroCallForMenuType() {
        Call<List<MenuTypeModel>> menuTypeModelCall = BuzzdApi.GetMenuType();
        // GlobalMethods.ShowProgressBar(this);
        menuTypeModelCall.enqueue(new Callback<List<MenuTypeModel>>() {
            @Override
            public void onResponse(Call<List<MenuTypeModel>> call, Response<List<MenuTypeModel>> response) {
                //    GlobalMethods.HideProgressBar();
                if (response.isSuccessful() && response.body() != null) {
                    menuTypeData = response.body();
                    BindMenuType();
                }


            }

            @Override
            public void onFailure(Call<List<MenuTypeModel>> call, Throwable t) {
                //  GlobalMethods.HideProgressBar();
            }
        });
    }

     public void RetroCallForDishType() {
        Call<List<DishTypeModel>> dishtypecall = BuzzdApi.GetDishesType();
        //   GlobalMethods.ShowProgressBar(this);
        dishtypecall.enqueue(new Callback<List<DishTypeModel>>() {
            @Override
            public void onResponse(Call<List<DishTypeModel>> call, Response<List<DishTypeModel>> response) {
                // GlobalMethods.HideProgressBar();
                if (response.isSuccessful() && response.body() != null) {
                    dishTypeData = response.body();
                    BindDishType();

                }

            }

            @Override
            public void onFailure(Call<List<DishTypeModel>> call, Throwable t) {
                //    GlobalMethods.HideProgressBar();
            }
        });
    }

    //add data to  spinner
    public void BindMenuType() {
       //we took a array for taking the spinner menu data  from server(response is taken in menuTypeData) to this array
        ArrayList<String> menu = new ArrayList<String>();
        //Array for spinner menu, run upto all data will add,menuTypeData have all data like menu id,status,menu nname,from that we took menuName
        for (MenuTypeModel data : menuTypeData) {
            menu.add(data.getMenuName());
        }

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, menu);
        //taking spinner oBj,we are going to setAdapter to Spinner,we pass Aray Adapter obj in setAdapter method
        //        menuSpinner.setAdapter(dataAdapter1);


        //     menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        //       @Override
        //     public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        // your code here
        //       MenuTypeModel obj = menuTypeData.get(position);
        //   if(!obj.getMenuName().equals("All")) {
        //     int menuTypeId = obj.MenuID;
        //   List<DishesModel> dishData = new ArrayList<DishesModel>();

        //              for (DishesModel DishData : dishData) {
        //                if (DishData.getMenuTypeID() == menuTypeId) {
        //                  dishData.add(DishData);
        //            }
        //      }
        //                bindRetroData(dishData);
        //             }
        //             else if(dishData.size()!=0){
        //             bindRetroData(dishData);
        //       }
        //   }

        //          @Override
        //        public void onNothingSelected(AdapterView<?> parentView) {
        // your code here
        //      }

        // });

    }
//Add dishes to the list
      public void BindDishType() {
          //popup show to us in dishactivity
        //choose arraylist,because there is no of items
        final ArrayList<String> dish = new ArrayList<String>();
        //dishTypeData is list ref have the response from the server
        for (DishTypeModel data : dishTypeData) {
            dish.add(data.DishTypeName);
        }

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dish);
        //taking spinner oBj,we are going to setAdapter to Spinner,we pass Aray Adapter obj in setAdapter method
        dishSpinner.setAdapter(dataAdapter1);

        dishSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                DishTypeModel obj = dishTypeData.get(position);

                if (!obj.DishTypeName.equals("All")) {
                    int dishTypeId = obj.DishID;

                    List<DishesModel> dishData = new ArrayList<DishesModel>();

                    for (DishesModel DishData : dishData) {
                        if (DishData.getDishTypeID() == dishTypeId) {
                            dishData.add(DishData);
                        }
                    }
                    BindData(dishData);
                }
                else if (dishData.size() != 0) {

                    BindData(dishData);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }


    @Override
    public void transferCountToCart(Integer cartCount) {

        toolbarCartTxt.setText(cartCount.toString());
    }


    @Override
      public void ShowCartCount() {
        int count = GlobalMethods.GetCount(this);
        TextView toolbarCartTxt = (TextView) findViewById(R.id.toolbar_cart_text);
        toolbarCartTxt.setText(String.valueOf(count));
     }

     //Show cart on toolbar
      public void showCartCountOnCart() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_drawer_id);
        setSupportActionBar(toolbar);

        TextView toolbarCartTxt = (TextView) findViewById(R.id.toolbar_cart_text);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.toolbar_image_layout);


        int count = GlobalMethods.GetCount(this);
        toolbarCartTxt.setText(String.valueOf(count));

        //Remove apptitle from Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();

//Layout of cart recipeImage and text

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


    @Override
    public void interfaceDishToRating(int restaurantId, int dishId, String dishName) {
        Intent i = new Intent(DishesActivity.this, RateRecipeActivity.class);
        i.putExtra("restaurantId", String.valueOf(restaurantId));
        i.putExtra("dishId", String.valueOf(dishId));
        i.putExtra("dishName", dishName);
        startActivity(i);
    }

    //on backpressed,this will update cart
  //  public void onBackPressed() {
   //     if (this.getClass().getSimpleName() == "DishesActivity") {

   //         Intent i = new Intent(this, RestaurantActivity.class);
     //       startActivity(i);
      //  } else {
     //       super.onBackPressed();
     //   }
  //  }
}


