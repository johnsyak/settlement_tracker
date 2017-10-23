package com.settlement_tracker.fragments.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.interfaces.FragmentCommunicator;

/**
 * Created by John on 8/18/2017.
 */

public class AdjustTab extends DialogFragment {
    View counterView;
    private ImageView upArrow;
    private ImageView downArrow;

    private TextView label;

    private int value;
    private TextView counter;
    private String l;
    private String fragTag;

    FragmentCommunicator controller;

    private static final String BRAIN_TAG = "brain", SURVIVAL_TAG = "survival", XP_TAG = "Hunt XP", COURAGE_TAG = "courage", UNDERSTANDING_TAG = "understanding", PROF_TAG = "prof";

    public AdjustTab() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        controller = (FragmentCommunicator) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        counterView = inflater.inflate(R.layout.counter, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(counterView);
        builder.setCancelable(false);
        getValues();
        setViews();
        setOnClickListeners();
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (l) {
                    case PROF_TAG:
                        controller.onProfClick(value);
                        break;
                    case BRAIN_TAG:
                        controller.onBrainClick(value);
                        break;
                    case SURVIVAL_TAG:
                        controller.onSurvivalClick(value);
                        break;
                    case XP_TAG:
                        controller.onHuntClick(value);
                        break;
                    case COURAGE_TAG:
                        controller.onCourageClick(value);
                        break;
                    case UNDERSTANDING_TAG:
                        controller.onUnderstandingClick(value);
                        break;
                    default:
                        Log.d("AdjustTab:", "type of click not found");
                        break;
                }
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }

    @Override //TODO maybe find a better way? This fix can work as its just using bundles
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Fragment parentFragment = null;
        switch(fragTag){
            case "stats":
                parentFragment = getFragmentManager().getFragments().get(0);
                break;
            case "traits":
                parentFragment = getFragmentManager().getFragments().get(1);
        }
        if (parentFragment instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) parentFragment).onDismiss(dialog);
        }
    }

    private void getValues() {
        Bundle b = getArguments();
        l = b.getString("label");
        value = b.getInt("value");
        fragTag = b.getString("tag");
    }

    private void setViews() {
        upArrow = (ImageView) counterView.findViewById(R.id.up);
        downArrow = (ImageView) counterView.findViewById(R.id.down);
        label = (TextView) counterView.findViewById(R.id.label);
        counter = (TextView) counterView.findViewById(R.id.stat_counter);

        label.setText(l);
        setCounter();
    }

    public static AdjustTab newInstance() {
        return new AdjustTab();
    }


    private void setOnClickListeners() {
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value++;
                setCounter();
            }
        });

        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value--;
                setCounter();
            }
        });
    }

    private void setCounter() {
        counter.setText(Integer.toString(value));
    }

}
