package com.cresol.buzzapplication.activity;

/**
 * Created by Saurabh on 7/28/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.fragment.FacebookFragment;
import com.cresol.buzzapplication.fragment.GoogleFragment;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;


public class SignupActivity extends FragmentActivity {


    LinearLayout facebookLayout;

    UserSessionManager userSessionManager;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Initialise facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_signup);


        //get view of layout for changing the font style

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        GlobalMethods.overrideFonts(this, viewGroup);


        //Adding fragment in this layout
        facebookLayout = (LinearLayout) findViewById(R.id.facebooklayout);

        //create a class for saving the data in phone memory
        userSessionManager = new UserSessionManager(this);

        //here it will check,if is user not login than add fragment to this activity
        if (!userSessionManager.isUserLoggedIn()) {
            //Adding Fragment
            FacebookFragment fbFragment = new FacebookFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.facebooklayout, fbFragment).commit();

            //Adding Fragment
            //Create Google Fragment class object and adding in the MAinActivity
            GoogleFragment googleFragment = new GoogleFragment();
            android.support.v4.app.FragmentTransaction googleFt = getSupportFragmentManager().beginTransaction();
            googleFt.add(R.id.googlelayout, googleFragment).commit();


        } else {   // if it already login than the DAta activity class will show
            Intent i = new Intent(SignupActivity.this, MyProfileActivity.class);
            startActivity(i);
        }
    }


//Skip text at the bottom ,on click method to enter to HomeActivity
    public void skipMethod(View v) {
        Intent i = new Intent(SignupActivity.this, HomeActivity.class);
        startActivity(i);
    }

//onBackpressed
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
