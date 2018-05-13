package com.cresol.buzzapplication.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.activity.SplitBillActivity;
import com.cresol.buzzapplication.model.MyClassSplitBill;
import com.cresol.buzzapplication.model.SplitBillCheckBoxSelectorInterface;

import java.util.List;

/**
 * Created by Saurabh on 8/2/2016.
 */
public class SplitBillAdapter extends ArrayAdapter<MyClassSplitBill> {
    Activity context;
    List<MyClassSplitBill> mydata;
    Viewholder viewholder;


    public SplitBillAdapter(Activity context, int resource, List<MyClassSplitBill> mydata) {
        super(context, resource, mydata);
        this.context = context;
        this.mydata = mydata;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //First Step initialize
        viewholder = new Viewholder();
        //Using inflator for getting the view
        LayoutInflater inflater = context.getLayoutInflater();
        view = inflater.inflate(R.layout.split_bill_child_list, null, true);
        //view is not null
        if (view != null) {
            //save the view of friend_Name of list in viewholder by which
            viewholder.friend_Name = (TextView) view.findViewById(R.id.split_bill_list_txt_id);
            //save the view of checkbox in list
            viewholder.checkBox = (CheckBox) view.findViewById(R.id.split_bill_list_checkbox_id);
            //Also save the position of checkbox view,position starts from 0
            viewholder.checkBox.setTag(position);
            // here we save the view ids of friends name,checkbox and position of checkbox
            view.setTag(viewholder);
        } else {
            //otherwise get the tag
            viewholder = (Viewholder) view.getTag();
        }
        //position of view is taking in view holder class
        viewholder.position = position;

        //change the textfont
        Typeface customfont = Typeface.createFromAsset(context.getAssets(), "Raleway-Regular.ttf");
        viewholder.friend_Name.setTypeface(customfont);

        // Set data

        //taking Data of list of which position  in BillData variable
        MyClassSplitBill BillData = mydata.get(position);

        //
        viewholder.friend_Name.setText(BillData.getFriend_Name());


        //Here we wil write code to check and uncheck the checkbox
        // condition if saveSplitBillData i.e list created for saving the checked checkbox not empty and size of list should be bigger than
        //the checked element position

        if (!SplitBillActivity.saveSplitBillData.isEmpty() && SplitBillActivity.saveSplitBillData.size() > position) {
            //checkstatus for the new created list in which we are saving checked checkbox
            //we are doing this,bcz on scrolling down the checked checkbox get uncheck

            for (MyClassSplitBill checkstatus : SplitBillActivity.saveSplitBillData) {
                //here checking saved list to the already existing list
                //taking checkstatus (here checked and unchecked both will come)with friend name is equal to created list for saving checked checkbox
                if (checkstatus.getFriend_Name().equals(BillData.getFriend_Name())) {
                    //stay checked
                    viewholder.checkBox.setChecked(true);
                }
            }

        }
        //Event Handler
        //for check or uncheck
        viewholder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            //buttonview is checkview
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // here we are taking position of checked checkbox
                int getPosition = (Integer) buttonView.getTag();

                if (isChecked) {
                    //To add check box
                    ((SplitBillCheckBoxSelectorInterface) context).CheckBoxSelectorMediator(getPosition, true);
                } else {
                    //To remove chekbox value
                    ((SplitBillCheckBoxSelectorInterface) context).CheckBoxSelectorMediator(getPosition, false);
                }
            }
        });


        return view;
    }

    public class Viewholder {
        public TextView friend_Name;
        public CheckBox checkBox;
        public int position;

    }
}