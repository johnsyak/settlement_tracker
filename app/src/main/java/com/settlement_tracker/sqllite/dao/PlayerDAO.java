package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.settlement_tracker.sqlite.model.Player;
import com.settlement_tracker.sqlite.model.Settlement;
import com.settlement_tracker.sqlite.model.TrackerObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 7/28/2017.
 */

public class PlayerDAO extends DAOSuper implements DAOInterface {
    public static final String TABLE = " player ";
    public static final String COLUMN_ID = "player_ID ";
    public static final String COLUMN_SETTLEMENT_ID = SettlementDAO.COLUMN_ID;
    public static final String COLUMN_NAME = "character_name ";
    public static final String COLUMN_GENDER = "gender ";
    public static final String COLUMN_NOTES = "notes ";
    public static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE;
    public static final String ALL_COLUMNS = "p." + COLUMN_ID + ",p." + COLUMN_SETTLEMENT_ID + ",p." + COLUMN_NAME + ",p." + COLUMN_GENDER+",p." +COLUMN_NOTES;

    public static final String TAG = "PlayerDAO";
    private String[] mAllColumns = {COLUMN_ID, COLUMN_SETTLEMENT_ID, COLUMN_NAME, COLUMN_GENDER,  COLUMN_NOTES};

    public PlayerDAO(Context context) {
        super(context);
    }

    public void deletePlayer(String name) {
        mDatabase.delete(TABLE, COLUMN_NAME + " = '" + name + "'", null);
    }

    @Override
    public long getPrimary(long name) {
        long id;
        Cursor cursor = mDatabase.query(TABLE, new String[]{COLUMN_ID}, COLUMN_NAME + " = '" + name + "'", null, null, null, null);
        id = cursor.getLong(0);
        return id;
    }

    @Override
    public TrackerObject insert(TrackerObject object) {
        Player p = (Player) object;
        ContentValues values = new ContentValues();
        values.put(COLUMN_SETTLEMENT_ID, p.getFk());
        values.put(COLUMN_NAME, p.getName());
        values.put(COLUMN_GENDER, p.getGender());
        values.put(COLUMN_NOTES, p.getNotes());
        super.insert(TABLE, values, TAG);
        Settlement s = SettlementDAO.settlementDAO(mContext).getSettlement(p.getFk());
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

    public Player getPlayer(String name) {
        return player(null, name);
    }

    private Player player(Long id, String name) {
        Player p= null;
        Cursor cursor = null;
        if (id != null) {
            cursor = mDatabase.query(TABLE, mAllColumns, COLUMN_ID+" = "+ id, null,null,null, null);
        } else if (name != null) {
            cursor = mDatabase.query(TABLE, mAllColumns, COLUMN_NAME+ " = "+"'"+name+"'", null, null, null, null);
        }else{
            Log.e(TAG, "An unexpected error has occurred");
        }
        while(cursor.moveToNext()){
            long playerId = cursor.getLong(0);
            long fk = cursor.getLong(1);
            String playerName = name;
            int gender = cursor.getInt(3);
            String notes = cursor.getString(4);
            p = new Player(playerId, fk, playerName, gender, notes);
        }
        return p;
    }

    public List<Player> listPlayers(Settlement settlement) {
        List<Player> players = new ArrayList<>();
        Cursor cursor = mDatabase.query(TABLE, mAllColumns, COLUMN_SETTLEMENT_ID +" =? ", DBManager.getString(settlement.getId()), null, null, null);
        while (cursor.moveToNext()) {
            players.add(new Player(cursor.getLong(0), cursor.getLong(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4)));
        }
        return players;
    }
}
