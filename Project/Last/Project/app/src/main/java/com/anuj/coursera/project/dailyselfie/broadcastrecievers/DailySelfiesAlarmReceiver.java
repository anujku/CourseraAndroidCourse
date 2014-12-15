package com.anuj.coursera.project.dailyselfie.broadcastrecievers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.anuj.coursera.project.dailyselfie.R;
import com.anuj.coursera.project.dailyselfie.activities.DailySelfiesActivity;


/**
 * Created by akulkarni on 029-29-11-14.
 */

public class DailySelfiesAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Prepare intent which is triggered if the notification is selected
        Intent selfiesActivityIntent = new Intent(context, DailySelfiesActivity.class);
        selfiesActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, selfiesActivityIntent, 0);

        // Build the notification
        Notification notification = new Notification.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.notification_message))
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);
    }
}