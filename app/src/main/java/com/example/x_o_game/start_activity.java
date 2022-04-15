package com.example.x_o_game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class start_activity extends AppCompatActivity {

    boolean sound_stat=true;
    MediaPlayer music;
    String difficulty ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);

        TextView w = findViewById(R.id.welcome);
        String text = "Welcome in <font color=\"#F60404\">X</font>-<font color=\"#34E814\">O</font> Game";
        w.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        music();
    }
    protected void onStart() {
        super.onStart();
        sound_stat=true;
        music.start();
        ImageView sound = findViewById(R.id.sound);
        sound.setImageResource(R.drawable.volume_up);
    }
    protected void onPause() {
        super.onPause();
        music.pause();
        ImageView sound = findViewById(R.id.sound);
        sound.setImageResource(R.drawable.volume_close);
        sound_stat=false;
    }

    public  void music(){

        music = MediaPlayer.create(this,R.raw.music4);
        music.start();
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                music.start();
            }
        });

        final ImageView sound = findViewById(R.id.sound);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sound_stat){
                    sound.setImageResource(R.drawable.volume_close);
                    sound_stat=false;
                    music.pause();
                }else{
                    sound.setImageResource(R.drawable.volume_up);
                    sound_stat=true;
                    music.start();
                    music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            music.start();
                        }
                    });
                }

            }
        });

    }

    public void exit (View view ){
//        music.release();
        finish();
    }
    public void single_player (View view ){
        final Intent intent = new Intent(getApplicationContext(),singlePlayer_activity.class);

        final String[] d = getResources().getStringArray(R.array.level);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose The Difficult of Level ");
        builder.setSingleChoiceItems(R.array.level, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                difficulty = d[i];

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
            }
        });
        Dialog D = builder.create();
        D.show();
//        finish();
    }
    public void multi_player (View view ){
            Intent i = new Intent(getApplicationContext(),multiplayer_activity.class);
            startActivity(i);
//            finish();
    }
    public void Online (View view ){
        Intent i = new Intent(getApplicationContext(),onlinelogin_activity.class);
        startActivity(i);
//            finish();
    }
}