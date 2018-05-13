package com.cresol.buzzapplication.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cresol.buzzapplication.R;
import com.cresol.buzzapplication.activity.RecipeMatchedActivity;
import com.cresol.buzzapplication.adapter.IngredientsAdapter;
import com.cresol.buzzapplication.adapter.RecipeHowToMakeAdapter;
import com.cresol.buzzapplication.adapter.RecipeNutritionAdapter;
import com.cresol.buzzapplication.model.RecipeNutritionModel;
import com.cresol.buzzapplication.util.GlobalMethods;
import com.cresol.buzzapplication.util.UserSessionManager;
import com.dgmltn.shareeverywhere.ShareView;
import com.eugeneek.smilebar.SmileBar;

import org.lucasr.twowayview.TwoWayView;

import java.util.List;

/**
 * Created by Saurabh on 8/4/2016.
 */
public class RecipeFragment extends Fragment {

    String howToMake = "";
    ListView ingredientListView;
    ListView howToMakeListView;
    RecipeNutritionAdapter recipeNutritionAdapter;
    TwoWayView listView;
    TextView headerTitle;
    TextView howToMakeHeader;
    TextView nutritionHeader;
    TextView ratingHeader;
    ScrollView scrollView;
    private Intent[] mSharedIntents;
    SmileBar smileBar;
    UserSessionManager sessionManager;
    int a=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.recipe_fragment, container, false);

        GlobalMethods.overrideFonts(getActivity(), v);


        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Initilize(view);
        FontChange();

        headerTitle.setText("Ingredients");

          /* //for the social sharing
        mSharedIntents = new Intent[]{getEmailIntent(), getTxtIntent(), getImageIntent()};
        // Since all the ShareView's in this activity share the same
        // ActivityChooserModel, setShareIntent() just once, on one of them
        // is sufficient to initialize them all.
        ShareView shareView = (ShareView) view.findViewById(R.id.share_view);
        shareView.setShareIntent(mSharedIntents);  */
        //upto this
        ingredientListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                // v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        smileBar.setOnRatingSliderChangeListener(new SmileBar.OnRatingSliderChangeListener() {
            @Override
            public void onPendingRating(int rating) {
                Log.i("onPendingRating", "" + rating);

            }

            @Override
            public void onFinalRating(int rating) {
                a++;
                Log.i("onFinalRating", "" + rating);
                if(a!=1) {
                    if (!sessionManager.isUserLoggedIn()) {
                        GlobalMethods.ShowLoginMessage(getActivity());
                    }
                }
            }

            @Override
            public void onCancelRating() {
                Log.i("onCancelRating", "cancel");
            }
        });

        smileBar.setRating(RecipeMatchedActivity.userRating);
        if(RecipeMatchedActivity.ingredientData!=null){
        BindIngredientData(RecipeMatchedActivity.ingredientData);
        }
        if(RecipeMatchedActivity.howToMakeData!=null) {
            BindHowToMakeData(RecipeMatchedActivity.howToMakeData);
        }
        if(RecipeMatchedActivity.nutritiondata!=null) {
            BindNutritionData(RecipeMatchedActivity.nutritiondata);
        }
        super.onViewCreated(view, savedInstanceState);
    }


    public void BindIngredientData(List<String> obj) {


        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getActivity(), R.layout.ingredient_child_list, obj);
        ingredientListView.setAdapter(ingredientsAdapter);
        //Remove scrolling of listview
        setListViewHeightBasedOnChildren(ingredientListView);


       scrollView.setSmoothScrollingEnabled(true);

        //These below lines for indentifying the scrolling even and call changing header text
        final ViewTreeObserver.OnScrollChangedListener onScrollChangedListener = new
                ViewTreeObserver.OnScrollChangedListener() {

                    @Override
                    public void onScrollChanged() {
                        //do stuff here
                      //  ChangingHeaderText();
                    }
                };

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            private ViewTreeObserver observer;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (observer == null) {
                    observer = scrollView.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                } else if (!observer.isAlive()) {
                    observer.removeOnScrollChangedListener(onScrollChangedListener);
                    observer = scrollView.getViewTreeObserver();
                    observer.addOnScrollChangedListener(onScrollChangedListener);
                }

                return false;
            }
        });

    }

    public void Initilize(View view) {
        sessionManager=new UserSessionManager(getActivity());
        headerTitle = (TextView) view.findViewById(R.id.Header);
        nutritionHeader = (TextView) view.findViewById(R.id.nutritionHeader);
        howToMakeHeader = (TextView) view.findViewById(R.id.howToMakeHeader);
        ratingHeader = (TextView) view.findViewById(R.id.ratingTextView);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        ingredientListView = (ListView) view.findViewById(R.id.listViewIngredients);
        howToMakeListView = (ListView) view.findViewById(R.id.listViewHowToMake);
        listView = (TwoWayView) view.findViewById(R.id.nutritionListView);
        smileBar = (SmileBar) view.findViewById(R.id.starBar);
    }

    public void FontChange() {
        Typeface ralewayBoldFont = Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Bold.ttf");
        headerTitle.setTypeface(ralewayBoldFont);
        nutritionHeader.setTypeface(ralewayBoldFont);
        howToMakeHeader.setTypeface(ralewayBoldFont);
        ratingHeader.setTypeface(ralewayBoldFont);

    }
     /*     //changing header on scrolling
       public void ChangingHeaderText() {

        int nutrition[] = new int[2];
        int header[] = new int[2];
        int howToMake[] = new int[2];

        nutritionHeader.getLocationInWindow(nutrition);
        headerTitle.getLocationInWindow(header);
        howToMakeHeader.getLocationInWindow(howToMake);

        int nutritionY = nutrition[1];
        int HeaderY = header[1];
        int howToMakeY = howToMake[1];

        if (HeaderY > howToMakeY - 10) {
            howToMakeHeader.setText("");
            headerTitle.setText("How To Make");
            nutritionHeader.setText("Nutrition");
        } else if (HeaderY > nutritionY + 44) {
            nutritionHeader.setText("");
            headerTitle.setText("Nutrition");
            howToMakeHeader.setText("How To Make");
        } else {
            headerTitle.setText("Ingredients");
            howToMakeHeader.setText("How To Make");
            nutritionHeader.setText("Nutrition");
        }


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

    }    */

    public void BindHowToMakeData(List<String> obj) {


        RecipeHowToMakeAdapter recipeHowToMakeAdapter = new RecipeHowToMakeAdapter(getActivity(), R.layout.ingredient_howtomake_child_list, obj);
        howToMakeListView.setAdapter(recipeHowToMakeAdapter);
        setListViewHeightBasedOnChildren(howToMakeListView);
    }

//remove scrolling in listview
        public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ActionBar.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void BindNutritionData(List<RecipeNutritionModel> obj) {
        //These 3 lines for testing
        RecipeNutritionModel data = new RecipeNutritionModel();
        data.setNutritionName("Testing");
        data.setQuantity("80");
        obj.add(data);
        recipeNutritionAdapter = new RecipeNutritionAdapter(getActivity(), R.layout.recipe_nutrition_child_list, obj);
        listView.setAdapter(recipeNutritionAdapter);
    }
//part of social networking links
    private Intent getEmailIntent() {
        String to = "foo@bar.com";
        String subject = "yo dude";
        String body = "Here wil come the message";

        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        String[] toArr = new String[]{to};
        intent.putExtra(Intent.EXTRA_EMAIL, toArr);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        return intent;
    }

    private Intent getTxtIntent() {
        String subject = "share subject";
        String text = "here's some share text";

        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        return intent;
    }

    private Intent getImageIntent() {
        Uri imageUri = getRandomImageUri();

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("recipeImage/jpeg");
        return shareIntent;
    }

    // Get the uri to a random recipeImage in the photo gallery
    private Uri getRandomImageUri() {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID};
        Cursor cursor = null;
        try {
            cursor = getActivity().getContentResolver().query(mediaUri, projection, null, null, null);
            cursor.moveToPosition((int) (Math.random() * cursor.getCount()));
            String id = cursor.getString(0);
            Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            return uri;
        } catch (Exception e) {
            return null;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
}
