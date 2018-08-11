package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowSimilarDetailsResponse;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Tv Show Similar Service Impl
 *
 * @author ranjithsuda
 */
public class TvShowSimilarServiceImpl extends BaseRetrofitService<TvShowSimilarDetailsResponse> {

    private int tvShowId;
    private String tvShowLang;
    private int pageIndex;

    public TvShowSimilarServiceImpl(int id, String lang, int pageIndex) {
        this.tvShowId = id;
        this.tvShowLang = lang;
        this.pageIndex = pageIndex;
    }

    @Override
    protected Observable<Response<TvShowSimilarDetailsResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getTvShowDetailsAPI()
                .getSimilarTvShows(tvShowId, pageIndex, TheMovieDbConstants.APP_API_KEY, tvShowLang);
    }

    @Override
    protected TvShowSimilarDetailsResponse transformResponseIfReq(TvShowSimilarDetailsResponse sourceInput) {
        if (sourceInput == null || sourceInput.getResults() == null || sourceInput.getResults().size() <= 0) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}
