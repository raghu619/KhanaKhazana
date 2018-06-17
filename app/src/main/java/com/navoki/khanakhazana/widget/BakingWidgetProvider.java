package com.navoki.khanakhazana.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.navoki.khanakhazana.R;
import com.navoki.khanakhazana.activities.HomeActivity;
import com.navoki.khanakhazana.models.IngredientsModel;
import com.navoki.khanakhazana.utils.AppConstants;
import com.navoki.khanakhazana.utils.Globle;

import java.util.List;

public class BakingWidgetProvider extends AppWidgetProvider {

    private RemoteViews mViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

            mViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
            appWidgetManager.updateAppWidget(appWidgetId, mViews);

            Intent configIntent = new Intent(context, HomeActivity.class);
            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
            mViews.setOnClickPendingIntent(R.id.img, configPendingIntent);
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
        mViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        Intent launchActivity = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchActivity, 0);
        mViews.setOnClickPendingIntent(R.id.img, pendingIntent);

        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED) ||
                action.equals(AppConstants.ACTION_DATA_UPDATE)) {
            mViews.setTextViewText(R.id.txt_ingredients, Globle.getRecipeName(context));
            List<IngredientsModel> list = Globle.getAppInstance().getIngredients(context);
            StringBuilder builder = new StringBuilder();
            for (IngredientsModel model : list)
                builder.append(model.getIngredient() + " " + model.getQuantity() + " " + model.getMeasure() + "\n");

            mViews.setTextViewText(R.id.text, builder.toString());

            //Now update all widgets
            AppWidgetManager.getInstance(context).updateAppWidget(
                    new ComponentName(context, BakingWidgetProvider.class), mViews);
        }
    }
}

