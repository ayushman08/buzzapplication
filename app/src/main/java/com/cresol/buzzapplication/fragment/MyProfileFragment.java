package com.cresol.buzzapplication.fragment;

/**
 * Created by Saurabh on 10/6/2016.
 */

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.model.ReadProfileInfo;
import com.cresol.buzzapplication.util.DataResource;
import com.cresol.buzzapplication.util.GlobalMethods;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Saurabh on 10/6/2016.
 */
public class MyProfileFragment extends Fragment {
    TextView firstname;
    TextView lastname;
    TextView mobilenumber;
    TextView mail;
    TextView firstnamedetail;
    TextView lastnamedetail;
    TextView mobilenumberdetail;
    TextView maildetail;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.my_profile_fragment, container, false);

        GlobalMethods.overrideFonts(getActivity(), v);

        InitilizeUI(v);
        SetIcons();

        //setting width of textViews
        //  firstname.getLayoutParams().width = GlobalMethods.GetDeviceWidth(getActivity())/2 ;
        //  lastname.getLayoutParams().width = GlobalMethods.GetDeviceWidth(getActivity())/2 ;
        //  mobilenumber.getLayoutParams().width = GlobalMethods.GetDeviceWidth(getActivity())/2 ;
        //  mail.getLayoutParams().width = GlobalMethods.GetDeviceWidth(getActivity())/2 ;

        if (GlobalMethods.isNetworkAvailable(getActivity())) {
            if (DataResource.cacheProfileInfo != null) {
                bindData(DataResource.cacheProfileInfo.getFirstName(), DataResource.cacheProfileInfo.getLastName(), DataResource.cacheProfileInfo.getEmail(), DataResource.cacheProfileInfo.getMobile());

            } else {
                retroCallProfileData();
            }
        }

        return v;
    }

    public void bindData(String firstname, String lastname, String email, String mobile) {
        try {
            maildetail.setText(email);
            firstnamedetail.setText(firstname);
            lastnamedetail.setText(lastname);
            mobilenumberdetail.setText(mobile);
        } catch (Exception e) {
            //catch the exception
            GlobalMethods.ApplicationError(getActivity(), e);
        }

    }

    public void retroCallProfileData() {
        // if (sessionManager.isUserLoggedIn()) {
        if (GlobalMethods.isNetworkAvailable(getActivity())) {

            api buzzApi = GlobalMethods.GetBuzzdAPI(getActivity());

            Call<ReadProfileInfo> outputcall = buzzApi.GetProfileData(13);

            GlobalMethods.ShowProgressBar(getActivity());
            outputcall.enqueue(new Callback<ReadProfileInfo>() {
                @Override
                public void onResponse(Call<ReadProfileInfo> call, Response<ReadProfileInfo> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        DataResource.cacheProfileInfo = response.body();
                        bindData(response.body().getFirstName(), response.body().getLastName(), response.body().getEmail(), response.body().getMobile());
                    }
                    GlobalMethods.HideProgressBar();
                }

                @Override
                public void onFailure(Call<ReadProfileInfo> call, Throwable t) {
                    GlobalMethods.HideProgressBar();
                }
            });

        }


        //   }

    }


    public void SetIcons() {
        Drawable firstnameimg = getResources().getDrawable(R.drawable.name);
        firstnameimg.setBounds(0, 0, (int) (firstnameimg.getIntrinsicWidth() * 0.5), (int) (firstnameimg.getIntrinsicWidth() * 0.5));
        ScaleDrawable sD1 = new ScaleDrawable(firstnameimg, 0, 10, 10);
        //by taking textview ref,set drawble recipeImage at left position to Text view by setCompoundDrawables(left,top,right,bottom)
        firstname.setCompoundDrawables(sD1.getDrawable(), null, null, null);


        Drawable lastnameimg = getResources().getDrawable(R.drawable.name);
        lastnameimg.setBounds(0, 0, (int) (lastnameimg.getIntrinsicWidth() * 0.5), (int) (lastnameimg.getIntrinsicWidth() * 0.5));
        ScaleDrawable sD2 = new ScaleDrawable(lastnameimg, 0, 10, 10);

        lastname.setCompoundDrawables(sD2.getDrawable(), null, null, null);


        Drawable mobilenumberimg = getResources().getDrawable(R.drawable.phone);
        mobilenumberimg.setBounds(0, 0, (int) (mobilenumberimg.getIntrinsicWidth() * 0.5), (int) (mobilenumberimg.getIntrinsicWidth() * 0.5));
        ScaleDrawable sD3 = new ScaleDrawable(mobilenumberimg, 0, 10, 10);

        mobilenumber.setCompoundDrawables(sD3.getDrawable(), null, null, null);

        Drawable mailimg = getResources().getDrawable(R.drawable.mail);
        mailimg.setBounds(0, 0, (int) (lastnameimg.getIntrinsicWidth() * 0.5), (int) (mobilenumberimg.getIntrinsicWidth() * 0.5));
        ScaleDrawable sD4 = new ScaleDrawable(mailimg, 0, 10, 10);

        mail.setCompoundDrawables(sD4.getDrawable(), null, null, null);
    }

    public void InitilizeUI(View v) {
        firstname = (TextView) v.findViewById(R.id.first_name_txt_id);
        lastname = (TextView) v.findViewById(R.id.last_name_txt_id);
        mobilenumber = (TextView) v.findViewById(R.id.mobile_no_txt_id);
        mail = (TextView) v.findViewById(R.id.mail_txt_id);

        firstnamedetail = (TextView) v.findViewById(R.id.first_name_edit_id);
        lastnamedetail = (TextView) v.findViewById(R.id.last_name_edit_id);
        mobilenumberdetail = (TextView) v.findViewById(R.id.mobile_no_edit_id);
        maildetail = (TextView) v.findViewById(R.id.mail_edit_id);
    }
}
