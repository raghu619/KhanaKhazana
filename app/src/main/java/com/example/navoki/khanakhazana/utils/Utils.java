package com.example.navoki.khanakhazana.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.example.navoki.khanakhazana.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Shivam Srivastava on 6/5/2018.
 */
public final class Utils {

    public static void showError(Exception error, Context context) {
        String message = null;
        if (error instanceof NetworkError) {
            message = context.getString(R.string.error_network);
        } else if (error instanceof ServerError) {
            message = context.getString(R.string.error_server);
        } else if (error instanceof AuthFailureError) {
            message = context.getString(R.string.error_auth);
        } else if (error instanceof ParseError) {
            message = context.getString(R.string.error_parse);
        } else if (error instanceof TimeoutError) {
            message = context.getString(R.string.error_timeout);
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Animation with
     * Exit current Activity- SlideOut to Left
     * Entry new Activity- SlideIn from Right
     *
     * @param context
     */
    public static void finishEntryAnimation(Context context, Intent intent) {
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_slide_in_from_right, R.anim.anim_slide_out_to_left);
    }

    //Check internet connection
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connectivityManager =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null &&
                (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected() ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected());
    }

    public static void scaleDown(Bitmap realImage, File file) {
        int maxSize = 400;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        int width = realImage.getWidth();
        int height = realImage.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        realImage = Bitmap.createScaledBitmap(realImage, width, height, true);

        try {
            FileOutputStream fOut = new FileOutputStream(file);
            realImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!realImage.isRecycled())
            realImage.recycle();
    }
}
