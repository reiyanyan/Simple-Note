package com.reiyan.simplenote.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.reiyan.simplenote.model.Notes;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insertData(Notes notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    LiveData<List<Notes>> getAllData();

    @Query("SELECT * FROM notes WHERE id=:noteId")
    LiveData<Notes> findById(int noteId);

    @Query("SELECT * FROM notes WHERE pin=:pin")
    LiveData<Notes> findByPin(int pin);

    @Query("SELECT * FROM notes WHERE title LIKE :key OR note LIKE :key ORDER BY id DESC")
    LiveData<List<Notes>> findByKey(String key);

    @Update
    void updateData(Notes notes);

    @Delete
    void deleteData(Notes notes);

}
