package com.ran.themoviedb.presenters;

import com.ran.themoviedb.fragments.SearchPeopleFragment;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.PeopleSearchResponse;
import com.ran.themoviedb.model.server.service.PeopleSearchServiceImpl;
import com.ran.themoviedb.view_pres_med.PeopleSearchView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p>
 * Movie Store Presenter between View and the Service Impl
 */
public class PeopleSearchDataPresenter extends BasePresenter {

    private final PeopleSearchView peopleSearchView;
    private final int pageIndex;
    private final PeopleSearchServiceImpl serviceImpl;

    public PeopleSearchDataPresenter(int pageIndex, String query, PeopleSearchView peopleSearchView) {
        super();
        this.peopleSearchView = peopleSearchView;
        this.pageIndex = pageIndex;
        serviceImpl = new PeopleSearchServiceImpl(pageIndex, query);
    }

    @Override
    public void start() {
        peopleSearchView.showProgressBar(true, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
        disposable.add(serviceImpl.requestData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onPeopleSearchResponse, this::onPeopleSearchAPIError));
    }

    @Override
    public void stop() {
        cancelReq();
        peopleSearchView.showProgressBar(false, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
    }

    private void onPeopleSearchResponse(PeopleSearchResponse response) {
        peopleSearchView.showProgressBar(false, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
        peopleSearchView.peopleScreenData(response, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
    }

    private void onPeopleSearchAPIError(Throwable error) {
        peopleSearchView.showProgressBar(false, pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
        if (error instanceof UserAPIErrorException) {
            peopleSearchView.peopleScreenError(((UserAPIErrorException) error).getApiErrorType(),
                    pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
        } else {
            peopleSearchView.peopleScreenError(UserAPIErrorType.UNEXPECTED_ERROR,
                    pageIndex == SearchPeopleFragment.FIRST_PAGE_INDEX);
        }

    }
}
