package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.TvShowDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

/**
 * TV Show Detail Service Impl
 *
 * @author ranjithsuda
 */
public class TvShowDetailServiceImpl extends BaseRetrofitService<TvShowDetailResponse> {

    private int tvShowId;
    private String language;

    public TvShowDetailServiceImpl(int tvShowId, String language) {
        this.tvShowId = tvShowId;
        this.language = language;
    }


    @Override
    protected Observable<Response<TvShowDetailResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getTvShowDetailsAPI()
                .getTvShowBasicDetails(tvShowId, TheMovieDbConstants.APP_API_KEY, language);
    }

    @Override
    protected TvShowDetailResponse transformResponseIfReq(TvShowDetailResponse sourceInput) {
        return sourceInput;
    }
}
