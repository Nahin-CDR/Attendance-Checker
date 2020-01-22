package com.nahin.attendancechecker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {


    CardView user,admin,game;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();
       //getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );


        /*code for banner ads starts **/


        MobileAds.initialize(this, "ca-app-pub-2485705965051323~7459480942");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*code for banner ads ends **/






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
                Intent intent = new Intent( getApplicationContext(),DashbordActivity.class );
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
