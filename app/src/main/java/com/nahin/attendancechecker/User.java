package com.nahin.attendancechecker;

import android.app.DatePickerDialog;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class User extends AppCompatActivity {

    //code for time starts
    TextView myTime;
    //code for time ends
    FirebaseAuth mAuth;

    //code for date starts
    DatePickerDialog picker;
    EditText dateInput;
    //code for date ends

    Button submitButton;
    int checkStatus;



   // private ValueEventListener eventListener; //new
    private FirebaseDatabase database = FirebaseDatabase.getInstance(); //new

    EditText phoneNumber,date,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user );
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        codeForTime();

        codeForDatePicker();

        submitInformation();
    }


    private void codeForTime() {

        myTime = (TextView)findViewById( R.id.timeID );
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy \n hh:mm:ss aa");
        String datetime = dateformat.format(c.getTime());
        myTime.setText( datetime );

    }


    private void codeForDatePicker() {

        //Code for date starts


        dateInput =(EditText) findViewById(R.id.date_id);
        dateInput.setInputType(InputType.TYPE_NULL);

        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(User.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateInput.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        //Code for date ends
    }


    private void submitInformation() {

        submitButton = (Button)findViewById( R.id.submit_button_id );
        submitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkStatus = 0;

            phoneNumber = (EditText)findViewById( R.id.phone_numberID );
            String getPhoneNumber = phoneNumber.getText().toString();

            date = (EditText)findViewById( R.id.date_id );
            String getDate = date.getText().toString();

            name = (EditText)findViewById( R.id.nameID );
            String getName = name.getText().toString();

            myTime = (TextView)findViewById( R.id.timeID );
            String inTime = myTime.getText().toString();

                if(isNetworkConnected()==true) {

                    if (getPhoneNumber.length() != 0 && getDate.length() != 0 && getName.length() != 0) {

                        if ( isNetworkConnected() == true) {

                            DatabaseReference requestRf = database.getReference( "StudentList" );

                            SendData sendData = new SendData( getName, getDate, getPhoneNumber, inTime, checkStatus );
                            // SendData sendData1 = new SendData( inTime );


                            requestRf.child( getDate ).child( getPhoneNumber ).setValue( sendData );


                            Toast.makeText( User.this, "Submitted Successfully", Toast.LENGTH_SHORT ).show();
                            date.setText( "" );
                            phoneNumber.setText( "" );
                            name.setText( "" );
                        }
                        else {
                            Toast.makeText( User.this, "No Internet !", Toast.LENGTH_SHORT ).show();
                        }

                    } else {

                        phoneNumber.setError( "Phone Number field Empty !" );
                        name.setError( "Name Field Empty !" );
                    }
                }
                else {
                    Toast.makeText( User.this, "No Internet !", Toast.LENGTH_SHORT ).show();
                }
            }
        });


    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(User.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
