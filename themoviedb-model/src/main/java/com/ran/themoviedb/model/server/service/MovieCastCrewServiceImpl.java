package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.CastCrewDetailResponse;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;


import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Move Cast Service Impl
 *
 * @author ranjithsuda
 */
public class MovieCastCrewServiceImpl extends BaseRetrofitService<CastCrewDetailResponse> {

    private int movieId;
    private String movieLang;

    public MovieCastCrewServiceImpl(int id, String lang) {
        this.movieId = id;
        this.movieLang = lang;
    }

    @Override
    protected Observable<Response<CastCrewDetailResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getMovieDetailsAPI()
                .getCastCrewDetails(movieId, TheMovieDbConstants.APP_API_KEY, movieLang);
    }

    @Override
    protected CastCrewDetailResponse transformResponseIfReq(CastCrewDetailResponse sourceInput) {
        if (sourceInput == null || (sourceInput.getCrew() == null || sourceInput.getCrew().size() < 0) && (sourceInput.getCast() == null || sourceInput.getCast().size() < 0)) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}
