package com.gopalpoddar4.notely.activities.DatabaseFiles;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = NoteEntity.class, version = 4 )
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    // 🔹 v1 → v2: Add `pinned` column
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE note_table ADD COLUMN pinned INTEGER NOT NULL DEFAULT 0");
        }
    };

    // 🔹 v2 → v3: Add `category` column
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE note_table ADD COLUMN category TEXT NOT NULL DEFAULT 'All'");
        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE note_table ADD COLUMN allcategory TEXT NOT NULL DEFAULT 'All'");
        }
    };



    public static synchronized NoteDatabase noteDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, NoteDatabase.class, "notedb")
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3,MIGRATION_3_4   )
                    // 🔁 all sequential migrations
                    // ⚠️ fallbackToDestructiveMigration hata do agar data important hai
                    .build();
        }
        return instance;
    }
}
