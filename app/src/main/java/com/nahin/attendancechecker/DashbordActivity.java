package com.nahin.attendancechecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class DashbordActivity extends AppCompatActivity {
    ImageView image;
    LinearLayout linearLayout;
    ScrollView scrollView;
    CardView cardView;
    EditText admin_mail,admin_pass;
    Button admin_log;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    private AdView mAdView;

    LinearLayout layout_for_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashbord );
        getSupportActionBar().hide();
        image = findViewById( R.id.gif_imageID );
        linearLayout = findViewById( R.id.layout_view_id );
        cardView = findViewById( R.id.cv_view_id );
        scrollView = findViewById( R.id.sv_login_view_id );
        admin_log = findViewById( R.id.admin_login_button );
        admin_mail = findViewById( R.id.admin_email_id );
        admin_pass = findViewById( R.id.admin_password_id );
        mAuth = FirebaseAuth.getInstance();

        layout_for_loading=findViewById( R.id.loading_layoutID );

       // progressDialog = new ProgressDialog( this );
      //  progressDialog.setMessage( "Please wait....." );



        /*code for banner ads starts **/


        MobileAds.initialize(this, "ca-app-pub-2485705965051323~7459480942");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*code for banner ads ends **/







        admin_log.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkConnected() == true) {


                    layout_for_loading.setVisibility( View.VISIBLE );
               // progressDialog.show();
                String email = admin_mail.getText().toString().trim();
                String password = admin_pass.getText().toString();
                if (!TextUtils.isEmpty( email ) && !TextUtils.isEmpty( password )) {

                    if(isNetworkConnected()==true) {


                        mAuth.signInWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    layout_for_loading.setVisibility( View.GONE );
                                    // progressDialog.dismiss();
                                    Intent intent = new Intent( getApplicationContext(), Admin.class );
                                    startActivity( intent );
                                    fileList();
                                } else {
                                    layout_for_loading.setVisibility( View.GONE );
                                    //progressDialog.dismiss();
                                    Toast.makeText( DashbordActivity.this, "User Not Found", Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } );
                    }
                    else {
                        Toast.makeText( DashbordActivity.this, "No Internet ! ", Toast.LENGTH_SHORT ).show();
                    }
                } else {
                    layout_for_loading.setVisibility( View.GONE );
                  //  progressDialog.dismiss();
                    if(email.length()==0 && password.length()!=0)
                    {
                        admin_mail.setError( "Mail Field Empty !" );
                    }
                    else if(password.length()==0 && email.length()!=0)
                    {
                        admin_pass.setError( "Password Field Empty !" );
                    }
                    else{
                        admin_mail.setError( "Mail Field Empty !" );
                        admin_pass.setError( "Password Field Empty !" );

                    }

                   // Toast.makeText( DashbordActivity.this, "Enter email and password", Toast.LENGTH_SHORT ).show();
                }


            }else {
                    Toast.makeText( DashbordActivity.this, "No Internet ! ", Toast.LENGTH_SHORT ).show();
                }
            }
        } );


        cardView.setVisibility( View.VISIBLE );
        linearLayout.setVisibility( View.VISIBLE );

        cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility( View.GONE );
                cardView.setVisibility( View.GONE );
                scrollView.setVisibility( View.VISIBLE );
            }
        } );
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(User.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
