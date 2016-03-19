/**
 * Created by Maria Bartoszuk on 19/03/2016.
 */

package com.example.maria.keepup;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.Console;

public class CreateAppointmentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createappointment);

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
                                }
                            }, 12, 0, true);
                    dialog.show(getFragmentManager(), "Timepickerdialog");
                }
            }
        });
    }
}
