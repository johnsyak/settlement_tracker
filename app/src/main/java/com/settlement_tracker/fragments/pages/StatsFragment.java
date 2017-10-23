package com.settlement_tracker.fragments.pages;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.fragments.popups.AdjustAttributesPopup;
import com.settlement_tracker.fragments.popups.AdjustDamagePopup;
import com.settlement_tracker.fragments.popups.AdjustTab;
import com.settlement_tracker.fragments.popups.CreatePlayerAlert;
import com.settlement_tracker.interfaces.FragmentCommunicator;
import com.settlement_tracker.sqlite.model.Attributes;
import com.settlement_tracker.sqlite.model.Health;
import com.settlement_tracker.sqlite.model.Injuries;
import com.settlement_tracker.sqlite.model.Player;
import com.settlement_tracker.sqlite.model.Survival;
import com.settlement_tracker.sqllite.dao.AttributesDAO;
import com.settlement_tracker.sqllite.dao.HealthDAO;
import com.settlement_tracker.sqllite.dao.InjuriesDAO;
import com.settlement_tracker.sqllite.dao.PlayerDAO;
import com.settlement_tracker.sqllite.dao.SurvivalDAO;

/**
 * Created by John on 5/28/2017.
 */

public class StatsFragment extends Fragment implements DialogInterface.OnDismissListener {
    private Player p;
    private Attributes a;
    private Health h;
    private Injuries i;
    private Survival s;

    Bundle savedState;
    View rootView;
    FragmentManager manager;
    FragmentCommunicator communicator;

    RelativeLayout headlayout;
    RelativeLayout armslayout;
    RelativeLayout bodylayout;
    RelativeLayout waistlayout;
    RelativeLayout legslayout;

    private Button characterButton = null;
    private TextView characterName = null;
    private TextView gender = null;
    private TextView survivalStat = null;
    private TextView movementStat = null;
    private TextView insanityStat = null;
    private TextView accuracyStat = null;
    private TextView strengthStat = null;
    private TextView evasionStat = null;
    private TextView luckStat = null;
    private TextView speedStat = null;

    private TextView hasInsanity = null;
    private TextView hasSurvival = null;
    private TextView hasDodge = null;
    private TextView hasEncourage = null;
    private TextView hasSurge = null;
    private TextView hasDash = null;

    private TextView headStat = null;
    private TextView armsStat = null;
    private TextView bodyStat = null;
    private TextView waistStat = null;
    private TextView legsStat = null;

    private TextView headLabel = null;
    private TextView armsLabel = null;
    private TextView bodyLabel = null;
    private TextView waistLabel = null;
    private TextView legsLabel = null;

    private TextView headInjured = null;
    private TextView armsInjured = null;
    private TextView bodyInjured = null;
    private TextView waistInjured = null;
    private TextView legsInjured = null;

    private TextView headBase = null;
    private TextView bodyBase = null;
    private TextView armBase = null;
    private TextView waistBase = null;
    private TextView legBase = null;

    private static final String HEAD_TAG = "head", BODY_TAG = "body", ARMS_TAG = "arms", LEGS_TAG = "legs", WAIST_TAG = "waist", SURVIVAL_TAG = "survival", BRAIN_TAG = "brain";

    private static final String LABEL = "label";
    private Bundle args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            args = savedInstanceState;
        else
            args = getArguments();
        this.setRetainInstance(true);
        manager = getFragmentManager();
        communicator = (FragmentCommunicator) getActivity();
        findBundle();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        findBundle();
        setValues();
        setDamageClickListeners();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_statsheet, container, false);
        initLayoutViews();
        setValues();
        setDamageClickListeners();
        return rootView;
    }

    private void setDamageClickListeners() {
        initDamageClickListener(headlayout, HEAD_TAG);
        initDamageClickListener(bodylayout, BODY_TAG);
        initDamageClickListener(waistlayout, WAIST_TAG);
        initDamageClickListener(legslayout, LEGS_TAG);
        initDamageClickListener(armslayout, ARMS_TAG);
    }

    private void initLayoutViews() {
        initNameTab();
        initSurvivalTab();
        initStatTab();
        initDamageTabViews();
        initBrainTab();
    }

    private void setValues() {
        setName();
        setSurvival();
        setStatTab();
        setBrainTab();
        setHeadTab();
        setArmTab();
        setBodyTab();
        setWaistTab();
        setLegTab();
    }

    private void setSurvival() {
        survivalStat.setText(Integer.toString(s.getSurvivalStat()));
        setViewVisible(hasSurvival, s.isSpendSurvival());
        setViewVisible(hasDodge, s.isDodge());
        setViewVisible(hasEncourage, s.isEncourage());
        setViewVisible(hasSurge, s.isSurge());
        setViewVisible(hasDash, s.isDash());
    }

    private void setViewVisible(View v, boolean injured) {
        if (injured)
            v.setVisibility(View.VISIBLE);
        else
            v.setVisibility(View.INVISIBLE);
    }

    private void setLegTab() {
        legsStat.setText(Integer.toString(h.getLegStat()));
        setViewVisible(legsInjured, i.isLegInjured() > 1);
        legBase.setText(Integer.toString(h.getBaseLegStat()));
    }

    private void setWaistTab() {
        waistStat.setText(Integer.toString(h.getWaistStat()));
        setViewVisible(waistInjured, i.isWaistInjured() > 1);
        waistBase.setText(Integer.toString(h.getBaseWaistStat()));
    }

    private void setBodyTab() {
        bodyStat.setText(Integer.toString(h.getBodyStat()));
        setViewVisible(bodyInjured, i.isBodyInjured() > 1);
        bodyBase.setText(Integer.toString(h.getBaseBodyStat()));
    }

    private void setArmTab() {
        armsStat.setText(Integer.toString(h.getArmStat()));
        setViewVisible(armsInjured, i.isArmInjured() > 1);
        armBase.setText(Integer.toString(h.getBaseArmStat()));
    }

    private void setHeadTab() {
        headStat.setText(Integer.toString(h.getHeadStat()));
        setViewVisible(headInjured, i.isHeadInjured() > 0);
        headBase.setText(Integer.toString(h.getBaseHeadStat()));
    }

    private void setBrainTab() {
        insanityStat.setText(Integer.toString(h.getInsanityStat()));
        setViewVisible(hasInsanity, i.isInsane());
    }

    private void initSurvivalTab() {
        RelativeLayout survivalLayout = (RelativeLayout) rootView.findViewById(R.id.survival_tab);
        survivalStat = (TextView) survivalLayout.findViewById(R.id.survival_stat);
        hasSurvival = (TextView) survivalLayout.findViewById(R.id.cannot_spend_survival_text);
        hasDodge = (TextView) survivalLayout.findViewById(R.id.dodge);
        hasEncourage = (TextView) survivalLayout.findViewById(R.id.encourage);
        hasSurge = (TextView) survivalLayout.findViewById(R.id.surge);
        hasDash = (TextView) survivalLayout.findViewById(R.id.dash);


        survivalLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                Bundle b = new Bundle();
                b.putString("label", SURVIVAL_TAG);
                b.putInt("value", s.getSurvivalStat());
                b.putString("tag", "stats");
                AdjustTab tab = AdjustTab.newInstance();
                tab.setArguments(b);
                tab.show(manager, "Adjust stats");
            }
        });
    }


    private void initStatTab() {
        RelativeLayout statLayout = (RelativeLayout) rootView.findViewById(R.id.stat_tab);
        movementStat = (TextView) statLayout.findViewById(R.id.movement_stat);
        accuracyStat = (TextView) statLayout.findViewById(R.id.accuracy_stat);
        strengthStat = (TextView) statLayout.findViewById(R.id.strength_stat);
        evasionStat = (TextView) statLayout.findViewById(R.id.evasion_stat);
        luckStat = (TextView) statLayout.findViewById(R.id.luck_stat);
        speedStat = (TextView) statLayout.findViewById(R.id.speed_stat);
        insanityStat = (TextView) statLayout.findViewById(R.id.insanity_stat);
        statLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                AdjustAttributesPopup adjuster = AdjustAttributesPopup.newInstance();
                Bundle b = new Bundle();
                b.putParcelable(AttributesDAO.TABLE, a);
                adjuster.setArguments(b);
                adjuster.show(transaction, "adjuster");
            }
        });
    }

    private void setName() {
        characterName.setText(p.getName());
        gender.setText(p.getGender() == 0 ? "Male" : "Female");
    }

    private void setStatTab() {
        movementStat.setText(Integer.toString(a.getMovement()));
        accuracyStat.setText(Integer.toString(a.getAccuracy()));
        strengthStat.setText(Integer.toString(a.getStrength()));
        evasionStat.setText(Integer.toString(a.getEvasion()));
        luckStat.setText(Integer.toString(a.getLuck()));
        speedStat.setText(Integer.toString(a.getSpeed()));
    }

    private void findBundle() {
            p = args.getParcelable(PlayerDAO.TABLE);
            a = args.getParcelable(AttributesDAO.TABLE);
            h = args.getParcelable(HealthDAO.TABLE);
            i = args.getParcelable(InjuriesDAO.TABLE);
            s = args.getParcelable(SurvivalDAO.TABLE);
    }

    private void initNameTab() {
        characterName = (TextView) rootView.findViewById(R.id.name);
        gender = (TextView) rootView.findViewById(R.id.gender);
        gender.setOnClickListener(new View.OnClickListener() {
            int genderID = 0;

            @Override
            public void onClick(View v) {
                if (gender.getText().equals("Male")) {
                    gender.setText("Female");
                    genderID = 1;
                } else {
                    gender.setText("Male");
                    genderID = 0;
                }
                communicator.onGenderClick(genderID);
            }
        });
    }

    private void initBrainTab() {
        RelativeLayout brainTab = (RelativeLayout) rootView.findViewById(R.id.brain_tab);
        insanityStat = (TextView) brainTab.findViewById(R.id.insanity_stat);
        hasInsanity = (TextView) brainTab.findViewById(R.id.is_insane);
        brainTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                Bundle b = new Bundle();
                b.putString("label", BRAIN_TAG);
                b.putInt("value", h.getInsanityStat());
                b.putString("tag", "stats");
                AdjustTab tab = AdjustTab.newInstance();
                tab.setArguments(b);
                tab.show(transaction, "Adjust stat");
            }
        });
    }

    private void initDamageClickListener(RelativeLayout layout, final String label) {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                AdjustDamagePopup adjuster = AdjustDamagePopup.newInstance();
                Bundle b = new Bundle();
                b.putParcelable("injuries", i);
                b.putParcelable("health", h);
                b.putString(LABEL, label);
                adjuster.setArguments(b);
                adjuster.show(transaction, "Adjust Stat");
            }
        });
    }

    private void initDamageTabViews() {
        headlayout = (RelativeLayout) rootView.findViewById(R.id.damage_tab_head);
        armslayout = (RelativeLayout) rootView.findViewById(R.id.damage_tab_arms);
        bodylayout = (RelativeLayout) rootView.findViewById(R.id.damage_tab_body);
        waistlayout = (RelativeLayout) rootView.findViewById(R.id.damage_tab_waist);
        legslayout = (RelativeLayout) rootView.findViewById(R.id.damage_tab_legs);

        headStat = (TextView) headlayout.findViewById(R.id.damage_stat);
        armsStat = (TextView) armslayout.findViewById(R.id.damage_stat);
        bodyStat = (TextView) bodylayout.findViewById(R.id.damage_stat);
        waistStat = (TextView) waistlayout.findViewById(R.id.damage_stat);
        legsStat = (TextView) legslayout.findViewById(R.id.damage_stat);

        headBase = (TextView) headlayout.findViewById(R.id.base_stat);
        armBase = (TextView) armslayout.findViewById(R.id.base_stat);
        legBase = (TextView) legslayout.findViewById(R.id.base_stat);
        waistBase = (TextView) waistlayout.findViewById(R.id.base_stat);
        bodyBase = (TextView) bodylayout.findViewById(R.id.base_stat);

        headLabel = (TextView) headlayout.findViewById(R.id.stat_label);
        armsLabel = (TextView) armslayout.findViewById(R.id.stat_label);
        bodyLabel = (TextView) bodylayout.findViewById(R.id.stat_label);
        waistLabel = (TextView) waistlayout.findViewById(R.id.stat_label);
        legsLabel = (TextView) legslayout.findViewById(R.id.stat_label);

        headInjured = (TextView) headlayout.findViewById(R.id.injury_status);
        armsInjured = (TextView) armslayout.findViewById(R.id.injury_status);
        bodyInjured = (TextView) bodylayout.findViewById(R.id.injury_status);
        waistInjured = (TextView) waistlayout.findViewById(R.id.injury_status);
        legsInjured = (TextView) legslayout.findViewById(R.id.injury_status);
        setDamageTabLabels();
    }

    private void setDamageTabLabels() {
        headLabel.setText("Head");
        armsLabel.setText("Arms");
        bodyLabel.setText("Body");
        waistLabel.setText("Waist");
        legsLabel.setText("Legs");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        findBundle();
        setValues();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
            args = savedInstanceState;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(AttributesDAO.TABLE, a);
        outState.putParcelable(HealthDAO.TABLE, h);
        outState.putParcelable(InjuriesDAO.TABLE, i);
        outState.putParcelable(SurvivalDAO.TABLE, s);
        super.onSaveInstanceState(outState);
    }
}
