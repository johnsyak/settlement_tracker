package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 8/22/2017.
 */

public class AbilityDetail extends TrackerObject implements ITrackerObject, Parcelable {
    private long fk;
    private boolean arts;
    private boolean skipHunt;
    private boolean maxArts;
    private boolean maxDisorders;

    public AbilityDetail(long id, long fk, boolean arts, boolean skipHunt, boolean maxArts, boolean maxDisorders) {
        this.id = id;
        this.fk = fk;
        this.arts = arts;
        this.skipHunt = skipHunt;
        this.maxArts = maxArts;
        this.maxDisorders = maxDisorders;
    }

    public AbilityDetail(long fk, boolean arts, boolean skipHunt, boolean maxArts, boolean maxDisorders) {
        this.fk = fk;
        this.arts = arts;
        this.skipHunt = skipHunt;
        this.maxArts = maxArts;
        this.maxDisorders = maxDisorders;
    }


    public AbilityDetail(Parcel in) {
        id = in.readLong();
        fk = in.readLong();
        arts = in.readInt() > 0;
        skipHunt = in.readInt() > 0;
        maxArts = in.readInt() > 0;
        maxDisorders = in.readInt() > 0;
    }

    public static final Creator<AbilityDetail> CREATOR = new Creator<AbilityDetail>() {
        @Override
        public AbilityDetail createFromParcel(Parcel in) {
            return new AbilityDetail(in);
        }

        @Override
        public AbilityDetail[] newArray(int size) {
            return new AbilityDetail[size];
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
        dest.writeInt((byte) (arts ? 1 : 0));
        dest.writeInt((byte) (skipHunt ? 1 : 0));
        dest.writeInt((byte) (maxArts ? 1 : 0));
        dest.writeInt((byte) (maxDisorders ? 1 : 0));
    }

    public long getFk() {
        return fk;
    }

    public boolean isArts() {
        return arts;
    }

    public boolean isSkipHunt() {
        return skipHunt;
    }

    public boolean isMaxArts() {
        return maxArts;
    }

    public boolean isMaxDisorders() {
        return maxDisorders;
    }
}
