package com.example.widgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MyDemoWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //access the time of device to update on widget screen
        String timeString = DateFormat.getTimeInstance(DateFormat.FULL).format(new Date());

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.my_demo_widget);

            // code to open MainActivity on Button click by user over app widget
            Intent myintent = new Intent(context, MainActivity.class);
            PendingIntent pendingintent1 = PendingIntent.getActivity(context,0,myintent,0);
            views.setOnClickPendingIntent(R.id.openActivityBtn, pendingintent1);


            views.setTextViewText(R.id.appwidget_text, timeString); // only after 30 min
            Toast.makeText(context, "Widget Update time: "+timeString+ " ID: "+appWidgetId, Toast.LENGTH_SHORT).show();
            Log.d("widgetupdate1","Widget Update time: "+timeString+ " ID: "+appWidgetId);


            //Create an Intent with the AppWidgetManager.ACTION_APPWIDGET_UPDATE action//
            Intent intentUpdate = new Intent(context, MyDemoWidget.class);
            intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

            //Update the current widget instance only, by creating an array that contains the widget’s unique ID//
            int[] idArray = new int[]{appWidgetId};
            intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

            //Wrap the intent as a PendingIntent, using PendingIntent.getBroadcast()//
            PendingIntent pendingUpdate = PendingIntent.getBroadcast(context, appWidgetId, intentUpdate,PendingIntent.FLAG_UPDATE_CURRENT);

            //Send the pending intent in response to the user tapping the Update Value button//
            views.setOnClickPendingIntent(R.id.UpdateValueBtn, pendingUpdate);
            //views.setTextViewText(R.id.appwidget_text, timeString);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
@Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {

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

   }