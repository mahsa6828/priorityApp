package com.example.noteappliction.ui.add;

import android.util.AndroidException;
import android.util.Log;
import android.widget.Toast;

import com.example.noteappliction.Base.BasePresenterImpl;
import com.example.noteappliction.data.databases.NoteDao;
import com.example.noteappliction.data.model.Note;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddPresenter extends BasePresenterImpl implements AddContract.presenter {
    AddContract.View view;
    private NoteDao noteDao;
    public AddPresenter(NoteDao noteDao,AddContract.View view){
        this.noteDao = noteDao;
        this.view=view;
    }
    @Override
    public void onSaveBtnClick(Note note) {
        disposable = noteDao.saveNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.close();
                });

    }

    @Override
    public void getDetailNote(int id) {
        disposable = noteDao.getNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(note -> {
                    view.showDetailNote(note);
                });
    }

    @Override
    public void updateNote(Note note) {
        disposable = noteDao.updateNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.close();
                });
    }

}
