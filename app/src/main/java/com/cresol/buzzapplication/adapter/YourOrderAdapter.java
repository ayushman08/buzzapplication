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
import com.cresol.buzzapplication.model.CartOrderModel;

import java.util.List;

/**
 * Created by Saurabh on 8/1/2016.
 */
public class YourOrderAdapter extends ArrayAdapter<CartOrderModel> {

    Activity context;
    List<CartOrderModel> mydata;
    ViewHolder viewHolder;

    public YourOrderAdapter(Activity context, int resource, List<CartOrderModel> mydata) {
        super(context, resource, mydata);
        this.context = context;
        this.mydata = mydata;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //Using inflator for getting the view of child list
        if (view == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.your_order_child_list, null, true);
            //Access textView and get data

            viewHolder.dishImage = (ImageView) view.findViewById(R.id.dishImage);
            viewHolder.dishName = (TextView) view.findViewById(R.id.dishName);
            viewHolder.dishPrice = (TextView) view.findViewById(R.id.dishPrice);
            viewHolder.dishQuantity = (TextView) view.findViewById(R.id.dishQuantity);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        viewHolder.dishImage.getLayoutParams().width = displaymetrics.widthPixels / 2;
        viewHolder.dishImage.getLayoutParams().height = (displaymetrics.widthPixels / 2) - 10;
        viewHolder.dishImage.requestLayout();
//for changing font
        Typeface customfont = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");
        viewHolder.dishName.setTypeface(customfont);
        viewHolder.dishPrice.setTypeface(customfont);
        viewHolder.dishQuantity.setTypeface(customfont);

        CartOrderModel obj = mydata.get(position);

        viewHolder.dishName.setText(obj.getDish_Name());
        viewHolder.dishQuantity.setText(obj.getDish_Quantity() + " plates");
        viewHolder.dishPrice.setText(obj.getDish_Price());

        if (obj.Dish_Image != null) {
            Glide.get(this.context).clearMemory();


            Glide.with(this.context)
                    .load(obj.getDish_Image())
                    .crossFade()
                    .into(viewHolder.dishImage);
        }

        return view;
    }

    public class ViewHolder {
        public TextView dishName;
        public TextView dishQuantity;
        public TextView dishPrice;
        public ImageView dishImage;

    }
}