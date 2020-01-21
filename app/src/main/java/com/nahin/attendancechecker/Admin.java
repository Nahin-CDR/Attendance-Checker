package com.nahin.attendancechecker;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Admin extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DatabaseReference databaseReference;
    List<SendData> studentDataList;
    ProgressDialog progressDialog;
    SendData mSendData;
    StudentAttendAdapter studentAttendAdapter;
    TextView currentDate;
    ImageView refreshimage;
    String dateTime;

    Button moreButton;

    Button getTotalStudent,goBackToPreviousList;


    LinearLayout header_viewer,items_viewer,footer_viewer;

    //code for total present student view
    LinearLayout linearLayout_of_total_student_view;
    String toDay;
    int sum=0;
    TextView textView_for_sum,txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin );
        getSupportActionBar().hide();

        mRecyclerView = findViewById(R.id.studentListId);
        GridLayoutManager gridLayoutManager = new GridLayoutManager( getApplicationContext(),1 );
        mRecyclerView.setLayoutManager( gridLayoutManager );
        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait Loading Data....");
        studentDataList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("StudentList");
        progressDialog.show();
        refreshimage = findViewById( R.id.refresh_id );
        currentDate = (TextView)findViewById( R.id.date_id_of_admin );
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("d-M-yyyy");
        dateTime = dateformat.format(c.getTime());
        currentDate.setText(dateTime);


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

        //refresh image id
        refreshimage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDateTime = currentDate.getText().toString();
                databaseReference.child( newDateTime ).orderByChild( "mystatus" ).equalTo( 0 ).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        studentDataList.clear();
                        for (DataSnapshot studentSnap : dataSnapshot.getChildren()){
                            SendData sendData = studentSnap.getValue(SendData.class);
                            studentDataList.add( sendData );
                        }
                        StudentAttendAdapter studentAttendAdapter = new StudentAttendAdapter( Admin.this,studentDataList );
                        mRecyclerView.setAdapter( studentAttendAdapter );
                        Toast.makeText(Admin.this, "Refresh Successful", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                } );
            }
        } );



        showingContents();


        showingTotalPresentStudent();




    }

    private void showingTotalPresentStudent() {

        getTotalStudent = (Button)findViewById( R.id.getSum_buttonID );
        goBackToPreviousList = (Button)findViewById( R.id.back_buttonID );
        linearLayout_of_total_student_view = (LinearLayout)findViewById( R.id.show_total_layoutID );

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("d-M-yyyy");
        toDay = dateformat.format(c.getTime());
        textView_for_sum = (TextView)findViewById( R.id. sum_of_contentID);

        txt=findViewById( R.id.sum_of_contentID2 );



        getTotalStudent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference.keepSynced( true );

                databaseReference.child(dateTime).orderByChild("mystatus").equalTo(1).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists())
                        {
                            sum = (int)dataSnapshot.getChildrenCount();
                            textView_for_sum.setText( "Total Present of Today "+sum +"\n");

                        }
                        else
                        {
                            textView_for_sum.setText( "Total Present of Today "+0 );
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );


                databaseReference.child(dateTime).orderByChild("mystatus").equalTo(2).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists())
                        {
                            sum = (int)dataSnapshot.getChildrenCount();
                            txt.setText( "Total Rejected of Today "+sum +"\n");

                        }
                        else
                        {
                            txt.setText( "Total rejected of Today "+0 );
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );


            }
        } );




        goBackToPreviousList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linearLayout_of_total_student_view.setVisibility( View.GONE );

                header_viewer.setVisibility( View.VISIBLE );
                items_viewer.setVisibility( View.VISIBLE);
                footer_viewer.setVisibility( View.VISIBLE );

            }
        } );







    }

    private void showingContents() {

        header_viewer = (LinearLayout)findViewById( R.id.header_layoutID );
        items_viewer = (LinearLayout)findViewById( R.id.item_list_show_layoutID );
        footer_viewer = (LinearLayout)findViewById( R.id.show_more_layoutID );

        header_viewer.setVisibility( View.VISIBLE );
        items_viewer.setVisibility( View.VISIBLE );
        footer_viewer.setVisibility( View.VISIBLE );

        moreButton = (Button)findViewById( R.id.more_buttonID );

        moreButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                header_viewer.setVisibility( View.GONE );
                items_viewer.setVisibility( View.GONE );
                footer_viewer.setVisibility( View.GONE );
                linearLayout_of_total_student_view.setVisibility( View.VISIBLE );

            }
        } );


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.child(dateTime).orderByChild("mystatus").equalTo(0).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               studentDataList.clear();
               for (DataSnapshot studentSnap : dataSnapshot.getChildren()){
                   SendData sendData = studentSnap.getValue(SendData.class);
                   studentDataList.add( sendData );
               }
               StudentAttendAdapter studentAttendAdapter = new StudentAttendAdapter( Admin.this,studentDataList );
               mRecyclerView.setAdapter( studentAttendAdapter );
               studentAttendAdapter.notifyDataSetChanged();
               progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        } );
    }
}