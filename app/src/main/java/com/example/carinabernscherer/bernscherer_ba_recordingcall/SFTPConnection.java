package com.example.carinabernscherer.bernscherer_ba_recordingcall;

import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by carinabernscherer on 12.01.16.
 */
public class SFTPConnection extends AsyncTask<Void, Void, Void> {

    private File f;
    private String un;
    private String pw;

    /**
     * Constructor
     * @param f
     * @param username
     * @param password
     */
    public SFTPConnection(File f, String username, String password) {
        this.f = f;
        this.un = username;
        this.pw = password;
    }

    /**
     * Methode from AsynTask
     * builds up the connection and transfers the file to the Almighty Server
     * @param params
     * @return
     */
    @Override
    protected Void doInBackground(Void... params) {

        final boolean[] conStatus = {false};
        Session session = null;
        final Channel[] channel = {null};
        String HOST_ADDRESS = "almighty.cs.univie.ac.at";
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");

        Log.i("Session", "is" + conStatus[0]);
        try {
            JSch ssh = new JSch();
            session = ssh.getSession(un, HOST_ADDRESS, 22);
            session.setPassword(pw);
            session.setConfig(config);

            String l = String.valueOf(f.length());
            Log.i(null, l);
            final Session finalSession = session;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        finalSession.connect();
                        conStatus[0] = finalSession.isConnected();
                        Log.i("Session", "is" + conStatus[0]);
                        channel[0] = finalSession.openChannel("sftp");
                        channel[0].connect();
                        ChannelSftp sftp = (ChannelSftp) channel[0];
                        sftp.put(new FileInputStream(f), f.getName());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();

        } catch (JSchException e) {
            e.printStackTrace();
            Log.i("Session", "is" + conStatus[0]);
        }

        return null;
    }

}

