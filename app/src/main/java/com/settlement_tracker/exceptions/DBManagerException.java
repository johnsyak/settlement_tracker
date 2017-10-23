package com.settlement_tracker.exceptions;

import com.settlement_tracker.sqllite.dao.DBManager;

import java.io.PrintStream;

/**
 * Created by John on 8/14/2017.
 */

public class DBManagerException extends RuntimeException {

    public DBManagerException(String m){
        super(m);
    }
}
