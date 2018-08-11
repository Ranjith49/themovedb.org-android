package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieStoreAPI;
import com.ran.themoviedb.model.server.entities.MovieStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p/>
 * Retrofit Service Impl to get MovieStore Response for {@link MovieStoreAPI}
 */
public class MovieStoreServiceImpl extends BaseRetrofitService<MovieStoreResponse> {

    private MovieStoreType movieStoreType;
    private int pageIndex;
    private String language;

    public MovieStoreServiceImpl(MovieStoreType storeType, int page, String language) {
        this.movieStoreType = storeType;
        this.pageIndex = page;
        this.language = language;
    }


    @Override
    protected Observable<Response<MovieStoreResponse>> getDataObservable() {
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

    @Override
    protected MovieStoreResponse transformResponseIfReq(MovieStoreResponse sourceInput) {
        return sourceInput;
    }
}
