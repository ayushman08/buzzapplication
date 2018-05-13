package com.cresol.buzzapplication.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.activity.SignupActivity;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.model.CartOrderModel;
import com.cresol.buzzapplication.sqlite.UserContract;
import com.cresol.buzzapplication.sqlite.UserHelper;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nitesh on 9/10/2016.
 */
public class GlobalMethods
{
   public static UserHelper userHelper;

   public static SQLiteDatabase sqLiteDatabase;

    public static void InitilizeSqlite(Context c){
        userHelper=new UserHelper(c);
        //getWritableDatabase() allow us to write data in database
        sqLiteDatabase=  userHelper.getWritableDatabase();
    }
// saving selected dish items according to thr restaurant
    public static void addCartData(String dishId,String dishName,String dishImage,String dishPrice,String dishQuantity,String restaurantId,Context c){

        InitilizeSqlite(c);
        ShowProgressBar(c);
     // we put the value in database
        ContentValues contentValues=new ContentValues();
        contentValues.put(UserContract.CartItems.Dish_Id,dishId);
        contentValues.put(UserContract.CartItems.Dish_Image,dishImage);
        contentValues.put(UserContract.CartItems.Dish_Name,dishName);
        contentValues.put(UserContract.CartItems.Dish_Price,dishPrice);
        contentValues.put(UserContract.CartItems.Dish_Quantity,dishQuantity);
        contentValues.put(UserContract.CartItems.Restaurant_Id,restaurantId);
       //one row inserted
        sqLiteDatabase.insert(UserContract.CartItems.Table_Name, null, contentValues);
        Log.e("Insert operation Status", "one row inserted");

        userHelper.close();
        sqLiteDatabase.close();

        HideProgressBar();

    }
      //When you click on cart icon
     public static List<CartOrderModel> GetInformation(Context c){

        List<CartOrderModel> cartOrderModels = new ArrayList<>();
        userHelper=new UserHelper(c);
        sqLiteDatabase=  userHelper.getReadableDatabase();

        Cursor cursor;

        String[] projections={UserContract.CartItems.Dish_Id,UserContract.CartItems.Dish_Name,UserContract.CartItems.Dish_Image,UserContract.CartItems.Dish_Quantity,
                UserContract.CartItems.Dish_Price,UserContract.CartItems.Restaurant_Id};

       cursor= sqLiteDatabase.query(UserContract.CartItems.Table_Name,projections,null,null,null,null,null);



        if(cursor.moveToNext()){
            do{
                //here getting the values from the database
                CartOrderModel obj=new CartOrderModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));

              //add data tot the list onj of  CartOrderModel type
                cartOrderModels.add(obj);

            }while (cursor.moveToNext());
        }

        userHelper.close();
        sqLiteDatabase.close();
        Log.e("Data Retrieved", "Done");
         //it will return all the values
        return  cartOrderModels;

    }
    //method for getting count of the toolbar carttext,which we saved  in  the sqlite database
        public static int GetCount(Context c){
        int count=0;
       //to manage the operation in the database
        userHelper=new UserHelper(c);
        sqLiteDatabase=  userHelper.getReadableDatabase();

        Cursor cursor;

        String[] projections={UserContract.CartItems.Dish_Name};

        cursor= sqLiteDatabase.query(UserContract.CartItems.Table_Name,projections,null,null,null,null,null);

        count= cursor.getCount();
        userHelper.close();
        sqLiteDatabase.close();
        Log.e("Data Count", "Done");
        return  count;
    }

 //here the checked value of dish activity is saved to the database
    public static Boolean CheckValueAvailable(Context c,String dishId,String restaurantId){
        Boolean result=false;

    userHelper=new UserHelper(c);
    sqLiteDatabase=  userHelper.getReadableDatabase();

    Cursor cursor;

  //saving the checked values in the database
    String[] selectionArgument={restaurantId,dishId};

    cursor=   sqLiteDatabase.rawQuery("SELECT * FROM " + UserContract.CartItems.Table_Name + " WHERE " + UserContract.CartItems.Restaurant_Id + " = ? AND " + UserContract.CartItems.Dish_Id + " = ?", selectionArgument);

    //cursor= sqLiteDatabase.query(UserContract.CartItems.Table_Name,projections,selection,selectionArgument,null,null,null);

    if(cursor.moveToNext()){
    do{
        result=true;
    }while (cursor.moveToNext());
}
    userHelper.close();
    sqLiteDatabase.close();
    Log.e("Check Value ", "Done");
    return result;
}

    public static Integer GetQuantity(Context c,String dishId,String restaurantId){
        Integer result=0;

        userHelper=new UserHelper(c);
        sqLiteDatabase=  userHelper.getReadableDatabase();

        Cursor cursor;

        String[] projections={UserContract.CartItems.Dish_Name};
        String selection=UserContract.CartItems.Dish_Id+"=?";
        String[] selectionArgument={restaurantId,dishId};

        cursor=   sqLiteDatabase.rawQuery("SELECT dish_quantity FROM "+UserContract.CartItems.Table_Name+" WHERE "+UserContract.CartItems.Restaurant_Id+" = ? AND "+UserContract.CartItems.Dish_Id+" = ?",selectionArgument);

        //cursor= sqLiteDatabase.query(UserContract.CartItems.Table_Name,projections,selection,selectionArgument,null,null,null);

        if(cursor.moveToNext()){
            do{
                result= Integer.valueOf(cursor.getString(0));

            }while (cursor.moveToNext());
        }
        userHelper.close();
        sqLiteDatabase.close();
        Log.e("Check Value ", "Done");
        return result;
    }

      //delete dish data
    public static void DeleteDishData(Context c,String dishId,String restaurantId){



        userHelper=new UserHelper(c);
        sqLiteDatabase=  userHelper.getReadableDatabase();


        String selection=UserContract.CartItems.Dish_Id+"=?";
        String[] selectionArgument={restaurantId,dishId};

        sqLiteDatabase.execSQL("Delete  FROM " + UserContract.CartItems.Table_Name + " WHERE " + UserContract.CartItems.Restaurant_Id + " = ? AND " + UserContract.CartItems.Dish_Id + " = ?", selectionArgument);


        //sqLiteDatabase.delete(UserContract.CartItems.Table_Name,selection,selectionArgument);


        userHelper.close();
        sqLiteDatabase.close();
        Log.e("Delete Status ", "Done");

    }





    //we make this method as Global,because for getting data from the server by Retrofit library we use it again and agai
    public static UserSessionManager sessionManager;
    //api calls  by Retrofit library
     public static api GetBuzzdAPI(Context context) {
        sessionManager=new UserSessionManager(context);
        //common path url
         String uriAddress = context.getResources().getString(R.string.ApiAddress);
        //Gson is a Java library that can be used to convert Java Objects into their JSON representation.
        // It can also be used to convert a JSON string to an equivalent Java object

           Gson gson = new GsonBuilder().create();


        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().readTimeout(20, TimeUnit.SECONDS).connectTimeout(20, TimeUnit.SECONDS).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder ongoing = chain.request().newBuilder();
                //ongoing.addHeader("Accept", "application/json;versions=1");
                  //for the authorization
                if (sessionManager.GetHeaderToken()!=null) {
                    ongoing.addHeader("Authorization", sessionManager.GetHeaderToken());

                }
                else {
                    ongoing.addHeader("Authorization", "Cresol123!@#");
                }
                // else if (!sessionManager.GetHeaderToken().isEmpty()) {
                //  ongoing.addHeader("Authorization", sessionManager.GetHeaderToken());
                //}

                return chain.proceed(ongoing.build());
            }
        }).build();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(uriAddress).addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();

        return retrofit.create(api.class);


    }


    //progress bar Handler
    static ProgressBarHandler mProgressBarHandler;

    public static   void ShowProgressBar(Context context){
        mProgressBarHandler = new ProgressBarHandler(context); // In onCreate
        mProgressBarHandler.show();
    }

    public static void HideProgressBar(){
        mProgressBarHandler.hide();
    }


     //for checking the Network connection in android

       public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {

            return true;
        } else {

            Toast.makeText(context, "Network is not Available", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    //to Change the font Style
    public static void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf"));
            }
        } catch (Exception e) {
        }
    }

    public static int GetDeviceHeight(Activity a){
        //setting dynamic height and width by taking width and height of screen
        Display display = a.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int screen_height = outMetrics.heightPixels;
        return screen_height;

    }
    public static int GetDeviceWidth(Activity a){
        //setting dynamic height and width by taking width and height of screen
        Display display = a.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int screen_width = outMetrics.widthPixels;
        return screen_width;

    }

   //method to catch the Exception
    public static void ApplicationError(Context c, Exception error){
        //We will write code for calling error api by reotro fit

    }


    public static void ShowLoginMessage(Context c){
        Toast.makeText(c,"Please login first",Toast.LENGTH_LONG).show();
        Intent i=new Intent(c,SignupActivity.class);
        c.startActivity(i);
    }

    public static void ClearCacheData(){
        DataResource.cacheAllRecipeData.clear();
        DataResource.cacheAllRestaurantData.clear();
        DataResource.cacheProfileInfo = null;
        DataResource.cacheLikeListObj.clear();
        DataResource.cacheDisLikedListObj.clear();
    }
//........................................................



}

