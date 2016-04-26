package de.artcom.locationsilencer;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class Silencer extends IntentService{

    private static final String TAG = Silencer.class.getSimpleName();

    public Silencer() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Silencer.this, "Silencer started", Toast.LENGTH_LONG).show();
            }
        });
    }
}
