package com.settlement_tracker.sqlite.model;

/**
 * Created by John on 7/31/2017.
 */

public class TrackerObject implements ITrackerObject{
    long id;
    public TrackerObject(){

    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

}
