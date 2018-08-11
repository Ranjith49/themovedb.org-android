package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.fragments.TvStoreFragment;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;
import com.ran.themoviedb.model.server.service.TVShowStoreServiceImpl;
import com.ran.themoviedb.view_pres_med.TvStoreView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/4/2016.
 * <p/>
 * TV Store Presenter between View and the Service Impl
 */
public class TvStoreDataPresenter extends BasePresenter {

    private final Context context;
    private final TVShowStoreType tvStoreType;
    private final TvStoreView tvStoreView;
    private final int pageIndex;
    private final TVShowStoreServiceImpl tvService;

    public TvStoreDataPresenter(Context context, TVShowStoreType tvStoreType, int pageIndex,
                                TvStoreView tvStoreView) {
        this.context = context;
        this.tvStoreType = tvStoreType;
        this.tvStoreView = tvStoreView;
        this.pageIndex = pageIndex;
        tvService = new TVShowStoreServiceImpl(tvStoreType, pageIndex,
                AppSharedPreferences.getInstance(context).getAppLanguageData());
    }

    @Override
    public void start() {
        tvStoreView.showProgressBar(true, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
        disposable.add(tvService.requestData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTvStoreResponseRetrieved, this::onTvStoreAPIError));
    }

    @Override
    public void stop() {
        cancelReq();
        tvStoreView.showProgressBar(false, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
    }

    private void onTvStoreResponseRetrieved(TVShowStoreResponse response) {
        tvStoreView.showProgressBar(false, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
        tvStoreView.tvScreenData(response, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
    }

    private void onTvStoreAPIError(Throwable throwable) {
        tvStoreView.showProgressBar(false, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
        if (throwable instanceof UserAPIErrorException) {
            tvStoreView.tvScreenError(((UserAPIErrorException) throwable).getApiErrorType(), pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
        } else {
            tvStoreView.tvScreenError(UserAPIErrorType.UNEXPECTED_ERROR, pageIndex == TvStoreFragment.FIRST_PAGE_INDEX);
        }

    }
}
