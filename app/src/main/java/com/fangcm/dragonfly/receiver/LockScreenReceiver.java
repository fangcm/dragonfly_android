package com.fangcm.dragonfly.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fangcm.dragonfly.MainService;

public class LockScreenReceiver extends BroadcastReceiver {
    private static final String TAG = "LockScreenReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MainService.ACTION_LOCK_SCREEN_START.equals(intent.getAction())) {
            Log.i(TAG, "onReceive ACTION_LOCK_SCREEN_START");
        } else if (MainService.ACTION_LOCK_SCREEN_TICK.equals(intent.getAction())) {
            Log.i(TAG, "onReceive ACTION_LOCK_SCREEN_TICK");
        } else if (MainService.ACTION_LOCK_SCREEN_FINISH.equals(intent.getAction())) {
            Log.i(TAG, "onReceive ACTION_LOCK_SCREEN_FINISH");
        }
    }
}
