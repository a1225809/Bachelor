package com.example.carinabernscherer.bernscherer_ba_recordingcall;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by carinabernscherer on 09.01.16.
 * ist die DAO Klasse zwischen der Datenbank und RecordingService
 */
public class DBDataSource {


    private static final String LOG_TAG = DBDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    /**
     * Constructor
     * @param context
     */
    public DBDataSource(Context context) {
        Log.d(LOG_TAG, " DataSource erzeugt dbHelper.");
        dbHelper = new DBHelper(context);
    }

    /**
     * create a reference of the database
     */
    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.i(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    /**
     * close the reference of the database
     */
    public void close() {
        dbHelper.close();
        Log.i(LOG_TAG, "Datenbank geschlossen.");
    }

    /**
     * add a file in the database
     * @param file
     */
    public void addFile(RecordingFile file) {
        dbHelper.addFile(file);

    }
}