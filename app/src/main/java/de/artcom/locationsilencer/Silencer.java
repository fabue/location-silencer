package de.artcom.locationsilencer;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
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
                Toast.makeText(Silencer.this, "Silencing mobile", Toast.LENGTH_LONG).show();
            }
        });

        ((AudioManager)getSystemService(Context.AUDIO_SERVICE)).setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
}
