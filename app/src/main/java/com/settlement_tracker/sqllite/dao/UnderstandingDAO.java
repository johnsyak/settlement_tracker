package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.settlement_tracker.sqlite.model.TrackerObject;
import com.settlement_tracker.sqlite.model.Understanding;

/**
 * Created by John on 8/22/2017.
 */

public class UnderstandingDAO extends DAOSuper implements DAOInterface {

    public static final String TABLE = "understanding ";
    public static final String COLUMN_ID = "understanding_ID ";
    public static final String COLUMN_TRAITS_ID = "traits_ID ";
    public static final String COLUMN_UNDERSTANDING_STAT = "understanding_stat ";
    public static final String COLUMN_INSIGHT = "insight ";
    public static final String COLUMN_WHITE_SECRET = "white_secret ";
    public static final String COLUMN_ANALYZE = "analyze ";
    public static final String COLUMN_EXPLORE = "explore ";
    public static final String COLUMN_TINKER = "tinker ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;
    public static final String[] mAllColumns = new String[]{COLUMN_ID, COLUMN_TRAITS_ID, COLUMN_UNDERSTANDING_STAT, COLUMN_INSIGHT, COLUMN_WHITE_SECRET, COLUMN_ANALYZE, COLUMN_EXPLORE, COLUMN_TINKER};

    public static final String TAG = "UnderstandingDAO";
    public UnderstandingDAO(Context context) {
        super(context);
    }

    @Override
    public long getPrimary(long id) {
        return 0;
    }

    @Override
    public TrackerObject insert(TrackerObject object) {
        Understanding u = (Understanding) object;
        ContentValues args = new ContentValues();
        args.put(COLUMN_TRAITS_ID, u.getFk());
        args.put(COLUMN_UNDERSTANDING_STAT, u.getUnderstandingStat());
        args.put(COLUMN_INSIGHT, u.isInsight());
        args.put(COLUMN_WHITE_SECRET, u.isWhiteSecret());
        args.put(COLUMN_ANALYZE, u.isCanAnalyze());
        args.put(COLUMN_EXPLORE, u.isCanExplore());
        super.insert(TABLE, args, TAG);
        u.setId(getNewest());
        return u;
    }

    @Override
    public void updateString(long pk, String column, String value) {

    }

    @Override
    public void updateInt(long pk, String column, int value) {
        super.updateInt(TABLE, COLUMN_ID, pk, column, value);
    }

    @Override
    public long getNewest() {
       return super.getNewest(TAG, TABLE, COLUMN_ID);
    }

    public Understanding getUnderstanding(long id) {
        Understanding u = null;
        Cursor c = mDatabase.query(TABLE, mAllColumns, COLUMN_ID + " = "+id, null, null, null, null);
        while(c.moveToNext()){
            u = new Understanding(c.getLong(0), c.getLong(1), c.getInt(2), c.getInt(3) > 0, c.getInt(4) > 0, c.getInt(5) > 0, c.getInt(6) > 0, c.getInt(7) > 0);
        }
        return u;
    }
}
