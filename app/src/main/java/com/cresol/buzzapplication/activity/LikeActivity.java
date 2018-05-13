package com.cresol.buzzapplication.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.adapter.LikeIngredientsListOneAdapter;
import com.cresol.buzzapplication.adapter.LikeIngredientsListTwoAdapter;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.drawer.DrawerActivity;
import com.cresol.buzzapplication.fragment.Dialog_Fragment_Add_Like_Ingredients;
import com.cresol.buzzapplication.model.AddIngredientModelInput;
import com.cresol.buzzapplication.model.AddIngredientModelOutput;
import com.cresol.buzzapplication.model.Ingredient_InterfaceDeleteData;
import com.cresol.buzzapplication.model.LikeActivityModel;
import com.cresol.buzzapplication.model.TransferDataDialogActivity;
import com.cresol.buzzapplication.util.DataResource;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;
import com.daimajia.swipe.util.Attributes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Saurabh on 7/29/2016.
 */
public class LikeActivity extends DrawerActivity implements Ingredient_InterfaceDeleteData, TransferDataDialogActivity {

     //list one adapter
    LikeIngredientsListOneAdapter LikeAdapter;
    //list Two adapter
    LikeIngredientsListTwoAdapter disLikedAdapter;
    //this we are taking for saving the data temporary to know how popup open
    //static by which we can access without creating object
    public static int okClickType;
    api buzzdApi;
    ImageView add_img_list1_ref;
    ImageView add_img_list2_ref;
    ListView likeList;
    ListView disLikeList;
    UserSessionManager sessionManager;
    private Context mContext = this;
    LinearLayout add_ingredient_Layout;
    //This is for checking edit or show screen
    String type;
    TextView switch_screen;
    UserSessionManager userSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflating activity to view and adding the view in drawer view


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_liking_status, null, false);
        drawer.addView(contentView, 0);



        GlobalMethods.overrideFonts(this, contentView);
        type = "Edit";


//on click in add recipeImage of first list ,dialog box should open,which is a dialog Fragment


//on click in add recipeImage of second list ,dialog box should open,which is a dialog Fragment

        initializeControls();

        if(userSessionManager.isUserLoggedIn()) {
            addingEventHandlers();

            if (DataResource.cacheLikeListObj != null && DataResource.cacheLikeListObj.size() != 0) {
                LikeAdapter.notifyDataSetChanged();
            } else {
                RetroCallForGettingLikeIngredient();
            }
            if (DataResource.cacheDisLikedListObj != null && DataResource.cacheDisLikedListObj.size() != 0) {
                disLikedAdapter.notifyDataSetChanged();
            } else {
                RetroCallForGettingDisLikeIngredient();
            }

            //  SwitchMethod();

        }
        else{
            GlobalMethods.ShowLoginMessage(this);
        }
    }

    public void initializeControls() {
        sessionManager = new UserSessionManager(this);
        buzzdApi = GlobalMethods.GetBuzzdAPI(this);


        likeList = (ListView) findViewById(R.id.listView3);
        disLikeList = (ListView) findViewById(R.id.listView4);
        add_img_list1_ref = (ImageView) findViewById(R.id.add_img_list_one);
        add_img_list2_ref = (ImageView) findViewById(R.id.add_image_list_two);

        add_ingredient_Layout = (LinearLayout) findViewById(R.id.add_ingredient_Layout);
        switch_screen = (TextView) findViewById(R.id.switch_screen);
         userSessionManager=new UserSessionManager(this);
        TextView addIngredientTextView = (TextView) findViewById(R.id.add_ingredient_list_one);
        TextView addDisLikeIngredientTextView = (TextView) findViewById(R.id.add_ingredient_list_two);
        Typeface ralewayBoldFont = Typeface.createFromAsset(getAssets(), "Raleway-Bold.ttf");
        addIngredientTextView.setTypeface(ralewayBoldFont);
        addDisLikeIngredientTextView.setTypeface(ralewayBoldFont);
    }

    public void addingEventHandlers() {
        add_img_list1_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikeActivity.okClickType = 1;
                FragmentManager fm = getFragmentManager();
                Dialog_Fragment_Add_Like_Ingredients dialogFragment = new Dialog_Fragment_Add_Like_Ingredients();
                dialogFragment.show(fm, "Add Ingredients Fragment");
            }
        });

        add_img_list2_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //it will give idea which list to access to  add data
                LikeActivity.okClickType = 2;
                //By fragment manager class,show dialog box fragment
                FragmentManager fm = getFragmentManager();
                //Create Dialog Fragment Object
                Dialog_Fragment_Add_Like_Ingredients dialogFragment = new Dialog_Fragment_Add_Like_Ingredients();

                dialogFragment.show(fm, "Add Ingredients Fragment");
            }
        });


        LikeAdapter = new LikeIngredientsListOneAdapter(this, type, DataResource.cacheLikeListObj);


        likeList.setAdapter(LikeAdapter);

        LikeAdapter.setMode(Attributes.Mode.Single);
        likeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //       ((SwipeLayout)( likeList.getChildAt(position -  likeList.getFirstVisiblePosition()))).open(true);
            }
        });


        disLikedAdapter = new LikeIngredientsListTwoAdapter(this, type, DataResource.cacheDisLikedListObj);

        disLikeList.setAdapter(disLikedAdapter);

        disLikedAdapter.setMode(Attributes.Mode.Single);
        disLikeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  ((SwipeLayout) (disLikeList.getChildAt(position - disLikeList.getFirstVisiblePosition()))).open(true);
            }
        });


    }

    public void SwitchMethod() {
        add_ingredient_Layout.setVisibility(View.GONE);
        switch_screen.setText("Edit");

        switch_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = switch_screen.getText().toString();
                if (switch_screen.getText().toString().equals("Edit")) {
                    type = "Edit";
                    add_ingredient_Layout.setVisibility(View.VISIBLE);
                    switch_screen.setText("Show");
                    addingEventHandlers();
                } else {
                    type = "Show";
                    add_ingredient_Layout.setVisibility(View.GONE);
                    switch_screen.setText("Edit");
                    addingEventHandlers();
                }
            }
        });
    }

       //For Removing
    @Override
      public void adapterMethodIngredientsPosition(int position, int type) {   //1 Means add ingredient 2 means disLike Ingredient
        if (type == 1) {
            LikeActivityModel obj = DataResource.cacheLikeListObj.get(position);
            RetroCallForRemovingLikeIngredient(obj.getIngredientName());

        } else if (type == 2) {

            LikeActivityModel obj = DataResource.cacheDisLikedListObj.get(position);
            RetroCallForRemovingDisLikeIngredient(obj.getIngredientName());
        }
    }

    //Adding Like Ingredient name
    @Override
    public void transferData(String ingredientName) {
        if (ingredientName.isEmpty()) {
            Toast.makeText(this, "Please enter Ingredients", Toast.LENGTH_LONG).show();
        } else {
            RetroCallForAddingLikeIngredient(ingredientName);
        }
    }

    //Adding disLike Ingredient Name
    @Override
    public void transferDataSecondList(String ingredientName) {
        if (ingredientName.isEmpty()) {
            Toast.makeText(this, "Please enter Ingredients", Toast.LENGTH_LONG).show();
        } else {
            RetroCallForAddingDisLikeIngredient(ingredientName);
        }
    }

    //Not use in this Activity,
    @Override
    public void transferAgeData(String editTxtAgeData) {

    }


     public void RetroCallForAddingLikeIngredient(String ingredientName) {
        if (GlobalMethods.isNetworkAvailable(this)) {
            if(userSessionManager.isUserLoggedIn()) {
                GlobalMethods.ShowProgressBar(this);
                AddIngredientModelInput obj = new AddIngredientModelInput();
                obj.UserId = sessionManager.GetUserId();
                 obj.IngredientName = ingredientName;

                Call<AddIngredientModelOutput> outputCall = buzzdApi.AddLikeIngredient(obj);

                outputCall.enqueue(new Callback<AddIngredientModelOutput>() {
                    @Override
                    public void onResponse(Call<AddIngredientModelOutput> call, Response<AddIngredientModelOutput> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            //likeListObj.clear();
                            if (DataResource.cacheLikeListObj != null) {
                                DataResource.cacheLikeListObj.clear();
                            }
                            for (String ingredientName : response.body().IngredientName) {

                                LikeActivityModel listOneData = new LikeActivityModel(ingredientName, R.drawable.cross);
                                //Adding data to listOne obj
                                DataResource.cacheLikeListObj.add(listOneData);
                            }

                            // to refresh list
                            LikeAdapter.notifyDataSetChanged();
                        }
                        GlobalMethods.HideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<AddIngredientModelOutput> call, Throwable t) {
                        GlobalMethods.HideProgressBar();
                    }
                });
            }
            else{Toast.makeText(this,"Please login first",Toast.LENGTH_LONG).show();  }
        }
    }


    public void RetroCallForAddingDisLikeIngredient(String ingredientName) {

        //first check for the Network
        if (GlobalMethods.isNetworkAvailable(this)) {
            //check for the Login,if not login than login first
            if(userSessionManager.isUserLoggedIn()){

            GlobalMethods.ShowProgressBar(this);

            AddIngredientModelInput obj = new AddIngredientModelInput();
            obj.UserId = sessionManager.GetUserId();
            obj.IngredientName = ingredientName;

            Call<AddIngredientModelOutput> outputCall = buzzdApi.AddDisLikeIngredient(obj);

            outputCall.enqueue(new Callback<AddIngredientModelOutput>() {
                @Override
                public void onResponse(Call<AddIngredientModelOutput> call, Response<AddIngredientModelOutput> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (DataResource.cacheDisLikedListObj != null) {
                            DataResource.cacheDisLikedListObj.clear();
                        }

                        for (String ingredientName : response.body().IngredientName) {

                            LikeActivityModel listOneData = new LikeActivityModel(ingredientName, R.drawable.cross);
                            //Adding data to listOne obj
                            DataResource.cacheDisLikedListObj.add(listOneData);
                        }

                        // to refresh list
                        disLikedAdapter.notifyDataSetChanged();
                    }
                    GlobalMethods.HideProgressBar();
                }

                @Override
                public void onFailure(Call<AddIngredientModelOutput> call, Throwable t) {
                    GlobalMethods.HideProgressBar();
                }
            });
        }
            else{Toast.makeText(this,"Please login first",Toast.LENGTH_LONG).show();  }
    }

    }

    public void RetroCallForRemovingLikeIngredient(String ingredientName) {

        if (GlobalMethods.isNetworkAvailable(this)) {
            GlobalMethods.ShowProgressBar(this);
            AddIngredientModelInput obj = new AddIngredientModelInput();
            obj.UserId = sessionManager.GetUserId();
          //  obj.UserId = 3;
            obj.IngredientName = ingredientName;
            Call<AddIngredientModelOutput> outputCall = buzzdApi.RemoveLikeIngredient(obj);


            outputCall.enqueue(new Callback<AddIngredientModelOutput>() {
                @Override
                public void onResponse(Call<AddIngredientModelOutput> call, Response<AddIngredientModelOutput> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        if (DataResource.cacheLikeListObj != null) {
                            DataResource.cacheLikeListObj.clear();
                        }
                        if (response.body().IngredientName != null) {
                            for (String ingredientName : response.body().IngredientName) {

                                LikeActivityModel listOneData = new LikeActivityModel(ingredientName, R.drawable.cross);
                                //Adding data to listOne obj
                                DataResource.cacheLikeListObj.add(listOneData);
                            }

                            // to refresh list

                        }
                        LikeAdapter.notifyDataSetChanged();
                    }
                    GlobalMethods.HideProgressBar();
                }

                @Override
                public void onFailure(Call<AddIngredientModelOutput> call, Throwable t) {
                    GlobalMethods.HideProgressBar();
                }
            });


        }
    }

    public void RetroCallForRemovingDisLikeIngredient(String ingredientName) {
        if (GlobalMethods.isNetworkAvailable(this)) {
            GlobalMethods.ShowProgressBar(this);

            AddIngredientModelInput obj = new AddIngredientModelInput();
            //obj.UserId = sessionManager.GetUserId();
            obj.UserId = 3;
            obj.IngredientName = ingredientName;

            Call<AddIngredientModelOutput> outputCall = buzzdApi.RemoveDisLikeIngredient(obj);
            outputCall.enqueue(new Callback<AddIngredientModelOutput>() {
                @Override
                public void onResponse(Call<AddIngredientModelOutput> call, Response<AddIngredientModelOutput> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (DataResource.cacheDisLikedListObj != null) {
                            DataResource.cacheDisLikedListObj.clear();
                        }
                        if(response.body().IngredientName!=null) {
                            for (String ingredientName : response.body().IngredientName) {
                                //class for customise data type i.e, String and Integer
                                LikeActivityModel listOneData = new LikeActivityModel(ingredientName, R.drawable.cross);
                                //Adding data to listOne obj
                                DataResource.cacheDisLikedListObj.add(listOneData);
                            }
                        }
                        // to refresh list
                        disLikedAdapter.notifyDataSetChanged();
                    }
                    GlobalMethods.HideProgressBar();
                }

                @Override
                public void onFailure(Call<AddIngredientModelOutput> call, Throwable t) {
                    GlobalMethods.HideProgressBar();
                }
            });

        }


    }


    public void RetroCallForGettingLikeIngredient() {
        if (GlobalMethods.isNetworkAvailable(this)) {
            // GlobalMethods.ShowProgressBar(this);

            Call<AddIngredientModelOutput> outputCall = buzzdApi.GetLikeIngredient(sessionManager.GetUserId());

            GlobalMethods.ShowProgressBar(this);
            outputCall.enqueue(new Callback<AddIngredientModelOutput>() {
                @Override
                public void onResponse(Call<AddIngredientModelOutput> call, Response<AddIngredientModelOutput> response) {
                    if (response.isSuccessful() & response.body() != null) {
                        if (response.body().IngredientName != null) {
                            if (DataResource.cacheLikeListObj != null) {
                                DataResource.cacheLikeListObj.clear();
                            }

                            for (String ingredientName : response.body().IngredientName) {

                                LikeActivityModel listOneData = new LikeActivityModel(ingredientName, R.drawable.cross);
                                //Adding data to listOne obj
                                DataResource.cacheLikeListObj.add(listOneData);
                            }

                            // to refresh list
                            LikeAdapter.notifyDataSetChanged();
                        }
                    }
                    GlobalMethods.HideProgressBar();
                }

                @Override
                public void onFailure(Call<AddIngredientModelOutput> call, Throwable t) {
                    GlobalMethods.HideProgressBar();
                }
            });
        }
    }

    public void RetroCallForGettingDisLikeIngredient() {
        if (GlobalMethods.isNetworkAvailable(this)) {
            // GlobalMethods.ShowProgressBar(this);

            Call<AddIngredientModelOutput> outputCall = buzzdApi.GetDisLikeIngredient(sessionManager.GetUserId());

            outputCall.enqueue(new Callback<AddIngredientModelOutput>() {
                @Override
                public void onResponse(Call<AddIngredientModelOutput> call, Response<AddIngredientModelOutput> response) {
                    if (response.isSuccessful() & response.body() != null) {

                        if (response.body().IngredientName != null) {
                            if (DataResource.cacheDisLikedListObj != null) {
                                DataResource.cacheDisLikedListObj.clear();
                            }
                            //here we gwt the ingredient name by retrofit library
                            for (String ingredientName : response.body().IngredientName) {

                                LikeActivityModel listOneData = new LikeActivityModel(ingredientName, R.drawable.cross);
                                //Adding data to listOne obj
                                DataResource.cacheDisLikedListObj.add(listOneData);
                            }
                        }
                        // to refresh list
                        disLikedAdapter.notifyDataSetChanged();
                    }
                    //  GlobalMethods.HideProgressBar();`````
                }

                @Override
                public void onFailure(Call<AddIngredientModelOutput> call, Throwable t) {
                    //   GlobalMethods.HideProgressBar();
                }
            });
        }
    }

}