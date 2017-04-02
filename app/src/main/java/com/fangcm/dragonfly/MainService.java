package com.fangcm.dragonfly;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.fangcm.dragonfly.receiver.CountdownReceiver;
import com.fangcm.dragonfly.receiver.LockScreenReceiver;


public class MainService extends Service {
    private static final String TAG = "MainService";

    private static final String ACTION_START_COUNTDOWN = "dragonfly.action.start.countdown";

    public static final String ACTION_NORMAL_SCREEN_TICK = "dragonfly.action.normalScreen.tick";
    public static final String ACTION_LOCK_SCREEN_START = "dragonfly.action.lockScreen.start";
    public static final String ACTION_LOCK_SCREEN_TICK = "dragonfly.action.lockScreen.tick";
    public static final String ACTION_LOCK_SCREEN_FINISH = "dragonfly.action.lockScreen.finish";

    private boolean currentLockScreen = false;
    private long lockScreenSeconds = 120;
    private long normalScreenSeconds = 120;
    private boolean timerFinished = true;
    private CountDownTimer countDownTimer = null;
    private LocalBroadcastManager localBroadcastManager = null;
    private LockScreenReceiver lockScreenReceiver = null;
    private CountdownReceiver countdownReceiver = null;


    private void startCountDownTimer(final long totalSeconds, final boolean lockScreen) {
        Log.i(TAG, "Starting timer...");

        cancelCountDownTimer();
        timerFinished = false;
        countDownTimer = new CountDownTimer(totalSeconds * 1000, lockScreen ? 1000 : 30000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Intent intent = lockScreen ? new Intent(ACTION_LOCK_SCREEN_TICK) : new Intent(ACTION_NORMAL_SCREEN_TICK);
                long secondsUntilFinished = millisUntilFinished / 1000;
                intent.putExtra("countdown", secondsUntilFinished);
                localBroadcastManager.sendBroadcast(intent);
                Log.i(TAG, "Countdown seconds remaining: " + secondsUntilFinished);
            }

            @Override
            public void onFinish() {
                Intent intent = lockScreen ? new Intent(ACTION_LOCK_SCREEN_FINISH) : new Intent(ACTION_LOCK_SCREEN_START);
                localBroadcastManager.sendBroadcast(intent);
                timerFinished = true;
                Log.i(TAG, "Timer finished");
                MainService.startActionCountDownTimer(getApplicationContext());
            }
        };

        countDownTimer.start();
    }

    private void cancelCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            Log.i(TAG, "Timer cancelled");
        }
        timerFinished = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        countdownReceiver = new CountdownReceiver();
        localBroadcastManager.registerReceiver(countdownReceiver, new IntentFilter(ACTION_NORMAL_SCREEN_TICK));

        lockScreenReceiver = new LockScreenReceiver();
        IntentFilter lockScreenFilter = new IntentFilter();
        lockScreenFilter.addAction(ACTION_LOCK_SCREEN_START);
        lockScreenFilter.addAction(ACTION_LOCK_SCREEN_TICK);
        lockScreenFilter.addAction(ACTION_LOCK_SCREEN_FINISH);
        localBroadcastManager.registerReceiver(lockScreenReceiver, lockScreenFilter);
    }

    @Override
    public void onDestroy() {
        cancelCountDownTimer();
        localBroadcastManager.unregisterReceiver(countdownReceiver);
        localBroadcastManager.unregisterReceiver(lockScreenReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (timerFinished) {
            if (currentLockScreen) {
                startCountDownTimer(lockScreenSeconds, true);
            } else {
                startCountDownTimer(normalScreenSeconds, false);
            }
            currentLockScreen = !currentLockScreen;
        }

        return START_REDELIVER_INTENT;
    }

    public static void startActionCountDownTimer(Context context) {
        Intent intent = new Intent(context, MainService.class);
        intent.setAction(ACTION_START_COUNTDOWN);
        context.startService(intent);
    }

}
