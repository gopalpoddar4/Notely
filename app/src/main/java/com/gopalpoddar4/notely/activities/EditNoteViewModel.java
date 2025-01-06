package com.gopalpoddar4.notely.activities;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteDao;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteDatabase;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;

public class EditNoteViewModel extends AndroidViewModel {
    private NoteDao noteDao;
    private NoteDatabase noteDatabase;
    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabase=NoteDatabase.noteDatabase(application);
        noteDao=noteDatabase.noteDao();
    }

    public LiveData<NoteEntity> getNote(int noteId){
        return noteDao.getNote(noteId);
    }

}
