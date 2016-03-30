/**
 * Created by Maria on 19/03/2016.
 */

package com.example.maria.keepup;

import android.net.Uri;
import android.provider.BaseColumns;

//Contains the data of the Appointments database.
public interface Constants extends BaseColumns {

    //Title of the database.
    public static final String TABLE_NAME = "appointments";

    //Columns in the Appointments database.
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String DETAILS = "details";
}