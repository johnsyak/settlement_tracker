package com.settlement_tracker.fragments.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.interfaces.FragmentCommunicator;
import com.settlement_tracker.sqlite.model.Health;
import com.settlement_tracker.sqlite.model.Injuries;

/**
 * Created by John on 8/16/2017.
 */

public class AdjustDamagePopup extends DialogFragment {
    Health h;
    Injuries i;

    private View adjustView;
    FragmentCommunicator controller;
    private ImageView damageBox1;
    private ImageView damageBox2;
    private TextView injured;
    private ImageView downArrow;
    private ImageView upArrow;
    private TextView counterStat;
    private TextView label;
    private EditText editStat;

    private int value = 0;
    private int injuryLvl = 0;
    private String l;
    private int baseStat = 0;

    private static final String LABEL = "label";
    private static final String HEAD_TAG = "head", BODY_TAG = "body", ARMS_TAG = "arms", LEGS_TAG = "legs", WAIST_TAG = "waist";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        controller = (FragmentCommunicator) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        inflater.inflate(R.layout.damage_counter, null);
        adjustView = inflater.inflate(R.layout.damage_counter, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(adjustView);
        getBundle();
        setCancelable(false);
        initViews();
        setValues();
        initClickListeners();
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                baseStat = Integer.parseInt(editStat.getText().toString());
                switch (l) {
                    case HEAD_TAG:
                        controller.onAdjustHeadClick(value, injuryLvl, baseStat);
                        break;
                    case BODY_TAG:
                        controller.onAdjustBodyClick(value, injuryLvl, baseStat);
                        break;
                    case LEGS_TAG:
                        controller.onAdjustLegsClick(value, injuryLvl, baseStat);
                        break;
                    case ARMS_TAG:
                        controller.onAdjustArmClick(value, injuryLvl, baseStat);
                        break;
                    case WAIST_TAG:
                        controller.onAdjustWaistClick(value, injuryLvl, baseStat);
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

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Fragment parentFragment = getFragmentManager().getFragments().get(0);//TODO need to figure out how to find by tag. This is a very bad fix.
        if(parentFragment instanceof DialogInterface.OnDismissListener){
            ((DialogInterface.OnDismissListener) parentFragment).onDismiss(dialog);
        }
    }

    private void setViewVisible(View v, boolean injured) {
        if (injured)
            v.setVisibility(View.VISIBLE);
        else
            v.setVisibility(View.INVISIBLE);
    }

    private void setCounterText() {
        counterStat.setText(Integer.toString(value));
    }

    private void setValues() {
        label.setText(l);
        switch (l) {
            case HEAD_TAG:
                value = h.getHeadStat();
                injuryLvl = i.isHeadInjured();
                baseStat = h.getBaseHeadStat();
                break;
            case BODY_TAG:
                value = h.getBodyStat();
                injuryLvl = i.isBodyInjured();
                baseStat = h.getBaseBodyStat();
                break;
            case LEGS_TAG:
                value = h.getLegStat();
                injuryLvl = i.isLegInjured();
                baseStat = h.getBaseLegStat();
                break;
            case ARMS_TAG:
                value = h.getArmStat();
                injuryLvl = i.isArmInjured();
                baseStat = h.getBaseArmStat();
                break;
            case WAIST_TAG:
                value = h.getWaistStat();
                injuryLvl = i.isWaistInjured();
                baseStat = h.getBaseWaistStat();
                break;
        }
        editStat.setText(Integer.toString(baseStat));

        Drawable background1 = null;
        Drawable background2 = null;

        if (l.equals(HEAD_TAG)) {
            setViewVisible(damageBox1, false);
            if (injuryLvl > 0)
                background2 = getResources().getDrawable(R.drawable.damaged);
            else
                background2 = getResources().getDrawable(R.drawable.customborder);
        }

        else {
            setViewVisible(damageBox1, true);
            setViewVisible(damageBox2, true);
            background2 = getResources().getDrawable(R.drawable.customborder);
            if (injuryLvl > 0) {
                background1 = getResources().getDrawable(R.drawable.damaged);
                if (injuryLvl > 1)
                    background2 = getResources().getDrawable(R.drawable.damaged);
                else
                    background2 = getResources().getDrawable(R.drawable.customborder);
            } else
                background1 = getResources().getDrawable(R.drawable.customborder);
        }

        damageBox1.setBackground(background1);
        damageBox2.setBackground(background2);
        setCounterText();
        counterStat.setText(Integer.toString(value));
        setViewVisible(injured, injuryLvl > 1);
    }

    private void initClickListeners() {
        initUpArrowClick();
        initDownArrowClick();
        if(!l.equals(HEAD_TAG))
            initDamageBox1Click();
        initDamageBox2Click();
    }

    private void initUpArrowClick() {
        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value++;
                setCounterText();
            }
        });
    }

    private void initDownArrowClick() {
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value--;
                setCounterText();
            }
        });
    }

    private void initDamageBox1Click() {
        damageBox1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(injuryLvl < 1) {
                    damageBox1.setBackground(getResources().getDrawable(R.drawable.damaged));
                    injuryLvl = 1;
                }
                else if(injuryLvl == 1) {
                    damageBox1.setBackground(getResources().getDrawable(R.drawable.customborder));
                    injuryLvl = 0;
                }
            }
        });
    }

    private void initDamageBox2Click() {
        damageBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(injuryLvl == 1 || l.equals(HEAD_TAG) && injuryLvl != 2){
                    damageBox2.setBackground(getResources().getDrawable(R.drawable.damaged));
                    injuryLvl = 2;
                    setViewVisible(injured, true);
                }else if(injuryLvl == 2) {
                    damageBox2.setBackground(getResources().getDrawable(R.drawable.customborder));
                    if(l.equals(HEAD_TAG))
                        injuryLvl = 0;
                    else
                        injuryLvl = 1;
                    setViewVisible(injured, false);
                }
            }
        });
    }

    private void initViews() {
        RelativeLayout counter = (RelativeLayout) adjustView.findViewById(R.id.adjust_damage_counter);
        downArrow = (ImageView) counter.findViewById(R.id.down);
        upArrow = (ImageView) counter.findViewById(R.id.up);
        counterStat = (TextView) counter.findViewById(R.id.stat_counter);
        label = (TextView) counter.findViewById(R.id.label);
        damageBox1 = (ImageView) adjustView.findViewById(R.id.damage_box1);
        damageBox2 = (ImageView) adjustView.findViewById(R.id.damage_box2);
        injured = (TextView) adjustView.findViewById(R.id.injured);
        editStat = (EditText) adjustView.findViewById(R.id.base_stat);
    }

    private void getBundle() {
        Bundle b = getArguments();
        i = b.getParcelable("injuries");
        h = b.getParcelable("health");
        l = b.getString(LABEL);
    }

    public static AdjustDamagePopup newInstance() {
        return new AdjustDamagePopup();
    }
}
