package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowSearchResponse;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Tv Show Search Service Impl
 *
 * @author ranjithsuda
 */
public class TvShowSearchServiceImpl extends BaseRetrofitService<TvShowSearchResponse> {

    private int pageIndex;
    private String query;

    public TvShowSearchServiceImpl(int page, String query) {
        this.pageIndex = page;
        this.query = query;
    }

    @Override
    protected Observable<Response<TvShowSearchResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getSearchAPI()
                .getTvSearchResults(TheMovieDbConstants.APP_API_KEY, pageIndex, query);
    }

    @Override
    protected TvShowSearchResponse transformResponseIfReq(TvShowSearchResponse sourceInput) {
        if (sourceInput == null || sourceInput.getResults() == null || sourceInput.getResults().size() <= 0) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}
