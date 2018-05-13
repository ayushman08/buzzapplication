package com.cresol.buzzapplication.util;

import com.cresol.buzzapplication.model.AllRecipeModel;
import com.cresol.buzzapplication.model.AllRestaurantModel;
import com.cresol.buzzapplication.model.LikeActivityModel;
import com.cresol.buzzapplication.model.ReadProfileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 10/6/2016.
 */
public class DataResource {
    public static List<AllRecipeModel> cacheAllRecipeData=new ArrayList<AllRecipeModel>();
    public static List<AllRestaurantModel> cacheAllRestaurantData=new ArrayList<AllRestaurantModel>();
    public static ReadProfileInfo cacheProfileInfo=new ReadProfileInfo();
    public static    List<LikeActivityModel> cacheLikeListObj=new ArrayList<LikeActivityModel>();
    public static    List<LikeActivityModel> cacheDisLikedListObj=new ArrayList<LikeActivityModel>();
}
