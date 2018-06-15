package com.example.navoki.khanakhazana.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.e("ListWidgetService","REun");
        return new ListViewFactory(this.getApplicationContext(),intent);
    }
}
