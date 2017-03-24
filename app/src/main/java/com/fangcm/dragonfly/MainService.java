package com.fangcm.dragonfly;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;


public class MainService extends IntentService {
    private static final String ACTION_REENTRY = "action.REENTRY";
    private static final String ACTION_BAZ = "action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.fangcm.dragonfly.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.fangcm.dragonfly.extra.PARAM2";


    public static void startActionReentry(Context context) {
        Intent intent = new Intent(context, MainService.class);
        intent.setAction(ACTION_REENTRY);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MainService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public MainService() {
        super("MainService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_REENTRY.equals(action)) {
                handleActionReentry();
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }


    private void handleActionReentry() {

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
