/**
 * Created by Maria on 19/03/2016.
 */

package com.example.maria.keepup;

import static android.provider.BaseColumns._ID;
import static com.example.maria.keepup.Constants.TABLE_NAME;
import static com.example.maria.keepup.Constants.TITLE;
import static com.example.maria.keepup.Constants.DATE;
import static com.example.maria.keepup.Constants.TIME;
import static com.example.maria.keepup.Constants.DETAILS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Represents the Appointments Table.
public class AppointmentsData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "appointments3.db";
    private static final int DATABASE_VERSION = 1;

    //Create a helper object for the Events database.
    public AppointmentsData(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT NOT NULL, "
                + DATE + " INTEGER NOT NULL, "
                + TIME + " INTEGER NOT NULL, "
                + DETAILS + " TEXT NOT NULL," +
                " UNIQUE("+TITLE+","+DATE+"));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}