package com.fangcm.dragonfly.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fangcm.dragonfly.MainService;


public class CountdownReceiver extends BroadcastReceiver {
    private static final String TAG = "CountdownReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MainService.ACTION_NORMAL_SCREEN_TICK.equals(intent.getAction())) {
            Log.i(TAG, "onReceive ACTION_NORMAL_TICK");
        }
    }
}
