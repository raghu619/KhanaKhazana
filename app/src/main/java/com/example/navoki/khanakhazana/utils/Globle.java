package com.example.navoki.khanakhazana.utils;

import android.app.Application;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.example.navoki.khanakhazana.models.RecipesModel;
import com.google.android.exoplayer2.SimpleExoPlayer;


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

}
