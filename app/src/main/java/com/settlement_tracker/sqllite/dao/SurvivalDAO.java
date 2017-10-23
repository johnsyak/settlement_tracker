package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.settlement_tracker.sqlite.model.Survival;
import com.settlement_tracker.sqlite.model.TrackerObject;

/**
 * Created by John on 8/9/2017.
 */

public class SurvivalDAO extends DAOSuper implements DAOInterface {
    public static final String TABLE = " survival ";
    public static final String COLUMN_ID = "survival_ID";
    public static final String COLUMN_ATTRIBUTES_ID = AttributesDAO.COLUMN_ID;
    public static final String COLUMN_SURVIVAL_STAT = "survival_stat ";
    public static final String COLUMN_SURVIVAL = "survival";
    public static final String COLUMN_DODGE = "dodge ";
    public static final String COLUMN_ENCOURAGE = "encourage ";
    public static final String COLUMN_SURGE = "surge ";
    public static final String COLUMN_DASH = "dash ";
    public static final String[] mAllColumns = new String[]{COLUMN_ID, COLUMN_ATTRIBUTES_ID, COLUMN_SURVIVAL_STAT, COLUMN_SURVIVAL, COLUMN_DODGE, COLUMN_ENCOURAGE, COLUMN_SURGE, COLUMN_DASH};
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;

    public static final String TAG = "SurvivalDAO";

    public SurvivalDAO(Context context) {
        super(context);
    }

    @Override
    public long getPrimary(long column) {
        return 0;
    }

    @Override
    public TrackerObject insert(TrackerObject object) {
        Survival s = (Survival) object;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ATTRIBUTES_ID, s.getFk());
        cv.put(COLUMN_SURVIVAL_STAT, s.getSurvivalStat());
        cv.put(COLUMN_SURVIVAL, s.isSpendSurvival());
        cv.put(COLUMN_DODGE, s.isDodge());
        cv.put(COLUMN_ENCOURAGE, s.isEncourage());
        cv.put(COLUMN_SURGE, s.isEncourage());
        cv.put(COLUMN_DASH, s.isDash());
        super.insert(TABLE, cv, TAG);
        s.setId(getNewest());
        return s;
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

    public Survival getSurvival(long id) {
        Survival s = null;
        Cursor c = mDatabase.query(TABLE, mAllColumns, COLUMN_ATTRIBUTES_ID + " = " + id, null, null, null, null);
        while (c.moveToNext()) {
            s = new Survival(c.getLong(0), c.getLong(1), c.getInt(2), c.getInt(3) > 0, c.getInt(4) > 0, c.getInt(5) > 0, c.getInt(6) > 0, c.getInt(7) > 0);
        }
        return s;
    }
}
