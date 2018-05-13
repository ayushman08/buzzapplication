package com.cresol.buzzapplication.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.model.CartCount;
import com.cresol.buzzapplication.model.DishesModel;
import com.cresol.buzzapplication.model.InterfaceDishToRating;
import com.cresol.buzzapplication.util.GlobalMethods;

import java.util.List;

/**
 * Created by Saurabh on 8/1/2016.
 */

// Customise Adapter extending ArrayAdapter<DishesModel>
public class DishesAdapter extends ArrayAdapter<DishesModel> {

    Activity context;
    //List
    List<DishesModel> mydata;
    //make a viewHolder class to add the whole view by which we can access the view
    ViewHolder viewholder;


    // Constructor  used in Choose items Activity,
    public DishesAdapter(Activity context, int resource, List<DishesModel> mydata) {
        super(context, resource, mydata);
        this.context = context;
        this.mydata = mydata;
    }

    @Override
    // we will getView  of every row,having arguements position of every view,View and viewGroup parent
    public View getView(final int position, View view, ViewGroup parent) {

        viewholder = new ViewHolder();
        //Using inflator for getting the view
        LayoutInflater inflater = context.getLayoutInflater();
        //inflating layout into View
        view = inflater.inflate(R.layout.choose_items_child_list, null, true);
        //We get the view
        if (view != null) {
            // viewHolder have access of whole List
            // save the view of every Dish
            viewholder.dishName = (TextView) view.findViewById(R.id.item_name);
            //Save the recipeImage in ViewHolder

            viewholder.dishImage = (ImageView) view.findViewById(R.id.item_image);

            viewholder.dishPrice = (TextView) view.findViewById(R.id.item_price);

            viewholder.addIcon = (ImageView) view.findViewById(R.id.add_icon_choose_items);
            viewholder.removeIconImg = (ImageView) view.findViewById(R.id.remove_icon_choose_items);
            viewholder.dishQuantity = (Spinner) view.findViewById(R.id.dishQuantity);
            viewholder.rateDish = (TextView) view.findViewById(R.id.rateDish);


            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }

        Typeface customfont = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");

        viewholder.dishPrice.setTypeface(customfont);
        viewholder.dishName.setTypeface(customfont);


        viewholder.position = position;
        //getting recipeImage in row view with position
        // int recipeImage =mydata.get(position).getRecipe_Image();
        // final ImageView imageView = (ImageView)view.findViewById(R.id.item_image);
        //setting Image in the recipeImage view in rowView
        //imageView.setImageResource(recipeImage);
        // removeIconImg = (ImageView) view.findViewById(R.id.remove_icon_choose_items);


        //Initallly set the visibility of remove icon gone,it will not take any space


        //  if(viewholder.removeIconImg.setVisibility(View.VISIBLE)&&DishesActivity.addedDishData)


        viewholder.removeIconImg.setTag(position);
        viewholder.rateDish.setTag(position);

        // addIconImg = (ImageView) view.findViewById(R.id.add_icon_choose_items);
        viewholder.addIcon.setTag(position);
        //get whole list items of row in obj

        //from the list,we take positionwise list
        DishesModel obj = mydata.get(position);
      //set name and price for the dishes list from the obj have positionwise data
        viewholder.dishName.setText(obj.getDishName());
        viewholder.dishPrice.setText(obj.getStartingPrice());

        //Spinner count in dish List
        Integer[] arraySpinner = new Integer[101];

        //Add spinner
        for (int i = 0; i < 100; i++) {
            arraySpinner[i] = i;
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_item, arraySpinner);
        viewholder.dishQuantity.setAdapter(adapter);


          //Here in temporary list we check if added list with id is present,then let it cross
        if (GlobalMethods.CheckValueAvailable(context, String.valueOf(obj.getDishID()), String.valueOf(obj.getRestaurantID()))) {
            //if dishid is present then set visibility
            viewholder.addIcon.setVisibility(View.GONE);
            viewholder.removeIconImg.setVisibility(View.VISIBLE);
            viewholder.dishQuantity.setSelection(GlobalMethods.GetQuantity(context, String.valueOf(obj.getDishID()), String.valueOf(obj.getRestaurantID())));
            viewholder.dishQuantity.setEnabled(false);
        }
        else {
            viewholder.dishQuantity.setEnabled(true);
            viewholder.addIcon.setVisibility(View.VISIBLE);
            viewholder.removeIconImg.setVisibility(View.GONE);
        }

        //Set the onclick listener on addIcon
         viewholder.addIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Take Position of listView
                int position = (int) v.getTag();
                //reach to the spinner
                LinearLayout layoutView = (LinearLayout) v.getParent().getParent().getParent().getParent();
                Spinner dishQuantity = (Spinner) layoutView.findViewById(R.id.dishQuantity);

                //Here from the spinner ,we get the  no of selected item into the variable quantity
                int quantity = Integer.valueOf(dishQuantity.getSelectedItem().toString());
                if (quantity != 0) {
                    //By position take object of DishList
                    DishesModel obj = mydata.get(position);
                    //Code for adding value in sqLite to show the selected items to the Your order Activity
                    GlobalMethods.addCartData(obj.getDishID().toString(), obj.getDishName(), obj.getDishImage(), obj.getStartingPrice(), String.valueOf(quantity), obj.getRestaurantID().toString(), context);

                    //Accessing the selected view of list
                    View view = (View) v.getParent();
                    //Getting the access of addIcon and removeIcon

                    ImageView add = (ImageView) view.findViewById(R.id.add_icon_choose_items);
                    ImageView remove = (ImageView) view.findViewById(R.id.remove_icon_choose_items);
                    //Changing the visibility
                    remove.setVisibility(View.VISIBLE);
                    add.setVisibility(View.GONE);
                    //After selecting value in spinner,let it disable
                    dishQuantity.setEnabled(false);

                    //Showing the cart Count
                    ((CartCount) getContext()).ShowCartCount();


                    //  int dishId=obj.getDishID();
                    //Write code to save dish id,it is unique
                    //  DishesActivity.addedDishData.add(dishId);
                    // ((CartCount)getContext()).transferCountToCart(DishesActivity.addedDishData.size());
                } else {
                    Toast.makeText(context, "Please add quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Give the whole View of List
          viewholder.removeIconImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get position of list view first in position variable
                int position = (int) v.getTag();

                // List with certain position row have all items like dishid,dishname
                DishesModel obj = mydata.get(position);

                //from that list row, we will get the dishid
                Integer dishID = obj.getDishID();
                Integer restaurantId = obj.getRestaurantID();

                LinearLayout layoutView = (LinearLayout) v.getParent().getParent().getParent().getParent();
                Spinner dishQuantity = (Spinner) layoutView.findViewById(R.id.dishQuantity);


                View view = (View) v.getParent();

                ImageView add = (ImageView) view.findViewById(R.id.add_icon_choose_items);
                ImageView remove = (ImageView) view.findViewById(R.id.remove_icon_choose_items);

                add.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
                dishQuantity.setEnabled(true);

                GlobalMethods.DeleteDishData(context, String.valueOf(dishID), String.valueOf(restaurantId));

                  //Showing the cart Count
                ((CartCount) getContext()).ShowCartCount();


            }
        });

        //Here clicking on Rate,by taking position
         viewholder.rateDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get position of list view first in position variable
                int position = (int) v.getTag();

                // List with certain position row have all items like dishid,dishname
                DishesModel obj = mydata.get(position);

                //from that list row, we will get the dishid
                Integer dishID = obj.getDishID();
                Integer restaurantId = obj.getRestaurantID();
                String dishName = obj.getDishName();

                //interface created for sending data from adapter  to the dish activity
                ((InterfaceDishToRating) getContext()).interfaceDishToRating(restaurantId, dishID, dishName);
            }
        });

        //adding  background to the spinner
         viewholder.dishQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //first item of spinner always selected ,so we put background on that
              ((TextView) parent.getChildAt(0)).setBackground(context.getResources().getDrawable(R.drawable.drawable_spinner));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       //......................................
        if (mydata.get(position).getDishImage() != null) {
        //first clear all the images in dish list,if anything available
            Glide.get(this.context).clearMemory();


            Glide.with(this.context)
                    .load(mydata.get(position).getDishImage())
                    .placeholder(R.drawable.buzz_watermark)
                    .centerCrop()
                    .crossFade()
                    .into(viewholder.dishImage);


        }


        return view;

    }


    public class ViewHolder {
        ImageView dishImage;
        TextView dishName;
        TextView dishPrice;
        Spinner dishQuantity;
        ImageView addIcon;
        int position;
        ImageView removeIconImg;
        TextView rateDish;
    }

}
