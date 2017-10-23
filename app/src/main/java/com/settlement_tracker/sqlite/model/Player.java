package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 7/31/2017.
 */

public final class Player extends TrackerObject implements ITrackerObject, Parcelable {

    private long fk;
    private String name;
    private int gender;
    private String notes;

    public Player(long id, long fk, String name, int gender, String notes) {
        this.id = id;
        this.fk = fk;
        this.name = name;
        this.gender = gender;
        this.notes = notes;
    }

    public Player(long fk, String name, int gender, String notes) {
        this.fk = fk;
        this.name = name;
        this.gender = gender;
        this.notes = notes;
    }

    public Player(Parcel input) {
        id = input.readLong();
        fk =input.readLong();
        name = input.readString();
        gender = input.readInt();
        notes = input.readString();
    }

    @Override
    public long getId() {
        return id;
    }

    public final long getFk() {
        return fk;
    }

    public final String getName() {
        return name;
    }

    public final String getNotes() {
        return notes;
    }

    public final int getGender(){
        return gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(fk);
        dest.writeString(name);
        dest.writeInt(gender);
        dest.writeString(notes);
    }

    public static final Parcelable.Creator<Player> CREATOR
            = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
