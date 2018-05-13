package com.cresol.buzzapplication.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.fragment.RecipeFragment;
import com.cresol.buzzapplication.model.RecipeInfoModel;
import com.cresol.buzzapplication.model.RecipeNutritionModel;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;


/**
 * Created by Saurabh on 8/1/2016.
 */
public class RecipeMatchedActivity extends DrawerActivity {


    public TextView recipeName;
    public TextView recipePrice;
    public RelativeLayout relativeLayoutImage;
    public int recipeId = 0;

    public ImageView recipeImageView;
    public String recipeImage;
    public TextView cookingTimeTextView;
    public static List<RecipeNutritionModel> nutritiondata = new ArrayList<RecipeNutritionModel>();
    public static List<String> ingredientData = new ArrayList<String>();
    public static List<String> howToMakeData = new ArrayList<String>();
    public static int userRating;
    api buzzdapi;
    DisplayMetrics displaymetrics;
    RatingBar ratingBar;
    TextView ingredientHeader;
    TextView howToMakeHeader;
    TextView nutritionHeader;
    TextView ingredientCountTextView;
    TextView caloryCountTextView;
    LinearLayout linearlayout;
    Toolbar toolbar;
    TextView   minutesText;
    TextView  caloriesText;
    TextView ingredientText;
     UserSessionManager userSessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add drawer view in avtivity view
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_matched_recipe, null, false);
        drawer.addView(contentView, 0);

        GlobalMethods.overrideFonts(this, contentView);


        InitilizeUI();
        BindUI();
        fade();
        //changing the font
        Typeface customfont = Typeface.createFromAsset(this.getAssets(), "Raleway-SemiBoldItalic.ttf");
        minutesText.setTypeface(customfont);
        ingredientText.setTypeface(customfont);
        caloriesText.setTypeface(customfont);
        //dynamically set the height of toolbar for collapsing in middle
         //setting height of toolbar to the 35 percent of the device height
         toolbar.getLayoutParams().height = (int) (GlobalMethods.GetDeviceHeight(this) * 0.35);



        // we get the recipeId and recipeimage here in recipeIdVar and recipeImage from recipe activity
         String recipeIdVar = getIntent().getExtras().getString("RecipeId");
         recipeImage = getIntent().getExtras().getString("RecipeImage");


        if (!recipeIdVar.isEmpty()) {
            recipeId = Integer.valueOf(recipeIdVar);
        }
        //calling api
        buzzdapi = GlobalMethods.GetBuzzdAPI(RecipeMatchedActivity.this);
        //check for the network
        if (GlobalMethods.isNetworkAvailable(RecipeMatchedActivity.this)) {
            //calling methods for getting data of recipe information
            RetroCallForRecipeInfo();
            //calling methods for getting data of nutrition information
            RetroCallForNutrition();

        }

    }


      //onClick method on recipeImage
     //  public void recipeRateUs(View v) {
    //    Intent i = new Intent(this, RateRecipeActivity.class);
      //    startActivity(i);

  //  }

    //add onclick method on  recipe fragment, please rate us text.

   // if (!userSessionManager.isUserLoggedIn()){
    public void rateRecipe(View v) {
        Intent i = new Intent(this, RateRecipeActivity.class);
        startActivity(i);

    }
   // }

    public void InitilizeUI() {
        recipeName = (TextView) findViewById(R.id.recipe_name_id);
        recipePrice = (TextView) findViewById(R.id.price_id);
        recipeImageView = (ImageView) findViewById(R.id.match_recipe_img_id);
        relativeLayoutImage = (RelativeLayout) findViewById(R.id.relativeLayoutImage);
        linearlayout = (LinearLayout) findViewById(R.id.rating_linearlayout);
        ingredientText = (TextView) findViewById(R.id.ingredient);
        caloriesText = (TextView) findViewById(R.id.calories);
        minutesText = (TextView) findViewById(R.id.minutes);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        cookingTimeTextView = (TextView) findViewById(R.id.cookingTime);
        ingredientCountTextView = (TextView) findViewById(R.id.ingredientCount);
        caloryCountTextView = (TextView) findViewById(R.id.caloryCount);

    }

    //Get call for calling recipe information from the server by recipeId
    public void RetroCallForRecipeInfo() {

        GlobalMethods.ShowProgressBar(this);

        //getting recipe information like ingredients,calories etc by recipe id i.e, unique
        Call<RecipeInfoModel> recipeInfoModelCall = buzzdapi.GetRecipeInfo(recipeId);
        recipeInfoModelCall.enqueue(new Callback<RecipeInfoModel>() {
            @Override
            public void onResponse(Call<RecipeInfoModel> call, Response<RecipeInfoModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //calling method
                    BindRetroData(response.body());

                }
                GlobalMethods.HideProgressBar();
            }

            @Override
            public void onFailure(Call<RecipeInfoModel> call, Throwable t) {
                GlobalMethods.HideProgressBar();
            }
        });
    }

    //here setting the values get from the server to the widgets
    //created method for getting data from the server of which type RecipeInfoModel  in data
    //RecipeInfoModel is a class for different type of data types
     public void BindRetroData(RecipeInfoModel data) {
        float ratingValue = 1.5f;
        ratingBar.setRating(ratingValue); // to set rating value
        ratingBar.setStepSize(ratingValue);// to show to stars
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        //check id data is not empty then set the data to the different text views
         if (data.getRecipeHowToMake() != null) {
            cookingTimeTextView.setText(data.getRecipeCokingTime());

            howToMakeData = data.getRecipeHowToMake();
            ingredientData = data.getIngredientList();
             userRating=Math.round(data.getRatingUserCount());
            //   nutritiondata=data.getNutritionList();

            //casting  the data.getIngredientCount() to string i.e, from RecipeInfoModel data we get the data.getIngredientCount() in Integer
             //cast it to String
             ingredientCountTextView.setText(String.valueOf(data.getIngredientCount()));
            caloryCountTextView.setText(String.valueOf(data.getCaloriesCount()));



             //adding fragment at oncreate method
             //to add fragment access fragmentmanger obj,
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

             final RecipeFragment recipefrag = new RecipeFragment();
            fragmentTransaction.add(R.id.fragment_container, recipefrag);
            fragmentTransaction.commit();

        }

        if (data.getRecipeName() != null) {
            recipeName.setText(data.getRecipeName());
        }
        if (data.getMaxPrice() != null) {
            recipePrice.setText(data.getMaxPrice().toString());
        }
        // Glide library used to get the recipeImage
        if (data.getRecipeImage() != null) {
            //this is the context
            Glide.get(this).clearMemory();

            //this is the FragmentActivity
            //setting image by using glide library to the recipeImageView
            Glide.with(this)
                    .load(data.getRecipeImage())
                    .centerCrop()
                    .crossFade()
                    .into(recipeImageView);
        }
    }


    public void BindUI() {

       ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        Glide.with(this)
                .load(recipeImage)
                // .placeholder(R.drawable.buzz_watermark)
                .centerCrop()
                .crossFade()
                .into(recipeImageView);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Disable toolbar title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

       //get nutrition details
      public void RetroCallForNutrition() {
        //   GlobalMethods.ShowProgressBar(this);

        //get list of data of nutrition like nutrition name and quantity from by recipeId
        Call<List<RecipeNutritionModel>> recipeNutritionInfo = buzzdapi.GetRecipeNutritionInfo(recipeId);
        recipeNutritionInfo.enqueue(new Callback<List<RecipeNutritionModel>>() {
            @Override
            public void onResponse(Call<List<RecipeNutritionModel>> call, Response<List<RecipeNutritionModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    nutritiondata = response.body();
                }
                //     GlobalMethods.HideProgressBar();
            }

            @Override
            public void onFailure(Call<List<RecipeNutritionModel>> call, Throwable t) {
                //     GlobalMethods.HideProgressBar();
            }
        });
    }
    public void fade() {
        //created animation in anim
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        recipeImageView.startAnimation(animation1);
    }
}