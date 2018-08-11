package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.fragments.MovieStoreFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.MovieSearchResponse;
import com.ran.themoviedb.model.server.service.MovieSearchServiceImpl;
import com.ran.themoviedb.view_pres_med.MovieSearchView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Movie Store Presenter between View and the Service Impl
 */
public class MovieSearchDataPresenter extends BasePresenter {

    private final Context context;
    private final MovieSearchView movieSearchView;
    private final String query;
    private final int pageIndex;
    private final MovieSearchServiceImpl movieService;

    public MovieSearchDataPresenter(Context context, int pageIndex, String query,
                                    MovieSearchView movieSearchView) {
        super();
        this.context = context;
        this.movieSearchView = movieSearchView;
        this.pageIndex = pageIndex;
        this.query = query;
        movieService = new MovieSearchServiceImpl(pageIndex, query);
    }

    @Override
    public void start() {
        movieSearchView.showProgressBar(true, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        disposable.add(movieService.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieSearchResponse, this::onMovieSearchAPIError));
    }

    @Override
    public void stop() {
        cancelReq();
        movieSearchView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
    }


    private void onMovieSearchResponse(MovieSearchResponse response) {
        movieSearchView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        movieSearchView.movieScreenData(response, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
    }

    private void onMovieSearchAPIError(Throwable error) {
        movieSearchView.showProgressBar(false, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        if (error instanceof UserAPIErrorException) {
            movieSearchView.movieScreenError(((UserAPIErrorException) error).getApiErrorType(), pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        } else {
            movieSearchView.movieScreenError(UserAPIErrorType.UNEXPECTED_ERROR, pageIndex == MovieStoreFragment.FIRST_PAGE_INDEX);
        }

    }
}
