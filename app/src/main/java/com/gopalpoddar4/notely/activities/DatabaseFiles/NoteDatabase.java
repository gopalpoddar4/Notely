package com.gopalpoddar4.notely.activities.DatabaseFiles;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = NoteEntity.class, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();
    public static synchronized NoteDatabase noteDatabase(Context context){

        if (instance==null){
            instance= Room.databaseBuilder(context,NoteDatabase.class,"notedb").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
