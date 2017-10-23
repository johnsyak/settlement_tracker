package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.settlement_tracker.sqlite.model.Attributes;
import com.settlement_tracker.sqlite.model.TrackerObject;

/**
 * Created by John on 8/9/2017.
 */

public class AttributesDAO extends DAOSuper implements DAOInterface {
    public static final String TABLE = "attributes ";
    public static final String COLUMN_ID = "attributes_id ";
    public static final String COLUMN_PLAYER_ID = PlayerDAO.COLUMN_ID;
    public static final String COLUMN_SURVIVAL = "survival_bonus ";
    public static final String COLUMN_MOVEMENT = "movement ";
    public static final String COLUMN_ACCURACY = "accuracy ";
    public static final String COLUMN_STRENGTH = "strength ";
    public static final String COLUMN_EVASION = "evasion ";
    public static final String COLUMN_LUCK = "luck ";
    public static final String COLUMN_SPEED = "speed ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;
    public static final String ALL_COLUMNS = "a." + COLUMN_ID + ",a." + COLUMN_PLAYER_ID + ",a." + COLUMN_SURVIVAL + ",a." + COLUMN_MOVEMENT + ",a." + COLUMN_ACCURACY + ",a." + COLUMN_STRENGTH + ",a." + COLUMN_EVASION + ",a." + COLUMN_LUCK + ",a." + COLUMN_SPEED;

    private static final String TAG = "AttributesDAO";
    private String[] mAllColumns = {COLUMN_ID, COLUMN_PLAYER_ID, COLUMN_SURVIVAL, COLUMN_MOVEMENT, COLUMN_ACCURACY, COLUMN_STRENGTH, COLUMN_EVASION, COLUMN_LUCK, COLUMN_SPEED};

    public AttributesDAO(Context context) {
        super(context);
    }

    @Override
    public long getPrimary(long id) {
        Long pk = null;
        String q = "SELECT " + COLUMN_ID + " from attributes a left join player p on a.player_ID = p.player_ID where a.player_ID = ?";
        Cursor c = mDatabase.rawQuery(q, DBManager.getString(id));
        while (c.moveToNext()) {
            pk = c.getLong(0);
        }
        if(pk == null){
            throw new SQLException("Primary key not found on: "+TAG);
        }
        return pk;
    }

    @Override
    public TrackerObject insert(TrackerObject object) {
        Attributes a = (Attributes) object;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PLAYER_ID, a.getFk());
        cv.put(COLUMN_SURVIVAL, a.hasSurvivalBonus());
        cv.put(COLUMN_MOVEMENT, a.getMovement());
        cv.put(COLUMN_ACCURACY, a.getAccuracy());
        cv.put(COLUMN_STRENGTH, a.getStrength());
        cv.put(COLUMN_EVASION, a.getEvasion());
        cv.put(COLUMN_LUCK, a.getLuck());
        cv.put(COLUMN_SPEED, a.getSpeed());
        long insertId = mDatabase.insertWithOnConflict(TABLE, null, cv, SQLiteDatabase.CONFLICT_ABORT);
        if (insertId == -1) {
            Toast.makeText(mContext, DBManager.ERROR_WRITE_FAILURE, Toast.LENGTH_LONG);
            throw new SQLException("Attribute object not found on: "+TAG);
        }
        a.setId(getNewest());
        return a;
    }

    @Override
    public void updateString(long pk, String column, String value) {
        super.updateString(TABLE, COLUMN_ID, pk, column, value);
    }

    @Override
    public void updateInt(long pk, String column, int value) {
        super.updateInt(TABLE, COLUMN_ID, pk, column, value);
    }

    public void update(TrackerObject object) {
        Attributes a = (Attributes)object;
        ContentValues args = new ContentValues();
        args.put(AttributesDAO.COLUMN_SPEED, a.getSpeed());
        args.put(AttributesDAO.COLUMN_LUCK, a.getLuck());
        args.put(AttributesDAO.COLUMN_MOVEMENT, a.getMovement());
        args.put(AttributesDAO.COLUMN_EVASION, a.getEvasion());
        args.put(AttributesDAO.COLUMN_STRENGTH, a.getStrength());
        args.put(AttributesDAO.COLUMN_ACCURACY, a.getAccuracy());
        super.update(args, AttributesDAO.TABLE, AttributesDAO.COLUMN_ID, a.getId());
    }

    @Override
    public long getNewest() {
        return super.getNewest(TAG, TABLE, COLUMN_ID);
    }

    public
    @Nullable
    Attributes getAttributes(long characterPK) {
        Attributes a = null;
        String q = "SELECT " + ALL_COLUMNS + " from attributes a left join player p on a.player_ID = p.player_ID where a.player_ID = ?";
        Cursor c = mDatabase.rawQuery(q, new String[]{String.valueOf(characterPK)});
        while (c.moveToNext()) {
            a = new Attributes(c.getLong(0), characterPK, c.getInt(2) > 0, c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6), c.getInt(7), c.getInt(8));
        }
        return a;
    }
}
