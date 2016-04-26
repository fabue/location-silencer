package de.artcom.locationsilencer;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class Silencer extends IntentService{

    private static final String TAG = Silencer.class.getSimpleName();

    public Silencer() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        Geofence geofence = geofencingEvent.getTriggeringGeofences().get(0);

        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        final String geofenceDescription =
                getTransitionString(geofenceTransition) + ", " +
                        geofence.getRequestId();

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Silencer.this, geofenceDescription, Toast.LENGTH_LONG).show();
            }
        });

        ((AudioManager)getSystemService(Context.AUDIO_SERVICE)).setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "Entered Geofence";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "Exit Geofence";
            default:
                return "Unknonw Geofence Transition";
        }
    }
}
