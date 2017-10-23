package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.settlement_tracker.sqlite.model.Health;
import com.settlement_tracker.sqlite.model.TrackerObject;

/**
 * Created by John on 8/9/2017.
 */

public class HealthDAO extends DAOSuper implements DAOInterface {
    public static final String TABLE = " health ";
    public static final String COLUMN_ID = "health_ID";
    public static final String COLUMN_ATTRIBUTES_ID = AttributesDAO.COLUMN_ID;
    public static final String COLUMN_INSANITY = "insanity ";
    public static final String COLUMN_HEAD = "head ";
    public static final String COLUMN_ARMS = "arms ";
    public static final String COLUMN_BODY = "body ";
    public static final String COLUMN_WAIST = "waist ";
    public static final String COLUMN_LEGS = "legs ";
    public static final String COLUMN_BASE_HEAD = "base_head ";
    public static final String COLUMN_BASE_ARMS = "base_arms ";
    public static final String COLUMN_BASE_BODY = "base_body ";
    public static final String COLUMN_BASE_WAIST = "base_waist ";
    public static final String COLUMN_BASE_LEGS = "base_legs ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;
    private String[] mAllColumns = {COLUMN_ID, COLUMN_ATTRIBUTES_ID, COLUMN_INSANITY, COLUMN_HEAD, COLUMN_ARMS, COLUMN_BODY, COLUMN_WAIST, COLUMN_LEGS, COLUMN_BASE_HEAD, COLUMN_BASE_ARMS, COLUMN_BASE_BODY, COLUMN_BASE_WAIST, COLUMN_BASE_LEGS};

    private static final String TAG = "HealthDAO";

    public HealthDAO(Context context) {
        super(context);
    }

    @Override
    public long getPrimary(long column) {
        return 0;
    }

    @Override
    public TrackerObject insert(TrackerObject object) {
        Health h = (Health) object;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ATTRIBUTES_ID, h.getFk());
        cv.put(COLUMN_INSANITY, h.getInsanityStat());
        cv.put(COLUMN_HEAD, h.getHeadStat());
        cv.put(COLUMN_ARMS, h.getArmStat());
        cv.put(COLUMN_BODY, h.getBodyStat());
        cv.put(COLUMN_WAIST, h.getWaistStat());
        cv.put(COLUMN_LEGS, h.getLegStat());
        cv.put(COLUMN_BASE_HEAD, h.getBaseHeadStat());
        cv.put(COLUMN_BASE_ARMS, h.getBaseArmStat());
        cv.put(COLUMN_BASE_BODY, h.getBaseBodyStat());
        cv.put(COLUMN_BASE_WAIST, h.getBaseWaistStat());
        cv.put(COLUMN_BASE_LEGS, h.getBaseLegStat());
        super.insert(TABLE, cv, TAG);
        h.setId(getNewest());
        return h;
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

    public Health getHealth(long id) {
        Health h = null;
        Cursor c = mDatabase.query(TABLE, mAllColumns, COLUMN_ATTRIBUTES_ID + " =?", DBManager.getString(id), null, null, null, null);
        while (c.moveToNext()) {
            h = new Health(c.getLong(0), id, c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8), c.getInt(9), c.getInt(10), c.getInt(11), c.getInt(12));
        }
        return h;
    }
}
