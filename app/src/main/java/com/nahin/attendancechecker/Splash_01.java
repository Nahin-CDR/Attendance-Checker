package com.nahin.attendancechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Splash_01 extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=3200;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_01 );

        getSupportActionBar().hide();
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );

        webView=(WebView)findViewById(R.id.prime_source);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/splash_01.html");
        webView.setWebViewClient(new WebViewClient());


        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_01.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slider_1,R.anim.slider_2);
                finish();
            }
        },SPLASH_SCREEN_TIME_OUT);



    }
}
