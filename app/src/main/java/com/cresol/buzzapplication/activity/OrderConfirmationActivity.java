package com.cresol.buzzapplication.activity;

import android.os.Bundle;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.drawer.DrawerActivity;

/**
 * Created by Saurabh on 10/24/2016.
 */
public class OrderConfirmationActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

    }
}