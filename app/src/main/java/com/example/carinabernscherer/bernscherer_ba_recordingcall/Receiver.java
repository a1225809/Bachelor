package com.example.carinabernscherer.bernscherer_ba_recordingcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by carinabernscherer on 17.11.15.
 */
public class Receiver extends BroadcastReceiver {
    /**
     * receiver always listens
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("PhoneLoader", "CallBroadcastReceiver::onReceive got Intent: " + intent.toString());

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String numberToCall = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        }

        PhoneListener listener = new PhoneListener(context);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

    }
}
