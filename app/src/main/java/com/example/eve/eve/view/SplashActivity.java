package com.example.eve.eve.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.eve.eve.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler splash = new Handler();
        splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref;
                pref = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=pref.edit();
                boolean  firstTime=pref.getBoolean("first", true);
                if(firstTime) {
                    editor.putBoolean("first",false);
                    editor.commit();
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, 2000);
    }


}
