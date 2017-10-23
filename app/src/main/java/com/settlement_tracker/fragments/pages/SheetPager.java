package com.settlement_tracker.fragments.pages;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.john.kingdomdeath.R;
import com.settlement_tracker.adapters.PageAdapter;
import com.settlement_tracker.fragments.popups.AdjustAttributesPopup;
import com.settlement_tracker.fragments.popups.AdjustDamagePopup;
import com.settlement_tracker.fragments.popups.AdjustTab;
import com.settlement_tracker.interfaces.FragmentCommunicator;
import com.settlement_tracker.sqlite.model.Ability;
import com.settlement_tracker.sqlite.model.AbilityDetail;
import com.settlement_tracker.sqlite.model.Attributes;
import com.settlement_tracker.sqlite.model.Courage;
import com.settlement_tracker.sqlite.model.Health;
import com.settlement_tracker.sqlite.model.Injuries;
import com.settlement_tracker.sqlite.model.Player;
import com.settlement_tracker.sqlite.model.Traits;
import com.settlement_tracker.sqlite.model.Survival;
import com.settlement_tracker.sqlite.model.Understanding;
import com.settlement_tracker.sqllite.dao.AbilityDAO;
import com.settlement_tracker.sqllite.dao.AbilityDetailDAO;
import com.settlement_tracker.sqllite.dao.AttributesDAO;
import com.settlement_tracker.sqllite.dao.CourageDAO;
import com.settlement_tracker.sqllite.dao.HealthDAO;
import com.settlement_tracker.sqllite.dao.InjuriesDAO;
import com.settlement_tracker.sqllite.dao.PlayerDAO;
import com.settlement_tracker.sqllite.dao.SurvivalDAO;
import com.settlement_tracker.sqllite.dao.TraitsDAO;
import com.settlement_tracker.sqllite.dao.UnderstandingDAO;

import java.util.List;
import java.util.Vector;

/**
 * Created by John on 8/8/2017.
 */

public class SheetPager extends FragmentActivity implements FragmentCommunicator{
    private Player player;
    private Attributes a;
    private Health h;

    Bundle args;
    private PageAdapter mPager;

    private PlayerDAO playerDAO;
    private AttributesDAO attributesDAO;
    private HealthDAO healthDAO;
    private InjuriesDAO injuriesDAO;
    private Injuries i;
    private SurvivalDAO survivalDAO;
    private Survival s;
    private UnderstandingDAO understandingDAO;
    private Understanding u;
    private Ability ab;
    private CourageDAO courageDAO;
    private Courage c;
    private AbilityDAO abilityDAO;
    private AbilityDetailDAO abilityDetailDAO;
    private AbilityDetail abd;
    private TraitsDAO traitsDAO;
    private Traits t;

    private static final String FIGHTARTS1 = "fightarts1", FIGHTARTS2 = "fightarts2", FIGHTARTS3 = "fightarts3", DISORDERS1 = "disorders1", DISORDERS2 = "disorders2",
            DISORDERS3 = "disorders3", IMPAIRMENTS1 = "impairments1", IMPAIRMENTS2 = "impairments2", IMPAIRMENTS3 = "impairments3", WEAPON_TYPE = "weapon_type";

    public static final int STALWART = 0, PREPARED = 1, MATCHMAKER = 2, ANALYZE = 3, EXPLORE = 4, TINKER =5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            int i = 0;
        }

        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            args = savedInstanceState;
        else
            args = getIntent().getExtras();
        initPlayer();
        initAllStats();
        setContentView(R.layout.page_adapter);
    }


    private void initPlayer() {
        player = args.getParcelable(PlayerDAO.TABLE);
    }

    private void initPaging() {
        //TODO somethings wrong with the screen flip
        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, StatsFragment.class.getName(), args));
        fragments.add(Fragment.instantiate(this, TraitsFragment.class.getName(), args));
        this.mPager = new PageAdapter(super.getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager) super.findViewById(R.id.view_pager);

        pager.setAdapter(this.mPager);
    }

    public void initAllStats() {
        getPlayer();
        getAttributes();
        getHealth();
        getInjuries();
        getSurvival();
        getTraits();
        getCourage();
        getUnderstanding();
        getAbilities();
        getAbilityDetail();
    }

    private void getPlayer() {
        player = args.getParcelable(PlayerDAO.TABLE);
    }

    public void getAttributes() {
        attributesDAO = new AttributesDAO(getApplicationContext());
        a = attributesDAO.getAttributes(player.getId());
        if (a == null) {
            a = new Attributes(player.getId(), false, 5, 0, 0, 0, 0, 0);
            a = (Attributes) attributesDAO.insert(a);
        }
        args.putParcelable(AttributesDAO.TABLE, a);
    }

    private void getHealth() {
        healthDAO = new HealthDAO(getApplicationContext());
        h = healthDAO.getHealth(a.getId());
        if (h == null) {
            h = new Health(a.getId(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            h = (Health) healthDAO.insert(h);
        }
        args.putParcelable(HealthDAO.TABLE, h);
    }

    private void getInjuries() {
        injuriesDAO = new InjuriesDAO(getApplicationContext());
        i = injuriesDAO.getInjuries(h.getId());
        if (i == null) {
            i = new Injuries(h.getId(), false, 0, 0, 0, 0, 0);
            i = (Injuries) injuriesDAO.insert(i);
        }
        args.putParcelable(InjuriesDAO.TABLE, i);
    }

    private void getSurvival() {
        survivalDAO = new SurvivalDAO(getApplicationContext());
        s = survivalDAO.getSurvival(h.getId());
        if (s == null) {
            s = new Survival(h.getId(), 0, false, false, false, false, false);
            s = (Survival) survivalDAO.insert(s);
        }
        args.putParcelable(SurvivalDAO.TABLE, s);
    }

    private void getTraits(){
        traitsDAO = new TraitsDAO(getApplicationContext());
        t = traitsDAO.getTraits(player.getId());
        if(t == null){
            t = new Traits(player.getId(), 0, "", 0, false, false);
            t = (Traits) traitsDAO.insert(t);
        }
        args.putParcelable(TraitsDAO.TABLE, t);
    }
    
    private void getCourage(){
        courageDAO = new CourageDAO(getApplicationContext());
        c = courageDAO.getCourage(player.getId());
        if(c == null){
            c = new Courage(t.getId(), 0, false, false ,false, false, false);
            c = (Courage) courageDAO.insert(c);
        }
        args.putParcelable(CourageDAO.TABLE, c);
    }

    private void getUnderstanding(){
        understandingDAO = new UnderstandingDAO(getApplicationContext());
        u = understandingDAO.getUnderstanding(player.getId());
        if(u == null){
            u = new Understanding(t.getId(), 0, false, false, false, false, false);
            u = (Understanding) understandingDAO.insert(u);
        }
        args.putParcelable(UnderstandingDAO.TABLE, u);
    }

    private void getAbilities(){
        abilityDAO = new AbilityDAO(getApplicationContext());
        ab = abilityDAO.getAbility(t.getId());
        if(ab == null) {
            ab = new Ability(t.getId(), "", "", "", "", "", "", "", "", "");
            ab = (Ability) abilityDAO.insert(ab);
        }
        args.putParcelable(AbilityDAO.TABLE, ab);
    }

    private void getAbilityDetail(){
        abilityDetailDAO = new AbilityDetailDAO(getApplicationContext());
        abd = abilityDetailDAO.getAbilityDetail(ab.getId());
        if(abd == null){
            abd = new AbilityDetail(ab.getId(), false, false, false, false);
            abd = (AbilityDetail) abilityDetailDAO.insert(abd);
        }
        args.putParcelable(AbilityDetailDAO.TABLE, abd);
    }

    @Override
    public void onStatConfirmClick(Attributes a) {
        this.a = a;
        attributesDAO.update(a);
        getAttributes();
    }

    @Override
    public void onAdjustHeadClick(int value, int injury, int baseStat) {
        healthDAO.updateInt(a.getId(), HealthDAO.COLUMN_HEAD, value);
        healthDAO.updateInt(a.getId(), HealthDAO.COLUMN_BASE_HEAD, baseStat);
        injuriesDAO.updateInt(i.getId(), InjuriesDAO.COLUMN_HEAD, injury);
        getHealth();
        getInjuries();
    }

    @Override
    public void onAdjustLegsClick(int value, int injury, int baseStat) {
        healthDAO.updateInt(h.getId(), HealthDAO.COLUMN_LEGS, value);
        healthDAO.updateInt(a.getId(), HealthDAO.COLUMN_BASE_LEGS, baseStat);
        injuriesDAO.updateInt(i.getId(), InjuriesDAO.COLUMN_LEGS, injury);
        getHealth();
        getInjuries();
    }

    @Override
    public void onAdjustArmClick(int value, int injury, int baseStat) {
        healthDAO.updateInt(h.getId(), HealthDAO.COLUMN_ARMS, value);
        healthDAO.updateInt(a.getId(), HealthDAO.COLUMN_BASE_ARMS, baseStat);
        injuriesDAO.updateInt(i.getId(), InjuriesDAO.COLUMN_ARMS, injury);
        getHealth();
        getInjuries();
    }

    @Override
    public void onAdjustWaistClick(int value, int injury, int baseStat) {
        healthDAO.updateInt(h.getId(), HealthDAO.COLUMN_WAIST, value);
        injuriesDAO.updateInt(i.getId(), InjuriesDAO.COLUMN_WAIST, injury);
        healthDAO.updateInt(a.getId(), HealthDAO.COLUMN_BASE_WAIST, baseStat);
        getHealth();
        getInjuries();
    }

    @Override
    public void onAdjustBodyClick(int value, int injury, int baseStat) {
        healthDAO.updateInt(h.getId(), HealthDAO.COLUMN_BODY, value);
        injuriesDAO.updateInt(i.getId(), InjuriesDAO.COLUMN_BODY, injury);
        healthDAO.updateInt(a.getId(), HealthDAO.COLUMN_BASE_BODY, baseStat);
        getHealth();
        getInjuries();
    }

    @Override
    public void onProfClick(int value) {
        traitsDAO.updateInt(t.getId(), TraitsDAO.COLUMN_WEAPON_PROFICIENCY, value);
        setProfLevel(value);
        getTraits();
    }

    private void setProfLevel(int value) {
        if(value > 2)
            traitsDAO.updateInt(t.getId(), TraitsDAO.COLUMN_IS_SPECIALIST, 1);
        else
            traitsDAO.updateInt(t.getId(), TraitsDAO.COLUMN_IS_SPECIALIST, 0);
        if(value > 7)
            traitsDAO.updateInt(t.getId(), TraitsDAO.COLUMN_IS_MASTER, 1);
        else
            traitsDAO.updateInt(t.getId(), TraitsDAO.COLUMN_IS_MASTER, 0);
    }

    @Override
    public void onBrainClick(int value) {
        healthDAO.updateInt(h.getId(), HealthDAO.COLUMN_INSANITY, value);
        injuriesDAO.updateInt(i.getId(), InjuriesDAO.COLUMN_INSANITY, value);
        if (value > 2)
            injuriesDAO.updateInt(h.getId(), InjuriesDAO.COLUMN_INSANITY, 1);
        getHealth();
        getInjuries();
    }

    @Override
    public void onSurvivalClick(int value) {
        survivalDAO.updateInt(s.getId(), SurvivalDAO.COLUMN_SURVIVAL_STAT, value);
        getSurvival();
    }

    @Override
    public void onHuntClick(int value) {
        traitsDAO.updateInt(t.getId(), TraitsDAO.COLUMN_HUNT_XP, value);
        getTraits();
    }

    private void setUnderstandingTrait(int value){
        if(value > 2)
            understandingDAO.updateInt(u.getId(), UnderstandingDAO.COLUMN_INSIGHT, 1);
        else
            understandingDAO.updateInt(u.getId(), UnderstandingDAO.COLUMN_INSIGHT, 0);
        if(value > 8)
            understandingDAO.updateInt(c.getId(), UnderstandingDAO.COLUMN_WHITE_SECRET, 1);
        else
            understandingDAO.updateInt(c.getId(), UnderstandingDAO.COLUMN_WHITE_SECRET, 0);
    }

    private void setCourageTrait(int value){
        if(value > 2)
            courageDAO.updateInt(c.getId(), CourageDAO.COLUMN_BOLD, 1);
        else
            courageDAO.updateInt(c.getId(), CourageDAO.COLUMN_BOLD, 0);
        if(value > 8)
            courageDAO.updateInt(c.getId(), CourageDAO.COLUMN_TRUTH, 1);
        else
            courageDAO.updateInt(c.getId(), CourageDAO.COLUMN_TRUTH, 0);
    }

    @Override
    public void onCourageClick(int value) {
        courageDAO.updateInt(t.getId(), CourageDAO.COLUMN_COURAGE, value);
        setCourageTrait(value);
        getCourage();
    }

    @Override
    public void onUnderstandingClick(int value) {
        understandingDAO.updateInt(u.getId(), UnderstandingDAO.COLUMN_UNDERSTANDING_STAT, value);
        setUnderstandingTrait(value);
        getUnderstanding();
    }

    @Override
    public void writeToDb(String text, String tag) {
        switch (tag) {
            case WEAPON_TYPE:
                traitsDAO.updateString(t.getId(), TraitsDAO.COLUMN_PROFICIENCY_TYPE, text);
                break;
            case FIGHTARTS1:
                abilityDAO.updateString(abd.getId(), AbilityDAO.COLUMN_FIGHTING_ARTS1, text);
                break;
            case FIGHTARTS2:
                abilityDAO.updateString(abd.getId(), AbilityDAO.COLUMN_FIGHTING_ARTS2, text);
                break;
            case FIGHTARTS3:
                abilityDAO.updateString(abd.getId(), AbilityDAO.COLUMN_FIGHTING_ARTS3, text);
                break;
            case DISORDERS1:
                abilityDAO.updateString(abd.getId(), AbilityDAO.COLUMN_DISORDERS1, text);
                break;
            case DISORDERS2:
                abilityDAO.updateString(abd.getId(), AbilityDAO.COLUMN_DISORDERS2, text);
                break;
            case DISORDERS3:
                abilityDAO.updateString(abd.getId(), AbilityDAO.COLUMN_DISORDERS3, text);
                break;
            case IMPAIRMENTS1:
                abilityDAO.updateString(abd.getId(), AbilityDAO.COLUMN_IMPAIRMENTS1, text);
                break;
            case IMPAIRMENTS2:
                abilityDAO.updateString(abd.getId(), AbilityDAO.COLUMN_IMPAIRMENTS2, text);
                break;
            case IMPAIRMENTS3:
                abilityDAO.updateString(abd.getId(), AbilityDAO.COLUMN_IMPAIRMENTS3, text);
                break;
            default:
                Log.d("writeToDb: ", "Failed");
                break;
        }
    }

    @Override
    public void checksToDb(boolean checked, int tag) {
        switch(tag){
            case STALWART:
                courageDAO.updateInt(c.getId(), CourageDAO.COLUMN_STALWART, checked?1:0);
                getCourage();
                break;
            case PREPARED:
                courageDAO.updateInt(c.getId(), CourageDAO.COLUMN_PREPARED, checked?1:0);
                getCourage();
                break;
            case MATCHMAKER:
                courageDAO.updateInt(c.getId(), CourageDAO.COLUMN_MATCHMAKER, checked?1:0);
                getCourage();
                break;
            case ANALYZE:
                understandingDAO.updateInt(c.getId(), UnderstandingDAO.COLUMN_ANALYZE, checked?1:0);
                getUnderstanding();
                break;
            case EXPLORE:
                understandingDAO.updateInt(c.getId(), UnderstandingDAO.COLUMN_EXPLORE, checked?1:0);
                getUnderstanding();
                break;
            case TINKER:
                understandingDAO.updateInt(c.getId(), UnderstandingDAO.COLUMN_TINKER, checked?1:0);
                getUnderstanding();
                break;
            default:
                Log.d("checksToDb", "Failed");
                break;
        }
    }

    @Override
    public void onGenderClick(int gender) {
        playerDAO = new PlayerDAO(getApplicationContext());
        playerDAO.updateInt(player.getId(), PlayerDAO.COLUMN_GENDER, gender);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(args == null) {
            args = new Bundle();
            initAllStats();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
            args = savedInstanceState;
    }

    @Override
    protected void onResume() {
        super.onResume();
        args = getIntent().getExtras();
        if(args != null) {
            initAllStats();
            this.initPaging();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PlayerDAO.TABLE, player);
        outState.putParcelable(AttributesDAO.TABLE, a);
        outState.putParcelable(HealthDAO.TABLE, h);
        outState.putParcelable(InjuriesDAO.TABLE, i);
        outState.putParcelable(SurvivalDAO.TABLE, s);
        outState.putParcelable(TraitsDAO.TABLE, t);
        outState.putParcelable(AbilityDAO.TABLE, ab);
        outState.putParcelable(AbilityDetailDAO.TABLE, abd);
        outState.putParcelable(UnderstandingDAO.TABLE, u);
        outState.putParcelable(CourageDAO.TABLE, c);
    }
}
