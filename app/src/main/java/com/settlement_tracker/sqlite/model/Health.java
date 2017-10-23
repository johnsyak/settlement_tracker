package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John on 8/9/2017.
 */

public class Health extends TrackerObject implements Parcelable, ITrackerObject {
    private final long fk;
    private final int insanityStat;
    private final int headStat;
    private final int armStat;
    private final int bodyStat;
    private final int waistStat;
    private final int baseLegStat;
    private final int baseHeadStat;
    private final int baseArmStat;
    private final int baseBodyStat;
    private final int baseWaistStat;

    public int getInsanityStat() {
        return insanityStat;
    }

    public int getHeadStat() {
        return headStat;
    }

    public int getArmStat() {
        return armStat;
    }

    public int getBodyStat() {
        return bodyStat;
    }

    public int getWaistStat() {
        return waistStat;
    }

    public int getLegStat() {
        return legStat;
    }

    public long getFk() {
        return fk;
    }

    public int getBaseHeadStat() {
        return baseHeadStat;
    }

    public int getBaseArmStat() {
        return baseArmStat;
    }

    public int getBaseBodyStat() {
        return baseBodyStat;
    }

    public int getBaseWaistStat() {
        return baseWaistStat;
    }

    public int getBaseLegStat() {
        return baseLegStat;
    }

    private final int legStat;

    public Health(long id, long fk, int insanityStat, int headStat, int armStat, int bodyStat, int waistStat, int legStat, int baseHeadStat, int baseArmStat, int baseBodyStat, int baseWaistStat, int baseLegStat) {
        this.id = id;
        this.fk = fk;
        this.insanityStat = insanityStat;
        this.headStat = headStat;
        this.armStat = armStat;
        this.bodyStat = bodyStat;
        this.waistStat = waistStat;
        this.legStat = legStat;
        this.baseLegStat = baseLegStat;
        this.baseHeadStat = baseHeadStat;
        this.baseArmStat = baseArmStat;
        this.baseBodyStat = baseBodyStat;
        this.baseWaistStat = baseWaistStat;
    }

    public Health(long fk, int insanityStat, int headStat, int armStat, int bodyStat, int waistStat, int legStat, int baseHeadStat, int baseArmStat, int baseBodyStat, int baseWaistStat, int baseLegStat) {
        this.fk = fk;
        this.insanityStat = insanityStat;
        this.headStat = headStat;
        this.armStat = armStat;
        this.bodyStat = bodyStat;
        this.waistStat = waistStat;
        this.legStat = legStat;
        this.baseLegStat = baseLegStat;
        this.baseHeadStat = baseHeadStat;
        this.baseArmStat = baseArmStat;
        this.baseBodyStat = baseBodyStat;
        this.baseWaistStat = baseWaistStat;
    }

    protected Health(Parcel in) {
        id = in.readLong();
        fk = in.readLong();
        insanityStat = in.readInt();
        headStat = in.readInt();
        armStat = in.readInt();
        bodyStat = in.readInt();
        waistStat = in.readInt();
        legStat = in.readInt();
        baseLegStat = in.readInt();
        baseHeadStat = in.readInt();
        baseArmStat = in.readInt();
        baseBodyStat = in.readInt();
        baseWaistStat = in.readInt();
    }

    public static final Creator<Health> CREATOR = new Creator<Health>() {
        @Override
        public Health createFromParcel(Parcel in) {
            return new Health(in);
        }

        @Override
        public Health[] newArray(int size) {
            return new Health[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(fk);
        dest.writeInt(insanityStat);
        dest.writeInt(headStat);
        dest.writeInt(armStat);
        dest.writeInt(bodyStat);
        dest.writeInt(waistStat);
        dest.writeInt(legStat);
        dest.writeInt(baseHeadStat);
        dest.writeInt(baseArmStat);
        dest.writeInt(baseBodyStat);
        dest.writeInt(baseWaistStat);
        dest.writeInt(baseLegStat);
    }

    @Override
    public long getId() {
        return id;
    }
}
