package com.example.navoki.khanakhazana.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.navoki.khanakhazana.R;
import com.example.navoki.khanakhazana.activities.HomeActivity;
import com.example.navoki.khanakhazana.database.AppDatabase;
import com.example.navoki.khanakhazana.models.IngredientsModel;

import java.util.List;


public class IngredientWidget extends AppWidgetProvider {
  /*  public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {

        String title = HomeActivity.getRecipeName(context);
        Log.e("updateAppWidget", "" + title);

        // Construct the RemoteViews object which defines the view of out widget
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        // Instruct the widget manager to update the widget
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(context, views);
        } else {
            setRemoteAdapterV11(context, views);
        }
        *//** PendingIntent to launch the MainActivity when the widget was clicked **//*
        Intent launchMain = new Intent(context, HomeActivity.class);
        PendingIntent pendingMainIntent = PendingIntent.getActivity(context, 0, launchMain, 0);
        views.setOnClickPendingIntent(R.id.container, pendingMainIntent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }*/

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e("onUpdate", "RUn " + R.id.list + " " + R.id.name + " " + R.layout.ingredient_widget);
        for (int i = 0; i < appWidgetIds.length; ++i) {

            Intent intent = new Intent(context, ListWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(),
                    R.layout.ingredient_widget);

            rv.setRemoteAdapter(appWidgetIds[i], R.id.list, intent);

          //  rv.setEmptyView(R.id.list, R.id.txt_ingredients);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    /**
     * Set the Adapter for out widget
     **/

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.list,
                new Intent(context, ListWidgetService.class));
    }


    /**
     * Deprecated method, don't create this if you are not planning to support devices below 4.0
     **/
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.list,
                new Intent(context, ListWidgetService.class));
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

