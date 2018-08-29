package com.ran.themoviedb.presenters;

import com.ran.themoviedb.fragments.SearchTvFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.TvShowSearchResponse;
import com.ran.themoviedb.model.server.service.TvShowSearchServiceImpl;
import com.ran.themoviedb.view_pres_med.TvSearchView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p>
 * Movie Store Presenter between View and the Service Impl
 */
public class TvSearchDataPresenter extends BasePresenter {

    private final TvSearchView tvSearchView;
    private final int pageIndex;
    private final TvShowSearchServiceImpl serviceImpl;

    public TvSearchDataPresenter(int pageIndex, String query, TvSearchView tvSearchView) {
        super();
        this.tvSearchView = tvSearchView;
        this.pageIndex = pageIndex;
        serviceImpl = new TvShowSearchServiceImpl(pageIndex, query);
    }

    @Override
    public void start() {
        tvSearchView.showProgressBar(true, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
        disposable.add(serviceImpl.requestData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTvSearchResponse, this::onTvSearchAPIError));
    }

    @Override
    public void stop() {
        cancelReq();
        tvSearchView.showProgressBar(false, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
    }

    private void onTvSearchResponse(TvShowSearchResponse response) {
        tvSearchView.showProgressBar(false, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
        tvSearchView.tvScreenData(response, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
    }

    private void onTvSearchAPIError(Throwable errorType) {
        tvSearchView.showProgressBar(false, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
        if (errorType instanceof UserAPIErrorException) {
            tvSearchView.tvScreenError(((UserAPIErrorException) errorType).getApiErrorType(), pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
        } else {
            tvSearchView.tvScreenError(UserAPIErrorType.UNEXPECTED_ERROR, pageIndex == SearchTvFragment.FIRST_PAGE_INDEX);
        }

    }
}
