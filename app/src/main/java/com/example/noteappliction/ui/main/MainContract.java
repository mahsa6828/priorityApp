package com.example.noteappliction.ui.main;

import com.example.noteappliction.data.model.Note;

import java.util.List;

public interface MainContract {
    interface View{
        void showNotes(List<Note> noteList);
        void showEmpty();
        void showMessageDelete();
        void showFilterDialog();

    }
    interface Presenter{
        void onAttach();
        void onDeletePopUpItemClick(Note note);
        void filterNote(String priority);
        void onFilterBtnClick();
        void searchNotes(String title);

    }
}
