package com.nahin.attendancechecker;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Admin extends AppCompatActivity {


    TextView currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin );
        getSupportActionBar().hide();

       currentDate = (TextView)findViewById( R.id.date_id_of_admin );
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("d-M-yyyy");
        String datetime = dateformat.format(c.getTime());
        currentDate.setText(datetime);
        currentDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog( Admin.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                currentDate.setText( dayOfMonth + "-" + (monthOfYear + 1) + "-" + year );
                            }
                        }, year, month, day );
                picker.show();
            }
        } );



    }
}
