package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.SearchAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSearchResponse;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class MovieSearchServiceImpl extends BaseRetrofitService<MovieSearchResponse> {

    private Handler handler;
    private int pageIndex;
    private String query;

    public MovieSearchServiceImpl(Handler handler, int page, String query) {
        this.handler = handler;
        this.pageIndex = page;
        this.query = query;
    }

    @Override
    protected void handleApiResponse(MovieSearchResponse response, int uniqueId) {
        if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
            handler.onMovieSearchAPIError(UserAPIErrorType.NOCONTENT_ERROR, uniqueId);
        } else {
            handler.onMovieSearchResponse(response, uniqueId);
        }
    }

    @Override
    protected void handleError(UserAPIErrorType errorType, int uniqueId) {
        handler.onMovieSearchAPIError(errorType, uniqueId);
    }

    @Override
    protected Call<MovieSearchResponse> getRetrofitCall() {
        return NetworkSDK.getInstance()
                .getSearchAPI()
                .getMovieSearchResults(TheMovieDbConstants.APP_API_KEY, pageIndex, query);
    }

    /**
     * Handler callbacks for the Presenter ..
     */
    public interface Handler {

        void onMovieSearchResponse(MovieSearchResponse response, int uniqueId);

        void onMovieSearchAPIError(UserAPIErrorType errorType, int uniqueId);
    }
}
