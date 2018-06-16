package com.example.navoki.khanakhazana.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.example.navoki.khanakhazana.models.IngredientsModel;
import com.example.navoki.khanakhazana.models.RecipesModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Globle extends Application {

    private RequestQueue mRequestQueue;
    private static Globle ourInstance;
    private static RecipesModel recipesModel;

    public static Globle getAppInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = (Globle) getApplicationContext();
    }

    private synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ourInstance, new HurlStack());
        }
        return mRequestQueue;
    }

    public synchronized <T> void addToRequestQueue(Request<T> req) {
        req.setTag("data");
        req.setShouldCache(false);
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public RecipesModel getRecipesModel() {
        return recipesModel;
    }

    public void setRecipesModel(RecipesModel recipesModel) {
        Globle.recipesModel = recipesModel;
    }

    public void saveIngredients(Context context, List<IngredientsModel> list) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<IngredientsModel>>() {
        }.getType();
        String res = gson.toJson(list, listType);
        SharedPreferences.Editor prefs = context.getSharedPreferences(AppConstants.SP_NAME, 0).edit();
        prefs.putString(AppConstants.SP_INGREDIENTS, res);
        prefs.commit();
    }

    public void saveRecipeName(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(AppConstants.SP_NAME, 0);
        prefs.edit().putString(AppConstants.SP_RECIPE_NAME, name).commit();
    }

    public static String getRecipeName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(AppConstants.SP_NAME, 0);
        return prefs.getString(AppConstants.SP_RECIPE_NAME, AppConstants.NOT_AVAILABLE);
    }

    public List<IngredientsModel> getIngredients(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(AppConstants.SP_NAME, 0);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<IngredientsModel>>() {
        }.getType();

        String res = prefs.getString(AppConstants.SP_INGREDIENTS, AppConstants.NOT_AVAILABLE);
        if (res.equals(AppConstants.NOT_AVAILABLE))
            return new ArrayList<>();
        else
            return gson.fromJson(prefs.getString(AppConstants.SP_INGREDIENTS, AppConstants.NOT_AVAILABLE), listType);
    }
}
