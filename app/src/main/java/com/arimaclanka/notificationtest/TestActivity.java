package com.arimaclanka.notificationtest;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.arimaclanka.notificationtest.databinding.ActivityTestBinding;

import java.util.UUID;

public class TestActivity extends AppCompatActivity {

    ActivityTestBinding binding;
    String title,msg,strCount;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        setBtnListeners();
    }

    private void setBtnListeners() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();
            }
        });
    }

    private void sendMsg(){
        title = binding.title.getText().toString().trim();
        msg = binding.msg.getText().toString().trim();
        strCount = binding.count.getText().toString().trim();
        if(TextUtils.isEmpty(title)){
            title = UUID.randomUUID().toString();
        }
        if(TextUtils.isEmpty(msg)){
            msg = UUID.randomUUID().toString();
        }
        if(TextUtils.isEmpty(strCount)){
            count = -1;
        }else {
            try {
                count = Integer.parseInt(strCount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                count = -1;
            }
        }
        Log.d("xoxoxo", "1.data " +title + " " +msg + " "+ count);
        Intent i = new Intent(this, NotiService.class);
        i.putExtra("tt", title);
        i.putExtra("mm", msg);
        i.putExtra("cc", count);
        startService(i);

      //  new NotificationHelper(this).notify(-1, title, msg, count, new Bundle());

    }
}
