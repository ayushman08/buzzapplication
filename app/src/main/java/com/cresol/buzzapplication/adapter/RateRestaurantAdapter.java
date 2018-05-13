package com.cresol.buzzapplication.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.activity.RateRestaurantActivity;
import com.cresol.buzzapplication.model.RestaurantRateTypeWithRating;

import java.util.List;

/**
 * Created by Saurabh on 7/28/2016.
 */
public class RateRestaurantAdapter extends ArrayAdapter<RestaurantRateTypeWithRating> {

    Activity context;
    ViewHolder viewHolder;


    List<RestaurantRateTypeWithRating> mydata;

    public RateRestaurantAdapter(Activity context, int resource, List<RestaurantRateTypeWithRating> mydata) {
        super(context, resource, mydata);
        this.context = context;
        this.mydata = mydata;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.rate_restaurant_child_list, null, true);
            viewHolder.ratingName = (TextView) view.findViewById(R.id.restaurant_services_txt);
            viewHolder.ratingValue = (RatingBar) view.findViewById(R.id.rate_services_rating_bar);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Typeface customfont = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");
        viewHolder.ratingName.setTypeface(customfont);


        viewHolder.ratingValue.setTag(position);
        RestaurantRateTypeWithRating obj = mydata.get(position);

        viewHolder.ratingName.setText(obj.getRatingID());
        if (obj.getRatingValue() != null) {
            viewHolder.ratingValue.setRating(Float.parseFloat(obj.getRatingValue()));
        }


        viewHolder.ratingValue.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    int pos = position;
                    RestaurantRateTypeWithRating obj = RateRestaurantActivity.saveRating.get(position);
                    //  int roundoff_rating = (int)Math.round(rating);

                    obj.setRatingValue(String.valueOf(rating));
                    RateRestaurantActivity.saveRating.set(position, obj);
                }
            }
        });


        return view;
    }

    public class ViewHolder {
        public TextView ratingName;
        public RatingBar ratingValue;

    }
}


