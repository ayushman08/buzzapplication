package com.cresol.buzzapplication.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.adapter.EditIngredientsListOneAdapter;
import com.cresol.buzzapplication.adapter.EditIngredientsListTwoAdapter;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.fragment.Dialog_Fragment_Enter_Age;
import com.cresol.buzzapplication.fragment.Dialog_Fragment_For_AddIngredients;
import com.cresol.buzzapplication.model.Ingredient_InterfaceDeleteData;
import com.cresol.buzzapplication.model.MyClassEditIngredients;
import com.cresol.buzzapplication.model.TransferDataDialogActivity;
import com.cresol.buzzapplication.util.GlobalMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saurabh on 8/1/2016.
 */
public class EditIngredientsActivity extends DrawerActivity implements Ingredient_InterfaceDeleteData, TransferDataDialogActivity {
    List<MyClassEditIngredients> listOneObj;
    List<MyClassEditIngredients> listTwoObj;
    EditIngredientsListTwoAdapter adapter2;

    EditIngredientsListOneAdapter adapter;

    public static int okClickType;
    TextView ageText;
    LinearLayout add_ingredient_Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflator provide view  of activity


        //adding drawer in activity view by addView method in DrawerActivity Class
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_edit_ingredients, null, false);
        //adding activity view in drawer
        drawer.addView(contentView, 0);


        showCartCountOnCart();


        //Show Dialog fragment to  add age in TextView
        ImageView age_ref = (ImageView) findViewById(R.id.age_edit_img_id);
        age_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                Dialog_Fragment_Enter_Age ageDialogFragment = new Dialog_Fragment_Enter_Age();
                ageDialogFragment.show(fm, "Age Fragment");

            }
        });


        // on click add icon of first list, dialog popup will open to enter data in edittxt
        ImageView add_img_ref = (ImageView) findViewById(R.id.add_img_id);
        add_img_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // we have same dialog frag to Take data,we took a static var by which we can identify
                EditIngredientsActivity.okClickType = 1;
                FragmentManager fm = getFragmentManager();
                Dialog_Fragment_For_AddIngredients dialogFragment = new Dialog_Fragment_For_AddIngredients();
                dialogFragment.show(fm, "Add Ingredients Dialog Fragment");
            }
        });

        // on click add icon of second list, dialog popup will open to enter data in edittxt
        ImageView add_img_list2_ref = (ImageView) findViewById(R.id.add_img2_id);
        add_img_list2_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditIngredientsActivity.okClickType = 2;
                //Access Fragment Manager class to show Dialog Fragment
                FragmentManager fm = getFragmentManager();
                //Take Dialog_fragment ref
                Dialog_Fragment_For_AddIngredients dialogFragment = new Dialog_Fragment_For_AddIngredients();
                //show method will show Dialog Fragment
                dialogFragment.show(fm, "Add Ingredients Dialog Fragment");
            }
        });


        //List for number of items,no limit
        listOneObj = new ArrayList<>();

        //CLass with three differen data types
        // MyClassEditIngredients listOneData=new MyClassEditIngredients("Potato",R.drawable.cross);
        // MyClassEditIngredients listOneData1=new MyClassEditIngredients("Cheese",R.drawable.cross);
        // MyClassEditIngredients listOneData2=new MyClassEditIngredients("Capsium",R.drawable.cross);


        //adding data in list obj
        // listOneObj.add(listOneData);
        // listOneObj.add(listOneData1);
        // listOneObj.add(listOneData2);

//Another list
        listTwoObj = new ArrayList<>();

        // MyClassEditIngredients listTwoData=new MyClassEditIngredients("Eggs",R.drawable.cross);
        // MyClassEditIngredients listTwoData1=new MyClassEditIngredients("Mushroom",R.drawable.cross);
        // MyClassEditIngredients listTwoData2=new MyClassEditIngredients("Brinjal",R.drawable.cross);
        //MyClassEditIngredients listTwoData3=new MyClassEditIngredients("Parseley",R.drawable.cross);

        //adding object in list by using add method
        // listTwoObj.add(listTwoData);
        //  listTwoObj.add(listTwoData1);
        //listTwoObj.add(listTwoData2);
        // listTwoObj.add(listTwoData3);

        //Adapter to take data
        adapter = new EditIngredientsListOneAdapter(this, R.layout.edit_ingredients_first_child_list, listOneObj);
        //Accessing listView to set adapter
        ListView list_view_one = (ListView) findViewById(R.id.add_ingredients_list_id);

        //by using setAdapter method,we set adapter obj in listView first
        list_view_one.setAdapter(adapter);


        //second list adapter ,set it to Second listView
        adapter2 = new EditIngredientsListTwoAdapter(this, R.layout.edit_ingredients_second_child_list, listTwoObj);
        //Accessing listView to set adapter
        ListView list_view_two = (ListView) findViewById(R.id.add_ingredients2_list_id);
        //by using setAdapter method,we set adapter obj in listView first
        list_view_two.setAdapter(adapter2);
    }

    //make interface and use interface method to take postion
    //take a type by which we can identify which list,mention list to be 1 and 2;
    @Override
    //method of interface,getting position and taking type,by which  we can identify from which list we want to remove data
    public void adapterMethodIngredientsPosition(int position, int type) {
        //we are using only one dialog Fragment to take data in edittxt,to differentiate we declare a static variable to hold data for temporary basis
        //Gave type by which we can identify which add position is too be clicked
        if (type == 1) {
            //remove element from list
            listOneObj.remove(position);
            //refresh list
            adapter.notifyDataSetChanged();
            Toast.makeText(this, String.valueOf(position), Toast.LENGTH_LONG).show();
        }
        //deleting items from the list items in add ingredients list
        else if (type == 2) {
            {
                //remove element from list
                listTwoObj.remove(position);
                //refresh list
                adapter2.notifyDataSetChanged();
                Toast.makeText(this, String.valueOf(position), Toast.LENGTH_LONG).show();
            }
        }
    }

    // to add items in add list one on clicking add recipeImage
    @Override
    public void transferData(String editTxtDialogData) {   // check if the ingredients is not empty,if empty show some message
        if (editTxtDialogData.isEmpty()) {
            Toast.makeText(this, "Please enter Ingredients", Toast.LENGTH_LONG).show();
        }
        // adding data  to list obj,fetching data from  Edit text by Dialog Fragment
        else {
            MyClassEditIngredients listOneData = new MyClassEditIngredients(editTxtDialogData, R.drawable.cross);
            listOneObj.add(listOneData);
            //refresh the list
            adapter.notifyDataSetChanged();
        }
    }

    //using interface method to transfer data from dialog box edit box to add data in second list
    @Override
    public void transferDataSecondList(String editTxtTwoDialogData) {//check if the ingredients is not empty,if empty show some message
        if (editTxtTwoDialogData.isEmpty()) {
            Toast.makeText(this, "Please enter Ingredients", Toast.LENGTH_LONG).show();
        } else {

            MyClassEditIngredients listTwoData = new MyClassEditIngredients(editTxtTwoDialogData, R.drawable.cross);
            listTwoObj.add(listTwoData);
            adapter2.notifyDataSetChanged();
        }
    }

    // interface method to transfer agedata fron popup to activityst
    @Override
    //editTxtAgeData have the age data and now showing it in Edittext
    public void transferAgeData(String editTxtAgeData) {
        ageText = (TextView) findViewById(R.id.age_edit_txt_id);
        ageText.setText(editTxtAgeData);
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
                Intent i = new Intent(getApplicationContext(), YourOrderActivity.class);
                startActivity(i);
            }
        });
    }
}