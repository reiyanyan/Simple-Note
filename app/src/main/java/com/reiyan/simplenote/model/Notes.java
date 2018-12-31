package com.reiyan.simplenote.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "notes")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String title;

    private String note;

    private int pin;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPin() { return pin;    }

    public void setPin(int pin) { this.pin = pin;    }
}
