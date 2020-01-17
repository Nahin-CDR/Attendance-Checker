package com.nahin.attendancechecker;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

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
                databaseReference.child( newDateTime ).orderByChild( "date" ).equalTo( currentDate.getText().toString() )
                        .orderByChild( "mystatus" ).equalTo( 0 )
                        .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        studentDataList.clear();
                        for (DataSnapshot studentSnap : dataSnapshot.getChildren()){
                            SendData sendData = studentSnap.getValue(SendData.class);
                            studentDataList.add( sendData );
                        }
                        StudentAttendAdapter studentAttendAdapter = new StudentAttendAdapter( Admin.this,studentDataList );
                        mRecyclerView.setAdapter( studentAttendAdapter );
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                } );

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
