package com.ran.themoviedb.presenters;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.response.AllMovieGenreListResponse;
import com.ran.themoviedb.model.server.service.AllMoviesGenreServiceImpl;
import com.ran.themoviedb.view_pres_med.AllMovieGenreView;

import java.lang.reflect.Type;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p>
 * Presenter for AllMovieGenreList from {@Link AllMoviesGenreServiceImpl} , propagate to UI
 */
public class AllMovieGenreListPresenter extends BasePresenter {

    private Context context;
    private AllMovieGenreView allMovieGenreView;
    private AllMoviesGenreServiceImpl service;


    public AllMovieGenreListPresenter(Context context, AllMovieGenreView allMovieGenreView) {
        super();
        this.context = context;
        this.allMovieGenreView = allMovieGenreView;
        service = new AllMoviesGenreServiceImpl();
    }

    @Override
    public void start() {
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onAllMovieGenreListRetrieved, this::onAllMovieGenreAPIError));
    }

    @Override
    public void stop() {
        cancelReq();
    }


    private void onAllMovieGenreListRetrieved(AllMovieGenreListResponse response) {
        Gson gson = new Gson();
        Type type = new TypeToken<AllMovieGenreListResponse>() {
        }.getType();
        AppSharedPreferences.getInstance(context).setGenreListData(gson.toJson(response, type));
        allMovieGenreView.isMovieGenreResponseRetrieval(true);
    }


    private void onAllMovieGenreAPIError(Throwable error) {
        allMovieGenreView.isMovieGenreResponseRetrieval(false);
    }
}
