package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 8/22/2017.
 */

public class Courage extends TrackerObject implements ITrackerObject, Parcelable {
    private long fk;
    private int courageStat;
    private boolean bold;
    private boolean seeTruth;
    private boolean stalwart;
    private boolean prepared;
    private boolean matchmaker;

    public Courage(long fk, int courageStat, boolean bold, boolean seeTruth, boolean stalwart, boolean prepared, boolean matchmaker) {
        this.fk = fk;
        this.courageStat = courageStat;
        this.bold = bold;
        this.seeTruth = seeTruth;
        this.stalwart = stalwart;
        this.prepared = prepared;
        this.matchmaker = matchmaker;
    }

    public Courage(long id, long fk, int courageStat, boolean bold, boolean seeTruth, boolean stalwart, boolean prepared, boolean matchmaker){
        this.id = id;
        this.fk = fk;
        this.courageStat = courageStat;
        this.bold = bold;
        this.seeTruth = seeTruth;
        this.stalwart = stalwart;
        this.prepared = prepared;
        this.matchmaker = matchmaker;
    }

    protected Courage(Parcel in) {
        id = in.readLong();
        fk = in.readLong();
        bold = in.readInt() > 0;
        seeTruth = in.readInt() > 0;
        stalwart = in.readInt() >0;
        prepared = in.readInt() >0;
        matchmaker = in.readInt() >0;
    }

    public static final Creator<Courage> CREATOR = new Creator<Courage>() {
        @Override
        public Courage createFromParcel(Parcel in) {
            return new Courage(in);
        }

        @Override
        public Courage[] newArray(int size) {
            return new Courage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(super.getId());
        dest.writeLong(fk);
        dest.writeInt(courageStat);
        dest.writeInt((byte)(bold? 1:0));
        dest.writeInt((byte)(seeTruth? 1:0));
        dest.writeInt((byte)(stalwart? 1:0));
        dest.writeInt((byte)(prepared? 1:0));
        dest.writeInt((byte)(matchmaker? 1:0));

    }

    public long getFk() {
        return fk;
    }

    public int getCourageStat() {
        return courageStat;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isSeeTruth() {
        return seeTruth;
    }

    public boolean isStalwart() {
        return stalwart;
    }

    public boolean isPrepared() {
        return prepared;
    }

    public boolean isMatchmaker() {
        return matchmaker;
    }
}
