package com.cresol.buzzapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.model.AllRecipeModel;
import com.cresol.buzzapplication.model.RecipeNutritionModel;
import com.github.lzyzsd.circleprogress.CircleProgress;

import java.util.List;

/**
 * Created by Nitesh on 9/15/2016.
 */
public class RecipeNutritionAdapter extends ArrayAdapter<RecipeNutritionModel> {

    Activity context;
    ViewHolder viewHolder;

    List<RecipeNutritionModel> NutritionData;
    public RecipeNutritionAdapter(Activity context, int resource , List<RecipeNutritionModel> mydata){
        super(context,resource,mydata);
        this.context=context;
        this.NutritionData=mydata;
    }






    @Override
    public View getView(int position, View view, ViewGroup parent) {
      //  try {
            if(view==null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = context.getLayoutInflater();
                view = inflater.inflate(R.layout.recipe_nutrition_child_list, null, true);
                viewHolder.nutritionName = (TextView)  view.findViewById(R.id.nutritionNameTextView);
               // viewHolder.quantityValue= (TextView)  view.findViewById(R.id.quantityTextView);
                viewHolder.circleProgress= (CircleProgress)  view.findViewById(R.id.circle_progress);
                view.setTag(viewHolder);
            }
            else{
                viewHolder=(ViewHolder)view.getTag();
            }

//change the font style
        Typeface customfont = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");
        viewHolder.nutritionName.setTypeface(customfont);
      //  viewHolder.quantityValue.setTypeface(customfont);


            final RecipeNutritionModel nutritionModel = NutritionData.get(position);

        String Name=nutritionModel.getNutritionName();
        String quantity=nutritionModel.getQuantity();
            viewHolder.nutritionName.setText(Name);
          // viewHolder.quantityValue.setText(quantity);
        viewHolder.circleProgress.setProgress(Integer.valueOf(quantity));
        




            return view;
       // }
       // catch(Exception e){
      //      return null;
      //  }


    }


    public class ViewHolder{
        public  TextView nutritionName;
        public  TextView quantityValue;
        public CircleProgress circleProgress;


    }

}
