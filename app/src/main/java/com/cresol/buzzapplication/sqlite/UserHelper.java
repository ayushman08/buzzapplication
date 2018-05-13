package com.cresol.buzzapplication.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nitesh on 9/25/2016.
 */// class to manage all the operations in the database
public class UserHelper extends SQLiteOpenHelper {

    public static final String Database_Name="BUZZD.DB";
    public static final int Database_Version=1;
    public static final String Create_Query="CREATE TABLE "+UserContract.CartItems.Table_Name+"("+UserContract.CartItems.Dish_Id+" Text,"+UserContract.CartItems.Dish_Name+
            " Text,"+UserContract.CartItems.Dish_Image+" Text,"+UserContract.CartItems.Dish_Price+" Text,"+UserContract.CartItems.Dish_Quantity+" Text,"+UserContract.CartItems.Restaurant_Id+" Text);";

    public UserHelper(Context c){

        super(c,Database_Name,null,Database_Version);
        Log.e("Database status","Database Created/Opened");
    }
    @Override
    //here database will create
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Query);
        Log.e("Table status", "Table Created");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
