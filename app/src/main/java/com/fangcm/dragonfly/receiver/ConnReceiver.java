package com.fangcm.dragonfly.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.fangcm.dragonfly.MainService;

public class ConnReceiver extends BroadcastReceiver {
    private static final String TAG = "ConnReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            Log.i(TAG, "onReceive CONNECTIVITY_ACTION");
            MainService.startActionCountDownTimer(context);
        }
    }
}
