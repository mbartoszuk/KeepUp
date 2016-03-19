/**
 * Created by Maria on 19/03/2016.
 */

package com.example.maria.keepup;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Constants extends BaseColumns {

    public static final String TABLE_NAME = "appointments";

    //Columns in the Appointments database.
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String DETAILS = "details";

    public static final String AUTHORITY = "com.example.maria.keepup";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

}