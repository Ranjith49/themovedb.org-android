package com.ran.themoviedb.presenters;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Interface that represents a Presenter in the model view presenter Pattern
 * defines methods to manage the Activity / Fragment lifecycle
 */
public abstract class BasePresenter {

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
