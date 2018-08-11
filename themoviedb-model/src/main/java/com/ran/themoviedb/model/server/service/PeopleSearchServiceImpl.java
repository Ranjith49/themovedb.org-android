package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleSearchResponse;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * People Search Service Impl
 *
 * @author ranjithsuda
 */
public class PeopleSearchServiceImpl extends BaseRetrofitService<PeopleSearchResponse> {

    private int pageIndex;
    private String query;

    public PeopleSearchServiceImpl(int page, String query) {
        this.pageIndex = page;
        this.query = query;
    }

    @Override
    protected Observable<Response<PeopleSearchResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getSearchAPI()
                .getPeopleSearchResults(TheMovieDbConstants.APP_API_KEY, pageIndex, query);
    }

    @Override
    protected PeopleSearchResponse transformResponseIfReq(PeopleSearchResponse sourceInput) {
        if (sourceInput == null || sourceInput.getResults() == null || sourceInput.getResults().size() <= 0) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}
