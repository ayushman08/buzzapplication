package com.cresol.buzzapplication.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.model.AllRecipeModel;
import com.cresol.buzzapplication.util.GlobalMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saurabh on 7/28/2016.
 */
public class RecipeAdapter extends ArrayAdapter<AllRecipeModel> {


    Activity context;
    ViewHolder viewHolder;
    //this will take all data in list
    List<AllRecipeModel> recipeData;
      //here we create a constructor,it will initialise the list from the activity,we pass third arguement as data which we fetch from the server
    //we take third arguement as list of List<AllRecipeModel> type
    public RecipeAdapter(Activity context, int resource, List<AllRecipeModel> allRecipeModelList) {
        super(context, resource, allRecipeModelList);
        this.context = context;
        this.recipeData = allRecipeModelList;
    }

    //method for getting the view of singlerow
    @Override
    public View getView(int position, View view, ViewGroup parent) {

         //initially the view is null
        try {
            if (view == null) {
                viewHolder = new ViewHolder();
                //here inflating the layout for the view
                LayoutInflater inflater = context.getLayoutInflater();
                view = inflater.inflate(R.layout.recipe_child_list, null, true);
                viewHolder.recipeImage = (ImageView) view.findViewById(R.id.recipe_img_id);
                viewHolder.recipeName = (TextView) view.findViewById(R.id.recipe_txt_one_id);
                viewHolder.chefName = (TextView) view.findViewById(R.id.chef_name);
           //setTag for saving different view details
                 view.setTag(viewHolder);
            } else {
                //here we get the view different details in viewHolder
                viewHolder = (ViewHolder) view.getTag();
            }

            //change the list view  font
            Typeface customfont = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");
            Typeface customfontBold = Typeface.createFromAsset(context.getAssets(), "Raleway-ExtraBold.ttf");
            viewHolder.recipeName.setTypeface(customfont);
            viewHolder.chefName.setTypeface(customfontBold);


            // recipeData is list ref which have all the data from the retrofit response,we get the positionwise list data in recipeData
               final AllRecipeModel recipeData = this.recipeData.get(position);

            //we set the value of RecipieName  from AllRecipeModel recipeData
             viewHolder.recipeName.setText(recipeData.RecipieName);
             viewHolder.chefName.setText(recipeData.ChefName);


            if (recipeData.RecipieName != null) {

                Glide.get(this.context).clearMemory();


                Glide.with(this.context)
                        .load(recipeData.RecipieImage)
                        .placeholder(R.drawable.buzz_watermark)
                        .centerCrop()
                        .crossFade()
                        .into(viewHolder.recipeImage);
            }


            return view;
        } catch (Exception e) {
            return null;
        }


    }

//create a class for holding the view,by which we dont have to find ids again and again in list view
    public class ViewHolder {
        public ImageView recipeImage;
        public TextView recipeName;
        public TextView chefName;

    }
}
