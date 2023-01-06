package com.example.noteappliction.ui.add;

import com.example.noteappliction.data.model.Note;

public interface AddContract {
    interface View{
        void close();
        void showDetailNote(Note note);


    }
    interface presenter {
        void onSaveBtnClick(Note note);
        void getDetailNote(int id);
        void updateNote(Note note);

    }
}
