package com.example.carinabernscherer.bernscherer_ba_recordingcall;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by carinabernscherer on 08.01.16.
 *
 * Really Database
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DBHelper.class.getSimpleName();
    public static final String DB_NAME = "PhoneLoader.db";
    public static final int DB_VERSION = 1;

    private static final String TABLE_FILES = "FILES";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_PATH = "PATH";

    private static final String[] COLUMNS = {KEY_NAME, KEY_PATH};

    /**
     * Constructor
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        Log.i(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    /**
     * create table Files
     * @param db
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_FILES =
                "CREATE TABLE IF NOT EXISTS FILES ( " +
                        "NAME TEXT, " +
                        "TYPE TEXT, " +
                        "SIZE TEXT, " +
                        "PATH TEXT )";
        Log.i(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE_FILES + " angelegt.");
        db.execSQL(SQL_CREATE_FILES);

    }

    /**
     *update database
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS FILES");
        this.onCreate(db);
    }

    /**
     * insert the file into the database
     * @param file
     */
    public void addFile(RecordingFile file) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, file.getName());

        values.put(KEY_PATH, file.getPath());

        db.insert(TABLE_FILES, null, values);


        db.close();
    }


}
