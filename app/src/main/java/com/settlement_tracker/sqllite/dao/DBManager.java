package com.settlement_tracker.sqllite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.settlement_tracker.TrackerInterfaces.SQLiteSchema;
import com.settlement_tracker.exceptions.DBManagerException;

/**
 * Created by John on 6/26/2017.
 */

public class DBManager extends SQLiteOpenHelper {
    //TODO work out proper closing of db
    private static final String DATABASE_NAME = " settlements.db ";
    private static final int DATABASE_VERSION = 1;

    public final static String ERROR_WRITE_FAILURE = "Failed to write to database";


    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getWritableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly())
            db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            eraseTables(db);
            onCreate(db);
        } catch (DBManagerException e) {
            e.printStackTrace();
        }
    }

    public static void eraseTables(SQLiteDatabase db){
        db.execSQL(SettlementDAO.DROP_TABLE);
        db.execSQL(PlayerDAO.DROP_TABLE);
        db.execSQL(AttributesDAO.DROP_TABLE);
        db.execSQL(SurvivalDAO.DROP_TABLE);
        db.execSQL(HealthDAO.DROP_TABLE);
        db.execSQL(InjuriesDAO.DROP_TABLE);
        db.execSQL(TraitsDAO.DROP_TABLE);
        db.execSQL(AbilityDAO.DROP_TABLE);
        db.execSQL(AbilityDetailDAO.DROP_TABLE);
        db.execSQL(UnderstandingDAO.DROP_TABLE);
        db.execSQL(CourageDAO.DROP_TABLE);
    }

    public static void createTables(SQLiteDatabase db){
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_SETTLEMENT);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_PLAYER);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_ATTRIBUTES);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_SURVIVAL);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_HEALTH);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_INJURIES);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_TRAITS);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_ABILITIES);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_ABILITY_DETAILS);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_UNDERSTANDING);
        db.execSQL(SQLiteSchema.SQL_CREATE_TABLE_COURAGE);
    }

    public static String queryTable(String table) {
        return "Select * from " + table;
    }


    public static String[] getString(long id) {
        return new String[]{Long.toString(id)};
    }
}

