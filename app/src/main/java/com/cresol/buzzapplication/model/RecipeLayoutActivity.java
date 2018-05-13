package com.cresol.buzzapplication.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cresol.buzzapplication.R;

/**
 * Created by Saurabh on 8/2/2016.
 */
public class RecipeLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_fragment);
    }
}