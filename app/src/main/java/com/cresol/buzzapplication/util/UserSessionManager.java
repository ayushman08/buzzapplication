package com.cresol.buzzapplication.util;

/**
 * Created by Saurabh on 9/9/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.cresol.buzzapplication.activity.MyProfileActivity;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.model.apimodel.UserRegistrationModel.Register_Response;
import com.cresol.buzzapplication.model.apimodel.UserRegistrationModel.UserInformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public  class UserSessionManager
{
    api buzzdApi;
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
     int PRIVATE_MODE = 0;


    // Sharedpref file name
    private static final String PREFER_NAME = "in.cresol.buzzapplication.UserSession";
    public static final String Email = "email";
    public static final String Name = "name";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    private static final String Login_By = "loginby";
    private static final String UserId = "userid";
    private static final String HeaderToken = "headertoken";

     // Constructor
    public UserSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }
     //make method to get the data drom the Google fragment ,name of profile,email,loginby--bcz differentiate between google and facebook
    public void AddData(final String name,String emailID,String loginby,Context context)
     {
        final String tempname=name;
        final String tempemailID=emailID;
        final String temploginby=loginby;


        UserInformation obj=new UserInformation();
        obj.setCity("");
        obj.setEmail(emailID);
        obj.setFirstName(name);
        obj.setLastName("");
        obj.setMobile("");

        buzzdApi=GlobalMethods.GetBuzzdAPI(context);

          //register the user information
         Call<Register_Response> register_responseCall= buzzdApi.Register(obj);

        GlobalMethods.ShowProgressBar(_context);
        buzzdApi = GlobalMethods.GetBuzzdAPI(_context);
        register_responseCall.enqueue(new Callback<Register_Response>() {
            @Override
            public void onResponse(Call<Register_Response> call, Response<Register_Response> response) {
                if (response.isSuccessful()) {
                    editor.putBoolean(IS_USER_LOGIN, true);
                    editor.putString(Name, tempname);
                    editor.putString(Email, tempemailID);
                    editor.putString(Login_By,temploginby);

                    if(response.body()!=null&&response.body().getUserId()!=null) {
                        editor.putString(UserId, response.body().getUserId());
                    }
                    if(response.body()!=null&&response.body().getToken()!=null) {
                        editor.putString(HeaderToken, response.body().getToken());
                    }
                    editor.commit();
                    //take
                    Intent i = new Intent(_context.getApplicationContext(), MyProfileActivity.class);

                    _context.startActivity(i);
                }
                GlobalMethods.HideProgressBar();
            }

            @Override
            public void onFailure(Call<Register_Response> call, Throwable t) {
                GlobalMethods.HideProgressBar();
                Toast.makeText(_context,t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });





    }


    public String GetName() {

        String name = pref.getString(Name, null);


        return name;

    }
    //method for checking by which button,you are logged in
    public String GetLoginby() {

        String token = pref.getString(Login_By, null);


        return token;

    }

    public String GetEmail() {

        String token = pref.getString(Email, null);


        return token;

    }



    public Integer GetUserId() {

        String a=pref.getString(UserId, null);
        if(a!=null) {
            Integer userId = Integer.valueOf(a);
            return userId;
        }
        else {
            return null;
        }




    }



    public String GetHeaderToken() {

        String headerToken = pref.getString(HeaderToken, null);


        return headerToken;

    }

    // Check for login
    public boolean isUserLoggedIn() {
        // Boolean data= pref.getBoolean(IS_USER_LOGIN, false);
        //here IS_USER_LOGIN is the key,as we know in shared preference data saved in key and value
        //false is default value
        //getBoolean is method of shared preference class
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public void logoutUser() {

        editor.clear();
        editor.commit();
        GlobalMethods.ClearCacheData();
    }
}
