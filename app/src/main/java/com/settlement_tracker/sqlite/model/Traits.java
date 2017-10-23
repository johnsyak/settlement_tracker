package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 8/22/2017.
 */

public class Traits extends TrackerObject implements ITrackerObject, Parcelable {
    long fk;
    int xp;
    String weaponType;
    int proficiencyStat;
    boolean isSpecialist;
    boolean isMaster;

    public Traits(long id, long fk, int xp, String weaponType, int proficiencyStat, boolean isSpecialist, boolean isMaster){
        this.id = id;
        this.fk = fk;
        this.xp = xp;
        this.weaponType = weaponType;
        this.proficiencyStat = proficiencyStat;
        this.isSpecialist = isSpecialist;
        this.isMaster = isMaster;
    }

    public Traits(long fk, int xp, String weaponType, int proficiencyStat, boolean isSpecialist, boolean isMaster){
        this.fk = fk;
        this.xp = xp;
        this.weaponType = weaponType;
        this.proficiencyStat = proficiencyStat;
        this.isSpecialist = isSpecialist;
        this.isMaster = isMaster;
    }
    protected Traits(Parcel in) {
        id = in.readLong();
        fk = in.readLong();
        weaponType = in.readString();
        proficiencyStat = in.readInt();
        isSpecialist = in.readByte() > 0;
        isMaster = in.readByte() > 0;
    }

    public static final Creator<Traits> CREATOR = new Creator<Traits>() {
        @Override
        public Traits createFromParcel(Parcel in) {
            return new Traits(in);
        }

        @Override
        public Traits[] newArray(int size) {
            return new Traits[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(fk);
        dest.writeString(weaponType);
        dest.writeInt(xp);
        dest.writeInt(proficiencyStat);
        dest.writeByte((byte)(isSpecialist? 1:0));
        dest.writeByte((byte)(isMaster? 1:0));
    }

    public String getWeaponType() {
        return weaponType;
    }

    public long getFk() {
        return fk;
    }

    public int getXp() {
        return xp;
    }

    public int proficiencyStat() {
        return proficiencyStat;
    }

    public boolean isSpecialist() {
        return isSpecialist;
    }

    public boolean isMaster() {
        return isMaster;
    }
}
