package com.example.navoki.khanakhazana.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.navoki.khanakhazana.R;
import com.example.navoki.khanakhazana.activities.HomeActivity;
import com.example.navoki.khanakhazana.models.IngredientsModel;

import java.util.List;



public class ListViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<IngredientsModel> list;

    public ListViewFactory(Context context,Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        getIngredientsList();

    }

    @Override
    public void onDataSetChanged() {

        getIngredientsList();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.e("COUNT",list.size()+" ");
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_ingredient);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(list.get(position).getIngredient());
        stringBuilder.append(context.getString(R.string.colon));
        stringBuilder.append(list.get(position).getQuantity());
        stringBuilder.append(context.getString(R.string.space));
        stringBuilder.append(list.get(position).getMeasure());
        views.setTextViewText(R.id.name, stringBuilder.toString());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void getIngredientsList() {
        list = HomeActivity.getIngredients(context);
        Log.e("Lsit size",list.size()+"");

    }
}
