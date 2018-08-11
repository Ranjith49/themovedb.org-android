package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.response.AllMovieGenreListResponse;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Created by ranjith.suda on 12/29/2015.
 * <p/>
 * Retrofit Service Implementation for {@see AllGenreListAPI}
 */
public class AllMoviesGenreServiceImpl extends BaseRetrofitService<AllMovieGenreListResponse> {

    public AllMoviesGenreServiceImpl() {

    }

    @Override
    protected Observable<Response<AllMovieGenreListResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getGenreListAPI()
                .getMovieGenreList(TheMovieDbConstants.APP_API_KEY);
    }

    @Override
    protected AllMovieGenreListResponse transformResponseIfReq(AllMovieGenreListResponse sourceInput) {
        if (sourceInput == null || sourceInput.getGenres() == null || sourceInput.getGenres().isEmpty()) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}
