package com.settlement_tracker.fragments.pages;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.fragments.popups.AdjustTab;
import com.settlement_tracker.interfaces.FragmentCommunicator;
import com.settlement_tracker.sqlite.model.Ability;
import com.settlement_tracker.sqlite.model.AbilityDetail;
import com.settlement_tracker.sqlite.model.Courage;
import com.settlement_tracker.sqlite.model.Traits;
import com.settlement_tracker.sqlite.model.Understanding;
import com.settlement_tracker.sqllite.dao.AbilityDAO;
import com.settlement_tracker.sqllite.dao.AbilityDetailDAO;
import com.settlement_tracker.sqllite.dao.CourageDAO;
import com.settlement_tracker.sqllite.dao.TraitsDAO;
import com.settlement_tracker.sqllite.dao.UnderstandingDAO;

/**
 * Created by John on 8/22/2017.
 */

public class TraitsFragment extends Fragment implements DialogInterface.OnDismissListener {
    Traits t;
    Courage c;
    Understanding u;
    Ability ab;
    AbilityDetail abd;

    View rootView;
    Button xpButton;
    Button courageButton;
    Button understandingButton;
    Button proficiencyButton;
    TextView xp;
    TextView milestone1;
    TextView milestone2;
    TextView milestone3;
    TextView milestone4;
    TextView retired;
    EditText weaponType;
    TextView weaponStat;
    TextView specialist;
    TextView master;
    TextView courStat;
    TextView underStat;
    TextView bold;
    TextView seeTruth;
    CheckBox stalwart;
    CheckBox prepared;
    CheckBox matchmaker;

    TextView insight;
    TextView whiteSecret;
    CheckBox analyze;
    CheckBox explore;
    CheckBox tinker;

    TextView fightArtsLabel;
    EditText fightArts1;
    EditText fightArts2;
    EditText fightArts3;

    TextView disordersLabel;
    EditText disorders1;
    EditText disorders2;
    EditText disorders3;

    TextView impairmentsLabel;
    EditText impairments1;
    EditText impairments2;
    EditText impairments3;

    FragmentManager manager;
    FragmentCommunicator communicator;

    private Bundle args;

    private static final String FIGHTARTS1 = "fightarts1", FIGHTARTS2 = "fightarts2", FIGHTARTS3 = "fightarts3", DISORDERS1 = "disorders1", DISORDERS2 = "disorders2",
            DISORDERS3 = "disorders3", IMPAIRMENTS1 = "impairments1", IMPAIRMENTS2 = "impairments2", IMPAIRMENTS3 = "impairments3", WEAPON_TYPE = "weapon_type";

    public static final int STALWART = 0, PREPARED = 1, MATCHMAKER = 2, ANALYZE = 3, EXPLORE = 4, TINKER = 5;

    private static final String XP_TAG = "Hunt XP", PROF_TAG = "prof", COURAGE_TAG = "courage", UNDERSTANDING_TAG = "understanding";

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
    }

    @Override
    public void onResume() {
        super.onResume();
        findBundle();
        setValues();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_traits, container, false);
        findBundle();
        initViews();
        setEditorActionClickListeners();
        setCheckboxListeners();
        setEditorFocusChangeListeners();
        setValues();
        setDamageClickListeners();
        return rootView;

    }

    private void findBundle() {
        t = args.getParcelable(TraitsDAO.TABLE);
        u = args.getParcelable(UnderstandingDAO.TABLE);
        c = args.getParcelable(CourageDAO.TABLE);
        ab = args.getParcelable(AbilityDAO.TABLE);
        abd = args.getParcelable(AbilityDetailDAO.TABLE);
    }

    private void initViews() {
        initWeaponType();
        initHuntXp();
        initWeaponProf();
        initCourage();
        initUnderstanding();
        initArts();
        initDisorders();
        initImpairments();
    }

    private void setDamageClickListeners() {
        xpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                Bundle b = new Bundle();
                b.putString("label", XP_TAG);
                b.putInt("value", t.getXp());
                b.putString("tag", "traits");
                AdjustTab tab = AdjustTab.newInstance();
                tab.setArguments(b);
                tab.show(manager, "Adjust stats");
            }
        });

        proficiencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                Bundle b = new Bundle();
                b.putString("label", PROF_TAG);
                b.putInt("value", t.proficiencyStat());
                b.putString("tag", "traits");
                AdjustTab tab = AdjustTab.newInstance();
                tab.setArguments(b);
                tab.show(manager, "Adjust stats");
            }
        });

        courageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                Bundle b = new Bundle();
                b.putString("label", COURAGE_TAG);
                b.putInt("value", c.getCourageStat());
                b.putString("tag", "traits");
                AdjustTab tab = AdjustTab.newInstance();
                tab.setArguments(b);
                tab.show(manager, "Adjust stats");
            }
        });

        understandingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                Bundle b = new Bundle();
                b.putString("label", UNDERSTANDING_TAG);
                b.putInt("value", u.getUnderstandingStat());
                b.putString("tag", "traits");
                AdjustTab tab = AdjustTab.newInstance();
                tab.setArguments(b);
                tab.show(manager, "Adjust stats");
            }
        });
    }

    private void initHuntXp() {
        RelativeLayout xpLayout = (RelativeLayout) rootView.findViewById(R.id.xp);
        xpButton = (Button) rootView.findViewById(R.id.xp_button);
        xp = (TextView) xpLayout.findViewById(R.id.xp_stat);
        milestone1 = (TextView) xpLayout.findViewById(R.id.milestone1);
        milestone2 = (TextView) xpLayout.findViewById(R.id.milestone2);
        milestone3 = (TextView) xpLayout.findViewById(R.id.milestone3);
        milestone4 = (TextView) xpLayout.findViewById(R.id.milestone4);
        retired = (TextView) xpLayout.findViewById(R.id.retired);
    }

    private void initWeaponType() {
        weaponType = (EditText) rootView.findViewById(R.id.proficiency_type);
        weaponType.setOnEditorActionListener(new SaveEditorDb(WEAPON_TYPE));
        weaponType.setOnFocusChangeListener(new SaveOnFocusLost(WEAPON_TYPE));
    }

    private void initWeaponProf() {
        final RelativeLayout weaponProf = (RelativeLayout) rootView.findViewById(R.id.proficiency);
        proficiencyButton = (Button) weaponProf.findViewById(R.id.proficiency_button);
        weaponStat = (TextView) weaponProf.findViewById(R.id.level);
        specialist = (TextView) weaponProf.findViewById(R.id.specialist);
        master = (TextView) weaponProf.findViewById(R.id.master);
    }

    private void initCourage() {
        RelativeLayout courageLayout = (RelativeLayout) rootView.findViewById(R.id.lore_courage);
        courStat = (TextView) courageLayout.findViewById(R.id.courage_level);
        bold = (TextView) courageLayout.findViewById(R.id.insight);
        seeTruth = (TextView) courageLayout.findViewById(R.id.white_secret);
        stalwart = (CheckBox) courageLayout.findViewById(R.id.analyze);
        prepared = (CheckBox) courageLayout.findViewById(R.id.explore);
        matchmaker = (CheckBox) courageLayout.findViewById(R.id.tinker);
        courageButton = (Button) courageLayout.findViewById(R.id.button_courage);

    }

    private void initUnderstanding() {
        RelativeLayout understandingLayout = (RelativeLayout) rootView.findViewById(R.id.lore_understanding);
        underStat = (TextView) understandingLayout.findViewById(R.id.understanding_level);
        insight = (TextView) understandingLayout.findViewById(R.id.insight);
        whiteSecret = (TextView) understandingLayout.findViewById(R.id.white_secret);
        analyze = (CheckBox) understandingLayout.findViewById(R.id.analyze);
        explore = (CheckBox) understandingLayout.findViewById(R.id.explore);
        tinker = (CheckBox) understandingLayout.findViewById(R.id.tinker);
        understandingButton = (Button) understandingLayout.findViewById(R.id.button_understanding);
    }

    private void initArts() {
        LinearLayout fightArtsLayout = (LinearLayout) rootView.findViewById(R.id.fighting_arts);
        fightArtsLabel = (TextView) fightArtsLayout.findViewById(R.id.type);
        fightArts1 = (EditText) fightArtsLayout.findViewById(R.id.trait1);
        fightArts2 = (EditText) fightArtsLayout.findViewById(R.id.trait2);
        fightArts3 = (EditText) fightArtsLayout.findViewById(R.id.trait3);
        fightArtsLabel.setText("Fighting arts");
    }

    private void initDisorders() {
        LinearLayout disordersLayout = (LinearLayout) rootView.findViewById(R.id.disorders);
        disordersLabel = (TextView) disordersLayout.findViewById(R.id.type);
        disorders1 = (EditText) disordersLayout.findViewById(R.id.trait1);
        disorders2 = (EditText) disordersLayout.findViewById(R.id.trait2);
        disorders3 = (EditText) disordersLayout.findViewById(R.id.trait3);
        disordersLabel.setText("Disorders");

    }

    private void initImpairments() {
        LinearLayout impairmentsLayout = (LinearLayout) rootView.findViewById(R.id.impairments);
        impairmentsLabel = (TextView) impairmentsLayout.findViewById(R.id.type);
        impairments1 = (EditText) impairmentsLayout.findViewById(R.id.trait1);
        impairments2 = (EditText) impairmentsLayout.findViewById(R.id.trait2);
        impairments3 = (EditText) impairmentsLayout.findViewById(R.id.trait3);
        impairmentsLabel.setText("Impairments");
    }

    private void setEditorActionClickListeners() {
        fightArts1.setOnEditorActionListener(new SaveEditorDb(FIGHTARTS1));
        fightArts2.setOnEditorActionListener(new SaveEditorDb(FIGHTARTS2));
        fightArts3.setOnEditorActionListener(new SaveEditorDb(FIGHTARTS3));
        disorders1.setOnEditorActionListener(new SaveEditorDb(DISORDERS1));
        disorders2.setOnEditorActionListener(new SaveEditorDb(DISORDERS2));
        disorders3.setOnEditorActionListener(new SaveEditorDb(DISORDERS3));
        impairments1.setOnEditorActionListener(new SaveEditorDb(IMPAIRMENTS1));
        impairments2.setOnEditorActionListener(new SaveEditorDb(IMPAIRMENTS2));
        impairments3.setOnEditorActionListener(new SaveEditorDb(IMPAIRMENTS3));
    }

    private void setCheckboxListeners() {
        stalwart.setOnCheckedChangeListener(new SaveCheckboxToDb(STALWART));
        prepared.setOnCheckedChangeListener(new SaveCheckboxToDb(PREPARED));
        matchmaker.setOnCheckedChangeListener(new SaveCheckboxToDb(MATCHMAKER));
        analyze.setOnCheckedChangeListener(new SaveCheckboxToDb(ANALYZE));
        explore.setOnCheckedChangeListener(new SaveCheckboxToDb(EXPLORE));
        tinker.setOnCheckedChangeListener(new SaveCheckboxToDb(TINKER));
    }

    private void setEditorFocusChangeListeners() {
        fightArts1.setOnFocusChangeListener(new SaveOnFocusLost(FIGHTARTS1));
        fightArts2.setOnFocusChangeListener(new SaveOnFocusLost(FIGHTARTS2));
        fightArts3.setOnFocusChangeListener(new SaveOnFocusLost(FIGHTARTS3));
        disorders1.setOnFocusChangeListener(new SaveOnFocusLost(DISORDERS1));
        disorders2.setOnFocusChangeListener(new SaveOnFocusLost(DISORDERS2));
        disorders3.setOnFocusChangeListener(new SaveOnFocusLost(DISORDERS3));
        impairments1.setOnFocusChangeListener(new SaveOnFocusLost(IMPAIRMENTS1));
        impairments2.setOnFocusChangeListener(new SaveOnFocusLost(IMPAIRMENTS2));
        impairments3.setOnFocusChangeListener(new SaveOnFocusLost(IMPAIRMENTS3));
    }

    private void setValues() {
        setWeaponType();
        setXP();
        setWeaponProf();
        setDisorders();
        setFightingArts();
        setCourage();
        setUnderstanding();
        setImpairments();
    }

    private void setWeaponType() {
        weaponType.setText(t.getWeaponType());
    }

    private void setWeaponProf() {
        int value = t.proficiencyStat();
        setViewVisible(specialist, false);
        weaponStat.setText(Integer.toString(value));
        setViewVisible(specialist, t.isSpecialist());
        setViewVisible(master, t.isMaster());
    }

    private void setViewVisible(View v, boolean isVisible) {
        if (isVisible)
            v.setVisibility(View.VISIBLE);
        else
            v.setVisibility(View.INVISIBLE);
    }

    private void setXP() {
        int value = t.getXp();
        xp.setText(Integer.toString(value));
        setViewVisible(milestone1, value > 1);
        setViewVisible(milestone2, value > 5);
        setViewVisible(milestone3, value > 9);
        setViewVisible(milestone4, value > 14);
        setViewVisible(retired, value > 16);
    }

    private void setImpairments() {
        impairments1.setText(ab.getImpairments1());
        impairments2.setText(ab.getImpairments2());
        impairments3.setText(ab.getImpairments3());
    }

    private void setDisorders() {
        disorders1.setText(ab.getDisorders1());
        disorders2.setText(ab.getDisorders2());
        disorders3.setText(ab.getDisorders3());
    }

    private void setFightingArts() {
        fightArts1.setText(ab.getFightArts1());
        fightArts2.setText(ab.getFightArts2());
        fightArts3.setText(ab.getFightArts3());
    }

    private void setUnderstanding() {
        int value = u.getUnderstandingStat();
        underStat.setText(Integer.toString(value));
        analyze.setChecked(u.isCanAnalyze());
        explore.setChecked(u.isCanExplore());
        tinker.setChecked(u.isCanTinker());
        setViewVisible(insight, u.isInsight());
        setViewVisible(whiteSecret, u.isWhiteSecret());
    }

    private void setCourage() {
        int value = c.getCourageStat();
        courStat.setText(Integer.toString(value));
        stalwart.setChecked(c.isStalwart());
        prepared.setChecked(c.isPrepared());
        matchmaker.setChecked(c.isMatchmaker());
        setViewVisible(bold, c.isBold());
        setViewVisible(seeTruth, c.isSeeTruth());
    }

    private class SaveCheckboxToDb implements CompoundButton.OnCheckedChangeListener {
        int type;

        public SaveCheckboxToDb(int type) {
            this.type = type;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            boolean checked = false;
            switch (type) {
                case STALWART:
                    checked = stalwart.isChecked();
                    break;
                case PREPARED:
                    checked = prepared.isChecked();
                    break;
                case MATCHMAKER:
                    checked = matchmaker.isChecked();
                    break;
                case ANALYZE:
                    checked = analyze.isChecked();
                    break;
                case EXPLORE:
                    checked = explore.isChecked();
                    break;
                case TINKER:
                    checked = tinker.isChecked();
                    break;
            }
            communicator.checksToDb(checked, type);
        }
    }

    private class SaveOnFocusLost implements View.OnFocusChangeListener {
        String type;

        public SaveOnFocusLost(String type) {
            this.type = type;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            String text;
            text = editTextKillFocus(type);
            communicator.writeToDb(text, type);
        }
    }

    private class SaveEditorDb implements TextView.OnEditorActionListener {
        String type;

        public SaveEditorDb(String type) {
            this.type = type;
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            String text;
            if (event == null && (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE)) {
                text = editTextKillFocus(type);
                communicator.writeToDb(text, type);
            }
            return false;
        }
    }



    private String editTextKillFocus(String type) {
        String text = "";
        switch (type) {
            case WEAPON_TYPE:
                text = weaponType.getText().toString().replace("'", "''");
                break;
            case FIGHTARTS1:
                text = fightArts1.getText().toString().replace("'", "''");
                break;
            case FIGHTARTS2:
                text = fightArts2.getText().toString().replace("'", "''");
                break;
            case FIGHTARTS3:
                text = fightArts3.getText().toString().replace("'", "''");
                break;
            case DISORDERS1:
                text = disorders1.getText().toString().replace("'", "''");
                break;
            case DISORDERS2:
                text = disorders2.getText().toString().replace("'", "''");
                break;
            case DISORDERS3:
                disorders3.getText().toString().replace("'", "''");
                break;
            case IMPAIRMENTS1:
                text = impairments1.getText().toString().replace("'", "''");
                break;
            case IMPAIRMENTS2:
                text = impairments2.getText().toString().replace("'", "''");
                break;
            case IMPAIRMENTS3:
                text = impairments3.getText().toString().replace("'", "''");
                break;
            default:
                text = "";
                break;
        }
        return text;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        findBundle();
        setValues();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
            args = savedInstanceState;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(TraitsDAO.TABLE, t);
        outState.putParcelable(AbilityDAO.TABLE, ab);
        outState.putParcelable(AbilityDetailDAO.TABLE, abd);
        outState.putParcelable(UnderstandingDAO.TABLE, u);
        outState.putParcelable(CourageDAO.TABLE, c);
        super.onSaveInstanceState(outState);
    }
}
