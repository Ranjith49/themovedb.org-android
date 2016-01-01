package com.ran.themoviedb.presenters;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.AllMovieGenreListResponse;
import com.ran.themoviedb.model.server.service.AllMoviesGenreServiceImpl;
import com.ran.themoviedb.view_pres_med.AllMovieGenreView;

import java.lang.reflect.Type;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p>
 * Presenter for AllMovieGenreList from {@Link AllMoviesGenreServiceImpl} , propagate to UI
 */
public class AllMovieGenreListPresenter extends BasePresenter
    implements AllMoviesGenreServiceImpl.Handler {

  private Context context;
  private AllMovieGenreView allMovieGenreView;
  private AllMoviesGenreServiceImpl service;
  private int uniqueId;


  public AllMovieGenreListPresenter(Context context, AllMovieGenreView allMovieGenreView, int
      uniqueId) {
    this.context = context;
    this.allMovieGenreView = allMovieGenreView;
    this.uniqueId = uniqueId;
    service = new AllMoviesGenreServiceImpl(this);
  }

  @Override
  public void start() {
    service.request(uniqueId);
  }

  @Override
  public void stop() {
    service.cancelRequest(uniqueId);
  }

  @Override
  public void onAllMovieGenreListRetrieved(AllMovieGenreListResponse response, int uniqueId) {
    Gson gson = new Gson();
    Type type = new TypeToken<AllMovieGenreListResponse>() {
    }.getType();
    AppSharedPreferences.getInstance(context).setGenreListData(gson.toJson(response, type));
    allMovieGenreView.isMovieGenreResponseRetrieval(true);
  }

  @Override
  public void onAllMovieGenreAPIError(UserAPIErrorType errorType, int uniqueId) {
    allMovieGenreView.isMovieGenreResponseRetrieval(false);
  }
}
