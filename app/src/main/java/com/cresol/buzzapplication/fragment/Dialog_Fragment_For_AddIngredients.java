package com.cresol.buzzapplication.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.activity.EditIngredientsActivity;
import com.cresol.buzzapplication.model.TransferDataDialogActivity;

/**
 * Created by Saurabh on 8/11/2016.
 */
public class Dialog_Fragment_For_AddIngredients extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_fragment_add_ingredients, container,
                false);
     //   getDialog().setTitle("Please enter Ingredients");
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

// cancel txt view,after inflation access the view
        TextView cancel_txt = (TextView) rootView.findViewById(R.id.dialog_cancel_txt);
        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        // ok textview
        TextView ok_txt = (TextView) rootView.findViewById(R.id.dialog_ingredients_ok_txt);
        ok_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText add_edittxt = (EditText) rootView.findViewById(R.id.add_ingredients_edit_txt);

         /*       if( LikeActivity.okClickType!=0){
                    if( LikeActivity.okClickType==1){
                        ((TransferDataDialogActivity)getActivity()).transferData(add_edittxt.getText().toString());
                    }

                    if(LikeActivity.okClickType==2){
                        ((TransferDataDialogActivity)getActivity()).transferDataSecondList(add_edittxt.getText().toString());
                    }
                }  */
                if (EditIngredientsActivity.okClickType != 0) {
                    if (EditIngredientsActivity.okClickType == 1) {
                        ((TransferDataDialogActivity) getActivity()).transferData(add_edittxt.getText().toString());
                    }
                    if (EditIngredientsActivity.okClickType == 2) {
                        ((TransferDataDialogActivity) getActivity()).transferDataSecondList(add_edittxt.getText().toString());
                    }
                }
                getDialog().dismiss();

            }
        });

        return rootView;
    }
}
