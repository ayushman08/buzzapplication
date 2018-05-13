package com.cresol.buzzapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.adapter.SplitBillAdapter;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.model.MyClassSplitBill;
import com.cresol.buzzapplication.model.SplitBillCheckBoxSelectorInterface;
import com.cresol.buzzapplication.util.GlobalMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saurabh on 8/1/2016.
 */
public class SplitBillActivity extends DrawerActivity implements SplitBillCheckBoxSelectorInterface {
    //Created a static list by which access directly without creating the obj,for saving the checked checkbox
    public static List<MyClassSplitBill> saveSplitBillData = new ArrayList<MyClassSplitBill>();

    List<MyClassSplitBill> obj = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_split_bill, null, false);
        drawer.addView(contentView, 0);

        GlobalMethods.overrideFonts(this, contentView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_drawer_id);
        setSupportActionBar(toolbar);

        //Remove apptitle from Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        MyClassSplitBill data = new MyClassSplitBill("Rohan Sharma");
        MyClassSplitBill data1 = new MyClassSplitBill("Prateek");
        MyClassSplitBill data2 = new MyClassSplitBill("Rahul");
        MyClassSplitBill data3 = new MyClassSplitBill("Pramod");
        MyClassSplitBill data4 = new MyClassSplitBill("Rohan Sharma");
        MyClassSplitBill data5 = new MyClassSplitBill("Neha Sharma");
        MyClassSplitBill data6 = new MyClassSplitBill("Arpit Jha");

        obj.add(data);
        obj.add(data1);
        obj.add(data2);
        obj.add(data3);
        obj.add(data4);
        obj.add(data5);
        obj.add(data6);

        //
        saveSplitBillData.clear();
        SplitBillAdapter adapter = new SplitBillAdapter(this, R.layout.split_bill_child_list, obj);

        ListView listView = (ListView) findViewById(R.id.list_split_bill_id);
        listView.setAdapter(adapter);

    }

    public void continueSplitButton(View v) {
        Intent i = new Intent(this, RateRestaurantActivity.class);
        startActivity(i);

    }

    @Override

    public void CheckBoxSelectorMediator(int position, boolean checkStatus) {
        if (checkStatus) {
            //add data
            MyClassSplitBill data = obj.get(position);
            saveSplitBillData.add(data);
        } else {
            //delete data
            //MyClassSplitBill data=obj.get(position);
            saveSplitBillData.remove(position);
        }
    }
}