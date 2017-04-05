package com.fangcm.dragonfly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class LockScreenActivity extends Activity {
    private static final String TAG = "LockScreenActivity";

    private SharedPreferences preferences;
    private TextView txtClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        txtClock = (TextView) findViewById(R.id.txtClock);
/*
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
*/
        MainService.startService(this);

        preferences = getSharedPreferences("dragonfly", Context.MODE_PRIVATE);
        //判断是不是首次登录，
        if (preferences.getBoolean("firststart", true)) {
            SharedPreferences.Editor editor = preferences.edit();
            //将登录标志位设置为false，下次登录时不在显示首次登录界面
            editor.putBoolean("firststart", false);
            editor.commit();

            finish();
        }
    }

    //Activity的启动模式(launchMode),通过这个方法接受Intent
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, " onNewIntent...");
        handleIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, " onStart...");
        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (MainService.ACTION_LOCK_SCREEN_START.equals(action)) {

        } else if (MainService.ACTION_LOCK_SCREEN_TICK.equals(action)) {
            if (intent.getExtras() != null) {
                long secondsUntilFinished = intent.getLongExtra("countdown", 0);
                long second = secondsUntilFinished % 60;
                long minute = secondsUntilFinished / 60 % 60;
                long hour = secondsUntilFinished / 3600;
                String leftTime = String.format("%d:%02d:%02d", hour, minute, second);
                txtClock.setText(leftTime);
            }

        } else if (MainService.ACTION_LOCK_SCREEN_FINISH.equals(action)) {

        }

    }

}
