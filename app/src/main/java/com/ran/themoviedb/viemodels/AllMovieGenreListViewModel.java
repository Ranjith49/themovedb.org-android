package com.ran.themoviedb.viemodels;

import android.arch.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.response.AllMovieGenreListResponse;
import com.ran.themoviedb.model.server.service.AllMoviesGenreServiceImpl;

import java.lang.reflect.Type;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * View Model for AllMovieGenreList from {@link com.ran.themoviedb.model.server.service.AllMoviesGenreServiceImpl} && propagate to UI.
 *
 * @author ranjithsuda
 */

public class AllMovieGenreListViewModel extends BaseViewModel<Void> {

    private MutableLiveData<Boolean> responseLiveData;
    private AllMoviesGenreServiceImpl service;

    public AllMovieGenreListViewModel() {
        super();
        initialiseViewModel();
    }

    @Override
    public void initialiseViewModel() {
        responseLiveData = new MutableLiveData<>();
        service = new AllMoviesGenreServiceImpl();
    }

    @Override
    public void startExecution(Void empty) {
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onAllMovieGenreListRetrieved, this::onAllMovieGenreAPIError));
    }

    private void onAllMovieGenreListRetrieved(AllMovieGenreListResponse response) {
        Gson gson = new Gson();
        Type type = new TypeToken<AllMovieGenreListResponse>() {
        }.getType();
        TheMovieDbAppController.getAppInstance().appSharedPreferences.setGenreListData(gson.toJson(response, type));
        responseLiveData.setValue(true);
    }


    private void onAllMovieGenreAPIError(Throwable error) {
        responseLiveData.setValue(false);
    }


    public MutableLiveData<Boolean> getResponseLiveData() {
        return responseLiveData;
    }
}
