package com.example.carinabernscherer.bernscherer_ba_recordingcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Main extends Activity {
    /**
     * create Service, send Intent
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, RecordingService.class);
        startService(intent);
        finish();
    }


}
