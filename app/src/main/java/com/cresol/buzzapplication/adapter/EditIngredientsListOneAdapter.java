package com.cresol.buzzapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.model.Ingredient_InterfaceDeleteData;
import com.cresol.buzzapplication.model.MyClassEditIngredients;

import java.util.List;

/**
 * Created by Saurabh on 8/2/2016.
 */
public class EditIngredientsListOneAdapter extends ArrayAdapter<MyClassEditIngredients> {


    Activity context;
    ViewHolder holder;

    List<MyClassEditIngredients> mydata;

    public EditIngredientsListOneAdapter(Activity context, int resource, List<MyClassEditIngredients> mydata) {
        super(context, resource, mydata);
        this.context = context;
        this.mydata = mydata;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {


        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.edit_ingredients_first_child_list, null, true);
            holder = new ViewHolder();

            holder.ingredientTxt = (TextView) view.findViewById(R.id.add_list1_txt_id);
            holder.add_img_ref = (ImageView) view.findViewById(R.id.add_list1_img_id);
//saving position of list_item in  self made holder class to restore ids of list view
            holder.add_img_ref.setTag(position);

            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }
        //saving position of list
        holder.position = position;
        holder.ingredientTxt.setText(mydata.get(position).getIngredientName());

        holder.add_img_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v != null) {
                    int position = holder.position;

                    ((Ingredient_InterfaceDeleteData) context).adapterMethodIngredientsPosition(position, 1);
                }
            }
        });


        return view;
    }

    public class ViewHolder {

        public TextView ingredientTxt;
        public ImageView add_img_ref;
        int position;
    }
}
