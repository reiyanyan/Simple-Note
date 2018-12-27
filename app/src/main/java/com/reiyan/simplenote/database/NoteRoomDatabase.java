package com.reiyan.simplenote.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.reiyan.simplenote.model.Notes;

@Database(entities = {Notes.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static NoteRoomDatabase instance;

    public static NoteRoomDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, NoteRoomDatabase.class, "db_note")
                    .build();
        }
        return instance;
    }

}
