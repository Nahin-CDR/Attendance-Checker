package com.nahin.attendancechecker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class StudentAttendAdapter extends ArrayAdapter {
    private Activity context;
    private List<SendData> studentDataLIst;
    public StudentAttendAdapter (Activity context,List<SendData> listStudent){
        super(context,R.layout.custom_list,listStudent);
        this.context = context;
        this.studentDataLIst = listStudent;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listviewstudent = inflater.inflate( R.layout.custom_list,null,true );
        int status;
        TextView viewText_date = listviewstudent.findViewById( R.id.view_time_id );
        TextView viewText_name = listviewstudent.findViewById( R.id.view_name_id );
        TextView viewText_mobile = listviewstudent.findViewById( R.id.view_mobile_id );
        Button viewButton_accept = listviewstudent.findViewById( R.id.accept_button_id );
        Button viewButton_deny = listviewstudent.findViewById( R.id.deny_button_id );

        //get from constructor class

        SendData sendData = studentDataLIst.get( position );
        viewText_date.setText( sendData.getTime() );
        viewText_name.setText( sendData.getMyName() );
        viewText_mobile.setText(sendData.getPhoneNumber());
        status = sendData.getMystatus();
        
        viewButton_accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( context, "Accept", Toast.LENGTH_SHORT ).show();  
            }
        } );
        viewButton_deny.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( context, "Denied", Toast.LENGTH_SHORT ).show();
            }
        } );
        
        
        
        return listviewstudent;
    }
}
