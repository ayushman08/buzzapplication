package com.cresol.buzzapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.adapter.RecipeAdapter;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.model.AllRecipeModel;
import com.cresol.buzzapplication.util.DataResource;
import com.cresol.buzzapplication.util.GlobalMethods;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saurabh on 7/28/2016.
 */

//Extending by drawerActivity  to add all the drawer property to this activity
public class RecipeActivity extends DrawerActivity {

    api BuzzdApi;
    RecipeAdapter adapter;
    List<AllRecipeModel> recipesData;
    TextView recipe_button;
    ImageView  recipe_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate the activity for getting view of  activity

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate method of LAYOUT_INFLATER_SERVICE will
        View contentView = inflater.inflate(R.layout.activity_recipe, null, false);
        //addView method of drawer,we add activity view to drawer
        drawer.addView(contentView, 0);

        GlobalMethods.overrideFonts(this, contentView);

        showCartCountOnCart();

        //Initialize all UI
        initilizeUI();


         fade();

        //api call by retrofit
         BuzzdApi = GlobalMethods.GetBuzzdAPI(RecipeActivity.this);

        //RetroCall or static data that we save in class DataResource
         if (GlobalMethods.isNetworkAvailable(RecipeActivity.this)) {
            if (DataResource.cacheAllRecipeData != null && !DataResource.cacheAllRecipeData.isEmpty()) {
                //bind data to the list,we save data statically during splash screen call,else we call by retrofit
                bindRetroData(DataResource.cacheAllRecipeData);
            } else {
                RetroCallForRecipes();
            }
        }

    }

    //show cart count to the toolbar
    public void showCartCountOnCart() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_drawer_id);
        setSupportActionBar(toolbar);
        TextView toolbarCartTxt = (TextView) findViewById(R.id.toolbar_cart_text);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.toolbar_image_layout);
        //getCount method in Global Class in which we are saving count to the sqlite database
        int count = GlobalMethods.GetCount(this);
        toolbarCartTxt.setText(String.valueOf(count));

        //Remove apptitle from Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Toggle button to actionbar,if toolbar is not present than add toolbar null and add to the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //add listener to drawer
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //listener to cart layout in Toolbar
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

    // onClick method for Create own Recipe textView
     public void recipeButton(View v) {
        Intent i = new Intent(this, CreateRecipeActivity.class);
        startActivity(i);
    }

    //retrocall for getting all recipes by retrofit from the server

    public void RetroCallForRecipes() {
        //Start progressbar
        GlobalMethods.ShowProgressBar(RecipeActivity.this);

        //as recipe have diffent type of data,so we make a class
        //and data we take from server

        Call<List<AllRecipeModel>> recipeapi = BuzzdApi.GetAllRecipes();
        recipeapi.enqueue(new Callback<List<AllRecipeModel>>() {
            @Override
            public void onResponse(Call<List<AllRecipeModel>> call, Response<List<AllRecipeModel>> response) {
                 //Validate if response is not null and successful
                if (response.isSuccessful() && response.body() != null) {
                    //we make a class in which we save the data coming from server at the time of splash screen
                    DataResource.cacheAllRecipeData = response.body();
                    // saving data in the list
                    recipesData = response.body();
                    //method calling in this we get the all recipe details in response.body like recipe id,recipe recipeImage etc
                    bindRetroData(response.body());

                }
                //hiding progress bar after call complete
                GlobalMethods.HideProgressBar();
            }

            @Override
            public void onFailure(Call<List<AllRecipeModel>> call, Throwable t) {
                GlobalMethods.HideProgressBar();
            }
        });
    }
       //initialising the textViews
    public void initilizeUI() {
        recipe_button = (TextView) findViewById(R.id.recipe_button);
        recipe_image = (ImageView) findViewById(R.id.recipe_img_id);
    }



    //bind data-> response(have array of AllRecipeModel ) come from the server,binding to the recipechildlist  layout
     public void bindRetroData(final List<AllRecipeModel> recipesData) {
       //taking data from retro call response,we add to the adapter
        adapter = new RecipeAdapter(RecipeActivity.this, R.layout.recipe_child_list, recipesData);

         //main list in which we are binding Data
        ListView list = (ListView) findViewById(R.id.recipe_list);
        list.setAdapter(adapter);

        //listener to the list
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         //here we get the position of recipe from the saving list

                //List recipeData have all the data of recipes
                //in data we get the position wise data of list
                AllRecipeModel data = recipesData.get(position);
                //on clicking list,come to the matched recipe according to id
                Intent i = new Intent(getApplicationContext(), RecipeMatchedActivity.class);
                //we get the recipeId by which uniquely identify,data have positionwise recipedetails from that we took recipeId
                 i.putExtra("RecipeId", String.valueOf(data.RecipieID));
                 i.putExtra("RecipeImage", data.RecipieImage);
                startActivity(i);
            }
        });
    }

    //animation for fade
    public void fade() {
        //created animation in anim
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        recipe_button.startAnimation(animation1);
    }
}
