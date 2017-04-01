package com.fangcm.dragonfly.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fangcm.dragonfly.MainService;

public class HomeKeyReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    private static final String SYSTEM_REASON = "reason";
    private static final String SYSTEM_HOME_KEY = "homekey";// home key
    private static final String SYSTEM_RECENT_APPS = "recentapps";// long home key

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(TAG, "onReceive " + action);

        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_REASON);
            if (reason != null) {
                if (reason.equals(SYSTEM_HOME_KEY)) {
                    // home key处理点

                    System.out.println("Home home home");
                    MainService.startActionCountDownTimer(context);

                } else if (reason.equals(SYSTEM_RECENT_APPS)) {
                    // long home key处理点

                    System.out.println("Long long Home home home");
                    MainService.startActionCountDownTimer(context);
                }
            }
        }
    }
}