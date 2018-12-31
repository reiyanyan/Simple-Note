package com.reiyan.simplenote.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.reiyan.simplenote.database.NoteDao;
import com.reiyan.simplenote.database.NoteRoomDatabase;

import java.util.List;

public class NotesModel extends AndroidViewModel {

    private NoteDao noteDao;
    private NoteRoomDatabase noteRoom;
    private LiveData<List<Notes>> allNotes;

    public NotesModel(@NonNull Application application) {
        super(application);

        noteRoom = NoteRoomDatabase.getInstance(application);
        noteDao = noteRoom.noteDao();
        allNotes = noteDao.getAllData();

    }

    public void inserData(Notes notes){
        new insert(noteDao).execute(notes);
    }

    public LiveData<List<Notes>> getAllNotes (){
        return allNotes;
    }

    public LiveData<Notes> findById(int noteId){
        return noteDao.findById(noteId);
    }

    public void updateData(Notes notes){
        new update(noteDao).execute(notes);
    }
    
    public void deleteData(Notes notes){
        new delete(noteDao).execute(notes);
    }

    public LiveData<List<Notes>> findByKey(String key){ return noteDao.findByKey(key); }

    //ALL ABOUT ASYNCTASK
    private class insert extends AsyncTask<Notes, Void, Void>{

        private NoteDao noteDao;

        public insert(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDao.insertData(notes[0]);
            return null;
        }
    }

    private class update extends AsyncTask<Notes, Void, Void> {

        private NoteDao noteDao;

        public update(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDao.updateData(notes[0]);
            return null;
        }
    }

    private class delete extends AsyncTask<Notes, Void, Void>{

        private NoteDao noteDao;

        public delete(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            noteDao.deleteData(notes[0]);
            return null;
        }
    }

}
