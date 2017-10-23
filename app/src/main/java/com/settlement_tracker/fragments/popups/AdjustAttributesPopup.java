package com.settlement_tracker.fragments.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.interfaces.FragmentCommunicator;
import com.settlement_tracker.sqlite.model.Attributes;
import com.settlement_tracker.sqllite.dao.AttributesDAO;

/**
 * Created by John on 8/15/2017.
 */

public class AdjustAttributesPopup extends DialogFragment {
    View alertView;

    ImageView speedDown;
    ImageView speedUp;
    ImageView luckDown;
    ImageView luckUp;
    ImageView evadeDown;
    ImageView evadeUp;
    ImageView strDown;
    ImageView strUp;
    ImageView accuDown;
    ImageView accuUp;
    ImageView moveDown;
    ImageView moveUp;
    private TextView speedCounter;
    private TextView speedLabel;
    private TextView luckCounter;
    private TextView luckLabel;
    private TextView evadeCounter;
    private TextView evadeLabel;
    private TextView strCounter;
    private TextView strLabel;
    private TextView accuCounter;
    private TextView accuLabel;
    private TextView moveCounter;
    private TextView moveLabel;

    Attributes a;
    int move;
    int accu;
    int str;
    int eva;
    int luck;
    int spd;
    FragmentCommunicator communicator;

    public static AdjustAttributesPopup newInstance() {
        return new AdjustAttributesPopup();
    }

    public AdjustAttributesPopup() {

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getTargetFragment() != null)
            communicator = (FragmentCommunicator) getTargetFragment();
        else
            communicator = (FragmentCommunicator) activity;


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getParentFragment();
        Bundle b = getArguments();
        a = b.getParcelable(AttributesDAO.TABLE);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        alertView = inflater.inflate(R.layout.adjust_window, null);
        setCancelable(false);
        setValues();
        initViews();
        initClickListeners();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(alertView);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Attributes confirmed = new Attributes(a.getId(), a.getFk(), a.hasSurvivalBonus(), move, accu, str, eva, luck, spd);
                communicator.onStatConfirmClick(confirmed);
                dismiss();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
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

    private void setValues() {
        move = a.getMovement();
        accu = a.getAccuracy();
        str = a.getStrength();
        eva = a.getEvasion();
        luck = a.getLuck();
        spd = a.getSpeed();
    }

    private void initClickListeners() {
        initMovementClick();
        initAccuClick();
        initStrClick();
        initEvaClick();
        initLuckClick();
        initSpdClick();
    }

    private void initMovementClick() {
        moveDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move--;
                moveCounter.setText(Integer.toString(move));
            }
        });
        moveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move++;
                moveCounter.setText(Integer.toString(move));
            }
        });
    }

    private void initAccuClick() {
        accuDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accu--;
                accuCounter.setText(Integer.toString(accu));
            }
        });
        accuUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accu++;
                accuCounter.setText(Integer.toString(accu));
            }
        });
    }

    private void initStrClick() {
        strDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str--;
                strCounter.setText(Integer.toString(str));
            }
        });
        strUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str++;
                strCounter.setText(Integer.toString(str));
            }
        });
    }

    private void initEvaClick() {
        evadeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eva--;
                evadeCounter.setText(Integer.toString(eva));
            }
        });
        evadeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eva++;
                evadeCounter.setText(Integer.toString(eva));
            }
        });
    }

    private void initLuckClick() {
        luckDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luck--;
                luckCounter.setText(Integer.toString(luck));
            }
        });
        luckUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luck++;
                luckCounter.setText(Integer.toString(luck));
            }
        });
    }

    private void initSpdClick() {
        speedDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spd--;
                speedCounter.setText(Integer.toString(spd));
            }
        });
        speedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spd++;
                speedCounter.setText(Integer.toString(spd));
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initViews() {
        initMovement();
        initAccuracy();
        initStrength();
        initEvasion();
        initLuck();
        initSpeed();
    }

    private void initSpeed() {
        RelativeLayout speed = (RelativeLayout) alertView.findViewById(R.id.speed_counter);
        speedDown = (ImageView) speed.findViewById(R.id.down);
        speedUp = (ImageView) speed.findViewById(R.id.up);
        speedCounter = (TextView) speed.findViewById(R.id.stat_counter);
        speedLabel = (TextView) speed.findViewById(R.id.label);
        speedCounter.setText(Integer.toString(a.getSpeed()));
        speedLabel.setText("Speed");

    }

    private void initLuck() {
        RelativeLayout luck = (RelativeLayout) alertView.findViewById(R.id.luck_counter);
        luckDown = (ImageView) luck.findViewById(R.id.down);
        luckUp = (ImageView) luck.findViewById(R.id.up);
        luckCounter = (TextView) luck.findViewById(R.id.stat_counter);
        luckLabel = (TextView) luck.findViewById(R.id.label);
        luckCounter.setText(Integer.toString(a.getLuck()));
        luckLabel.setText("Luck");
    }

    private void initEvasion() {
        RelativeLayout evade = (RelativeLayout) alertView.findViewById(R.id.evasion_counter);
        evadeDown = (ImageView) evade.findViewById(R.id.down);
        evadeUp = (ImageView) evade.findViewById(R.id.up);
        evadeCounter = (TextView) evade.findViewById(R.id.stat_counter);
        evadeLabel = (TextView) evade.findViewById(R.id.label);
        evadeCounter.setText(Integer.toString(a.getEvasion()));
        evadeLabel.setText("Evade");
    }

    private void initStrength() {
        RelativeLayout str = (RelativeLayout) alertView.findViewById(R.id.strength_counter);
        strDown = (ImageView) str.findViewById(R.id.down);
        strUp = (ImageView) str.findViewById(R.id.up);
        strCounter = (TextView) str.findViewById(R.id.stat_counter);
        strLabel = (TextView) str.findViewById(R.id.label);
        strCounter.setText(Integer.toString(a.getStrength()));
        strLabel.setText("Strength");
    }

    private void initAccuracy() {
        RelativeLayout accu = (RelativeLayout) alertView.findViewById(R.id.accuracy_counter);
        accuDown = (ImageView) accu.findViewById(R.id.down);
        accuUp = (ImageView) accu.findViewById(R.id.up);
        accuCounter = (TextView) accu.findViewById(R.id.stat_counter);
        accuLabel = (TextView) accu.findViewById(R.id.label);
        accuCounter.setText(Integer.toString(a.getAccuracy()));
        accuLabel.setText("Accuracy");
    }

    private void initMovement() {
        RelativeLayout move = (RelativeLayout) alertView.findViewById(R.id.movement_counter);
        moveDown = (ImageView) move.findViewById(R.id.down);
        moveUp = (ImageView) move.findViewById(R.id.up);
        moveCounter = (TextView) move.findViewById(R.id.stat_counter);
        moveLabel = (TextView) move.findViewById(R.id.label);
        moveCounter.setText(Integer.toString(a.getMovement()));
        moveLabel.setText("Move");
    }
}
