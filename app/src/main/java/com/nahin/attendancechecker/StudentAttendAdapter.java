package com.nahin.attendancechecker;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class StudentAttendAdapter extends RecyclerView.Adapter<StudentAdapterHolder> {


    String fbDate,phone;

    private Context mcontext;
    private List<SendData> studentDataLIst;
    public StudentAttendAdapter (Context mcontext, List<SendData> listStudent){

        //super(context,R.layout.custom_list,listStudent);
        this.mcontext = mcontext;
        this.studentDataLIst = listStudent;

    }

    @NonNull
    @Override
    public StudentAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = LayoutInflater.from( viewGroup.getContext()).inflate( R.layout.custom_list,viewGroup,false );

        //firebase work

        return new StudentAdapterHolder( mView );
    }
    @Override
    public void onBindViewHolder(final StudentAdapterHolder studentAdapterHolder, final int i) {
        studentAdapterHolder.viewText_date.setText( studentDataLIst.get( i ).getTime() );
        studentAdapterHolder.viewText_name.setText( studentDataLIst.get( i ).getMyName() );
        studentAdapterHolder.viewText_mobile.setText( studentDataLIst.get( i ).getPhoneNumber() );
        studentAdapterHolder.status = studentDataLIst.get( i ).getMystatus();
        studentAdapterHolder.firebaseDate = studentDataLIst.get( i ).getDate();


        studentAdapterHolder.viewButton_accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference("StudentList");
                fbDate = studentDataLIst.get( studentAdapterHolder.getAdapterPosition() ).getDate();
                phone = studentDataLIst.get( studentAdapterHolder.getAdapterPosition() ).getPhoneNumber();

                databaseReference.child( fbDate ).child( phone ).child( "mystatus" ).setValue( 1 );


//                Calendar c = Calendar.getInstance();
//
//                SimpleDateFormat dateformat = new SimpleDateFormat("d-M-yyyy");
//
//                String dateTime = dateformat.format(c.getTime());
//
//                FirebaseDatabase database =FirebaseDatabase.getInstance();
//
//                DatabaseReference reference = database.getReference("StudentList");
//
//                reference.child( dateTime ).child( "123" ).child( "mystatus" ).setValue( 1 );
//
//                Toast.makeText( mcontext, "Request Accepted !", Toast.LENGTH_SHORT ).show();

            }
        } );

        studentAdapterHolder.viewButton_deny.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Calendar c = Calendar.getInstance();
//
//                SimpleDateFormat dateformat = new SimpleDateFormat("d-M-yyyy");
//
//                String dateTime = dateformat.format(c.getTime());
//
//                FirebaseDatabase database =FirebaseDatabase.getInstance();
//
//                DatabaseReference reference = database.getReference("StudentList");
//
//
//                reference.child( dateTime ).child( "2569" ).child( "mystatus" ).setValue( 2 );
//
//
//                Toast.makeText( mcontext, "Denied!", Toast.LENGTH_SHORT ).show();
                DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference("StudentList");
                fbDate = studentDataLIst.get( studentAdapterHolder.getAdapterPosition() ).getDate();
                phone = studentDataLIst.get( studentAdapterHolder.getAdapterPosition() ).getPhoneNumber();

                databaseReference.child( fbDate ).child( phone ).child( "mystatus" ).setValue( 2 );

                Toast.makeText( mcontext, "Rejected !", Toast.LENGTH_SHORT ).show();


            }
        } );

    }
    @Override
    public int getItemCount() {

        return studentDataLIst.size();
    }
    public void filteredList(ArrayList<SendData> sendData){
        studentDataLIst = sendData;
        notifyDataSetChanged();
    }

}

class StudentAdapterHolder extends RecyclerView.ViewHolder{

    TextView viewText_date,viewText_name,viewText_mobile;
    Button viewButton_accept,viewButton_deny;
    int status;
    String firebaseDate;

    public StudentAdapterHolder(View itemView){
        super (itemView);
        viewText_date = itemView.findViewById( R.id.view_time_id );
        viewText_name = itemView.findViewById( R.id.view_name_id );
        viewText_mobile = itemView.findViewById( R.id.view_mobile_id );
        viewButton_accept = itemView.findViewById( R.id.accept_button_id );
        viewButton_deny = itemView.findViewById( R.id.deny_button_id );
    }

}