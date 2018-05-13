package com.cresol.buzzapplication.drawer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.activity.HomeActivity;
import com.cresol.buzzapplication.activity.LikeActivity;
import com.cresol.buzzapplication.activity.MyProfileActivity;
import com.cresol.buzzapplication.activity.RecipeActivity;
import com.cresol.buzzapplication.activity.SignupActivity;
import com.cresol.buzzapplication.activity.YourOrderActivity;
import com.cresol.buzzapplication.util.CustomTypefaceSpan;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Saurabh on 7/29/2016.
 */
public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawer;

    public UserSessionManager sessionManager;

    private static GoogleSignInOptions gso;

    //google api client
    private static GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        FacebookSdk.sdkInitialize(getApplicationContext());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_id);

        // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //  this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        // toggle.syncState();
        //      Toolbar toolbar =(Toolbar)findViewById(R.id.my_awesome_toolbar);
            //        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_id);
        navigationView.setNavigationItemSelectedListener(this);


       //Checked according to the activity

        if (this.getClass().getSimpleName().equals("HomeActivity")) {
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if (this.getClass().getSimpleName().equals("MyProfileActivity")) {
            navigationView.getMenu().getItem(1).setChecked(true);
        } else if (this.getClass().getSimpleName().equals("RecipeActivity")) {
            navigationView.getMenu().getItem(2).setChecked(true);
        } else if (this.getClass().getSimpleName().equals("LikeActivity")) {
            navigationView.getMenu().getItem(3).setChecked(true);
        } else if (this.getClass().getSimpleName().equals("YourOrderActivity")) {
            navigationView.getMenu().getItem(4).setChecked(true);
        }


      //changing the typeface of navigation view
        Menu m = navigationView.getMenu();


        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

         //if already login than show only logout
        //check is user is already login than show logout

        sessionManager = new UserSessionManager(getApplicationContext());
        if (sessionManager.isUserLoggedIn()) {
            navigationView.getMenu().getItem(6).setVisible(false);
        } else {
            navigationView.getMenu().getItem(5).setVisible(false);
        }


    }


    //method to overrite the typeface for menu
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Raleway-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    //backpresed
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_id);
        //for opening and closing of drawer

        //if drawer is open already
        /**  for start Push object to x-axis position at the start of its container, not changing its size. */
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //selecting items of drawer navigation menu items
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //checking by id of menu item
         int id = item.getItemId();


        //  else { Toast.makeText(this,"Please login first",Toast.LENGTH_LONG).show();}



        if (id == R.id.nav_my_profile_id) {
              if( sessionManager.isUserLoggedIn()){
                 Intent i = new Intent(this, MyProfileActivity.class);
                  startActivity(i);
               }
              else {
                  GlobalMethods.ShowLoginMessage(this);
              }
        }

        else if (id == R.id.nav_home_id) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_recipe_id) {

            Intent i = new Intent(this, RecipeActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_Like_id) {
             if( sessionManager.isUserLoggedIn()){
            Intent i = new Intent(this, LikeActivity.class);
            startActivity(i);
             }
              else { Toast.makeText(this,"Please login first",Toast.LENGTH_LONG).show();}
        } else if (id == R.id.cart) {
           //first check is login,by calling the  sessionManager.isUserLoggedIn() method
            if (sessionManager.isUserLoggedIn()) {

                //  check cart is either empty or not by GetCount method in GlobalMethods class as static,this is context means the current activity
                if (GlobalMethods.GetCount(this) != 0) {
                    Intent i = new Intent(this, YourOrderActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Cart is Empty.Please add product.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Please login first", Toast.LENGTH_LONG).show();
            }
        }


//logout menu item,call logout method
        else if (id == R.id.nav_log_out_id) {
            Logout();


        } else if (id == R.id.nav_log_in_id) {

            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
        }
   /*     else if(id == R.id.nav_create_recipe_id){

            Intent i = new Intent(getApplicationContext(), CreateRecipeActivity.class);
            startActivity(i);
        }*/
            //this will close the drawer in drawer is open
           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_id);
           drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // method for signout by Google by checking validation
    public void signoutgoogle() {
        GlobalMethods.ShowProgressBar(DrawerActivity.this);
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                sessionManager.logoutUser();
                GlobalMethods.HideProgressBar();
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);


            }
        });


    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }


    public void disconnectFromFacebook() {
//progress bar
      //  GlobalMethods.ShowProgressBar(getApplicationContext());
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
                //after logout from facebook
                sessionManager.logoutUser();
                //hide progress bar
              //  GlobalMethods.HideProgressBar();
                //transfer to dignupp activity
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);

            }
        }).executeAsync();
        //When you execute something synchronously, you wait for it to finish before moving on to another task.
        // When you execute something asynchronously, you can move on to another task before it finishes.


    }

    //This section for logout
    public void Logout() {
      //we make method to check by which button we are going to logout
        if (sessionManager.GetLoginby().equals("fb")) {
            disconnectFromFacebook();
        } else {
            signoutgoogle();
        }

    }

}
