/**
 * Created by Maria Bartoszuk on 19/03/2016.
 */

package com.example.maria.keepup;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.maria.keepup.Constants.TABLE_NAME;
import static com.example.maria.keepup.Constants.TITLE;
import static com.example.maria.keepup.Constants.DATE;
import static com.example.maria.keepup.Constants.TIME;
import static com.example.maria.keepup.Constants.DETAILS;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.Console;

public class CreateAppointmentActivity extends ActionBarActivity {

    private AppointmentsData appointments;
    CalendarDay date;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createappointment);

        appointments = new AppointmentsData(this);
        date = getIntent().getParcelableExtra("date");

        final EditText textfield_appointmentTime = (EditText) findViewById(R.id.textfield_appointmentTime);
        textfield_appointmentTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    TimePickerDialog dialog = TimePickerDialog.newInstance(
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                                    textfield_appointmentTime.setText(String.format("%d:%02d", hourOfDay, minute));
                                    time = minute + hourOfDay * 100;
                                }
                            }, 12, 0, true);
                    dialog.show(getFragmentManager(), "Timepickerdialog");
                }
            }
        });

        Button saveCreateAppointment = (Button) findViewById(R.id.button_saveCreateAppointment);
        saveCreateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAppointment();
            }
        });
    }

    private void addAppointment() {

        String title = getAppointmentTitle();

        EditText appointmentDetails = (EditText) findViewById(R.id.textfield_appointmentDetails);
        String details = appointmentDetails.getText().toString();

        int databaseDate = getDatabaseDate();

        System.out.printf("Saving: %s, %d, %d, %s\n", title, databaseDate, time, details);

        if (isTitleUnique()) {
            // Insert a new record into the Events data source.
            SQLiteDatabase db = appointments.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TITLE, title);
            values.put(DATE, databaseDate);
            values.put(TIME, time);
            values.put(DETAILS, details);
            db.insertOrThrow(TABLE_NAME, null, values);
            setResult(RESULT_OK);
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Repeating entry")
                    .setMessage("Appointment " + title + " already exists, please choose a different event title.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();
        }
    }

    @NonNull
    private String getAppointmentTitle() {
        EditText appointmentTitle = (EditText) findViewById(R.id.textfield_appointmentTitle);
        return appointmentTitle.getText().toString();
    }

    private int getDatabaseDate() {
        return date.getDay() + date.getMonth() * 100 + date.getYear() * 10000;
    }

    private boolean isTitleUnique() {
        String title = getAppointmentTitle();
        int date = getDatabaseDate();

        Cursor cursor = appointments.getReadableDatabase().query(TABLE_NAME, new String[]{TITLE},
                TITLE + "=? AND " + DATE + "=?",
                new String[]{title, Integer.toString(date)},
                null, null, null);
        if (cursor == null) {
            return true;
        }
        int count = cursor.getCount();
        cursor.close();
        return count == 0;
    }
}
