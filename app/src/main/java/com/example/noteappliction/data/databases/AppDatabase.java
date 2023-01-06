package com.example.noteappliction.data.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteappliction.data.model.Note;

@Database(entities = {Note.class},exportSchema = false,version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context){
        if (appDatabase==null)
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "NOTE_DATABASE")
                    .allowMainThreadQueries()
                    .build();
        return appDatabase;

    }
    public abstract NoteDao getNoteDao();
}
