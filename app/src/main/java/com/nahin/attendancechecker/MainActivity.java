package com.nahin.attendancechecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {


    CardView user,admin,game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();
       //getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );
        
        userArea();
        adminArea();
        gameArea();

    }

    private void gameArea() {

        game= (CardView)findViewById( R.id.gameID );
        game.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   Intent intent = new Intent( getApplicationContext(),Game.class );
                   startActivity( intent );
                   overridePendingTransition( R.anim.slider_1,R.anim.slider_2 );


            }
        } );


    }

    private void adminArea() {
        admin = findViewById( R.id.adminID );
        admin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),Admin.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slider_1,R.anim.slider_2 );
            }
        } );
    }

    private void userArea() {
       user = findViewById( R.id.userID);
        user.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),User.class );
                startActivity( intent );
                overridePendingTransition( R.anim.slider_1,R.anim.slider_2 );

            }
        } );
    }
}
