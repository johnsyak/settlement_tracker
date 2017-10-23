package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.settlement_tracker.sqlite.model.Injuries;
import com.settlement_tracker.sqlite.model.TrackerObject;

/**
 * Created by John on 8/9/2017.
 */

public class InjuriesDAO extends DAOSuper implements DAOInterface {
    public static final String TABLE = " injuries ";
    public static final String COLUMN_ID = "injuries_ID";
    public static final String COLUMN_HEALTH_ID = HealthDAO.COLUMN_ID;
    public static final String COLUMN_INSANITY = "insanity ";
    public static final String COLUMN_HEAD = "head ";
    public static final String COLUMN_ARMS = "arms ";
    public static final String COLUMN_BODY = "body ";
    public static final String COLUMN_WAIST = "waist ";
    public static final String COLUMN_LEGS = "legs ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;

    private String[] mAllColumns = {COLUMN_ID, COLUMN_HEALTH_ID, COLUMN_INSANITY, COLUMN_HEAD, COLUMN_ARMS, COLUMN_BODY, COLUMN_WAIST, COLUMN_LEGS};

    private static final String TAG = "InjuriesDAO";

    public InjuriesDAO(Context context) {
        super(context);
    }

    @Override
    public long getPrimary(long column) {
        return 0;
    }

    @Override
    public TrackerObject insert(TrackerObject object) {
        Injuries i = (Injuries) object;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_HEALTH_ID, i.getFk());
        cv.put(COLUMN_INSANITY, i.isInsane());
        cv.put(COLUMN_HEAD, i.isHeadInjured());
        cv.put(COLUMN_ARMS, i.isArmInjured());
        cv.put(COLUMN_BODY, i.isBodyInjured());
        cv.put(COLUMN_WAIST, i.isWaistInjured());
        cv.put(COLUMN_LEGS, i.isLegInjured());
        super.insert(TABLE, cv, TAG);
        i.setId(getNewest());
        return i;
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

    public Injuries getInjuries(long id) {
        Injuries i = null;
        Cursor c = mDatabase.query(TABLE, mAllColumns, COLUMN_HEALTH_ID + " =?", new String[]{Long.toString(id)}, null, null, null, null);
        while (c.moveToNext()) {
            i = new Injuries(c.getLong(0), c.getLong(1), c.getInt(2) >0 , c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6), c.getInt(7));
        }
        return i;
    }
}
