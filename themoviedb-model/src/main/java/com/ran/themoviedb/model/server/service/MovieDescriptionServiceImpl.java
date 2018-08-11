package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieDetailResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Movie Description Service Impl
 *
 * @author ranjithsuda
 */
public class MovieDescriptionServiceImpl extends BaseRetrofitService<MovieDetailResponse> {

    private int movieId;
    private String language;

    public MovieDescriptionServiceImpl(int movieId, String language) {
        this.movieId = movieId;
        this.language = language;
    }


    @Override
    protected Observable<Response<MovieDetailResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getMovieDetailsAPI()
                .getMovieBasicDetails(movieId, TheMovieDbConstants.APP_API_KEY, language);
    }

    @Override
    protected MovieDetailResponse transformResponseIfReq(MovieDetailResponse sourceInput) {
        return sourceInput;
    }
}
