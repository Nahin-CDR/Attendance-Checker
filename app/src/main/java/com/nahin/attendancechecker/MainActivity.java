package com.nahin.attendancechecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {


    TextView user,admin;
    CardView cv1,cv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();

     //   admin = (ImageView)findViewById( R.id.adminID);

        userArea();
        adminArea();

    }

    private void adminArea() {
        admin = findViewById( R.id.adminID );
        admin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),Admin.class );
                overridePendingTransition( R.anim.slider_1,R.anim.slider_2 );
                startActivity( intent );
            }
        } );
    }

    private void userArea() {
       user = findViewById( R.id.userID);
        user.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),User.class );
                overridePendingTransition( R.anim.slider_1,R.anim.slider_2 );
                startActivity( intent );
            }
        } );
    }
}
