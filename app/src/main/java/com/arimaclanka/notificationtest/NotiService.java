package com.arimaclanka.notificationtest;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

public class NotiService extends IntentService {

    String title,msg,strCount;
    int count;

    public NotiService() {
        super("NotiService");
    }

    public NotiService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        title = intent.getStringExtra("tt");
        msg = intent.getStringExtra("mm");
        count = intent.getIntExtra("cc", 0);
        Log.d("xoxoxo", "2.data " +title + " " +msg + " "+ count);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("xoxoxo", "2.1.data " +title + " " +msg + " "+ count);
                new NotificationHelper(NotiService.this).notify(-1, title, msg, count, new Bundle());
            }
        }, 8000);
    }
}
