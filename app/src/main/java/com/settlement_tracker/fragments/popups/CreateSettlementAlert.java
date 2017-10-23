package com.settlement_tracker.fragments.popups;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.john.kingdomdeath.R;

/**
 * Created by John on 7/26/2017.
 */

public class CreateSettlementAlert extends DialogFragment {
    Communicator communicator;
    EditText editSettlementName;

    public CreateSettlementAlert() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertView = inflater.inflate(R.layout.alert_create_settlement, null);
        setCancelable(false);
        editSettlementName = (EditText) alertView.findViewById(R.id.settlement_name_input);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

    public static CreateSettlementAlert newInstance() {
        return new CreateSettlementAlert();
    }

    public interface Communicator {
        void onDialogMessage(String message);
    }

    private void onTrueResult() {
        String settlementName = editSettlementName.getText().toString().replace("'", "''");
        if (!settlementName.isEmpty())
            communicator.onDialogMessage(settlementName);
        else
            Toast.makeText(getActivity(), "You must enter a settlement name to continue", Toast.LENGTH_LONG).show();
    }

    private void onCancelResult() {
        dismiss();
    }
}
