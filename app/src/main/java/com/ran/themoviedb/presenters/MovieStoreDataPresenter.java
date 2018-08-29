package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.fragments.MovieStoreFragment;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;
import com.ran.themoviedb.model.server.service.MovieStoreServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieStoreView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Movie Store Presenter between View and the Service Impl
 */
public class MovieStoreDataPresenter extends BasePresenter {

    private final MovieStoreView movieStoreView;
    private final int pageIndex;
    private final MovieStoreServiceImpl movieService;

    public MovieStoreDataPresenter(Context context, MovieStoreType movieStoreType, int pageIndex,
                                   MovieStoreView movieStoreView) {
        super();
        this.movieStoreView = movieStoreView;
        this.pageIndex = pageIndex;
        movieService = new MovieStoreServiceImpl(movieStoreType,
                pageIndex,
                TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData());
    }

    @Override
    public void start() {
        movieStoreView.showProgressBar(true, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        disposable.add(movieService.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieStoreResponse, this::onMovieStoreAPIError));
    }

    @Override
    public void stop() {
        cancelReq();
        movieStoreView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
    }

    private void onMovieStoreResponse(MovieStoreResponse response) {
        movieStoreView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        movieStoreView.movieScreenData(response, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
    }


    private void onMovieStoreAPIError(Throwable error) {
        movieStoreView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        if (error instanceof UserAPIErrorException) {
            movieStoreView.movieScreenError(((UserAPIErrorException) error).getApiErrorType(), pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        } else {
            movieStoreView.movieScreenError(UserAPIErrorType.UNEXPECTED_ERROR, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        }

    }
}
