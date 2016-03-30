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
//Time picker widget in Material Design imported from jCenter (default Android library).
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

//Creates the appointment with all its details and saves to the database.
public class CreateAppointmentActivity extends ActionBarActivity {

    private AppointmentsData appointments; //Database object
    private CalendarDay date; //Selected date
    private int time; //Integer representation of time (hours * 100 + minutes)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createappointment);

        appointments = new AppointmentsData(this);
        date = getIntent().getParcelableExtra("date");

        //The field for the user to pick the time.
        final EditText textfield_appointmentTime = (EditText) findViewById(R.id.textfield_appointmentTime);
        //Opens the Radial Picker when the field is in focus.
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

        //Save created appointment button.
        Button saveCreateAppointment = (Button) findViewById(R.id.button_saveCreateAppointment);
        saveCreateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAppointment();
            }
        });
    }

    //Adds a new appointment with its details, checking whether it matches the criteria.
    private void addAppointment() {

        //Appointment details - title, summary and chosen date.
        String title = getAppointmentTitle();
        EditText appointmentDetails = (EditText) findViewById(R.id.textfield_appointmentDetails);
        String details = appointmentDetails.getText().toString();
        int databaseDate = getDatabaseDate();

        //Checks for title uniqueness on the chosen date.
        if (isTitleUnique()) {
            //Inserts a new row into the Appointments database.
            SQLiteDatabase db = appointments.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TITLE, title);
            values.put(DATE, databaseDate);
            values.put(TIME, time);
            values.put(DETAILS, details);
            db.insertOrThrow(TABLE_NAME, null, values);
            setResult(RESULT_OK); //signal to the Toast
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Repeating entry")
                    .setMessage("Appointment " + title + " already exists for the chosen date, please choose a different event title.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }

    //Accesses the title of the appointment.
    private String getAppointmentTitle() {
        EditText appointmentTitle = (EditText) findViewById(R.id.textfield_appointmentTitle);
        return appointmentTitle.getText().toString();
    }

    //Accesses the date of the appointment, in an int format YYYYMMDD.
    private int getDatabaseDate() {
        return date.getDay() + date.getMonth() * 100 + date.getYear() * 10000;
    }

    //Checks whether an entered title is unique for the chosen date.
    private boolean isTitleUnique() {
        String title = getAppointmentTitle();
        int date = getDatabaseDate();

        //SQL statement looking if the date and title combination already exists in the database.
        Cursor cursor = appointments.getReadableDatabase().query(TABLE_NAME, new String[]{TITLE},
                TITLE + "=? AND " + DATE + "=?",
                new String[]{title, Integer.toString(date)}, null, null, null);
        //Case of repetition found.
        if (cursor != null && cursor.moveToFirst()) {
            return false;
        }
        //No case of repetition found.
        return true;
    }
}