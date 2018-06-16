package com.example.navoki.khanakhazana.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.navoki.khanakhazana.R;
import com.example.navoki.khanakhazana.activities.HomeActivity;
import com.example.navoki.khanakhazana.models.IngredientsModel;
import com.example.navoki.khanakhazana.utils.Globle;

import java.util.List;

public class BakingWidgetProvider extends AppWidgetProvider {

    private RemoteViews mViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            mViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
            appWidgetManager.updateAppWidget(appWidgetId, mViews);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        final String action = intent.getAction();

        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED)) {
            mViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
            mViews.setTextViewText(R.id.txt_ingredients, Globle.getRecipeName(context));
            List<IngredientsModel> list = Globle.getAppInstance().getIngredients(context);
            StringBuilder builder = new StringBuilder();
            for (IngredientsModel model : list)
                builder.append(model.getIngredient() + " " + model.getQuantity() + " " + model.getMeasure() + "\n");

            mViews.setTextViewText(R.id.list, builder.toString());

            Intent launchActivity = new Intent(context, HomeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchActivity, 0);
            mViews.setOnClickPendingIntent(R.id.container, pendingIntent);

            //Now update all widgets
            AppWidgetManager.getInstance(context).updateAppWidget(
                    new ComponentName(context, BakingWidgetProvider.class), mViews);
        }
    }
}