package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSearchResponse;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Movie Search Sevice Impl
 *
 * @author ranjithsuda
 */
public class MovieSearchServiceImpl extends BaseRetrofitService<MovieSearchResponse> {

    private int pageIndex;
    private String query;

    public MovieSearchServiceImpl(int page, String query) {
        this.pageIndex = page;
        this.query = query;
    }


    @Override
    protected Observable<Response<MovieSearchResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getSearchAPI()
                .getMovieSearchResults(TheMovieDbConstants.APP_API_KEY, pageIndex, query);
    }

    @Override
    protected MovieSearchResponse transformResponseIfReq(MovieSearchResponse sourceInput) {
        if (sourceInput == null || sourceInput.getResults() == null || sourceInput.getResults().size() <= 0) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}

