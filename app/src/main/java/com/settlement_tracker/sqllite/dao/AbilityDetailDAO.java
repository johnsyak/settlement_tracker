package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.settlement_tracker.exceptions.InvalidName;
import com.settlement_tracker.sqlite.model.AbilityDetail;
import com.settlement_tracker.sqlite.model.TrackerObject;

/**
 * Created by John on 8/22/2017.
 */

public class AbilityDetailDAO extends DAOSuper implements DAOInterface {
    public static final String TABLE = "ability_details ";
    public static final String COLUMN_ID="ability_details_ID ";
    public static final String COLUMN_ABILITIES_ID= "ability_ID ";
    public static final String COLUMN_CAN_USE_ARTS= "can_use_arts ";
    public static final String COLUMN_SKIP_HUNT= "skip_hunt ";
    public static final String COLUMN_MAX_ARTS= "max_arts ";
    public static final String COLUMN_MAX_DISORDERS= "max_disorders ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;
    public static final String[] mAllColumns = new String[]{COLUMN_ID, COLUMN_ABILITIES_ID, COLUMN_CAN_USE_ARTS, COLUMN_SKIP_HUNT, COLUMN_MAX_ARTS, COLUMN_MAX_DISORDERS};

    public static final String TAG = "AbilityDetailDAO";
    public AbilityDetailDAO(Context context){super(context);}

    @Override
    public long getPrimary(long id) {
        return 0;
    }

    @Override
    public TrackerObject insert(TrackerObject object){
        AbilityDetail abd = (AbilityDetail)object;
        ContentValues args = new ContentValues();
        args.put(COLUMN_ABILITIES_ID, abd.getFk());
        args.put(COLUMN_CAN_USE_ARTS, abd.isArts());
        args.put(COLUMN_SKIP_HUNT, abd.isSkipHunt());
        args.put(COLUMN_MAX_ARTS, abd.isMaxArts());
        args.put(COLUMN_MAX_DISORDERS, abd.isMaxDisorders());
        super.insert(TABLE, args, TAG);
        abd.setId(getNewest());
        return abd;
    }

    @Override
    public void updateString(long pk, String column, String value) {

    }

    @Override
    public void updateInt(long pk, String column, int value) {

    }

    @Override
    public long getNewest() {
        return super.getNewest(TAG, TABLE, COLUMN_ID);
    }

    public AbilityDetail getAbilityDetail(long id) {
        AbilityDetail abd = null;
        Cursor c = mDatabase.query(TABLE, mAllColumns, COLUMN_ID + " = "+id, null, null, null, null);
        while(c.moveToNext()){
            abd = new AbilityDetail(c.getLong(0), c.getLong(1), c.getInt(2) >0, c.getInt(3) >0, c.getInt(4) >0, c.getInt(5) >0);
        }
        return abd;
    }
}
