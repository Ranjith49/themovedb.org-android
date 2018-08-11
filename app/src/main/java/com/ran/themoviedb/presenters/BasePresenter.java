package com.ran.themoviedb.presenters;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Interface that represents a Presenter in the model view presenter Pattern
 * defines methods to manage the Activity / Fragment lifecycle
 */
public abstract class BasePresenter {

    protected CompositeDisposable disposable;

    public BasePresenter() {
        disposable = new CompositeDisposable();
    }

    protected void cancelReq() {
        disposable.clear();
    }

    /**
     * Called when the presenter is initialized
     */
    public abstract void start();

    /**
     * Called when the presenter is stop, i.e when an activity
     * or a fragment finishes
     */
    public abstract void stop();
}
