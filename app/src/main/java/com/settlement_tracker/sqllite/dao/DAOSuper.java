package com.settlement_tracker.sqllite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.settlement_tracker.sqlite.model.Injuries;
import com.settlement_tracker.sqlite.model.Settlement;
import com.settlement_tracker.sqlite.model.TrackerObject;

import static android.content.ContentValues.TAG;

/**
 * Created by John on 8/10/2017.
 */

public class DAOSuper {
    protected final Context mContext;
    protected DBManager dbManager;
    protected SQLiteDatabase mDatabase;

    private static final String WHERE = " =? ";

    public DAOSuper(Context context) {
        this.mContext = context;
        this.dbManager = new DBManager(mContext);
        try {
            dbManager.getWritableDatabase();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on open db " + e.getMessage());
            e.printStackTrace();
        }
        this.mDatabase = dbManager.getWritableDatabase();

    }

    protected long getPrimary(String table, String[] columns, String columnName, long columnId){
        Cursor cursor = mDatabase.query(table, columns, columnName+ " = '" + columnId + "'", null, null, null, null);
        long id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getLong(0);
        }
        return id;
    }

    protected long getNewest(String tag, String table, String id) {
        Long pk = null;
        Cursor c = mDatabase.query(table, new String[]{id}, null, null, null, null, id + " desc");
        c.moveToFirst();
        pk = c.getLong(0);
        if (pk == null)
            throw new SQLException("Primary key not found at: " + tag);
        return pk;
    }

    protected void insert(String table, ContentValues values, String tag) {
        long insertId = mDatabase.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_ABORT);
        if (insertId == -1) {
            Toast.makeText(mContext, DBManager.ERROR_WRITE_FAILURE, Toast.LENGTH_LONG);
        }
    }

    protected void update(String table, String columnPK, long pk, String column, String newString, Integer newInt){
        if(newString != null){
            updateString(table, columnPK, pk, column, newString);
        }else{
            updateInt(table, columnPK, pk, column, newInt);
        }
    }

    protected void updateString(String table, String columnPK, long pk, String column, String value){
        ContentValues args = new ContentValues();
        args.put(column, value);
        mDatabase.update(table, args, columnPK+ WHERE, new String[]{Long.toString(pk)});
    }


    protected void updateInt(String table, String columnPK, long pk, String column, int value){
            ContentValues args = new ContentValues();
            args.put(column, value);
            mDatabase.update(table, args, columnPK + WHERE, new String[]{Long.toString(pk)});
    }

    protected void update(ContentValues args, String table, String columnIDname, long pk){
        mDatabase.update(table, args, columnIDname+ WHERE, new String[]{Long.toString(pk)});

    }

    protected void delete(String table, String columnID, long id){
        mDatabase.delete(table, columnID + " = '" + id + "'", null);
    }
}
