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
import com.cresol.buzzapplication.model.TransferDataDialogActivity;

/**
 * Created by Saurabh on 8/22/2016.
 */
public class Dialog_Fragment_Enter_Age extends DialogFragment {
    String a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_fragment_enter_age, container,
                false);
        //getDialog().setTitle("Please enter your Age");
        //to remove the default space for  title
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        TextView cancel_txt = (TextView) rootView.findViewById(R.id.cancel_age_dialog);
        cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        TextView ok_txt = (TextView) rootView.findViewById(R.id.ok_age_dialog);
        ok_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText age_edittxt = (EditText) rootView.findViewById(R.id.age_edit_text);
                // interface for transfering data to edittxt


                ((TransferDataDialogActivity) getActivity()).transferAgeData(age_edittxt.getText().toString());
                getDialog().dismiss();
            }


        });


        return rootView;
    }
}
