package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.settlement_tracker.sqlite.model.Ability;
import com.settlement_tracker.sqlite.model.TrackerObject;

/**
 * Created by John on 8/22/2017.
 */

public class AbilityDAO extends DAOSuper implements DAOInterface {
    public static final String TABLE = "ability ";
    public static final String COLUMN_ID = "ability_ID ";
    public static final String COLUMN_TRAITS_ID = "traits_ID";
    public static final String COLUMN_FIGHTING_ARTS1 = "fighting_arts1 ";
    public static final String COLUMN_FIGHTING_ARTS2 = "fighting_arts2 ";
    public static final String COLUMN_FIGHTING_ARTS3 = "fight_arts3 ";
    public static final String COLUMN_DISORDERS1 = "disorders1 ";
    public static final String COLUMN_DISORDERS2 = "disorders2 ";
    public static final String COLUMN_DISORDERS3 = "disorders3 ";
    public static final String COLUMN_IMPAIRMENTS1 = "impairments1 ";
    public static final String COLUMN_IMPAIRMENTS2 = "impairments2 ";
    public static final String COLUMN_IMPAIRMENTS3 = "impairments3 ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;
    private static final String[] mAllColumns = new String[]{COLUMN_ID, COLUMN_TRAITS_ID, COLUMN_FIGHTING_ARTS1, COLUMN_FIGHTING_ARTS2, COLUMN_FIGHTING_ARTS3, COLUMN_DISORDERS1, COLUMN_DISORDERS2,
            COLUMN_DISORDERS3, COLUMN_IMPAIRMENTS1, COLUMN_IMPAIRMENTS2, COLUMN_IMPAIRMENTS3};

    public static final String TAG = "AbilityDAO";

    public AbilityDAO(Context context) {
        super(context);
    }

    @Override
    public long getPrimary(long id) {
        return 0;
    }

    @Override
    public TrackerObject insert(TrackerObject object) {
        Ability ab = (Ability)object;
        ContentValues args = new ContentValues();
        args.put(COLUMN_TRAITS_ID, ab.getFk());
        args.put(COLUMN_FIGHTING_ARTS1, ab.getFightArts1());
        args.put(COLUMN_FIGHTING_ARTS2, ab.getFightArts2());
        args.put(COLUMN_FIGHTING_ARTS3, ab.getFightArts3());
        args.put(COLUMN_DISORDERS1, ab.getDisorders1());
        args.put(COLUMN_DISORDERS2, ab.getDisorders2());
        args.put(COLUMN_DISORDERS3, ab.getDisorders3());
        args.put(COLUMN_IMPAIRMENTS1, ab.getImpairments1());
        args.put(COLUMN_IMPAIRMENTS2, ab.getImpairments2());
        args.put(COLUMN_IMPAIRMENTS3, ab.getImpairments3());
        super.insert(TABLE, args, TAG);
        ab.setId(getNewest());
        return ab;
    }

    @Override
    public void updateString(long pk, String column, String value) {
        super.updateString(TABLE, COLUMN_ID, pk, column, value);
    }

    @Override
    public void updateInt(long pk, String column, int value) {

    }

    @Override
    public long getNewest() {
        return super.getNewest(TAG, TABLE, COLUMN_ID);
    }

    public Ability getAbility(long id) {
        Ability ab = null;
        Cursor c = mDatabase.query(TABLE, mAllColumns, COLUMN_ID + " = " + id, null, null, null, null);
        while (c.moveToNext()) {
            ab = new Ability(c.getLong(0), c.getLong(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10));
        }
        return ab;
    }

    public static AbilityDAO newInstance(Context context) {
        return new AbilityDAO(context);
    }
}
