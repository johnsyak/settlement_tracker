package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.settlement_tracker.exceptions.InvalidName;
import com.settlement_tracker.sqlite.model.Courage;
import com.settlement_tracker.sqlite.model.TrackerObject;

/**
 * Created by John on 8/22/2017.
 */

public class CourageDAO extends DAOSuper implements DAOInterface {

    public static final String TABLE = "courage ";
    public static final String COLUMN_ID = "courage_ID ";
    public static final String COLUMN_PROFICIENCY_ID = "proficiency_ID ";
    public static final String COLUMN_COURAGE = "courage_stat ";
    public static final String COLUMN_BOLD = "bold ";
    public static final String COLUMN_TRUTH = "truth ";
    public static final String COLUMN_STALWART = "stalwart ";
    public static final String COLUMN_PREPARED = "prepared ";
    public static final String COLUMN_MATCHMAKER = "matchmaker ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;
    public static final String[] mAllColumns = new String[]{COLUMN_ID, COLUMN_PROFICIENCY_ID, COLUMN_COURAGE, COLUMN_BOLD, COLUMN_TRUTH, COLUMN_STALWART, COLUMN_PREPARED, COLUMN_MATCHMAKER};

    public static final String TAG = "CourageDAO";
    public CourageDAO(Context context) {
        super(context);
    }

    @Override
    public long getPrimary(long id) {
        return super.getPrimary(TABLE, mAllColumns, COLUMN_ID, id);
    }

    @Override
    public TrackerObject insert(TrackerObject object)  {
        Courage c = (Courage) object;
        ContentValues args = new ContentValues();
        args.put(COLUMN_PROFICIENCY_ID, c.getFk());
        args.put(COLUMN_COURAGE, c.getCourageStat());
        args.put(COLUMN_BOLD, c.isBold());
        args.put(COLUMN_TRUTH, c.isSeeTruth());
        args.put(COLUMN_STALWART, c.isStalwart());
        args.put(COLUMN_PREPARED, c.isPrepared());
        args.put(COLUMN_MATCHMAKER, c.isMatchmaker());
        super.insert(TABLE, args, COLUMN_ID);
        c.setId(getNewest());
        return c;
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

    public Courage getCourage(long id) {
        Courage cour = null;
        Cursor c = mDatabase.query(TABLE, mAllColumns, COLUMN_ID + " = "+id, null, null, null, null);
        while(c.moveToNext()){
            cour = new Courage(c.getLong(0), c.getLong(1), c.getInt(2), c.getInt(3) > 0, c.getInt(4) > 0, c.getInt(5) > 0, c.getInt(6) > 0, c.getInt(7) > 0);
        }
        return cour;
    }
}
