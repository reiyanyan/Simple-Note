package com.reiyan.simplenote.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.reiyan.simplenote.model.Notes;

@Database(entities = {Notes.class}, version = 2, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static NoteRoomDatabase instance;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE notes ADD COLUMN pin INTEGER DEFAULT 0 NOT NULL");
        }
    };

    public static NoteRoomDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, NoteRoomDatabase.class, "db_note")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

}
