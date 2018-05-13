package com.cresol.buzzapplication.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.model.AllRestaurantModel;

import java.util.List;

/**
 * Created by Saurabh on 7/28/2016.
 */
public class RestaurantAdapter extends ArrayAdapter<AllRestaurantModel> {

    Activity context;
    List<AllRestaurantModel> mydata;
    ViewHolder viewHolder;

    public RestaurantAdapter(Activity context, int resource, List<AllRestaurantModel> mydata) {
        super(context, resource, mydata);
        this.context = context;
        this.mydata = mydata;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {


        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.restaurant_child_list, null, true);

            viewHolder.restaurantImage = (ImageView) view.findViewById(R.id.restaurant_image_id);
            viewHolder.restaurantName = (TextView) view.findViewById(R.id.restaurant_name_id);
            viewHolder.restaurantDescription = (TextView) view.findViewById(R.id.restaurant_description_id);
            viewHolder.restaurantOpenStatus = (TextView) view.findViewById(R.id.open_id);
            viewHolder.restaurantDistance = (TextView) view.findViewById(R.id.restaurant_distance_id);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Typeface customFontBold = Typeface.createFromAsset(context.getAssets(), "Raleway-Bold.ttf");
        Typeface customfont = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");
        viewHolder.restaurantName.setTypeface(customFontBold);
        viewHolder.restaurantDescription.setTypeface(customfont);
        viewHolder.restaurantOpenStatus.setTypeface(customfont);
        viewHolder.restaurantDistance.setTypeface(customfont);


         AllRestaurantModel obj = mydata.get(position);

        viewHolder.restaurantName.setText(obj.getRestaurantName());
        viewHolder.restaurantDescription.setText(obj.getDescription());
        viewHolder.restaurantOpenStatus.setText(obj.getRestaurantOpeningStatus());
        viewHolder.restaurantDistance.setText(obj.getRestaurantDistance() + " From here.");

        //Dynamic height and width code
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;



        viewHolder.restaurantImage.getLayoutParams().height = height / 5;
        viewHolder.restaurantImage.requestLayout();

        if (obj.getRestaurantImage() != null) {

            Glide.get(this.context).clearMemory();


            Glide.with(this.context)
                    .load(obj.getRestaurantImage())
                    .placeholder(R.drawable.buzz_watermark)
                    .centerCrop()
                    .crossFade()
                    .into(viewHolder.restaurantImage);
        }


        return view;
    }

    public class ViewHolder {
        public ImageView restaurantImage;
        public TextView restaurantName;
        public TextView restaurantDescription;
        public TextView restaurantOpenStatus;
        public TextView restaurantDistance;

    }

}

