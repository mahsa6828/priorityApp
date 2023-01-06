package com.example.noteappliction.Base;

import io.reactivex.rxjava3.disposables.Disposable;

public class BasePresenterImpl implements BasePresenter{
    public Disposable disposable;
    @Override
    public void Onstop() {
        if (disposable != null){
            disposable.dispose();
        }

    }
}
