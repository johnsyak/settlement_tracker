package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.settlement_tracker.exceptions.InvalidName;
import com.settlement_tracker.sqlite.model.Player;
import com.settlement_tracker.sqlite.model.Settlement;
import com.settlement_tracker.sqlite.model.TrackerObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by John on 7/25/2017.
 */

public class SettlementDAO extends DAOSuper implements DAOInterface {
    public static final String TABLE = "settlement ";
    public static final String COLUMN_ID = "settlement_ID ";
    public static final String COLUMN_NAME = "settlement_name ";
    public static final String COLUMN_NOTES = "notes ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;

    public static final String TAG = "SettlmentDAO";
    private static String[] mAllColumns = {"settlement_ID",
            "settlement_name",
            "notes"
    };

    private final static String ERROR_SETTLEMENT_NOT_FOUND = "An error has occurred. Settlement could not be found.";

    public final static String ERROR_NAME_EXISTS = "Name already exists.";

    public SettlementDAO(Context context) {
        super(context);
    }

    @Override
    public long getPrimary(long columnId) {
        return super.getPrimary(TABLE, new String[]{COLUMN_ID}, COLUMN_NAME, columnId);
    }

    @Override
    public TrackerObject insert(TrackerObject object) throws InvalidName {
        Settlement s = (Settlement)object;
        if (nameExists(s.getName())) {
            throw new InvalidName(ERROR_NAME_EXISTS);
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, s.getName());
        values.put(COLUMN_NOTES, s.getNotes());
        super.insert(TABLE, values, TAG);
        s.setId(getNewest());
        return s;
    }

    public void delete(TrackerObject object) {
        super.delete(TABLE, COLUMN_ID, object.getId());
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

    public List<Settlement> listSettlements() {
        List<Settlement> settlements = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(DBManager.queryTable(TABLE), null);
        while (cursor.moveToNext()) {
            settlements.add(new Settlement(cursor.getLong(0), cursor.getString(1), cursor.getString(2)));
        }
        if (!settlements.isEmpty()) {
            bindCharacters(settlements);
        }
        return settlements;
    }

    private void bindCharacters(List<Settlement> settlements) {
        List<Player> characters;
        for (int i = 0; i < settlements.size(); i++) {
            characters = getCharacters(settlements.get(i));
            if (!characters.isEmpty()) {
                settlements.get(i).setCharacters(characters);
            }
        }
    }

    public Settlement getSettlement(String name) {
        return settlement(name, null);
    }

    public Settlement getSettlement(long id) {
        return settlement(null, id);
    }

    private Settlement settlement(String name, Long id) {
        Settlement settlement = null;
        Cursor cursor = null;
        if (id != null) {
            cursor = mDatabase.query(TABLE, mAllColumns, COLUMN_ID + " = " + id, null, null, null, null);
        } else if (name != null && !name.isEmpty()) {
            cursor = mDatabase.query(TABLE, mAllColumns, COLUMN_NAME + " = " + "'" + name + "'", null, null, null, null);
        }
        while (cursor.moveToNext()) {
            long i = cursor.getLong(0);
            String notes = cursor.getString(1);
            settlement = new Settlement(i, name, notes);
        }
        if (settlement == null) {
            Toast.makeText(mContext, ERROR_SETTLEMENT_NOT_FOUND, Toast.LENGTH_SHORT);
            throw new SQLException("Settlement could not be found");
        }
        bindCharacters(Arrays.asList(settlement));
        return settlement;
    }

    public List<Player> getCharacters(Settlement settlement) {
        List<Player> characters = new ArrayList<>();
        String q = "select " + PlayerDAO.ALL_COLUMNS + " from settlement s left join player p on s.settlement_ID = p.settlement_ID where p.settlement_ID =?";
        Cursor cursor = mDatabase.rawQuery(q, new String[]{String.valueOf(settlement.getId())});
        while (cursor.moveToNext()) {
            characters.add(new Player(cursor.getLong(0), cursor.getLong(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4)));
        }
        return characters;
    }

    protected static SettlementDAO settlementDAO(Context context) {
        return new SettlementDAO(context);
    }

    public boolean nameExists(String name) {
        Cursor cursor = mDatabase.query(TABLE, mAllColumns, COLUMN_NAME + " = " + "'" + name + "'", null, null, null, null);
        return cursor.getCount() > 0;
    }
}
