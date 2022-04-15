package com.example.x_o_game;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class single_level_dialog extends DialogFragment {

    String sent_d;

    public interface single_dialogListner{
        void onPositiveButton_clicked(String level);
    }

    single_dialogListner listner ;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try {
            listner = (single_dialogListner) context;
        } catch (ClassCastException e) {

        }

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] d = getActivity().getResources().getStringArray(R.array.level);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose The Difficult of Level ");
        builder.setSingleChoiceItems(R.array.level, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sent_d = d[i];

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                listner.onPositiveButton_clicked(sent_d);
                Intent iw = new Intent(getContext(),singlePlayer_activity.class);
                iw.putExtra("difficulty",sent_d);
                startActivity(iw);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });



        return builder.create();
    }
}
