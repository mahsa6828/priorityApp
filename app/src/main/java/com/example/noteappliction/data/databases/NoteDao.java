package com.example.noteappliction.data.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteappliction.data.model.Note;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface NoteDao {
    @Insert
    Completable saveNote(Note note);

    @Update
    Completable updateNote(Note note);

    @Delete
    Completable deleteNote(Note note);

    @Query("select * from NOTE_TABLE")
    Observable<List<Note>> getAllNotes();

    @Query("select * from NOTE_TABLE where id ==:id")
    Observable<Note> getNote(int id);

    @Query("select * from NOTE_TABLE WHERE priority ==:priority")
    Observable<List<Note>> getFilterNote(String priority);

    @Query("select * from NOTE_TABLE where title like '%' || :title || '%'")
    Observable<List<Note>> searchNotes(String title);




}
