package com.ran.themoviedb.presenters;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.preloaddb.SnappyDBEntityTypes;
import com.ran.themoviedb.model.preloaddb.SnappyPreloadData;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.response.TheMovieDbConfigResponse;
import com.ran.themoviedb.model.server.service.TheMovieDbConfigServiceImpl;
import com.ran.themoviedb.model.utils.ApplicationUtils;

import java.lang.reflect.Type;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 12/30/2015.
 * <p/>
 * Presenter for TheMovieDb Config Values from {@Link TheMovieDbConfigServiceImpl} , save Date
 */
public class TheMovieDbConfigPresenter {

    private static final String TAG = TheMovieDbConfigPresenter.class.getSimpleName();

    private static TheMovieDbConfigPresenter instance;
    private final TypeToken<TheMovieDbConfigResponse> typeToken;

    public synchronized static TheMovieDbConfigPresenter getInstance() {
        if (instance == null) {
            instance = new TheMovieDbConfigPresenter();
        }
        return instance;
    }

    private TheMovieDbConfigPresenter() {
        typeToken = new TypeToken<TheMovieDbConfigResponse>() {
        };
    }

    /**
     * Method to Fetch the MovieDb Configuration at any point of time
     */
    public void fetchMovieDbConfig() {
        new TheMovieDbConfigServiceImpl()
                .requestData(SnappyDBEntityTypes.MOVIE_DB_CONFIG, SnappyPreloadData.MOVIE_DB_CONFIG,typeToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConfigReturned, this::onConfigError);
    }

    private void onConfigReturned(TheMovieDbConfigResponse response) {
        TheMovieDbImagesConfig imagesConfig = response.getImages();
        Gson gson = new Gson();
        Type type = new TypeToken<TheMovieDbImagesConfig>() {
        }.getType();
        AppSharedPreferences.getInstance(ApplicationUtils.getApplication())
                .setMovieImageConfigData(gson.toJson(imagesConfig, type));
    }

    private void onConfigError(Throwable throwable) {
        Log.e(TAG, "config error", throwable);
    }
}
