package com.ran.themoviedb.viemodels;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Base View Model to be extended by all the View models
 *
 * @author ranjithsuda
 */

public abstract class BaseViewModel<INPUT> extends ViewModel {

    protected CompositeDisposable disposable;

    public BaseViewModel() {
        this.disposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    /**
     * Method to be called to initialize View Model
     */
    public abstract void initialiseViewModel();

    /**
     * Method to be called to execute the Logic
     *
     * @param input -- input data
     */
    public abstract void startExecution(INPUT input);
}
