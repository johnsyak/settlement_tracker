package com.settlement_tracker.fragments.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.john.kingdomdeath.R;

/**
 * Created by John on 8/1/2017.
 */

public class CreatePlayerAlert extends DialogFragment {
    Communicator communicator;
    private EditText name;
    private RadioGroup gender;
    private RadioButton male;
    private RadioButton female;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator)getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertView = inflater.inflate(R.layout.alert_create_player, null);
        setCancelable(false);
        name = (EditText)alertView.findViewById(R.id.player_name_input);
        male = (RadioButton) alertView.findViewById(R.id.radio_male);
        female = (RadioButton) alertView.findViewById(R.id.radio_female);
        gender = (RadioGroup) alertView.findViewById(R.id.group_gender);
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        builder.setView(alertView);
        builder.setPositiveButton(R.string.create_settlement, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                onTrueResult();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel_settlement, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                onCancelResult();
            }
        });
        return builder.create();
    }

    public interface Communicator{
        void onDialogMessage(String name, int gender);
    }

    public static CreatePlayerAlert newInstance() {
        CreatePlayerAlert p = new CreatePlayerAlert();
        return p;
    }
    private void onTrueResult(){
        String player = name.getText().toString().replace("'", "''");
        if(!player.isEmpty()) {

            int radioID = gender.getCheckedRadioButtonId();
            View genderClicked = gender.findViewById(radioID);
            communicator.onDialogMessage(player.replace("'", "\'"), gender.indexOfChild(genderClicked));
        }
    }
    private void onCancelResult(){
        dismiss();
    }
}
