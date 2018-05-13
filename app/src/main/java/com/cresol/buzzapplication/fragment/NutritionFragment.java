package com.cresol.buzzapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.activity.RecipeMatchedActivity;
import com.cresol.buzzapplication.adapter.RecipeNutritionAdapter;
import com.cresol.buzzapplication.api.api;
import com.cresol.buzzapplication.model.RecipeNutritionModel;
import com.cresol.buzzapplication.util.GlobalMethods;

import org.lucasr.twowayview.TwoWayView;

import java.util.List;

/**
 * Created by Saurabh on 8/4/2016.
 */
public class NutritionFragment extends Fragment {

    api buzzdApi;
    TwoWayView listView;
    RecipeNutritionAdapter recipeNutritionAdapter;
    int recipeId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String recipeIdobj = getArguments().getString("RecipeId");
        if (!recipeIdobj.isEmpty()) {
            recipeId = Integer.valueOf(recipeIdobj);
        }

        View v = inflater.inflate(R.layout.nutrition_fragment, container, false);

        GlobalMethods.overrideFonts(getActivity(), v);
        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buzzdApi = GlobalMethods.GetBuzzdAPI(getActivity());
        listView = (TwoWayView) view.findViewById(R.id.nutritionListView);
        if (GlobalMethods.isNetworkAvailable(getActivity())) {
            //  RetroCallForNutrition();
            BindData(RecipeMatchedActivity.nutritiondata);
        }
        super.onViewCreated(view, savedInstanceState);
    }


    public void BindData(List<RecipeNutritionModel> obj) {

        recipeNutritionAdapter = new RecipeNutritionAdapter(getActivity(), R.layout.recipe_nutrition_child_list, obj);
        listView.setAdapter(recipeNutritionAdapter);
    }


}