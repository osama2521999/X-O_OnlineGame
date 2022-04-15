package com.example.x_o_game;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class singlePlayer_activity extends AppCompatActivity /*implements single_level_dialog.single_dialogListner*/  {

    String difficulty;
//    public singlePlayer_activity(String difficulty){
//        this.difficulty = difficulty;
//    }

    TextView r1c1,r1c2,r1c3;
    TextView r2c1,r2c2,r2c3;
    TextView r3c1,r3c2,r3c3;

    int turn = 0 ;
    boolean sound_stat;
    MediaPlayer music;
    ImageView sound;
    ImageView reload;

    TextView o_value,x_value ;
    int x_counter,o_counter = 0;

    int Random_Stat=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_activity);

//        dialog();
        difficulty= getIntent().getExtras().get("difficulty").toString();
//        Toast.makeText(this,difficulty,Toast.LENGTH_SHORT).show();
        declaration();
        music();
        close();

        player_action();
    }

    protected void  onStart() {
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
        o_value = findViewById(R.id.O_value);
        x_value =findViewById(R.id.X_value);
    }

    public void check (){
//        t = new text_views() ;
        if( (r1c1.getText().equals("X") && r2c1.getText().equals("X") && r3c1.getText().equals("X"))||(r1c2.getText().equals("X") && r2c2.getText().equals("X") && r3c2.getText().equals("X"))||(r1c3.getText().equals("X") && r2c3.getText().equals("X") && r3c3.getText().equals("X"))||
                (r1c1.getText().equals("X") && r1c2.getText().equals("X") && r1c3.getText().equals("X"))||(r2c1.getText().equals("X") && r2c2.getText().equals("X") && r2c3.getText().equals("X"))||(r3c1.getText().equals("X") && r3c2.getText().equals("X") && r3c3.getText().equals("X"))||
                (r1c1.getText().equals("X") && r2c2.getText().equals("X") && r3c3.getText().equals("X"))||(r1c3.getText().equals("X") && r2c2.getText().equals("X") && r3c1.getText().equals("X"))
        ){
            x_counter+=1;
            x_value.setText(String.valueOf(x_counter));
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Winner");
            dialog.setMessage("X_Player is Winner");
            dialog.setIcon(R.drawable.red_heart);
            dialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    reloaded();
                }
            });
            dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    music.release();
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
            turn=-1;
        }else
            if((r1c1.getText().equals("O") && r2c1.getText().equals("O") && r3c1.getText().equals("O"))||(r1c2.getText().equals("O") && r2c2.getText().equals("O") && r3c2.getText().equals("O"))||(r1c3.getText().equals("O") && r2c3.getText().equals("O") && r3c3.getText().equals("O"))||
                (r1c1.getText().equals("O") && r1c2.getText().equals("O") && r1c3.getText().equals("O"))||(r2c1.getText().equals("O") && r2c2.getText().equals("O") && r2c3.getText().equals("O"))||(r3c1.getText().equals("O") && r3c2.getText().equals("O") && r3c3.getText().equals("O"))||
                (r1c1.getText().equals("O") && r2c2.getText().equals("O") && r3c3.getText().equals("O"))||(r1c3.getText().equals("O") && r2c2.getText().equals("O") && r3c1.getText().equals("O"))
        ){
            o_counter+=1;
            o_value.setText(String.valueOf(o_counter));
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Winner");
            dialog.setMessage("O_Player is Winner");
            dialog.setIcon(R.drawable.green_heart);
            dialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    reloaded();
                }
            });
            dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    music.release();
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
            turn=-1;
        }else
            if(!r1c1.getText().equals("")&&!r1c2.getText().equals("")&&!r1c3.getText().equals("")&&!r2c1.getText().equals("")&&!r2c2.getText().equals("")&&!r2c3.getText().equals("")&&!r3c1.getText().equals("")&&!r3c2.getText().equals("")&&!r3c3.getText().equals("")){

            reload();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Draw");
            dialog.setMessage("No One win , Drawing");
            dialog.setIcon(R.drawable.warning);
            dialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    reloaded();
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
            turn=-1;
        }
    }

    public void player_action(){

        r1c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn==0){
                    if(r1c1.getText().equals("")){
                        r1c1.setText("X");
                        r1c1.setTextColor(Color.RED);
                        turn=1;
                        check();
                        C_player();
                    }
                }else {
                    toast();
                }
            }
        });
        r1c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn==0){
                    if(r1c2.getText().equals("")){
                        r1c2.setText("X");
                        r1c2.setTextColor(Color.RED);
                        turn=1;
                        check();
                        C_player();
                    }
                }else {
                    toast();
                }
            }
        });
        r1c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn==0){
                    if(r1c3.getText().equals("")){
                        r1c3.setText("X");
                        r1c3.setTextColor(Color.RED);
                        turn=1;
                        check();
                        C_player();
                    }
                }else {
                    toast();
                }
            }
        });
        r2c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn==0){
                    if(r2c1.getText().equals("")){
                        r2c1.setText("X");
                        r2c1.setTextColor(Color.RED);
                        turn=1;
                        check();
                        C_player();
                    }
                }else {
                    toast();
                }
            }
        });
        r2c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn==0){
                    if(r2c2.getText().equals("")){
                        r2c2.setText("X");
                        r2c2.setTextColor(Color.RED);
                        turn=1;
                        check();
                        C_player();

                    }
                }else {
                    toast();
                }
            }
        });
        r2c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn==0){
                    if(r2c3.getText().equals("")){
                        r2c3.setText("X");
                        r2c3.setTextColor(Color.RED);
                        turn=1;
                        check();
                        C_player();
                    }
                }else {
                    toast();
                }
            }
        });
        r3c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn==0){
                    if(r3c1.getText().equals("")){
                        r3c1.setText("X");
                        r3c1.setTextColor(Color.RED);
                        turn=1;
                        check();
                        C_player();
                    }
                }else {
                    toast();
                }
            }
        });
        r3c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn==0){
                    if(r3c2.getText().equals("")){
                        r3c2.setText("X");
                        r3c2.setTextColor(Color.RED);
                        turn=1;
                        check();
                        C_player();
                    }
                }else {
                    toast();
                }
            }
        });
        r3c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turn==0){
                    if(r3c3.getText().equals("")){
                        r3c3.setText("X");
                        r3c3.setTextColor(Color.RED);
                        turn=1;
                        check();
                        C_player();
                    }
                }else {
                    toast();
                }
            }
        });

    }

    public void defense(){

        if((r1c1.getText().equals("X") && r1c2.getText().equals("X")&&r1c3.getText().equals("")) || ( r3c3.getText().equals("X") && r2c3.getText().equals("X")&&r1c3.getText().equals("") )|| (r3c1.getText().equals("X") && r2c2.getText().equals("X")&&r1c3.getText().equals("")) ){
            r1c3.setText("O");
            r1c3.setTextColor(Color.GREEN);
        }else
        if((r2c1.getText().equals("X") && r2c2.getText().equals("X") &&r2c3.getText().equals("")) || (r1c3.getText().equals("X") && r3c3.getText().equals("X")&&r2c3.getText().equals("") ) ){
            r2c3.setText("O");
            r2c3.setTextColor(Color.GREEN);
        }else
        if((r3c1.getText().equals("X") && r3c2.getText().equals("X")&&r3c3.getText().equals("") ) || ( r1c3.getText().equals("X") && r2c3.getText().equals("X")&&r3c3.getText().equals("") ) || (r1c1.getText().equals("X") && r2c2.getText().equals("X"))&&r3c3.getText().equals("") )  {
            r3c3.setText("O");
            r3c3.setTextColor(Color.GREEN);
        }else
        if((r1c3.getText().equals("X") && r1c2.getText().equals("X")&&r1c1.getText().equals("")) ||(r3c1.getText().equals("X")  && r2c1.getText().equals("X")&&r1c1.getText().equals("")) || (r3c3.getText().equals("X") && r2c2.getText().equals("X")&&r1c1.getText().equals("") )  ){
            r1c1.setText("O");
            r1c1.setTextColor(Color.GREEN);
        }else
        if((r2c3.getText().equals("X") && r2c2.getText().equals("X")&&r2c1.getText().equals("")) ||( r1c1.getText().equals("X") && r3c1.getText().equals("X")&&r2c1.getText().equals("") ) ){
            r2c1.setText("O");
            r2c1.setTextColor(Color.GREEN);
        }else
        if((r3c3.getText().equals("X") && r3c2.getText().equals("X")&&r3c1.getText().equals(""))|| (r1c1.getText().equals("X") && r2c1.getText().equals("X")&&r3c1.getText().equals("")) || (r1c3.getText().equals("X") && r2c2.getText().equals("X")&&r3c1.getText().equals("")) ){
            r3c1.setText("O");
            r3c1.setTextColor(Color.GREEN);
        }else
        if(( r1c2.getText().equals("X") && r2c2.getText().equals("X")&&r3c2.getText().equals("")) || (r3c1.getText().equals("X") && r3c3.getText().equals("X")&&r3c2.getText().equals("")) ){
            r3c2.setText("O");
            r3c2.setTextColor(Color.GREEN);
        }else
        if((r3c2.getText().equals("X") && r2c2 .getText().equals("X")&&r1c2.getText().equals("")) ||(r1c1.getText().equals("X") && r1c3.getText().equals("X")&&r1c2.getText().equals("")) ){
            r1c2.setText("O");
            r1c2.setTextColor(Color.GREEN);
        }else
        if((r1c2.getText().equals("X") && r3c2.getText().equals("X")&&r2c2.getText().equals(""))|| (r2c1 .getText().equals("X") && r2c3.getText().equals("X")&&r2c2.getText().equals("")) || (r1c1.getText().equals("X") && r3c3.getText().equals("X")&&r2c2.getText().equals("")) || (r1c3.getText().equals("X") && r3c1.getText().equals("X")&&r2c2.getText().equals("")) ){
            r2c2.setText("O");
            r2c2.setTextColor(Color.GREEN);
        }else{
            r_c();
        }

    }
    public void attack(){

        if((r1c1.getText().equals("O") && r1c2.getText().equals("O") && r1c3.getText().equals("") ) || (r3c1.getText().equals("O") && r2c2.getText().equals("O") && r1c3.getText().equals(""))  ||(r3c3.getText().equals("O") && r2c3.getText().equals("O")&& r1c3.getText().equals(""))){
            r1c3.setText("O");
            r1c3.setTextColor(Color.GREEN);
        }else
        if((r2c1.getText().equals("O") && r2c2.getText().equals("O")&& r2c3.getText().equals("")) ||(r1c3.getText().equals("O") && r3c3.getText().equals("O") && r2c3.getText().equals("") ) ){
            r2c3.setText("O");
            r2c3.setTextColor(Color.GREEN);
        }else
        if((r3c1.getText().equals("O") && r3c2.getText().equals("O") && r3c3.getText().equals("")) || (r1c3.getText().equals("O") && r2c3.getText().equals("O")&&r3c3.getText().equals("") ) ||(r1c1.getText().equals("O") && r2c2.getText().equals("O")&&r3c3.getText().equals("")) ){
            r3c3.setText("O");
            r3c3.setTextColor(Color.GREEN);
        } else
        if((r1c1.getText().equals("O") && r2c1.getText().equals("O")&&r3c1.getText().equals("") ) || (r1c3.getText().equals("O") && r2c2.getText().equals("O")&&r3c1.getText().equals("") ) || (r3c3.getText().equals("O") && r3c2.getText().equals("O")&&r3c1.getText().equals("") ) ){
            r3c1.setText("O");
            r3c1.setTextColor(Color.GREEN);
        }else
        if((r1c2.getText().equals("O") && r2c2.getText().equals("O")&&r3c2.getText().equals("")) || (r3c1.getText().equals("O") && r3c3.getText().equals("O") &&r3c2.getText().equals("")) ){
            r3c2.setText("O");
            r3c2.setTextColor(Color.GREEN);
        }else
        if((r1c1.getText().equals("O") && r3c1.getText().equals("O")&&r2c1.getText().equals("")) || (r2c3.getText().equals("O") && r2c2.getText().equals("O")&&r2c1.getText().equals("")) ){
            r2c1.setText("O");
            r2c1.setTextColor(Color.GREEN);
        }else
        if((r1c2.getText().equals("O") && r3c2.getText().equals("O")&&r2c2.getText().equals("")) || (r2c1.getText().equals("O") && r2c3.getText().equals("O")&&r2c2.getText().equals("")) || (r1c1.getText().equals("O") && r3c3.getText().equals("O")&&r2c2.getText().equals("")) || (r1c3.getText().equals("O") && r3c1.getText().equals("O")&&r2c2.getText().equals("")) ){
            r2c2.setText("O");
            r2c2.setTextColor(Color.GREEN);
        }else
        if((r1c1.getText().equals("O") && r1c3.getText().equals("O")&&r1c2.getText().equals("")) || (r3c2.getText().equals("O") && r2c2.getText().equals("O")&&r1c2.getText().equals("")) ){
            r1c2.setText("O");
            r1c2.setTextColor(Color.GREEN);
        }else
        if((r3c3.getText().equals("O") && r2c2.getText().equals("O")&&r1c1.getText().equals("")) || (r1c2.getText().equals("O") && r1c3.getText().equals("O")&&r1c1.getText().equals("")) || (r3c1.getText().equals("O") && r2c1.getText().equals("O")&&r1c1.getText().equals("")) ){
            r1c1.setText("O");
            r1c1.setTextColor(Color.GREEN);
        }else{
            defense();
        }

    }
    public void r_c(){
        //Random_Stat= (int) (Math.random()*((MAX-MINI)+MINI))+MINI;
        Random_Stat= (int) (Math.random()*((4-1)+1))+1;

        if(difficulty.equals("Hard")){

            if(r2c2.getText().equals("")){
                r2c2.setText("O");
                r2c2.setTextColor(Color.GREEN);
            }else if(Random_Stat==1){
                if(r1c1.getText().equals("")){
                    r1c1.setText("O");
                    r1c1.setTextColor(Color.GREEN);
                }else if(r1c3.getText().equals("")){
                    r1c3.setText("O");
                    r1c3.setTextColor(Color.GREEN);
                }else if(r3c1.getText().equals("")){
                    r3c1.setText("O");
                    r3c1.setTextColor(Color.GREEN);
                }else if(r3c3.getText().equals("")){
                    r3c3.setText("O");
                    r3c3.setTextColor(Color.GREEN);
                }
            }else if(Random_Stat==2){
                if(r1c3.getText().equals("")){
                    r1c3.setText("O");
                    r1c3.setTextColor(Color.GREEN);
                }else if(r3c1.getText().equals("")){
                    r3c1.setText("O");
                    r3c1.setTextColor(Color.GREEN);
                }else if(r3c3.getText().equals("")){
                    r3c3.setText("O");
                    r3c3.setTextColor(Color.GREEN);
                }else if(r1c1.getText().equals("")){
                    r1c1.setText("O");
                    r1c1.setTextColor(Color.GREEN);
                }
            }else if(Random_Stat==3){
                if(r3c1.getText().equals("")){
                    r3c1.setText("O");
                    r3c1.setTextColor(Color.GREEN);
                }else if(r3c3.getText().equals("")){
                    r3c3.setText("O");
                    r3c3.setTextColor(Color.GREEN);
                }else if(r1c1.getText().equals("")){
                    r1c1.setText("O");
                    r1c1.setTextColor(Color.GREEN);
                }else if(r1c3.getText().equals("")){
                    r1c3.setText("O");
                    r1c3.setTextColor(Color.GREEN);
                }
            }else if(Random_Stat==4){
                if(r3c3.getText().equals("")){
                    r3c3.setText("O");
                    r3c3.setTextColor(Color.GREEN);
                }else if(r1c1.getText().equals("")){
                    r1c1.setText("O");
                    r1c1.setTextColor(Color.GREEN);
                }else if(r1c3.getText().equals("")){
                    r1c3.setText("O");
                    r1c3.setTextColor(Color.GREEN);
                }else if(r3c1.getText().equals("")){
                    r3c1.setText("O");
                    r3c1.setTextColor(Color.GREEN);
                }
            }

        }
        else if(difficulty.equals("Medium")){

            if(r2c2.getText().equals("")){
                r2c2.setText("O");
                r2c2.setTextColor(Color.GREEN);
            }else if(Random_Stat==1){
                if(r1c2.getText().equals("")){
                    r1c2.setText("O");
                    r1c2.setTextColor(Color.GREEN);
                }else if(r2c3.getText().equals("")){
                    r2c3.setText("O");
                    r2c3.setTextColor(Color.GREEN);
                }else if(r2c1.getText().equals("")){
                    r2c1.setText("O");
                    r2c1.setTextColor(Color.GREEN);
                }else if(r3c2.getText().equals("")){
                    r3c2.setText("O");
                    r3c2.setTextColor(Color.GREEN);
                }
            }else if(Random_Stat==2){
                if(r2c3.getText().equals("")){
                    r2c3.setText("O");
                    r2c3.setTextColor(Color.GREEN);
                }else if(r2c1.getText().equals("")){
                    r2c1.setText("O");
                    r2c1.setTextColor(Color.GREEN);
                }else if(r3c2.getText().equals("")){
                    r3c2.setText("O");
                    r3c2.setTextColor(Color.GREEN);
                }else if(r1c2.getText().equals("")){
                    r1c2.setText("O");
                    r1c2.setTextColor(Color.GREEN);
                }
            }else if(Random_Stat==3){
                 if(r2c1.getText().equals("")){
                    r2c1.setText("O");
                    r2c1.setTextColor(Color.GREEN);
                }else if(r3c2.getText().equals("")){
                    r3c2.setText("O");
                    r3c2.setTextColor(Color.GREEN);
                }else if(r1c2.getText().equals("")){
                    r1c2.setText("O");
                    r1c2.setTextColor(Color.GREEN);
                }else if(r2c3.getText().equals("")){
                    r2c3.setText("O");
                    r2c3.setTextColor(Color.GREEN);
                }
            }else if(Random_Stat==4){
                if(r3c2.getText().equals("")){
                    r3c2.setText("O");
                    r3c2.setTextColor(Color.GREEN);
                }else if(r1c2.getText().equals("")){
                    r1c2.setText("O");
                    r1c2.setTextColor(Color.GREEN);
                }else if(r2c3.getText().equals("")){
                    r2c3.setText("O");
                    r2c3.setTextColor(Color.GREEN);
                }else if(r2c1.getText().equals("")){
                    r2c1.setText("O");
                    r2c1.setTextColor(Color.GREEN);
                }
            }

        }
        else if(difficulty.equals("Easy")){

            if(Random_Stat==1){
                if(r2c1.getText().equals("")){
                    r2c1.setText("O");
                    r2c1.setTextColor(Color.GREEN);
                }else if(r2c3.getText().equals("")){
                    r2c3.setText("O");
                    r2c3.setTextColor(Color.GREEN);
                }else if(r1c2.getText().equals("")){
                    r1c2.setText("O");
                    r1c2.setTextColor(Color.GREEN);
                }else if(r3c2.getText().equals("")){
                    r3c2.setText("O");
                    r3c2.setTextColor(Color.GREEN);
                }
            }else if(Random_Stat==2){
                if(r2c3.getText().equals("")){
                    r2c3.setText("O");
                    r2c3.setTextColor(Color.GREEN);
                }else if(r1c2.getText().equals("")){
                    r1c2.setText("O");
                    r1c2.setTextColor(Color.GREEN);
                }else if(r3c2.getText().equals("")){
                    r3c2.setText("O");
                    r3c2.setTextColor(Color.GREEN);
                }else if(r2c1.getText().equals("")){
                    r2c1.setText("O");
                    r2c1.setTextColor(Color.GREEN);
                }
            }else if(Random_Stat==3){
                if(r1c2.getText().equals("")){
                    r1c2.setText("O");
                    r1c2.setTextColor(Color.GREEN);
                }else if(r3c2.getText().equals("")){
                    r3c2.setText("O");
                    r3c2.setTextColor(Color.GREEN);
                }else if(r2c1.getText().equals("")){
                    r2c1.setText("O");
                    r2c1.setTextColor(Color.GREEN);
                }else if(r2c3.getText().equals("")){
                    r2c3.setText("O");
                    r2c3.setTextColor(Color.GREEN);
                }
            }else if(Random_Stat==4){
                 if(r3c2.getText().equals("")){
                    r3c2.setText("O");
                    r3c2.setTextColor(Color.GREEN);
                }else if(r2c1.getText().equals("")){
                    r2c1.setText("O");
                    r2c1.setTextColor(Color.GREEN);
                }else if(r2c3.getText().equals("")){
                    r2c3.setText("O");
                    r2c3.setTextColor(Color.GREEN);
                }else if(r1c2.getText().equals("")){
                    r1c2.setText("O");
                    r1c2.setTextColor(Color.GREEN);
                }
            }
        }

    }

    public void C_player(){

        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                    if(turn==1){
                        turn=0;
                        attack();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                check();
                            }
                        });
                    }

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        t.start();
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
        turn=0;
        Random_Stat=0;
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
    public void toast(){
        Toast.makeText(this ,"Not Your Turn",Toast.LENGTH_SHORT).show();
    }
    public void music(){
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