package com.cresol.buzzapplication.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.activity.SignupActivity;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.model.ProfileInput;
import com.cresol.buzzapplication.model.ProfileOutput;
import com.cresol.buzzapplication.model.ReadProfileInfo;
import com.cresol.buzzapplication.util.DataResource;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saurabh on 10/6/2016.
 */
public class UpdateProfileFragment extends Fragment {
    TextView firstname;
    TextView lastname;
    TextView mobilenumber;
    TextView mail;
    UserSessionManager sessionManager;
    EditText firstNameEdit;
    EditText emailEdit;
    EditText lastNameEdit;
    EditText mobileEdit;
    public TextView status;
    TextView save_txt_idbtn;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.profile_fragment, container, false);


        GlobalMethods.overrideFonts(getActivity(), v);


        TextView saveInfo = (TextView) v.findViewById(R.id.save_txt_id);
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retroCallProfileUpdate();
            }
        });

        //setting dynamic height and width by taking width and height of screen
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int screen_height = outMetrics.heightPixels;
        int screen_width = outMetrics.widthPixels;

        //  class to store the value in our machine
        sessionManager = new UserSessionManager(getActivity());
        //Access TextView
        firstname = (TextView) v.findViewById(R.id.first_name_txt_id);
        lastname = (TextView) v.findViewById(R.id.last_name_txt_id);
        mobilenumber = (TextView) v.findViewById(R.id.mobile_no_txt_id);
        mail = (TextView) v.findViewById(R.id.mail_txt_id);
        emailEdit = (EditText) v.findViewById(R.id.mail_edit_id);
        firstNameEdit = (EditText) v.findViewById(R.id.first_name_edit_id);
        lastNameEdit = (EditText) v.findViewById(R.id.last_name_edit_id);
        mobileEdit = (EditText) v.findViewById(R.id.mobile_no_edit_id);
        status = (TextView) v.findViewById(R.id.status);
        save_txt_idbtn = (TextView) v.findViewById(R.id.save_txt_id);

//setting width of textViews
        // firstname.getLayoutParams().width = screen_width/2 ;
        //    lastname.getLayoutParams().width = screen_width/2 ;
        //   mobilenumber.getLayoutParams().width = screen_width/2 ;
        //   mail.getLayoutParams().width = screen_width/2 ;

        status.setText("Edit");

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        SetIcons();
        if (GlobalMethods.isNetworkAvailable(getActivity())) {
            if (DataResource.cacheProfileInfo != null) {
                bindData(DataResource.cacheProfileInfo.getFirstName(), DataResource.cacheProfileInfo.getLastName(), DataResource.cacheProfileInfo.getEmail(), DataResource.cacheProfileInfo.getMobile());

            } else {
                retroCallProfileData();
            }
        }


        return v;
    }



    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }


    //set icons left to the TextView
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

    //Call to the server for getting profile data
    public void retroCallProfileData() {
         if (sessionManager.isUserLoggedIn()) {
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


          }
        else{
             GlobalMethods.ShowLoginMessage(getActivity());
         }

    }

    //After editing in profile data we update data to the server
    public void retroCallProfileUpdate() {
         if (sessionManager.isUserLoggedIn()){
        if (GlobalMethods.isNetworkAvailable(getActivity())) {

            if(!firstNameEdit.getText().toString().isEmpty()&&!emailEdit.getText().toString().isEmpty()&&!lastname.getText().toString().isEmpty()&&!mobileEdit.getText().toString().isEmpty())
             {
                 if(mobileEdit.getText().toString().length()==10){
                     api buzzApi = GlobalMethods.GetBuzzdAPI(getActivity());

                GlobalMethods.ShowProgressBar(getActivity());

                ProfileInput profileInput = new ProfileInput();
                profileInput.setCustomerID(13);
                profileInput.setFirstName(firstNameEdit.getText().toString());
                profileInput.setEmailId(emailEdit.getText().toString());
                profileInput.setLastName(lastname.getText().toString());
                profileInput.setMobile(mobileEdit.getText().toString());


                Call<ProfileOutput> profileResponse = buzzApi.updateProfile(profileInput);
                profileResponse.enqueue(new Callback<ProfileOutput>() {
                    @Override
                    public void onResponse(Call<ProfileOutput> call, Response<ProfileOutput> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (!response.body().getStatus().equals("false")) {
                                DataResource.cacheProfileInfo = response.body().getProfileData();
                                Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
                            }
                        }
                        Show();
                        GlobalMethods.HideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<ProfileOutput> call, Throwable t) {
                        GlobalMethods.HideProgressBar();
                    }
                });
             }
            else{
                Toast.makeText(getActivity(),"Please write valid 10 Digital mobile Number.",Toast.LENGTH_LONG).show();
            }
            }
            else{
                Toast.makeText(getActivity(),"Please all detail properly.",Toast.LENGTH_LONG).show();
            }
        }

    }
    else{
        GlobalMethods.ShowLoginMessage(getActivity());
    }


    }

    public void Show() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ShowProfileFragment myprofilefrag = new ShowProfileFragment();
        //Saving data to server
        fragmentTransaction.replace(R.id.profile_container, myprofilefrag);
        fragmentTransaction.commit();
    }

    public void bindData(String firstname, String lastname, String email, String mobile) {
        emailEdit.setText(email);
        firstNameEdit.setText(firstname);
        lastNameEdit.setText(lastname);
        mobileEdit.setText(mobile);
        // CheckEdit();
    }

    public void CheckEdit() {
        if (status.getText().toString().equals("Edit")) {
            emailEdit.setEnabled(true);
            firstNameEdit.setEnabled(true);
            mobileEdit.setEnabled(true);
            status.setEnabled(true);
            status.setText("View");
            save_txt_idbtn.setVisibility(View.VISIBLE);
        } else {
            emailEdit.setEnabled(false);
            firstNameEdit.setEnabled(false);
            mobileEdit.setEnabled(false);
            status.setEnabled(false);
            status.setText("Edit");
            save_txt_idbtn.setVisibility(View.INVISIBLE);

        }
    }

}
