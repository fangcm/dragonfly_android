package com.fangcm.dragonfly.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fangcm.dragonfly.MainService;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i(TAG, "onReceive ACTION_BOOT_COMPLETED");
            MainService.startActionCountDownTimer(context);
        }
    }
}
