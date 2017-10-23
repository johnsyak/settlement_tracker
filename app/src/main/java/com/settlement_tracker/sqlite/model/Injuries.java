package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 8/9/2017.
 */

public class Injuries extends TrackerObject implements ITrackerObject, Parcelable {
    private final long fk;
    private final boolean insane;
    private final int headInjured;
    private final int armInjured;
    private final int bodyInjured;
    private final int waistInjured;
    private final int legInjured;

    public Injuries(long fk, boolean insane, int headInjured, int armInjured, int bodyInjured, int waistInjured, int legInjured) {
        this.fk = fk;
        this.insane = insane;
        this.headInjured=headInjured;
        this.legInjured=legInjured;
        this.armInjured=armInjured;
        this.bodyInjured=bodyInjured;
        this.waistInjured=waistInjured;
    }

    public Injuries(long id, long fk, boolean insane, int headInjured, int armInjured, int bodyInjured, int waistInjured, int legInjured) {
        this.id=id;
        this.fk = fk;
        this.insane = insane;
        this.headInjured=headInjured;
        this.legInjured=legInjured;
        this.armInjured=armInjured;
        this.bodyInjured=bodyInjured;
        this.waistInjured=waistInjured;
    }

    protected Injuries(Parcel in) {
        id= in.readLong();
        fk = in.readLong();
        insane = in.readByte() != 0;
        headInjured = in.readInt();
        armInjured = in.readInt();
        bodyInjured = in.readInt();
        waistInjured = in.readInt();
        legInjured = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(fk);
        dest.writeByte((byte) (insane ? 1 : 0));
        dest.writeInt(headInjured);
        dest.writeInt(armInjured);
        dest.writeInt(bodyInjured);
        dest.writeInt(waistInjured);
        dest.writeInt(legInjured);
    }

    public static final Creator<Injuries> CREATOR = new Creator<Injuries>() {
        @Override
        public Injuries createFromParcel(Parcel in) {
            return new Injuries(in);
        }

        @Override
        public Injuries[] newArray(int size) {
            return new Injuries[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public long getId() {
        return id;
    }

    public long getFk() {
        return fk;
    }

    public boolean isInsane() {
        return insane;
    }

    public int isHeadInjured() {
        return headInjured;
    }

    public int isArmInjured() {
        return armInjured;
    }

    public int isBodyInjured() {
        return bodyInjured;
    }

    public int isWaistInjured() {
        return waistInjured;
    }

    public int isLegInjured() {
        return legInjured;
    }
}
