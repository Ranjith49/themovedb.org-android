package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieStoreAPI;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p/>
 * Retrofit Service Impl to get MovieStore Response for {@link MovieStoreAPI}
 */
public class MovieStoreServiceImpl extends BaseRetrofitService<MovieStoreResponse> {

    private Handler handler;
    private MovieStoreType movieStoreType;
    private int pageIndex;
    private String language;

    public MovieStoreServiceImpl(Handler handler, MovieStoreType storeType, int page,
                                 String language) {
        this.handler = handler;
        this.movieStoreType = storeType;
        this.pageIndex = page;
        this.language = language;
    }

    @Override
    protected void handleApiResponse(MovieStoreResponse response, int uniqueId) {
        handler.onMovieStoreResponse(response, uniqueId);
    }

    @Override
    protected void handleError(UserAPIErrorType errorType, int uniqueId) {
        handler.onMovieStoreAPIError(errorType, uniqueId);
    }

    @Override
    protected Call<MovieStoreResponse> getRetrofitCall() {
        switch (movieStoreType) {
            case MOVIE_POPULAR:
                return NetworkSDK.getInstance()
                        .getMovieStoreAPI()
                        .getPopularMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
            case MOVIE_NOW_PLAYING:
                return NetworkSDK.getInstance()
                        .getMovieStoreAPI()
                        .getNowShowingMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
            case MOVIE_TOP_RATED:
                return NetworkSDK.getInstance()
                        .getMovieStoreAPI()
                        .getTopRatedMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
            case MOVIE_UPCOMING:
                return NetworkSDK.getInstance()
                        .getMovieStoreAPI()
                        .getUpComingMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
            default:
                return NetworkSDK.getInstance()
                        .getMovieStoreAPI()
                        .getPopularMovieList(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
        }
    }

    /**
     * Handler callbacks for the Presenter ..
     */
    public interface Handler {

        void onMovieStoreResponse(MovieStoreResponse response, int uniqueId);

        void onMovieStoreAPIError(UserAPIErrorType errorType, int uniqueId);
    }
}
