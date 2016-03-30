/**
 * Created by Maria Bartoszuk on 19/03/2016.
 */

package com.example.maria.keepup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//Calendar widget in Material Design imported from jCenter (default Android library).
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

//Calendar class provides methods for converting between a specific point in time and a set of calendar fields.
import java.util.Calendar;

public class MainActivity extends ActionBarActivity {

    //Activity request identifier for Creating an appointment.
    //Used to display the correct Toast after CreateAppointmentActivity is finished.
    public static final int CREATE_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Calendar View variable.
        final MaterialCalendarView calendar = (MaterialCalendarView) findViewById(R.id.calendarView);
        //Setting today's date as a default.
        calendar.setSelectedDate(Calendar.getInstance());

        //Create Appointment button.
        Button button_createAppointment = (Button) findViewById(R.id.button_createAppointment);
        button_createAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Opens CreateAppointmentActivity.
                Intent intent = new Intent(MainActivity.this, CreateAppointmentActivity.class);
                //Passes the checked date to the activity.
                intent.putExtra("date", calendar.getSelectedDate());
                startActivityForResult(intent, CREATE_REQUEST);
            }
        });

        //Delete Appointment button.
        Button button_deleteAppointment = (Button) findViewById(R.id.button_deleteAppointment);
        button_deleteAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Opens DeleteAppointmentActivity
                Intent intent = new Intent(MainActivity.this, CreateAppointmentActivity.class);
            }
        });
    }

    //Displays the confirmation to the user about the completed action.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Confirms that an appointment was created successfully.
        if (requestCode == CREATE_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "Appointment saved successfully.", Toast.LENGTH_LONG).show();
        }
    }
}