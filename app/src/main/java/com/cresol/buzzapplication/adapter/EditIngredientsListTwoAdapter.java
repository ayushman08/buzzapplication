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
public class EditIngredientsListTwoAdapter extends ArrayAdapter<MyClassEditIngredients> {


    Activity context;


    MyViewHolder secondListHolder;
    List<MyClassEditIngredients> mydata;

    public EditIngredientsListTwoAdapter(Activity context, int resource, List<MyClassEditIngredients> mydata) {
        super(context, resource, mydata);
        this.context = context;
        this.mydata = mydata;
    }
//class to hold view of list at first time it will save in holder class,now we dont access repeatedly list item viewByIds


    public class MyViewHolder {

        public TextView ingredientTxt;
        public ImageView add_img_ref;
        int position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {


        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.edit_ingredients_second_child_list, null, true);
            secondListHolder = new MyViewHolder();

            secondListHolder.ingredientTxt = (TextView) view.findViewById(R.id.add_list2_txt_id);
            secondListHolder.add_img_ref = (ImageView) view.findViewById(R.id.add_list2_img_id);
//saving position of list_item
            secondListHolder.add_img_ref.setTag(position);
            view.setTag(secondListHolder);
        } else {

            secondListHolder = (MyViewHolder) view.getTag();
        }
        secondListHolder.position = position;

        secondListHolder.ingredientTxt.setText(mydata.get(position).getIngredientName());
        secondListHolder.add_img_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v != null) {
                    int position = secondListHolder.position;
                    //Taking position by using interface(to connect from one activity to other),taking position in adapter method

                    ((Ingredient_InterfaceDeleteData) context).adapterMethodIngredientsPosition(position, 2);
                }
            }
        });


        return view;
    }
}
