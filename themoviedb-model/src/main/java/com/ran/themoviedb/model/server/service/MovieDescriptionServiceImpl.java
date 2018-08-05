package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieDetailResponse;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 1/9/2016.
 */
public class MovieDescriptionServiceImpl extends BaseRetrofitService<MovieDetailResponse> {

    private Handler handler;
    private int movieId;
    private String language;

    public MovieDescriptionServiceImpl(Handler handler, int movieId, String language) {
        this.handler = handler;
        this.movieId = movieId;
        this.language = language;
    }

    @Override
    protected void handleApiResponse(MovieDetailResponse response, int uniqueId) {
        handler.onMovieDetailResponse(response, uniqueId);
    }

    @Override
    protected void handleError(UserAPIErrorType errorType, int uniqueId) {
        handler.onMovieError(errorType, uniqueId);
    }

    @Override
    protected Call<MovieDetailResponse> getRetrofitCall() {
        return NetworkSDK.getInstance()
                .getMovieDetailsAPI()
                .getMovieBasicDetails(movieId, TheMovieDbConstants.APP_API_KEY, language);
    }

    /**
     * Handler CallBack to presenter with Response /Error ..
     */
    public interface Handler {
        void onMovieDetailResponse(MovieDetailResponse response, int uniqueId);

        void onMovieError(UserAPIErrorType errorType, int uniqueId);
    }
}
