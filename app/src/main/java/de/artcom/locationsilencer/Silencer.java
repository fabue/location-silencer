package de.artcom.locationsilencer;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class Silencer extends IntentService{

    private static final String TAG = Silencer.class.getSimpleName();

    public Silencer() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
    }
}
