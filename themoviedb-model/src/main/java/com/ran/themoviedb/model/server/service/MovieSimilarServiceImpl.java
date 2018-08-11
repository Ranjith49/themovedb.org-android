package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.MovieSimilarDetailsResponse;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Similar Movies Service Impl
 *
 * @author ranjithsuda
 */
public class MovieSimilarServiceImpl extends BaseRetrofitService<MovieSimilarDetailsResponse> {

    private int movieId;
    private String movieLang;
    private int pageIndex;

    public MovieSimilarServiceImpl(int id, String lang, int pageIndex) {
        this.movieId = id;
        this.movieLang = lang;
        this.pageIndex = pageIndex;
    }


    @Override
    protected Observable<Response<MovieSimilarDetailsResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getMovieDetailsAPI()
                .getSimilarMovies(movieId, pageIndex, TheMovieDbConstants.APP_API_KEY, movieLang);
    }

    @Override
    protected MovieSimilarDetailsResponse transformResponseIfReq(MovieSimilarDetailsResponse sourceInput) {
        if (sourceInput == null || sourceInput.getResults() == null || sourceInput.getResults().size() <= 0) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}
