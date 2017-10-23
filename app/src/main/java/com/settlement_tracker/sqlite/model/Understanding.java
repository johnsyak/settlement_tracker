package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.settlement_tracker.sqllite.dao.UnderstandingDAO;

/**
 * Created by John on 8/22/2017.
 */

public class Understanding extends TrackerObject implements ITrackerObject, Parcelable {
    long fk;
    int understandingStat;
    boolean insight;
    boolean whiteSecret;
    boolean canAnalyze;
    boolean canExplore;
    boolean canTinker;

    private UnderstandingDAO understandingDAO;

    public Understanding(long id, long fk, int understandingStat, boolean insight, boolean whiteSecret, boolean canAnalyze, boolean canExplore, boolean canTinker){
        this.id=id;
        this.fk = fk;
        this.understandingStat = understandingStat;
        this.insight = insight;
        this.whiteSecret = whiteSecret;
        this.canAnalyze = canAnalyze;
        this.canExplore = canExplore;
        this.canTinker = canTinker;
    }

    public Understanding(long fk, int understandingStat, boolean insight, boolean whiteSecret, boolean canAnalyze, boolean canExplore, boolean canTinker){
        this.fk = fk;
        this.understandingStat = understandingStat;
        this.insight = insight;
        this.whiteSecret = whiteSecret;
        this.canAnalyze = canAnalyze;
        this.canExplore = canExplore;
        this.canTinker = canTinker;
    }

    protected Understanding(Parcel in) {
        id = in.readLong();
        fk = in.readLong();
        understandingStat = in.readInt();
        insight = in.readByte() != 0;
        whiteSecret = in.readByte() != 0;
        canAnalyze = in.readByte() != 0;
        canExplore = in.readByte() != 0;
        canTinker = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(super.getId());
        dest.writeLong(fk);
        dest.writeInt(understandingStat);
        dest.writeByte((byte) (insight ? 1 : 0));
        dest.writeByte((byte) (whiteSecret ? 1 : 0));
        dest.writeByte((byte) (canAnalyze ? 1 : 0));
        dest.writeByte((byte) (canExplore ? 1 : 0));
        dest.writeByte((byte) (canTinker ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Understanding> CREATOR = new Creator<Understanding>() {
        @Override
        public Understanding createFromParcel(Parcel in) {
            return new Understanding(in);
        }

        @Override
        public Understanding[] newArray(int size) {
            return new Understanding[size];
        }
    };

    public long getFk() {
        return fk;
    }

    public int getUnderstandingStat() {
        return understandingStat;
    }

    public boolean isInsight() {
        return insight;
    }

    public boolean isWhiteSecret() {
        return whiteSecret;
    }

    public boolean isCanAnalyze() {
        return canAnalyze;
    }

    public boolean isCanExplore() {
        return canExplore;
    }

    public boolean isCanTinker() {
        return canTinker;
    }
}
