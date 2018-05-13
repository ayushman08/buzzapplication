package com.cresol.buzzapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.util.UserSessionManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nitesh on 9/8/2016.
 */
public class FacebookFragment extends Fragment {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    public UserSessionManager userSessionManager;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {

                    JSONObject json = response.getJSONObject();
                    try {
                        if (json != null) {
                            String email = "";
                            String name = "";
                            if (json.isNull("email") == false) {
                                //here we get the fb profile data
                                email = json.getString("email");
                                name = json.getString("name");
//                            textView.setText(email + name+ "fb");


                                //   accessTokenTracker.stopTracking();
                                //  profileTracker.stopTracking();
                                userSessionManager.AddData(name, email, "fb", getContext());
                                accessTokenTracker.stopTracking();
                                profileTracker.stopTracking();

                            }
                            Log.d("email", email);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,email,picture");
            request.setParameters(parameters);
            request.executeAsync();
            //   AccessToken accessToken = loginResult.getAccessToken();
            //   Profile profile = Profile.getCurrentProfile();
            //  displayMessage(profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {
            Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    };

    public FacebookFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        userSessionManager = new UserSessionManager(getContext());
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };


        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.facebook_button_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.fb_login_button);

//permission to take public profile data
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions("public_profile");   //By taking this,it is not giving th
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    //it will give the data
    private void displayMessage(Profile profile) {
        if (profile != null) {

            //get the data from the profile
            //  textView.setText(profile.getName() + profile.getFirstName() + profile.getName());

            //      userSessionManager.AddData(profile.getName(), "","fb");
            //    accessTokenTracker.stopTracking();
            //  profileTracker.stopTracking();

            //take
            // Intent i = new Intent(getActivity(), MyProfileActivity.class);

            //    startActivity(i);
            //((Activity) getActivity()).overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //  accessTokenTracker.stopTracking();
        //  profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();

        //   Profile profile = Profile.getCurrentProfile();
        // displayMessage(profile);
    }


}