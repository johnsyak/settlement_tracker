package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 8/22/2017.
 */

public class Ability extends TrackerObject implements ITrackerObject, Parcelable {
    long fk;
    String fightArts1;
    String fightArts2;
    String fightArts3;
    String disorders1;
    String disorders2;
    String disorders3;
    String impairments1;
    String impairments2;
    String impairments3;

    public Ability(Parcel in) {
        id = in.readLong();
        fk = in.readLong();
        fightArts1 = in.readString();
        fightArts2 = in.readString();
        fightArts3 = in.readString();
        disorders1 = in.readString();
        disorders2 = in.readString();
        disorders3 = in.readString();
        impairments1 = in.readString();
        impairments2 = in.readString();
        impairments3 = in.readString();
    }

    public static final Creator<Ability> CREATOR = new Creator<Ability>() {
        @Override
        public Ability createFromParcel(Parcel in) {
            return new Ability(in);
        }

        @Override
        public Ability[] newArray(int size) {
            return new Ability[size];
        }
    };

    public Ability(long fk, String fightArts1, String fightArts2, String fightArts3, String disorders1, String disorders2, String disorders3, String impairments1, String impairments2, String impairments3) {
        this.fk = fk;
        this.fightArts1 = fightArts1;
        this.fightArts2 = fightArts2;
        this.fightArts3 = fightArts3;
        this.disorders1 = disorders1;
        this.disorders2 = disorders2;
        this.disorders3 = disorders3;
        this.impairments1 = impairments1;
        this.impairments2 = impairments2;
        this.impairments3 = impairments3;
    }

    public Ability(long id, long fk, String fightArts1, String fightArts2, String fightArts3, String disorders1, String disorders2, String disorders3, String impairments1, String impairments2, String impairments3) {
        this.id = id;
        this.fk = fk;
        this.fightArts1 = fightArts1;
        this.fightArts2 = fightArts2;
        this.fightArts3 = fightArts3;
        this.disorders1 = disorders1;
        this.disorders2 = disorders2;
        this.disorders3 = disorders3;
        this.impairments1 = impairments1;
        this.impairments2 = impairments2;
        this.impairments3 = impairments3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(fk);
        dest.writeString(fightArts1);
        dest.writeString(fightArts2);
        dest.writeString(fightArts3);
        dest.writeString(disorders1);
        dest.writeString(disorders2);
        dest.writeString(disorders3);
        dest.writeString(impairments1);
        dest.writeString(impairments2);
        dest.writeString(impairments3);
    }

    @Override
    public long getId() {
        return id;
    }

    public long getFk(){
        return fk;
    }
    public String getFightArts1() {
        return fightArts1;
    }

    public String getFightArts2() {
        return fightArts2;
    }

    public String getFightArts3() {
        return fightArts3;
    }

    public String getDisorders1() {
        return disorders1;
    }

    public String getDisorders2() {
        return disorders2;
    }

    public String getDisorders3() {
        return disorders3;
    }

    public String getImpairments1() {
        return impairments1;
    }

    public String getImpairments2() {
        return impairments2;
    }

    public String getImpairments3() {
        return impairments3;
    }
}
