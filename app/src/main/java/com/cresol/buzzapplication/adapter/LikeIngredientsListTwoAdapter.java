package com.cresol.buzzapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.model.Ingredient_InterfaceDeleteData;
import com.cresol.buzzapplication.model.LikeActivityModel;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

/**
 * Created by Saurabh on 8/25/2016.
 */
public class LikeIngredientsListTwoAdapter extends BaseSwipeAdapter {

    private Context mContext;

    String type;
    ViewHolder holder;
    List<LikeActivityModel> mydata;

    public LikeIngredientsListTwoAdapter(Activity context, String type, List<LikeActivityModel> mydata) {

        this.mContext = context;
        this.mydata = mydata;
        this.type = type;
    }
//class to hold view of list at first time it will save in holder class,now we dont access repeatedly list item viewByIds

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.like_list_two_child_list, null);

        return v;
    }

    public class MyViewHolder {

        public TextView ingredientTxt;
        public ImageView add_img_ref;
        public int position;
        public LinearLayout linearLayout;
    }


    @Override
    public void fillValues(int position, View convertView) {
        holder = new ViewHolder();
        if (convertView != null) {


            holder.swipeLayout = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(position));
            holder.trash = (ImageView) convertView.findViewById(R.id.trash);
            holder.ingredientTxt = (TextView) convertView.findViewById(R.id.like_list2_txt_id);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.trash.setTag(position);


        Typeface customfont = Typeface.createFromAsset(mContext.getAssets(), "Raleway-Regular.ttf");
        holder.ingredientTxt.setTypeface(customfont);

        if (position % 2 == 0) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#f7f7f7"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#f3f3f3"));
        }

        LikeActivityModel data = mydata.get(position);

        holder.ingredientTxt.setText(data.getIngredientName());

        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                //   YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }

            @Override
            public void onClose(SwipeLayout layout) {
                super.onClose(layout);
            }
        });
        holder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view != null) {
                    int position = (Integer) view.getTag();
                    //Taking position by using interface(to connect from one activity to other),taking position in adapter method

                    ((Ingredient_InterfaceDeleteData) mContext).adapterMethodIngredientsPosition(position, 2);
                    Toast.makeText(mContext, "Deleting", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getCount() {
        return mydata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        public TextView ingredientTxt;
        public ImageView trash;
        public int position;
        public LinearLayout linearLayout;
        public SwipeLayout swipeLayout;
    }
}
