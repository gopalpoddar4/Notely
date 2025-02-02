package com.gopalpoddar4.notely.activities;

import android.app.Application;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteDao;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteDatabase;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;

import java.util.List;

public class AddNoteViewModel extends AndroidViewModel {
    private NoteDao noteDao;
    private NoteDatabase noteDatabase;
    LiveData<List<NoteEntity>> allNotes;
    LiveData<List<NoteEntity>> allNotesOldFirst;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabase=NoteDatabase.noteDatabase(application);
        noteDao=noteDatabase.noteDao();
        allNotes=noteDao.getallnotes();
        allNotesOldFirst=noteDao.getAllnoteOldFirst();

    }
    private MutableLiveData<Integer> myValue=new MutableLiveData<>();
    public LiveData<Integer> noteformate( ){
        return myValue;
    }
    public void setvalue(int value){
        myValue.setValue(value);
    }
    public void insert(NoteEntity noteEntity){
        new InsertAsyncTask(noteDao).execute(noteEntity);
    }
    public void delete(NoteEntity noteEntity){
        new DeleteAsuncTask(noteDao).execute(noteEntity);
    }
    LiveData<List<NoteEntity>> getAllNotes(){
        return allNotes;
    }
    LiveData<List<NoteEntity>> getAllNotesOldFirst(){
        return allNotesOldFirst;
    }
    LiveData<List<NoteEntity>> searchNote(String query){
        return noteDao.searchNote("%" + query + "%");
    }
    private class DeleteAsuncTask extends AsyncTask<NoteEntity,Void,Void>{
        NoteDao mNoteDao;
        public DeleteAsuncTask(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.deleteNote(noteEntities[0]);
            return null;
        }
    }

    private class InsertAsyncTask extends AsyncTask<NoteEntity,Void,Void>{
        NoteDao mNoteDao;
        public InsertAsyncTask(NoteDao noteDao) {
            this.mNoteDao=noteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.insertNote(noteEntities[0]);
            return null;
        }
    }

}
