package com.example.carinabernscherer.bernscherer_ba_recordingcall;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by carinabernscherer on 26.11.15.
 */
public class PhoneListener extends PhoneStateListener {

    Context context;

    /**
     * Constructor
     * @param c
     */
    public PhoneListener(Context c) {
        context = c;
    }

    /**
     *  decide which state the TelephonyManager has
     * @param state
     * @param incomingNumber
     */
    public void onCallStateChanged(int state, String incomingNumber) {
        Log.i(incomingNumber, "");

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("CallRecorder", "CALL_STATE_IDLE, stoping recording");
                Boolean stopped = context.stopService(new Intent(context, RecordingService.class));
                Log.i("CallRecorder", "stopService" + stopped);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("CallRecorder", "CALL_STATE_RINGING");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("CallRecorder", "CALL_STATE_OFFHOOK starting recording");
                Intent callIntent = new Intent(context, RecordingService.class);
                ComponentName name = context.startService(callIntent);
                if (null == name) {
                    Log.e("CallRecorder", "startService for RecordService returned null ComponentName");
                } else {
                    Log.i("CallRecorder", "startService returned " + name.flattenToString());
                }
                break;

        }
    }
}

