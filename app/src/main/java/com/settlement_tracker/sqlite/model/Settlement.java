package com.settlement_tracker.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 7/25/2017.
 */

public final class Settlement extends TrackerObject implements ITrackerObject, Parcelable {
    private String name;
    private String notes;
    @Nullable
    private List<Player> characters = new ArrayList<>();

    public Settlement(long id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;

    }

    public Settlement(String name, int numPlayers, String notes){
        this.name = name;
        this.notes = notes;
    }

    public Settlement(Parcel input) {
        id = input.readLong();
        name = input.readString();
        notes = input.readString();
        input.readTypedList(characters, Player.CREATOR);

    }

    public final String getName() {
        return name;
    }


    public final String getNotes() {
        return notes;
    }

    public final void setCharacters(List<Player> characters) {
        this.characters = characters;
    }

    public final void addCharacter(Player c){
        this.characters.add(c);
    }

    public final
    @Nullable
    List<Player> getCharacters() {
        return characters;
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
        dest.writeString(name);
        dest.writeString(notes);
        dest.writeTypedList(characters);
    }

    public static final Parcelable.Creator<Settlement> CREATOR
            = new Parcelable.Creator<Settlement>() {
        public Settlement createFromParcel(Parcel in) {
            return new Settlement(in);
        }

        public Settlement[] newArray(int size) {
            return new Settlement[size];
        }
    };
}
