package com.arimaclanka.notificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import java.util.Date;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PRIVATE;

/**
 * Created by Thilina on 6/15/2018.
 */

public class NotificationHelper extends ContextWrapper {

    private NotificationManager mNotificationManager;
    public static final String DEFAULT_CHANNEL = "Public";
    private Bundle bundle;

    public NotificationHelper(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_desc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(
                    DEFAULT_CHANNEL,
                    name,
                    importance);
            channel.setDescription(description);
            channel.setLightColor(Color.CYAN);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 500, 200, 500});
            getNotificationManager().createNotificationChannel(channel);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotificationBuilderOreo(String title, String body, int count) {
        return new Notification.Builder(getApplicationContext(), DEFAULT_CHANNEL)
                .setSmallIcon(getSmallIcon())
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setNumber(2)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setSound(getSound())
                .setContentIntent(getPendingIntent())
                ;
        //  .setTimeoutAfter(5000)   .setNumber(count)  .setNumber(count)
    }

    public NotificationCompat.Builder getNotificationBuilderPre(String title, String body, int count) {
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this, DEFAULT_CHANNEL)
                .setSmallIcon(getSmallIcon())
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setAutoCancel(true)
                .setNumber(2)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(VISIBILITY_PRIVATE)
                .setSound(getSound())
                .setContentIntent(getPendingIntent());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        return notificationBuilder;
    }

    public void notify(int id, String title, String body, int unReadCount, Bundle bundle) {
        Log.d("xoxoxo", "3.data " +title + " " +body + " "+ unReadCount);
        if (id == -1) {
            id = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        }
        this.bundle = bundle;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationManager().notify(id, getNotificationBuilderOreo(title, body, unReadCount).build());
        } else {
            getNotificationManager().notify(id, getNotificationBuilderPre(title, body, unReadCount).build());
        }
    }

    private PendingIntent getPendingIntent() {
        Intent pendingIntent;
        pendingIntent = new Intent(this, TestActivity.class);
        // pendingIntent.putExtra("bundle", bundle);
    /*    pendingIntent.putExtra("proceedNotifications", true);
        pendingIntent.putExtra("transaction_id", bundle.getInt("transaction_id"));
        pendingIntent.putExtra("notification_id", bundle.getInt("notification_id"));*/
        return PendingIntent.getActivity(this, 0, pendingIntent, PendingIntent.FLAG_ONE_SHOT);
        /*  return PendingIntent.getActivity(this, 0, pendingIntent, PendingIntent.FLAG_ONE_SHOT);*/
    }

    private Uri getSound() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    private int getSmallIcon() {
        return R.mipmap.ic_launcher;
    }

    private NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }
}
