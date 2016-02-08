package com.example.carinabernscherer.bernscherer_ba_recordingcall;

import java.io.File;

import java.io.IOException;
import java.lang.Exception;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Environment;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.provider.SyncStateContract;
import android.widget.Toast;
import android.util.Log;

import com.jcraft.jsch.JSchException;

/**
 * Heart of the implementation
 * the real Service, saves the file in the memory of the phone
 * records the call
 */
public class RecordingService  extends Service

{
    private String username = "a1225809";
    private String password = "Bernschi23";
    private DBDataSource dataSource;
    private File f;
    private MediaRecorder recorder = null;
    private boolean isRecording = false;
    private File recording = null;

    /**
     * save the file on the SD-Card
     * creates the name of the file
     * @return
     * @throws InterruptedException
     * @throws JSchException
     */
    private File makeOutputFile() throws InterruptedException, JSchException {

        File dir = new File(getCacheDir(),File.separator+"PhoneLoad/");


        if (!dir.exists()) {
            try {
                dir.mkdirs();
                Log.i("Path to files ", dir.getAbsolutePath());

            } catch (Exception e) {
                Log.e("PhoneLoad", "RecordService::makeOutputFile unable to create directory " + dir + ": " + e);
                Toast t = Toast.makeText(getApplicationContext(), "unable to create the directory " + dir + " to store recordings: " + e, Toast.LENGTH_LONG);
                t.show();

            }
        } else {
            if (!dir.canWrite()) {
                Toast.makeText(getApplicationContext(), "Something went wrong PhoneLoad doesn't make the directory " + dir + " to store recordings", Toast.LENGTH_LONG).show();
                return null;
            }
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SS");
        String prefix = sdf.format(new Date()) + "voicecall";
        String suffix = ".amr";

        try {
             f =  File.createTempFile(prefix, suffix, dir);
            String size = String.valueOf(f.length());

            RecordingFile file = new RecordingFile(prefix,size,suffix,dir.getPath()+f.getName());
            Toast.makeText(getApplicationContext(),"FILE SETZEN",Toast.LENGTH_LONG).show();

            dataSource = new DBDataSource(this);
            Log.i(null,"Die Datenquelle wird geöffnet.");
            dataSource.open();
            dataSource.addFile(file);

            dataSource.close();

            return f;



        } catch (IOException e) {
            Log.e("PhoneLoad", "RecordService::makeOutputFile unable to create temp file in " + dir + ": " + e);
            Toast t = Toast.makeText(getApplicationContext(), "PhoneLoad was unable to create temp file in " + dir + ": " + e, Toast.LENGTH_LONG);
            t.show();
            return null;
        }



    }


    /**
     * creates MediaRecorder Object
     */
    public void onCreate() {
        super.onCreate();
        recorder = new MediaRecorder();
        Toast.makeText(getApplicationContext(), "Service Created", Toast.LENGTH_SHORT).show();
        Log.i("PhoneLoad", "onCreate created MediaRecorder object");
    }

    /**
     * MediaRecorder settings
     * @param intent
     * @param startId
     */
    @Override
    public void onStart(Intent intent, int startId) {
        while(isRecording == false) {

            try {
                try {
                    recording = makeOutputFile();
                } catch (JSchException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {

                recorder.reset();
                recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                recorder.setOutputFile(recording.getAbsolutePath());

                try {
                    recorder.prepare();
                } catch (java.io.IOException e) {
                    Toast.makeText(getApplicationContext(), "PhoneLoader was unable to start recording: " + e, Toast.LENGTH_LONG).show();


                }
                recorder.start();
                isRecording = true;
                Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_SHORT).show();
                isRecording = true;

            } catch (java.lang.Exception e) {
                Toast.makeText(getApplicationContext(), "PhoneLoader was unable to start recording: " + e, Toast.LENGTH_LONG).show();


                Log.e("PhoneLoader", "RecordService::onStart exception", e);
                recorder = null;
            }
        }
        return;
    }

    /**
     * destroys the MediaRecorder Object
     * transfer the files to the both server - classes
     */
    public void onDestroy() {
        SFTPConnection sft = new SFTPConnection(f,username,password);
        sft.doInBackground();
       /** ServerConnection c = new ServerConnection();

        try {
            c.connectToDatabase(f,username,password);
        } catch (Exception e) {
                e.printStackTrace();
        }

        **/
        super.onDestroy();

        if (recorder != null) {
            isRecording = false;
            recorder.release();
            Toast.makeText(getApplicationContext(), "Recording Finished  ", Toast.LENGTH_LONG).show();

        }


    }


    public IBinder onBind(Intent intent) {
        return null;
    }

}
