package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 8/9/2017.
 */

public final class Attributes extends TrackerObject implements ITrackerObject, Parcelable {
    private final long fk;
    private final boolean survivalBonus;
    private final int movement;
    private final int accuracy;
    private final int strength;
    private final int evasion;
    private final int luck;
    private final int speed;

    public Attributes(long id, long fk, boolean survivalBonus, int movement, int accuracy, int strength, int evasion, int luck, int speed){
        this.id=id;
        this.fk=fk;
        this.survivalBonus = survivalBonus;
        this.movement = movement;
        this.accuracy = accuracy;
        this.strength= strength;
        this.evasion= evasion;
        this.luck=luck;
        this.speed = speed;
    }
    public Attributes(long fk, boolean survivalBonus, int movement, int accuracy, int strength, int evasion, int luck, int speed) {
        this.fk=fk;
        this.survivalBonus = survivalBonus;
        this.movement = movement;
        this.accuracy = accuracy;
        this.strength= strength;
        this.evasion= evasion;
        this.luck=luck;
        this.speed = speed;
    }

    public Attributes(Parcel input){
        this.id = input.readLong();
        this.fk = input.readLong();
        this.survivalBonus = input.readInt() == 1;
        this.movement= input.readInt();
        this.accuracy=input.readInt();
        this.strength=input.readInt();
        this.evasion=input.readInt();
        this.luck=input.readInt();
        this.speed=input.readInt();

    }

    public final long getFk() {
        return fk;
    }

    public final boolean hasSurvivalBonus() {
        return survivalBonus;
    }

    public final int getMovement() {
        return movement;
    }

    public final int getAccuracy() {
        return accuracy;
    }

    public final int getStrength() {
        return strength;
    }

    public final int getEvasion() {
        return evasion;
    }

    public final int getLuck() {
        return luck;
    }

    public final int getSpeed() {
        return speed;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(fk);
        dest.writeInt(survivalBonus ? 1 : 0);
        dest.writeInt(movement);
        dest.writeInt(accuracy);
        dest.writeInt(strength);
        dest.writeInt(evasion);
        dest.writeInt(luck);
        dest.writeInt(speed);
    }

    public static final Parcelable.Creator<Attributes> CREATOR
            = new Parcelable.Creator<Attributes>() {
        public Attributes createFromParcel(Parcel in) {
            return new Attributes(in);
        }

        public Attributes[] newArray(int size) {
            return new Attributes[size];
        }
    };
}
