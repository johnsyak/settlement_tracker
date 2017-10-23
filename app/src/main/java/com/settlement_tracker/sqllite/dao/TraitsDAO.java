package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.settlement_tracker.sqlite.model.TrackerObject;
import com.settlement_tracker.sqlite.model.Traits;

/**
 * Created by John on 8/22/2017.
 */

public class TraitsDAO extends DAOSuper implements DAOInterface {
    public static final String TABLE = "traits ";
    public static final String COLUMN_ID="traits_ID";
    public static final String COLUMN_PLAYER_ID= "player_ID ";
    public static final String COLUMN_HUNT_XP= "hunt_XP ";
    public static final String COLUMN_PROFICIENCY_TYPE = "weapon_type ";
    public static final String COLUMN_WEAPON_PROFICIENCY= "weapon_proficiency ";
    public static final String COLUMN_IS_SPECIALIST= "specialist ";
    public static final String COLUMN_IS_MASTER= "master ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;
    public static final String[] mAllColumns = new String[]{COLUMN_ID, COLUMN_PLAYER_ID, COLUMN_HUNT_XP, COLUMN_PROFICIENCY_TYPE, COLUMN_WEAPON_PROFICIENCY,COLUMN_IS_SPECIALIST, COLUMN_IS_MASTER};

    public static final String TAG = "TraitsDAO";

    public TraitsDAO(Context context){
        super(context);
    }

    @Override
    public long getPrimary(long id) {
        return 0;
    }

    @Override
    public TrackerObject insert(TrackerObject object) {
        Traits t = (Traits) object;
        ContentValues args = new ContentValues();
        args.put(COLUMN_PLAYER_ID, t.getFk());
        args.put(COLUMN_HUNT_XP, t.getXp());
        args.put(COLUMN_PROFICIENCY_TYPE, t.getWeaponType());
        args.put(COLUMN_WEAPON_PROFICIENCY, t.proficiencyStat());
        args.put(COLUMN_IS_SPECIALIST, t.isSpecialist());
        args.put(COLUMN_IS_MASTER, t.isMaster());
        super.insert(TABLE, args, TAG);
        t.setId(getNewest());
        return t;
    }

    @Override
    public void updateString(long pk, String column, String value) {
        super.updateString(TABLE, COLUMN_ID, pk, column, value);
    }

    @Override
    public void updateInt(long pk, String column, int value) {
        super.updateInt(TABLE, COLUMN_ID, pk, column, value);
    }

    @Override
    public long getNewest() {
        return super.getNewest(TAG, TABLE, COLUMN_ID);
    }

    public Traits getTraits(long id) {
        Traits t = null;
        Cursor c = mDatabase.query(TABLE, mAllColumns, COLUMN_ID + " = "+id, null, null, null, null);
        while(c.moveToNext()){
            t = new Traits(c.getLong(0), c.getLong(1), c.getInt(2), c.getString(3), c.getInt(4), c.getInt(5) > 0, c.getInt(6) > 0);
        }
        return t;
    }
}
