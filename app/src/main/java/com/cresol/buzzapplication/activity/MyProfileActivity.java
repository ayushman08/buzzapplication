package com.cresol.buzzapplication.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.fragment.ShowProfileFragment;
import com.cresol.buzzapplication.fragment.UpdateProfileFragment;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;


/**
 * Created by Saurabh on 7/29/2016.
 */
public class MyProfileActivity extends DrawerActivity {

    TextView edit;

    TextView save;
    UserSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set drawer by addview method of drawer in activity view,getting view by inflator
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_myprofile, null, false);

        drawer.addView(contentView, 0);

        GlobalMethods.overrideFonts(this, contentView);

        showCartCountOnCart();
        InitilizeUI();
        bindUI();

        if(sessionManager.isUserLoggedIn())
        {
            AddShowProfileFragment();
        }
        else{
            GlobalMethods.ShowLoginMessage(this);
        }




    }

    public void AddShowProfileFragment(){


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        ShowProfileFragment myprofilefrag = new ShowProfileFragment();
        //Saving data to server
        fragmentTransaction.replace(R.id.profile_container, myprofilefrag);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


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

    public void Edit(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        UpdateProfileFragment profilefrag = new UpdateProfileFragment();
        //Saving data to server
        fragmentTransaction.replace(R.id.profile_container, profilefrag);
        fragmentTransaction.commit();
    }

    public void bindUI(){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ShowProfileFragment myprofilefrag = new ShowProfileFragment();
                //Saving data to server
                fragmentTransaction.replace(R.id.profile_container, myprofilefrag);
                fragmentTransaction.commit();
            }
        });

    }

    public void InitilizeUI(){

        edit = (TextView) findViewById(R.id.edit);



        save = (TextView) findViewById(R.id.save);


        sessionManager=new UserSessionManager(this);
    }
}