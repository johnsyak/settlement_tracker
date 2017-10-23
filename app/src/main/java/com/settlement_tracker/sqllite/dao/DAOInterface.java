package com.settlement_tracker.sqllite.dao;

import com.settlement_tracker.exceptions.InvalidName;
import com.settlement_tracker.sqlite.model.TrackerObject;

/**
 * Created by John on 7/25/2017.
 */

public interface DAOInterface {
    long getPrimary(long id);
    TrackerObject insert(TrackerObject object) throws InvalidName;
    void updateString(long pk, String column, String value);
    void updateInt(long pk, String column, int value);
    long getNewest();
}
