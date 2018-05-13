package com.cresol.buzzapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.util.GlobalMethods;

/**
 * Created by Saurabh on 8/1/2016.
 */
public class RateRecipeActivity extends AppCompatActivity {

    Integer restaurantId;
    Integer dishId;
    String dishName;
    TextView dishNameTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_recipe);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_rate_recipe, null, false);


        GlobalMethods.overrideFonts(this, contentView);

        String restaurantIdVar = getIntent().getExtras().getString("restaurantId");
        if(restaurantIdVar!=null) {
            restaurantId = Integer.valueOf(restaurantIdVar);
        }


        String dishIdVar = getIntent().getExtras().getString("dishId");
        if(dishIdVar!=null) {
            dishId = Integer.valueOf(dishIdVar);

            dishName = getIntent().getExtras().getString("dishName");

        }

        dishNameTextView = (TextView) findViewById(R.id.dishName);
        if(dishName!=null) {
            dishNameTextView.setText(dishName);
        }

    }


    //Add onclick method on ContinueText
    public void continueButton(View v) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }

}