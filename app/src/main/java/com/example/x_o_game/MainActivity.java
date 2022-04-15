package com.example.x_o_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_main);
            Thread start = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(5000);
                        Intent i = new Intent(getApplicationContext(),start_activity.class);
                        startActivity(i);
                        finish();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            };
            start.start();
        } catch (Exception e) {

        }


    }
}