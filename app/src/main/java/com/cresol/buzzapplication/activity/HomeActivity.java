package com.cresol.buzzapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.google.firebase.iid.FirebaseInstanceId;


/**
 * Created by Saurabh on 7/29/2016.
 */
public class HomeActivity extends DrawerActivity {
    TextView recipetxt;
    TextView findtxt;

    Button  showToken;

    private static final String TAG ="MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inflater provide the view of activity
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        // Taking drawer reference,we are adding drawer to activity view by addView method in DrawerActivity class
        drawer.addView(contentView, 0);
        //get token for the firebase push notification
        showToken = (Button) findViewById(R.id.button);
        showToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG,"Token"+token);
                Toast.makeText(HomeActivity.this,token,Toast.LENGTH_LONG).show();
            }
        });
           //to change the font
        GlobalMethods.overrideFonts(this, contentView);
        //set drawable left of TextView

        //initialise UI

        recipetxt = (TextView) findViewById(R.id.see_recipe_txt_id);
        findtxt = (TextView) findViewById(R.id.find_recipe_txt_id);

        setIcon();

        //animation for fading the button
        fade();
        //listeners on TextView
        listenersTextView();


    }

    public void setIcon() {
        Drawable recipeimgref = getResources().getDrawable(R.drawable.recipes_img);
        // change width of img
        recipeimgref.setBounds(0, 0, (int) (recipeimgref.getIntrinsicWidth() * 0.5), (int) (recipeimgref.getIntrinsicWidth() * 0.5));

        ScaleDrawable sD1 = new ScaleDrawable(recipeimgref, 0, 10, 10);
        recipetxt.setCompoundDrawables(sD1.getDrawable(), null, null, null);

        //set drawable left
        //set recipeImage at drawable left in text view
        Drawable findrecipeimgref = getResources().getDrawable(R.drawable.resto_img);
        findrecipeimgref.setBounds(0, 0, (int) (findrecipeimgref.getIntrinsicWidth() * 0.5), (int) (findrecipeimgref.getIntrinsicWidth() * 0.5));
        //scaling of the recipeImage
        ScaleDrawable sD2 = new ScaleDrawable(findrecipeimgref, 0, 10, 10);

        //set at left position,we can set at,top,right,bottom by setCompoundDrawables method
        findtxt.setCompoundDrawables(sD2.getDrawable(), null, null, null);
    }

    //method for fading
    public void fade() {
           //created animation in anim
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        recipetxt.startAnimation(animation1);
        findtxt.startAnimation(animation1);
    }

    //add listeners on TextView
    public void listenersTextView() {
        findtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(HomeActivity.this,"Page under construction,Please swipe Left to Drawer",Toast.LENGTH_LONG).show();
                Intent i = new Intent(HomeActivity.this, RestaurantActivity.class);
                startActivity(i);
            }
        });
        recipetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this, RecipeActivity.class);
                startActivity(i);
            }
        });

    }


}