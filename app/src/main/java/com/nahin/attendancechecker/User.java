package com.nahin.attendancechecker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    Button  check,goCheck;
    LinearLayout check_layout;
    ScrollView scrollView_submit;
    DatabaseReference requestRf;
    String toDay;
    EditText inputNumberToSearch;
    TextView accepted;
    Integer status;
    ProgressDialog progressDialog;
    CardView cv_nodata;


    LinearLayout loading_layout;

   // private ValueEventListener eventListener; //new
    private FirebaseDatabase database = FirebaseDatabase.getInstance(); //new

    EditText phoneNumber,date,name;
    private AdView mAdView;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user );
        getSupportActionBar().hide();

      //  progressDialog = new ProgressDialog(this);
      //  progressDialog.setMessage("Please wait,Checking your status....");
        mAuth = FirebaseAuth.getInstance();

        codeForTime();

        codeForDatePicker();

        submitInformation();

        checkingStatus();


            /*code for banner ads starts **/

        MobileAds.initialize(this, "ca-app-pub-2485705965051323~7459480942");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*code for banner ads ends **/




        mInterstitialAd = newInterstitialAd();
        loadInterstitial();

    }

    private void checkingStatus() {

        check_layout=findViewById( R.id.layout_view_Status );
        scrollView_submit = findViewById( R.id.submit_information_layout_id );
        check = (Button)findViewById( R.id.checkID);
        goCheck =(Button)findViewById( R.id.goCheck );
        scrollView_submit.setVisibility( View.VISIBLE );
        inputNumberToSearch = (EditText)findViewById( R.id.check_phoneID );
        accepted = (TextView)findViewById( R.id.accepted_view_text_ID );

        loading_layout=(LinearLayout)findViewById( R.id.loading_layoutID );


        cv_nodata=(CardView)findViewById( R.id.cv_view_id );


        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("d-M-yyyy");
        toDay = dateformat.format(c.getTime());


        // FirebaseDatabase database;


        goCheck.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //overridePendingTransition( R.anim.slider_1,R.anim.slider_2 );
                scrollView_submit.setVisibility( View.GONE );
                check_layout.setVisibility( View.VISIBLE );

            }
        } );

        check.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loading_layout.setVisibility( View.VISIBLE );

              //  progressDialog.show();
                String getNumberForStatus = inputNumberToSearch.getText().toString().trim();
                if(isNetworkConnected()==true)
                {
                   if(getNumberForStatus.length()==11)
                   {
                       requestRf = FirebaseDatabase.getInstance().getReference().child("StudentList").child(toDay).child(getNumberForStatus);
                       requestRf.addValueEventListener( new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                               if(dataSnapshot.exists())
                               {

                                   cv_nodata.setVisibility( View.GONE );
                               String getDate = dataSnapshot.child( "date" ).getValue().toString();
                               String myName  = dataSnapshot.child( "myName" ).getValue().toString();
                               //String myStatus = dataSnapshot.child( "mystatus" ).getValue().toString();
                               String phoneNumber = dataSnapshot.child( "phoneNumber" ).getValue().toString();
                               String time = dataSnapshot.child( "time" ).getValue().toString();
                               status =  dataSnapshot.child( "mystatus" ).getValue(Integer.class);


                              // pending.setText( myName );

                               if(status==0)
                               {
                                   accepted.setText( "Dear "+myName+"\nYour phone number is "+phoneNumber+".\nThe request you have sent at\n"
                                   +time+"\nis now waiting on admin panel to approve.\nThanks for being with us." );
                                   accepted.setTextColor( Color.parseColor( "#0228E8" ) );

                                   loading_layout.setVisibility( View.GONE );
                                   //progressDialog.dismiss();
                               }
                               else if(status==1)
                               {
                                   accepted.setText( "Congratulations !!!\nDear "+myName+"\nYour phone number is "+phoneNumber+".\nThe request you have sent at\n"
                                           +time+"\nis accepted.\nThanks for being with us." );
                                   accepted.setTextColor( Color.parseColor( "#07C70E" ) );
                                   loading_layout.setVisibility( View.GONE );
                                   // progressDialog.dismiss();
                               }
                               else if(status==2)
                               {
                                   accepted.setText( "Bad luck For you !!!\nDear "+myName+"\nYour phone number is "+phoneNumber+".\nThe request you have sent at\n"
                                           +time+"\nis rejected.\nGood luck for next time." );
                                   accepted.setTextColor( Color.parseColor( "#E4291B" ) );
                                   loading_layout.setVisibility( View.GONE );
                                   // progressDialog.dismiss();
                               }
                               }else
                               {

                                   accepted.setText( "" );
                                   cv_nodata.setVisibility( View.VISIBLE );
                                  // accepted.setText( "No data found relevant to this phone number!" );
                                  // accepted.setTextColor( Color.parseColor( "#E4291B" ) );
                                   // Toast.makeText( User.this, , Toast.LENGTH_SHORT ).show();
                                   loading_layout.setVisibility( View.GONE );
                                   // progressDialog.dismiss();
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {
                               //Toast.makeText( User.this, "No data found !", Toast.LENGTH_SHORT ).show();
                               //progressDialog.dismiss();
                           }
                       } );








                   }
                   else 
                   {
                       inputNumberToSearch.setError( "Invalid Number !" );
                       loading_layout.setVisibility( View.GONE );
                       // progressDialog.dismiss();
                   }
                }
                else
                {
                    Toast.makeText( User.this, "No Internet !", Toast.LENGTH_SHORT ).show();
                    loading_layout.setVisibility( View.GONE );
                    // progressDialog.dismiss();
                }





            }
        } );







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
                            showInterstitial();
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

    //interstitial code started

    private InterstitialAd newInterstitialAd () {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id_normal));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                submitButton.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                submitButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
               // Intent intent = new Intent(getApplicationContext(),MainActivity.class);
               // startActivity(intent);
               // overridePendingTransition(R.anim.slider_1,R.anim.slider_2);
                finish();

            }
        });
        return interstitialAd;
    }

    private void showInterstitial () {

        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();


        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
          //  Intent intent = new Intent(getApplicationContext(),MainActivity.class);
          //  startActivity(intent);
             finish();

        }
    }


    private void loadInterstitial () {
        // Disable the next level button and load the ad.
        submitButton.setEnabled(false);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }



//interstitial code ended


























    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(User.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
