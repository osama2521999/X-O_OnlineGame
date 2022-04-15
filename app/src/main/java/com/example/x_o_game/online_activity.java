package com.example.x_o_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;

public class online_activity extends AppCompatActivity {


    TextView r1c1,r1c2,r1c3;
    TextView r2c1,r2c2,r2c3;
    TextView r3c1,r3c2,r3c3;
    TextView x_player,o_player;

    TextView o_value,x_value ;
    int x_counter,o_counter = 0;


    int turn = 0 ;
    boolean sound_stat;
    MediaPlayer music;
    ImageView sound;
    ImageView reload;
    //    text_views t  ;


    String playerSession = "";
    String userName = "";
    String otherPlayer = "";
    String loginUID = "";
    String requestType = "";

    String myName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("activation");
    DatabaseReference myRef2 = database.getReference("Board");
    DatabaseReference myRef3 = database.getReference("Other_player_checking");
    DatabaseReference myRef4 = database.getReference("Score");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_activity);

        userName = getIntent().getExtras().get("user_name").toString();
        loginUID = getIntent().getExtras().get("login_uid").toString();
        otherPlayer = getIntent().getExtras().get("other_player").toString();
        requestType = getIntent().getExtras().get("request_type").toString();
        playerSession = getIntent().getExtras().get("player_session").toString();


//        myRef.child("active").child("true").setValue(userName);
//        myRef.child("active").child("false").setValue(otherPlayer);
        myRef.child("true").setValue(userName);
        myRef.child("false").setValue(otherPlayer);
        myRef.child("active").child("Playing_type").setValue("X");
        myRef.child("active").child("playing_type_counter").setValue("1");

//        myRef.child("active").child("false").child("Playing_type").setValue("O");
        myName=userName;

        myRef3.child("Check").setValue("false");
        myRef.child("Check").setValue("");
//        myRef3.child("X_player_score").setValue("0");
//        myRef3.child("O_player_score").setValue("0");

//        myRef.child("rrrrr").setValue(requestType);


//        myRef2.child(userName).setValue("X");
//        myRef2.child(otherPlayer).setValue("O");

        declaration();
        x_player.setText(userName+":");
        o_player.setText(otherPlayer+":");

//        player_action();
//        Other_player_action();




        myRef4.child("X_player_score").setValue("0");
        myRef4.child("O_player_score").setValue("0");

        music();
        close();

    }



    protected  void onStart() {
        super.onStart();
        reload.setVisibility(View.INVISIBLE);
//        myRef.child("active").child(userName).setValue("true");
//        myRef.child("active").child(otherPlayer).setValue("false");
        stag_players_names_color();
        player_action();
        board();
//        other_check();
//        Toast.makeText(this, String.valueOf(o_player.getTextColors().getDefaultColor())+"    "+String.valueOf(Color.RED) ,Toast.LENGTH_SHORT).show();



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

        x_player=findViewById(R.id.x_player);
        o_player=findViewById(R.id.o_player);

        reload = findViewById(R.id.reload);
        sound = findViewById(R.id.sound);
        music = MediaPlayer.create(this,R.raw.music4);
        x_value =findViewById(R.id.X_value);
        o_value = findViewById(R.id.O_value);
        myRef2.child("r1c1").setValue("");
        myRef2.child("r1c2").setValue("");
        myRef2.child("r1c3").setValue("");
        myRef2.child("r2c1").setValue("");
        myRef2.child("r2c2").setValue("");
        myRef2.child("r2c3").setValue("");
        myRef2.child("r3c1").setValue("");
        myRef2.child("r3c2").setValue("");
        myRef2.child("r3c3").setValue("");
    }

    public void check (){
//        t = new text_views() ;

        if((r1c1.getText().equals("O") && r2c1.getText().equals("O") && r3c1.getText().equals("O"))||(r1c2.getText().equals("O") && r2c2.getText().equals("O") && r3c2.getText().equals("O"))||(r1c3.getText().equals("O") && r2c3.getText().equals("O") && r3c3.getText().equals("O"))||
                (r1c1.getText().equals("O") && r1c2.getText().equals("O") && r1c3.getText().equals("O"))||(r2c1.getText().equals("O") && r2c2.getText().equals("O") && r2c3.getText().equals("O"))||(r3c1.getText().equals("O") && r3c2.getText().equals("O") && r3c3.getText().equals("O"))||
                (r1c1.getText().equals("O") && r2c2.getText().equals("O") && r3c3.getText().equals("O"))||(r1c3.getText().equals("O") && r2c2.getText().equals("O") && r3c1.getText().equals("O"))
        ){
//            read_score("O");
            o_counter+=1;
            myRef4.child("O_player_score").setValue(String.valueOf(o_counter));
            if(x_player.getTextColors().getDefaultColor()==(Color.GREEN)){
                o_value.setText(String.valueOf(o_counter));
            }else if(o_player.getTextColors().getDefaultColor()==(Color.RED)){
                x_value.setText(String.valueOf(o_counter));
            }
//            o_value.setText(String.valueOf(o_counter));
            AlertDialog.Builder dialog = new AlertDialog.Builder(online_activity.this);
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

            myRef3.child("Check").setValue("true");
//            myRef.child("Check").setValue(null);
            myRef.child("Check").setValue(otherPlayer);
//            Other_player_checking();

        }else
        if( (r1c1.getText().equals("X") && r2c1.getText().equals("X") && r3c1.getText().equals("X"))||(r1c2.getText().equals("X") && r2c2.getText().equals("X") && r3c2.getText().equals("X"))||(r1c3.getText().equals("X") && r2c3.getText().equals("X") && r3c3.getText().equals("X"))||
                (r1c1.getText().equals("X") && r1c2.getText().equals("X") && r1c3.getText().equals("X"))||(r2c1.getText().equals("X") && r2c2.getText().equals("X") && r2c3.getText().equals("X"))||(r3c1.getText().equals("X") && r3c2.getText().equals("X") && r3c3.getText().equals("X"))||
                (r1c1.getText().equals("X") && r2c2.getText().equals("X") && r3c3.getText().equals("X"))||(r1c3.getText().equals("X") && r2c2.getText().equals("X") && r3c1.getText().equals("X"))
        )
        {
//            read_score("X");
            x_counter+=1;
            myRef4.child("X_player_score").setValue(String.valueOf(x_counter));
            if(x_player.getTextColors().getDefaultColor()==(Color.RED)){
                x_value.setText(String.valueOf(x_counter));
            }else if(o_player.getTextColors().getDefaultColor()==(Color.RED)){
                o_value.setText(String.valueOf(x_counter));
            }
//            x_value.setText(String.valueOf(x_counter));
            AlertDialog.Builder dialog = new AlertDialog.Builder(online_activity.this);
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

            myRef3.child("Check").setValue("true");
//            myRef.child("Check").setValue(null);
            myRef.child("Check").setValue(otherPlayer);
//            Other_player_checking();

        }else if(!r1c1.getText().equals("")&&!r1c2.getText().equals("")&&!r1c3.getText().equals("")&&!r2c1.getText().equals("")&&!r2c2.getText().equals("")&&!r2c3.getText().equals("")&&!r3c1.getText().equals("")&&!r3c2.getText().equals("")&&!r3c3.getText().equals("")){

            reload();
            AlertDialog.Builder dialog = new AlertDialog.Builder(online_activity.this);
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

        }


//        if((dataSnapshot.child("r1c1").getValue(String.class).equals("X") && dataSnapshot.child("r2c1").getValue(String.class).equals("X") && dataSnapshot.child("r3c1").getValue(String.class).equals("X"))||( dataSnapshot.child("r1c2").getValue(String.class).equals("X") && dataSnapshot.child("r2c2").getValue(String.class).equals("X") && dataSnapshot.child("r3c2").getValue(String.class).equals("X") )||(dataSnapshot.child("r1c3").getValue(String.class).equals("X") && dataSnapshot.child("r2c3").getValue(String.class).equals("X") &&dataSnapshot.child("r3c3").getValue(String.class).equals("X") )||
//                (dataSnapshot.child("r1c1").getValue(String.class).equals("X") &&dataSnapshot.child("r1c2").getValue(String.class).equals("X")  && dataSnapshot.child("r1c3").getValue(String.class).equals("X"))||(dataSnapshot.child("r2c1").getValue(String.class).equals("X") && dataSnapshot.child("r2c2").getValue(String.class).equals("X")  &&dataSnapshot.child("r2c3").getValue(String.class).equals("X") )||(dataSnapshot.child("r3c1").getValue(String.class).equals("X") && dataSnapshot.child("r3c2").getValue(String.class).equals("X") && dataSnapshot.child("r3c3").getValue(String.class).equals("X") )||
//                (dataSnapshot.child("r1c1").getValue(String.class).equals("X") && dataSnapshot.child("r2c2").getValue(String.class).equals("X")&& dataSnapshot.child("r3c3").getValue(String.class).equals("X"))||(dataSnapshot.child("r1c3").getValue(String.class).equals("X") && dataSnapshot.child("r2c2").getValue(String.class).equals("X") && dataSnapshot.child("r3c1").getValue(String.class).equals("X"))
//        )

    }

    public void  Other_player_checking(){
        myRef.child("Check").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot1) {

                myRef3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot2) {
                        myRef4.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                if(dataSnapshot1.getValue(String.class).equals(userName) && dataSnapshot2.child("Check").getValue().equals("true")){
//
//                                    Toast.makeText(getApplicationContext(),dataSnapshot1.getValue(String.class),Toast.LENGTH_SHORT).show();

                                    if((r1c1.getText().equals("O") && r2c1.getText().equals("O") && r3c1.getText().equals("O"))||(r1c2.getText().equals("O") && r2c2.getText().equals("O") && r3c2.getText().equals("O"))||(r1c3.getText().equals("O") && r2c3.getText().equals("O") && r3c3.getText().equals("O"))||
                                            (r1c1.getText().equals("O") && r1c2.getText().equals("O") && r1c3.getText().equals("O"))||(r2c1.getText().equals("O") && r2c2.getText().equals("O") && r2c3.getText().equals("O"))||(r3c1.getText().equals("O") && r3c2.getText().equals("O") && r3c3.getText().equals("O"))||
                                            (r1c1.getText().equals("O") && r2c2.getText().equals("O") && r3c3.getText().equals("O"))||(r1c3.getText().equals("O") && r2c2.getText().equals("O") && r3c1.getText().equals("O"))
                                    ){
                                        o_counter+=1;
                                        if(x_player.getTextColors().getDefaultColor()==(Color.GREEN)){
                                            o_value.setText(o_counter);
                                        }else if(o_player.getTextColors().getDefaultColor()==(Color.RED)){
                                            x_value.setText(x_counter);
                                        }
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(online_activity.this);
                                        dialog.setTitle("Winner");
                                        dialog.setMessage("O_Player is Winner");
                                        dialog.setIcon(R.drawable.green_heart);
                                        dialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                        myRef.child("Check").setValue(null);
                                                reloaded();
                                            }
                                        });
                                        dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
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
                                    )
                                    {
                                        x_counter+=1;
                                        if(x_player.getTextColors().getDefaultColor()==(Color.RED)){
                                            x_value.setText(x_counter);
                                        }else if(o_player.getTextColors().getDefaultColor()==(Color.RED)){
                                            o_value.setText(o_counter);
                                        }
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(online_activity.this);
                                        dialog.setTitle("Winner");
                                        dialog.setMessage("X_Player is Winner");
                                        dialog.setIcon(R.drawable.red_heart);
                                        dialog.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                        myRef.child("Check").setValue(null);
                                                reloaded();
                                            }
                                        });
                                        dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
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

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void board(){

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("r1c1").getValue(String.class).equals("X")){
                    r1c1.setText("X");
                    r1c1.setTextColor(Color.RED);

                }else if(dataSnapshot.child("r1c1").getValue(String.class).equals("O")) {
                    r1c1.setText("O");
                    r1c1.setTextColor(Color.GREEN);

                }
                if(dataSnapshot.child("r1c2").getValue(String.class).equals("X")){
                    r1c2.setText("X");
                    r1c2.setTextColor(Color.RED);

                }else if(dataSnapshot.child("r1c2").getValue(String.class).equals("O")){
                    r1c2.setText("O");
                    r1c2.setTextColor(Color.GREEN);

                }
                if(dataSnapshot.child("r1c3").getValue(String.class).equals("X")){
                    r1c3.setText("X");
                    r1c3.setTextColor(Color.RED);

                }else if(dataSnapshot.child("r1c3").getValue(String.class).equals("O")){
                    r1c3.setText("O");
                    r1c3.setTextColor(Color.GREEN);

                }
                if(dataSnapshot.child("r2c1").getValue(String.class).equals("X")){
                    r2c1.setText("X");
                    r2c1.setTextColor(Color.RED);

                }else if(dataSnapshot.child("r2c1").getValue(String.class).equals("O")){
                    r2c1.setText("O");
                    r2c1.setTextColor(Color.GREEN);

                }
                if(dataSnapshot.child("r2c2").getValue(String.class).equals("X")){
                    r2c2.setText("X");
                    r2c2.setTextColor(Color.RED);


                }else if(dataSnapshot.child("r2c2").getValue(String.class).equals("O")){
                    r2c2.setText("O");
                    r2c2.setTextColor(Color.GREEN);

                }
                if(dataSnapshot.child("r2c3").getValue(String.class).equals("X")){
                    r2c3.setText("X");
                    r2c3.setTextColor(Color.RED);

                }else if(dataSnapshot.child("r2c3").getValue(String.class).equals("O")){
                    r2c3.setText("O");
                    r2c3.setTextColor(Color.GREEN);

                }
                if(dataSnapshot.child("r3c1").getValue(String.class).equals("X")){
                    r3c1.setText("X");
                    r3c1.setTextColor(Color.RED);

                }else if(dataSnapshot.child("r3c1").getValue(String.class).equals("O")){
                    r3c1.setText("O");
                    r3c1.setTextColor(Color.GREEN);

                }
                if(dataSnapshot.child("r3c2").getValue(String.class).equals("X")){
                    r3c2.setText("X");
                    r3c2.setTextColor(Color.RED);

                }else if(dataSnapshot.child("r3c2").getValue(String.class).equals("O")){
                    r3c2.setText("O");
                    r3c2.setTextColor(Color.GREEN);

                }
                if(dataSnapshot.child("r3c3").getValue(String.class).equals("X")){
                    r3c3.setText("X");
                    r3c3.setTextColor(Color.RED);


                }else if(dataSnapshot.child("r3c3").getValue(String.class).equals("O")){
                    r3c3.setText("O");
                    r3c3.setTextColor(Color.GREEN);

                }
//                check();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void read_restart_board(){
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("r1c1").getValue(String.class).equals("")){
                    r1c1.setText("");
                }
                if(dataSnapshot.child("r1c2").getValue(String.class).equals("")){
                    r1c2.setText("");
                }
                if(dataSnapshot.child("r1c3").getValue(String.class).equals("")){
                    r1c3.setText("");
                }
                if(dataSnapshot.child("r2c1").getValue(String.class).equals("")){
                    r2c1.setText("");
                }
                if(dataSnapshot.child("r2c2").getValue(String.class).equals("")){
                    r2c2.setText("");
                }
                if(dataSnapshot.child("r2c3").getValue(String.class).equals("")){
                    r2c3.setText("");
                }
                if(dataSnapshot.child("r3c1").getValue(String.class).equals("")){
                    r3c1.setText("");
                }
                if(dataSnapshot.child("r3c2").getValue(String.class).equals("")){
                    r3c2.setText("");
                }
                if(dataSnapshot.child("r3c3").getValue(String.class).equals("")){
                    r3c3.setText("");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void playing_type(){
        myRef.child("active").child("playing_type_counter").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(Integer.parseInt((String) dataSnapshot.getValue())%2==0){
                    myRef.child("active").child("Playing_type").setValue("O");
                }else{
                    myRef.child("active").child("Playing_type").setValue("X");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void stag_players_names_color(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                if(String.valueOf(value.get("false")).equals(userName)&&dataSnapshot.child("active").child("playing_type_counter").getValue().equals("1")){
                    x_player.setTextColor(Color.RED);
                    o_player.setTextColor(Color.GREEN);
                }else if(String.valueOf(value.get("true")).equals(userName)&&dataSnapshot.child("active").child("playing_type_counter").getValue().equals("1")){
                    o_player.setTextColor(Color.RED);
                    x_player.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void player_action(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot1) {

                final HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot1.getValue();



                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot2) {




                            r1c1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    if(dataSnapshot2.child("r1c1").getValue(String.class).equals("")&&String.valueOf(value.get("true")).equals(userName)){
                                        playing_type();
//                                        myRef.child("active").child("Playing_type").setValue(playing_type);
                                        myRef2.child("r1c1").setValue(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
                                        r1c1.setText(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
//                                        r1c1.setTextColor(Color.RED);
//                                        board();
//                                        check();

//                                        myRef.child("active").child("true").setValue(otherPlayer);
//                                        myRef.child("active").child("false").setValue(userName);

                                        myRef.child("true").setValue(otherPlayer);
                                        myRef.child("false").setValue(userName);
                                        myRef.child("active").child("playing_type_counter").setValue(String.valueOf(String.valueOf(Integer.parseInt((String) dataSnapshot1.child("active").child("playing_type_counter").getValue())+1)));

                                        board();
                                        check();

                                    }
                                }
                            });
                            r1c2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(dataSnapshot2.child("r1c2").getValue(String.class).equals("")&&String.valueOf(value.get("true")).equals(userName)){
                                        playing_type();
//                                        myRef.child("active").child("Playing_type").setValue(playing_type);
                                        myRef2.child("r1c2").setValue(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
                                        r1c2.setText(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
//                                        r1c2.setTextColor(Color.RED);
//                                        board();
//                                        check();

//                                        myRef.child("active").child("true").setValue(otherPlayer);
//                                        myRef.child("active").child("false").setValue(userName);

                                        myRef.child("true").setValue(otherPlayer);
                                        myRef.child("false").setValue(userName);
                                        myRef.child("active").child("playing_type_counter").setValue(String.valueOf(String.valueOf(Integer.parseInt((String) dataSnapshot1.child("active").child("playing_type_counter").getValue())+1)));

                                        board();
                                        check();

                                    }
                                }
                            });
                            r1c3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(dataSnapshot2.child("r1c3").getValue(String.class).equals("")&&String.valueOf(value.get("true")).equals(userName)){
                                        playing_type();
//                                        myRef.child("active").child("Playing_type").setValue(playing_type);
                                        myRef2.child("r1c3").setValue(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
                                        r1c3.setText(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
//                                        r1c3.setTextColor(Color.RED);
//                                        board();
//                                        check();

//                                        myRef.child("active").child("true").setValue(otherPlayer);
//                                        myRef.child("active").child("false").setValue(userName);

                                        myRef.child("true").setValue(otherPlayer);
                                        myRef.child("false").setValue(userName);
                                        myRef.child("active").child("playing_type_counter").setValue(String.valueOf(String.valueOf(Integer.parseInt((String) dataSnapshot1.child("active").child("playing_type_counter").getValue())+1)));
                                        board();
                                        check();

                                    }
                                }

                            });
                            r2c1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(dataSnapshot2.child("r2c1").getValue(String.class).equals("")&&String.valueOf(value.get("true")).equals(userName)){
                                        playing_type();
//                                        myRef.child("active").child("Playing_type").setValue(playing_type);
                                        myRef2.child("r2c1").setValue(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
                                        r2c1.setText(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
//                                        r2c1.setTextColor(Color.RED);
//                                        board();
//                                        check();

//                                        myRef.child("active").child("true").setValue(otherPlayer);
//                                        myRef.child("active").child("false").setValue(userName);

                                        myRef.child("true").setValue(otherPlayer);
                                        myRef.child("false").setValue(userName);
                                        myRef.child("active").child("playing_type_counter").setValue(String.valueOf(String.valueOf(Integer.parseInt((String) dataSnapshot1.child("active").child("playing_type_counter").getValue())+1)));
                                        board();
                                        check();

                                    }
                                }

                            });
                            r2c2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(dataSnapshot2.child("r2c2").getValue(String.class).equals("")&&String.valueOf(value.get("true")).equals(userName)){
                                        playing_type();
//                                        myRef.child("active").child("Playing_type").setValue(playing_type);
                                        myRef2.child("r2c2").setValue(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
                                        r2c2.setText(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
//                                        r2c2.setTextColor(Color.RED);
//                                        board();
//                                        check();

//                                        myRef.child("active").child("true").setValue(otherPlayer);
//                                        myRef.child("active").child("false").setValue(userName);

                                        myRef.child("true").setValue(otherPlayer);
                                        myRef.child("false").setValue(userName);
                                        myRef.child("active").child("playing_type_counter").setValue(String.valueOf(String.valueOf(Integer.parseInt((String) dataSnapshot1.child("active").child("playing_type_counter").getValue())+1)));
                                        board();
                                        check();

                                    }
                                }

                            });
                            r2c3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(dataSnapshot2.child("r2c3").getValue(String.class).equals("")&&String.valueOf(value.get("true")).equals(userName)){
                                        playing_type();
//                                        myRef.child("active").child("Playing_type").setValue(playing_type);
                                        myRef2.child("r2c3").setValue(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
                                        r2c3.setText(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
//                                        r2c3.setTextColor(Color.RED);
//                                        board();
//                                        check();

//                                        myRef.child("active").child("true").setValue(otherPlayer);
//                                        myRef.child("active").child("false").setValue(userName);

                                        myRef.child("true").setValue(otherPlayer);
                                        myRef.child("false").setValue(userName);
                                        myRef.child("active").child("playing_type_counter").setValue(String.valueOf(String.valueOf(Integer.parseInt((String) dataSnapshot1.child("active").child("playing_type_counter").getValue())+1)));
                                        board();
                                        check();

                                    }
                                }
                            });
                            r3c1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if(dataSnapshot2.child("r3c1").getValue(String.class).equals("")&&String.valueOf(value.get("true")).equals(userName)){
                                        playing_type();
//                                        myRef.child("active").child("Playing_type").setValue(playing_type);
                                        myRef2.child("r3c1").setValue(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
                                        r3c1.setText(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
//                                        r3c1.setTextColor(Color.RED);
//                                        board();
//                                        check();

//                                        myRef.child("active").child("true").setValue(otherPlayer);
//                                        myRef.child("active").child("false").setValue(userName);

                                        myRef.child("true").setValue(otherPlayer);
                                        myRef.child("false").setValue(userName);
                                        myRef.child("active").child("playing_type_counter").setValue(String.valueOf(String.valueOf(Integer.parseInt((String) dataSnapshot1.child("active").child("playing_type_counter").getValue())+1)));
                                        board();
                                        check();

                                    }
                                }
                            });
                            r3c2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(dataSnapshot2.child("r3c2").getValue(String.class).equals("")&&String.valueOf(value.get("true")).equals(userName)){
                                        playing_type();
//                                        myRef.child("active").child("Playing_type").setValue(playing_type);
                                        myRef2.child("r3c2").setValue(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
                                        r3c2.setText(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
//                                        r3c2.setTextColor(Color.RED);
//                                        board();
//                                        check();

//                                        myRef.child("active").child("true").setValue(otherPlayer);
//                                        myRef.child("active").child("false").setValue(userName);

                                        myRef.child("true").setValue(otherPlayer);
                                        myRef.child("false").setValue(userName);
                                        myRef.child("active").child("playing_type_counter").setValue(String.valueOf(String.valueOf(Integer.parseInt((String) dataSnapshot1.child("active").child("playing_type_counter").getValue())+1)));
                                        board();
                                        check();

                                    }
                                }
                            });
                            r3c3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(dataSnapshot2.child("r3c3").getValue(String.class).equals("")&&String.valueOf(value.get("true")).equals(userName)){
                                        playing_type();
//                                        myRef.child("active").child("Playing_type").setValue(playing_type);
                                        myRef2.child("r3c3").setValue(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
                                        r3c3.setText(dataSnapshot1.child("active").child("Playing_type").getValue(String.class));
//                                        r3c3.setTextColor(Color.RED);
//                                        board();
//                                        check();

//                                        myRef.child("active").child("true").setValue(otherPlayer);
//                                        myRef.child("active").child("false").setValue(userName);

                                        myRef.child("true").setValue(otherPlayer);
                                        myRef.child("false").setValue(userName);
                                        myRef.child("active").child("playing_type_counter").setValue(String.valueOf(String.valueOf(Integer.parseInt((String) dataSnapshot1.child("active").child("playing_type_counter").getValue())+1)));
                                        board();
                                        check();

                                    }
                                }
                            });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

//            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        Other_player_checking();
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
        r1c1.setEnabled(true);
        r1c2.setEnabled(true);
        r1c3.setEnabled(true);
        r2c1.setEnabled(true);
        r2c2.setEnabled(true);
        r2c3.setEnabled(true);
        r3c1.setEnabled(true);
        r3c2.setEnabled(true);
        r3c3.setEnabled(true);
        myRef2.child("r1c1").setValue("");
        myRef2.child("r1c2").setValue("");
        myRef2.child("r1c3").setValue("");
        myRef2.child("r2c1").setValue("");
        myRef2.child("r2c2").setValue("");
        myRef2.child("r2c3").setValue("");
        myRef2.child("r3c1").setValue("");
        myRef2.child("r3c2").setValue("");
        myRef2.child("r3c3").setValue("");
        read_restart_board();
        myRef.child("active").child("playing_type_counter").setValue("1");
//        myRef.child("active").child("true").setValue(userName);
//        myRef.child("active").child("false").setValue(otherPlayer);
        myRef.child("true").setValue(userName);
        myRef.child("false").setValue(otherPlayer);

        myRef3.child("Check").setValue("false");
//        myRef.child("Check").removeValue();
//        myRef.child("Check").setValue(null);
//        myRef.child("Check").setValue("");

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

//??????????????????????????????????????????????????????????????????????????????????????????????????

//    TextView tvPlayer1, tvPlayer2;
//
//    String playerSession = "";
//    String userName = "";
//    String otherPlayer = "";
//    String loginUID = "";
//    String requestType = "", myGameSign = "X";
//
//    int gameState = 0;
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.gggggggggggggg);
//
//        userName = getIntent().getExtras().get("user_name").toString();
//        loginUID = getIntent().getExtras().get("login_uid").toString();
//        otherPlayer = getIntent().getExtras().get("other_player").toString();
//        requestType = getIntent().getExtras().get("request_type").toString();
//        playerSession = getIntent().getExtras().get("player_session").toString();
//
//        tvPlayer1 = (TextView) findViewById(R.id.tvPlayer1);
//        tvPlayer2 = (TextView) findViewById(R.id.tvPlayer2);
//
//        gameState = 1;
//
//        if(requestType.equals("From")){
//            myGameSign = "0";
//            tvPlayer1.setText("Your turn");
//            tvPlayer2.setText("Your turn");
//            myRef.child("playing").child(playerSession).child("turn").setValue(userName);
//            //setEnableClick(true);
//        }else{
//            myGameSign = "X";
//            tvPlayer1.setText(otherPlayer + "\'s turn");
//            tvPlayer2.setText(otherPlayer + "\'s turn");
//            myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);
//            //setEnableClick(false);
//        }
//
//
//        myRef.child("playing").child(playerSession).child("turn").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                try{
//                    String value = (String) dataSnapshot.getValue();
//                    if(value.equals(userName)) {
//                        tvPlayer1.setText("Your turn");
//                        tvPlayer2.setText("Your turn");
//                        setEnableClick(true);
//                        activePlayer = 1;
//                    }else if(value.equals(otherPlayer)){
//                        tvPlayer1.setText(otherPlayer + "\'s turn");
//                        tvPlayer2.setText(otherPlayer + "\'s turn");
//                        setEnableClick(false);
//                        activePlayer = 2;
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//
//        myRef.child("playing").child(playerSession).child("game")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        try{
//                            Player1.clear();
//                            Player2.clear();
//                            activePlayer = 2;
//                            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
//                            if(map != null){
//                                String value = "";
//                                String firstPlayer = userName;
//                                for(String key:map.keySet()){
//                                    value = (String) map.get(key);
//                                    if(value.equals(userName)){
//                                        //activePlayer = myGameSign.equals("X")?1:2;
//                                        activePlayer = 2;
//                                    }else{
//                                        //activePlayer = myGameSign.equals("X")?2:1;
//                                        activePlayer = 1;
//                                    }
//                                    firstPlayer = value;
//                                    String[] splitID = key.split(":");
//                                    OtherPlayer(Integer.parseInt(splitID[1]));
//                                }
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//
//                    }
//                });
//    }
//
//
//
//    public void GameBoardClick(View view){
//        ImageView selectedImage = (ImageView) view;
//
//        if(playerSession.length() <= 0){
//            Intent intent = new Intent(getApplicationContext(), onlinelogin_activity.class);
//            startActivity(intent);
//            finish();
//        }else {
//            int selectedBlock = 0;
//            switch ((selectedImage.getId())) {
//                case R.id.iv_11: selectedBlock = 1; break;
//                case R.id.iv_12: selectedBlock = 2; break;
//                case R.id.iv_13: selectedBlock = 3; break;
//
//                case R.id.iv_21: selectedBlock = 4; break;
//                case R.id.iv_22: selectedBlock = 5; break;
//                case R.id.iv_23: selectedBlock = 6; break;
//
//                case R.id.iv_31: selectedBlock = 7; break;
//                case R.id.iv_32: selectedBlock = 8; break;
//                case R.id.iv_33: selectedBlock = 9; break;
//            }
//            myRef.child("playing").child(playerSession).child("game").child("block:"+selectedBlock).setValue(userName);
//            myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);
//            setEnableClick(false);
//            activePlayer = 2;
//            PlayGame(selectedBlock, selectedImage);
//        }
//    }
//
//
//
//
//
//
//    int activePlayer = 1;
//    ArrayList<Integer> Player1 = new ArrayList<Integer>();
//    ArrayList<Integer> Player2 = new ArrayList<Integer>();
//
//    void PlayGame(int selectedBlock, ImageView selectedImage){
//        if(gameState == 1) {
//            if (activePlayer == 1) {
//                selectedImage.setImageResource(R.drawable.ttt_x);
//                Player1.add(selectedBlock);
//                //activePlayer = 2;
//                //AutoPlay();
//            }else if (activePlayer == 2) {
//                selectedImage.setImageResource(R.drawable.ttt_o);
//                Player2.add(selectedBlock);
//                //activePlayer = 1;
//            }
//
//            selectedImage.setEnabled(false);
//            CheckWinner();
//        }
//    }
//
//
//    void CheckWinner(){
//        int winner = 0;
//
//        /********* for Player 1 *********/
//        if(Player1.contains(1) && Player1.contains(2) && Player1.contains(3)){ winner = 1; }
//        if(Player1.contains(4) && Player1.contains(5) && Player1.contains(6)){ winner = 1; }
//        if(Player1.contains(7) && Player1.contains(8) && Player1.contains(9)){ winner = 1; }
//
//        if(Player1.contains(1) && Player1.contains(4) && Player1.contains(7)){ winner = 1; }
//        if(Player1.contains(2) && Player1.contains(5) && Player1.contains(8)){ winner = 1; }
//        if(Player1.contains(3) && Player1.contains(6) && Player1.contains(9)){ winner = 1; }
//
//        if(Player1.contains(1) && Player1.contains(5) && Player1.contains(9)){ winner = 1; }
//        if(Player1.contains(3) && Player1.contains(5) && Player1.contains(7)){ winner = 1; }
//
//
//        /********* for Player 2 *********/
//        if(Player2.contains(1) && Player2.contains(2) && Player2.contains(3)){ winner = 2; }
//        if(Player2.contains(4) && Player2.contains(5) && Player2.contains(6)){ winner = 2; }
//        if(Player2.contains(7) && Player2.contains(8) && Player2.contains(9)){ winner = 2; }
//
//        if(Player2.contains(1) && Player2.contains(4) && Player2.contains(7)){ winner = 2; }
//        if(Player2.contains(2) && Player2.contains(5) && Player2.contains(8)){ winner = 2; }
//        if(Player2.contains(3) && Player2.contains(6) && Player2.contains(9)){ winner = 2; }
//
//        if(Player2.contains(1) && Player2.contains(5) && Player2.contains(9)){ winner = 2; }
//        if(Player2.contains(3) && Player2.contains(5) && Player2.contains(7)){ winner = 2; }
//
//
//
//
//        if(winner != 0 && gameState == 1){
//            if(winner == 1){
//                ShowAlert(otherPlayer +" is winner");
//            }else if(winner == 2){
//                ShowAlert("You won the game");
//            }
//            gameState = 2;
//        }
//
//        ArrayList<Integer> emptyBlocks = new ArrayList<Integer>();
//        for(int i=1; i<=9; i++){
//            if(!(Player1.contains(i) || Player2.contains(i))){
//                emptyBlocks.add(i);
//            }
//        }
//        if(emptyBlocks.size() == 0) {
//            if(gameState == 1) {
//                AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
//                ShowAlert("Draw");
//            }
//            gameState = 3;
//        }
//    }
//
//
//    void OtherPlayer(int selectedBlock) {
//
//        ImageView selectedImage = (ImageView) findViewById(R.id.iv_11);
//        switch (selectedBlock) {
//            case 1:
//                selectedImage = (ImageView) findViewById(R.id.iv_11);
//                break;
//            case 2:
//                selectedImage = (ImageView) findViewById(R.id.iv_12);
//                break;
//            case 3:
//                selectedImage = (ImageView) findViewById(R.id.iv_13);
//                break;
//
//            case 4:
//                selectedImage = (ImageView) findViewById(R.id.iv_21);
//                break;
//            case 5:
//                selectedImage = (ImageView) findViewById(R.id.iv_22);
//                break;
//            case 6:
//                selectedImage = (ImageView) findViewById(R.id.iv_23);
//                break;
//
//            case 7:
//                selectedImage = (ImageView) findViewById(R.id.iv_31);
//                break;
//            case 8:
//                selectedImage = (ImageView) findViewById(R.id.iv_32);
//                break;
//            case 9:
//                selectedImage = (ImageView) findViewById(R.id.iv_33);
//                break;
//        }
//
//        PlayGame(selectedBlock, selectedImage);
//    }
//
//
//
//    void ResetGame(){
//        gameState = 1;
//        activePlayer = 1;
//        Player1.clear();
//        Player2.clear();
//
//        myRef.child("playing").child(playerSession).removeValue();
//
//        ImageView iv;
//        iv = (ImageView) findViewById(R.id.iv_11); iv.setImageResource(0); iv.setEnabled(true);
//        iv = (ImageView) findViewById(R.id.iv_12); iv.setImageResource(0); iv.setEnabled(true);
//        iv = (ImageView) findViewById(R.id.iv_13); iv.setImageResource(0); iv.setEnabled(true);
//
//        iv = (ImageView) findViewById(R.id.iv_21); iv.setImageResource(0); iv.setEnabled(true);
//        iv = (ImageView) findViewById(R.id.iv_22); iv.setImageResource(0); iv.setEnabled(true);
//        iv = (ImageView) findViewById(R.id.iv_23); iv.setImageResource(0); iv.setEnabled(true);
//
//        iv = (ImageView) findViewById(R.id.iv_31); iv.setImageResource(0); iv.setEnabled(true);
//        iv = (ImageView) findViewById(R.id.iv_32); iv.setImageResource(0); iv.setEnabled(true);
//        iv = (ImageView) findViewById(R.id.iv_33); iv.setImageResource(0); iv.setEnabled(true);
//
//    }
//
//
//
//
//    void ShowAlert(String Title){
//        AlertDialog.Builder b = new AlertDialog.Builder(this, R.style.TransparentDialog);
//        b.setTitle(Title)
//                .setMessage("Start a new game?")
//                .setNegativeButton("Menu", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(getApplicationContext(), start_activity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .show();
//    }
//
//
//
//    void setEnableClick(boolean trueORfalse){
//        ImageView iv;
//        iv = (ImageView) findViewById(R.id.iv_11); iv.setClickable(trueORfalse);
//        iv = (ImageView) findViewById(R.id.iv_12); iv.setClickable(trueORfalse);
//        iv = (ImageView) findViewById(R.id.iv_13); iv.setClickable(trueORfalse);
//
//        iv = (ImageView) findViewById(R.id.iv_21); iv.setClickable(trueORfalse);
//        iv = (ImageView) findViewById(R.id.iv_22); iv.setClickable(trueORfalse);
//        iv = (ImageView) findViewById(R.id.iv_23); iv.setClickable(trueORfalse);
//
//        iv = (ImageView) findViewById(R.id.iv_31); iv.setClickable(trueORfalse);
//        iv = (ImageView) findViewById(R.id.iv_32); iv.setClickable(trueORfalse);
//        iv = (ImageView) findViewById(R.id.iv_33); iv.setClickable(trueORfalse);
//    }


}