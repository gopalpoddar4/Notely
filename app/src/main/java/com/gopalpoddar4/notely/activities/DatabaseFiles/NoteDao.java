package com.gopalpoddar4.notely.activities.DatabaseFiles;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    LiveData<List<NoteEntity>> getallnotes();

    @Insert
    void insertNote(NoteEntity noteEntity);

    @Update
    void updateNote(NoteEntity noteEntity);

    @Delete
    void deleteNote(NoteEntity noteEntity);

    @Query("SELECT * FROM note_table WHERE id=:noteId")
    LiveData<NoteEntity> getNote(int noteId);

    @Query("SELECT * FROM note_table WHERE title LIKE :query OR note_description LIKE :query ORDER BY id DESC")
    LiveData<List<NoteEntity>> searchNote(String query);
}
