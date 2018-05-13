package com.cresol.buzzapplication.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cresol.buzzapplication.R;

import java.util.List;

/**
 * Created by Nitesh on 10/5/2016.
 */
public class RecipeHowToMakeAdapter extends ArrayAdapter<String> {

    Activity context;
    ViewHolder viewHolder;

    List<String> howToMakeData;

    public RecipeHowToMakeAdapter(Activity context, int resource, List<String> mydata) {
        super(context, resource, mydata);
        this.context = context;
        this.howToMakeData = mydata;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
         // try {
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.ingredient_howtomake_child_list, null, true);
            viewHolder.howToMake = (TextView) view.findViewById(R.id.howToMakeTextView);
            viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //change the font style
        Typeface customfont = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");
        viewHolder.howToMake.setTypeface(customfont);
        final String howToMake = howToMakeData.get(position);
        viewHolder.howToMake.setText(howToMake);

        if (position % 2 == 0) {
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#f7f7f7"));
        } else {
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }

        return view;
       //  }
      //  catch(Exception e){
        //     return null;
      //   }


    }


    public class ViewHolder {
        public TextView howToMake;
        public LinearLayout linearLayout;

    }

}
