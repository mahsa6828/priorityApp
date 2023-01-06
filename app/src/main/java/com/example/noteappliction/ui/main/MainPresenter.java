package com.example.noteappliction.ui.main;

import androidx.core.app.BundleCompat;

import com.example.noteappliction.Base.BasePresenterImpl;
import com.example.noteappliction.data.databases.NoteDao;
import com.example.noteappliction.data.model.Note;
import com.example.noteappliction.ui.add.AddFragment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainPresenter extends BasePresenterImpl implements MainContract.Presenter {
    NoteDao noteDao;
    MainContract.View view;
    public MainPresenter(NoteDao noteDao,MainContract.View view){
        this.view = view;
        this.noteDao = noteDao;

    }
    @Override
    public void onAttach() {
        disposable = noteDao.getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(noteList -> {
                    if (!noteList.isEmpty()){
                        view.showNotes(noteList);
                    }
                    else
                    {
                        view.showEmpty();
                    }

                });

    }

    @Override
    public void onDeletePopUpItemClick(Note note) {
        disposable = noteDao.deleteNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.showMessageDelete();
                });
    }

    @Override
    public void filterNote(String priority) {
        disposable = noteDao.getFilterNote(priority)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(noteList -> {
                    if (!noteList.isEmpty()){
                        view.showNotes(noteList);
                    }
                    else
                    {
                        view.showEmpty();
                    }

                });
    }

    @Override
    public void onFilterBtnClick() {
         view.showFilterDialog();
    }

    @Override
    public void searchNotes(String title) {
        disposable = noteDao.searchNotes(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(noteList -> {
                    if (!noteList.isEmpty()){
                        view.showNotes(noteList);
                    }
                    else
                    {
                        view.showEmpty();
                    }

                });
    }


}
