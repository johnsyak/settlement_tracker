package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 8/9/2017.
 */

public class Survival extends TrackerObject implements ITrackerObject, Parcelable {
    private final long fk;
    private final int survivalStat;
    private final boolean spendSurvival;
    private final boolean dodge;
    private final boolean encourage;
    private final boolean surge;
    private final boolean dash;

    public Survival(long fk, int survivalStat, boolean spendSurvival, boolean dodge, boolean encourage, boolean surge, boolean dash) {
        this.fk = fk;
        this.survivalStat = survivalStat;
        this.spendSurvival = spendSurvival;
        this.dodge = dodge;
        this.encourage = encourage;
        this.surge = surge;
        this.dash = dash;
    }

    public Survival(long id, long fk, int survivalStat, boolean spendSurvival, boolean dodge, boolean encourage, boolean surge, boolean dash) {
        this.id = id;
        this.fk = fk;
        this.survivalStat = survivalStat;
        this.spendSurvival = spendSurvival;
        this.dodge = dodge;
        this.encourage = encourage;
        this.surge = surge;
        this.dash = dash;
    }

    protected Survival(Parcel in) {
        id = in.readLong();
        fk = in.readLong();
        survivalStat = in.readInt();
        spendSurvival=in.readInt() != 0;
        dodge = in.readByte() != 0;
        encourage = in.readByte() != 0;
        surge = in.readByte() != 0;
        dash = in.readByte() != 0;
    }

    public long getFk() {
        return fk;
    }

    public int getSurvivalStat() {
        return survivalStat;
    }

    public boolean isSpendSurvival() {
        return spendSurvival;
    }

    public boolean isDodge() {
        return dodge;
    }

    public boolean isEncourage() {
        return encourage;
    }

    public boolean isSurge() {
        return surge;
    }

    public boolean isDash() {
        return dash;
    }

    public static final Creator<Survival> CREATOR = new Creator<Survival>() {
        @Override
        public Survival createFromParcel(Parcel in) {
            return new Survival(in);
        }

        @Override
        public Survival[] newArray(int size) {
            return new Survival[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(fk);
        dest.writeInt(survivalStat);
        dest.writeByte((byte) (spendSurvival? 1 :0));
        dest.writeByte((byte) (dodge ? 1 : 0));
        dest.writeByte((byte) (encourage ? 1 : 0));
        dest.writeByte((byte) (surge ? 1 : 0));
        dest.writeByte((byte) (dash ? 1 : 0));
    }

    @Override
    public long getId() {
        return id;
    }
}
