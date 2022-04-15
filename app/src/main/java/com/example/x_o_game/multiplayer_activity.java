package com.example.x_o_game;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class multiplayer_activity extends AppCompatActivity {

    TextView r1c1,r1c2,r1c3;
    TextView r2c1,r2c2,r2c3;
    TextView r3c1,r3c2,r3c3;

    int turn = 0 ;
    boolean sound_stat;
    MediaPlayer music;
    ImageView sound;
    ImageView reload;
//    text_views t  ;
    TextView o_value ;
    TextView x_value ;
    int x_counter,o_counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_activity);
        declaration();
        check();
        player_action();

        music();
        close();

    }

    protected  void onStart() {
        super.onStart();
        reload.setVisibility(View.INVISIBLE);

    }
    protected void onPause() {
        super.onPause();
        music.pause();
        sound.setImageResource(R.drawable.volume_close);
        sound_stat=false;
    }

    void declaration(){
        r1c1=findViewById(R.id.r1_c1);
        r1c2=findViewById(R.id.r1_c2);
        r1c3=findViewById(R.id.r1_c3);
        r2c1=findViewById(R.id.r2_c1);
        r2c2=findViewById(R.id.r2_c2);
        r2c3=findViewById(R.id.r2_c3);
        r3c1=findViewById(R.id.r3_c1);
        r3c2=findViewById(R.id.r3_c2);
        r3c3=findViewById(R.id.r3_c3);
        reload = findViewById(R.id.reload);
        sound = findViewById(R.id.sound);
        music = MediaPlayer.create(this,R.raw.music4);
        x_value =findViewById(R.id.X_value);
        o_value = findViewById(R.id.O_value);
    }

    public void check (){
//        t = new text_views() ;
            if((r1c1.getText().equals("O") && r2c1.getText().equals("O") && r3c1.getText().equals("O"))||(r1c2.getText().equals("O") && r2c2.getText().equals("O") && r3c2.getText().equals("O"))||(r1c3.getText().equals("O") && r2c3.getText().equals("O") && r3c3.getText().equals("O"))||
                    (r1c1.getText().equals("O") && r1c2.getText().equals("O") && r1c3.getText().equals("O"))||(r2c1.getText().equals("O") && r2c2.getText().equals("O") && r2c3.getText().equals("O"))||(r3c1.getText().equals("O") && r3c2.getText().equals("O") && r3c3.getText().equals("O"))||
                    (r1c1.getText().equals("O") && r2c2.getText().equals("O") && r3c3.getText().equals("O"))||(r1c3.getText().equals("O") && r2c2.getText().equals("O") && r3c1.getText().equals("O"))
            ){
                o_counter+=1;
//                o_value = findViewById(R.id.O_value);
                o_value.setText(String.valueOf(o_counter));
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Winner");
                dialog.setMessage("O_Player is Winner");
                dialog.setIcon(R.drawable.green_heart);
                dialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        r1c1.setText("");
                        r1c2.setText("");
                        r1c3.setText("");
                        r2c1.setText("");
                        r2c2.setText("");
                        r2c3.setText("");
                        r3c1.setText("");
                        r3c2.setText("");
                        r3c3.setText("");
                        reload.setVisibility(View.INVISIBLE);
                    }
                });
                dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        music.release();
                        finish();
                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        restart();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();

            }else
            if( (r1c1.getText().equals("X") && r2c1.getText().equals("X") && r3c1.getText().equals("X"))||(r1c2.getText().equals("X") && r2c2.getText().equals("X") && r3c2.getText().equals("X"))||(r1c3.getText().equals("X") && r2c3.getText().equals("X") && r3c3.getText().equals("X"))||
                    (r1c1.getText().equals("X") && r1c2.getText().equals("X") && r1c3.getText().equals("X"))||(r2c1.getText().equals("X") && r2c2.getText().equals("X") && r2c3.getText().equals("X"))||(r3c1.getText().equals("X") && r3c2.getText().equals("X") && r3c3.getText().equals("X"))||
                    (r1c1.getText().equals("X") && r2c2.getText().equals("X") && r3c3.getText().equals("X"))||(r1c3.getText().equals("X") && r2c2.getText().equals("X") && r3c1.getText().equals("X"))
            ){
                x_counter+=1;
//                x_value =findViewById(R.id.X_value);
                x_value.setText(String.valueOf(x_counter));
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Winner");
                dialog.setMessage("X_Player is Winner");
                dialog.setIcon(R.drawable.red_heart);
                dialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        r1c1.setText("");
                        r1c2.setText("");
                        r1c3.setText("");
                        r2c1.setText("");
                        r2c2.setText("");
                        r2c3.setText("");
                        r3c1.setText("");
                        r3c2.setText("");
                        r3c3.setText("");
                        reload.setVisibility(View.INVISIBLE);
                    }
                });
                dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        music.release();
                        finish();
                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        restart();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }else if(!r1c1.getText().equals("")&&!r1c2.getText().equals("")&&!r1c3.getText().equals("")&&!r2c1.getText().equals("")&&!r2c2.getText().equals("")&&!r2c3.getText().equals("")&&!r3c1.getText().equals("")&&!r3c2.getText().equals("")&&!r3c3.getText().equals("")){

             reload();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Draw");
            dialog.setMessage("No One win , Drawing");
                dialog.setIcon(R.drawable.warning);
            dialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    r1c1.setText("");
                    r1c2.setText("");
                    r1c3.setText("");
                    r2c1.setText("");
                    r2c2.setText("");
                    r2c3.setText("");
                    r3c1.setText("");
                    r3c2.setText("");
                    r3c3.setText("");
                    reload.setVisibility(View.INVISIBLE);
                }
            });
            dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    music.release();
                    finish();
                }
            });
            AlertDialog alert = dialog.create();
            alert.show();

            }
        }

    public void player_action(){
//        t=new text_views();
        r1c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r1c1.getText().equals("")){
                    if(turn==0){
                        r1c1.setText("X");
                        r1c1.setTextColor(Color.RED);
                        turn=1;
                    }else{
                        r1c1.setText("O");
                        r1c1.setTextColor(Color.GREEN);
                        turn=0;
                    }
                    check();
                }
            }
        });
        r1c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r1c2.getText().equals("")){
                    if(turn==0){
                        r1c2.setText("X");
                        r1c2.setTextColor(Color.RED);
                        turn=1;
                    }else{
                        r1c2.setText("O");
                        r1c2.setTextColor(Color.GREEN);
                        turn=0;
                    }
                    check();
                }
            }
        });
        r1c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r1c3.getText().equals("")){
                    if(turn==0){
                        r1c3.setText("X");
                        r1c3.setTextColor(Color.RED);
                        turn=1;
                    }else{
                        r1c3.setText("O");
                        r1c3.setTextColor(Color.GREEN);
                        turn=0;
                    }
                    check();
                }
            }
        });
        r2c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r2c1.getText().equals("")){
                    if(turn==0){
                        r2c1.setText("X");
                        r2c1.setTextColor(Color.RED);
                        turn=1;
                    }else{
                        r2c1.setText("O");
                        r2c1.setTextColor(Color.GREEN);
                        turn=0;
                    }
                    check();
                }
            }
        });
        r2c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r2c2.getText().equals("")){
                    if(turn==0){
                        r2c2.setText("X");
                        r2c2.setTextColor(Color.RED);
                        turn=1;
                    }else{
                        r2c2.setText("O");
                        r2c2.setTextColor(Color.GREEN);
                        turn=0;
                    }
                    check();
                }
            }
        });
        r2c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r2c3.getText().equals("")){
                    if(turn==0){
                        r2c3.setText("X");
                        r2c3.setTextColor(Color.RED);
                        turn=1;
                    }else{
                        r2c3.setText("O");
                        r2c3.setTextColor(Color.GREEN);
                        turn=0;
                    }
                    check();
                }
            }
        });
        r3c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r3c1.getText().equals("")){
                    if(turn==0){
                        r3c1.setText("X");
                        r3c1.setTextColor(Color.RED);
                        turn=1;
                    }else{
                        r3c1.setText("O");
                        r3c1.setTextColor(Color.GREEN);
                        turn=0;
                    }
                    check();
                }
            }
        });
        r3c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r3c2.getText().equals("")){
                    if(turn==0){
                        r3c2.setText("X");
                        r3c2.setTextColor(Color.RED);
                        turn=1;
                    }else{
                        r3c2.setText("O");
                        r3c2.setTextColor(Color.GREEN);
                        turn=0;
                    }
                    check();
                }
            }
        });
        r3c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(r3c3.getText().equals("")){
                    if(turn==0){
                        r3c3.setText("X");
                        r3c3.setTextColor(Color.RED);
                        turn=1;
                    }else{
                        r3c3.setText("O");
                        r3c3.setTextColor(Color.GREEN);
                        turn=0;
                    }
                    check();
                }
            }
        });
    }

    public void restart(){

        r1c1.setEnabled(false);
        r1c2.setEnabled(false);
        r1c3.setEnabled(false);
        r2c1.setEnabled(false);
        r2c2.setEnabled(false);
        r2c3.setEnabled(false);
        r3c1.setEnabled(false);
        r3c2.setEnabled(false);
        r3c3.setEnabled(false);

        reload();
        reload.setEnabled(true);

    }

    public void reloaded(){
        r1c1.setText("");
        r1c2.setText("");
        r1c3.setText("");
        r2c1.setText("");
        r2c2.setText("");
        r2c3.setText("");
        r3c1.setText("");
        r3c2.setText("");
        r3c3.setText("");
        r1c1.setEnabled(true);
        r1c2.setEnabled(true);
        r1c3.setEnabled(true);
        r2c1.setEnabled(true);
        r2c2.setEnabled(true);
        r2c3.setEnabled(true);
        r3c1.setEnabled(true);
        r3c2.setEnabled(true);
        r3c3.setEnabled(true);
        reload.setVisibility(View.INVISIBLE);
    }
    public void reload(){
        reload.setVisibility(View.VISIBLE);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloaded();
            }
        });
    }

    public void music(){
        music.start();
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                music.start();
            }
        });

        sound = findViewById(R.id.sound);
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
    public void close(){
        final ImageView close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                music.release();
                finish();
            }
        });
    }
}