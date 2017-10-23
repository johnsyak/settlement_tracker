package com.settlement_tracker.fragments.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.TrackerInterfaces.OnDeletePopup;

/**
 * Created by John on 9/7/2017.
 */

public class DeleteAlert extends DialogFragment {
    String type;
    String msg;
    View view;
    int position;
    OnDeletePopup onDeletePopup;
    TextView nameField;
    public DeleteAlert(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDeletePopup = (OnDeletePopup)activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.delete_alert, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirm();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel();
            }
        });
        nameField = (TextView)rootView.findViewById(R.id.delete_item);
        nameField.setText(" "+msg+"?");
        return builder.create();
    }

    private void confirm() {
        onDeletePopup.delete(msg, view, position);
    }

    private void cancel() {
        dismiss();
    }

    public static DeleteAlert newInstance() {
        return new DeleteAlert();
    }

    public void setArgs(String msg, String type, View view, int position) {
        this.msg = msg;
        this.type = type;
        this.view = view;
        this.position = position;
    }
}
