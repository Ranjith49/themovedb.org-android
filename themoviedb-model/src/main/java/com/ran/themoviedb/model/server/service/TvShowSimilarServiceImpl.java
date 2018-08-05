package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.TvShowDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowSimilarDetailsResponse;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 1/11/2016.
 */
public class TvShowSimilarServiceImpl extends BaseRetrofitService<TvShowSimilarDetailsResponse> {

    private Handler handler;
    private int tvShowId;
    private String tvShowLang;
    private int pageIndex;

    public TvShowSimilarServiceImpl(Handler handler, int id, String lang, int pageIndex) {
        this.tvShowId = id;
        this.tvShowLang = lang;
        this.handler = handler;
        this.pageIndex = pageIndex;
    }

    @Override
    protected void handleApiResponse(TvShowSimilarDetailsResponse response, int uniqueId) {
        if (response == null || response.getResults() == null || response.getResults().size() <= 0) {
            handler.onSimilarTVShowsError(UserAPIErrorType.NOCONTENT_ERROR, uniqueId);
        } else {
            handler.onSimilarTvShowsResponse(response, uniqueId);
        }
    }

    @Override
    protected void handleError(UserAPIErrorType errorType, int uniqueId) {
        handler.onSimilarTVShowsError(errorType, uniqueId);
    }

    @Override
    protected Call<TvShowSimilarDetailsResponse> getRetrofitCall() {
        return NetworkSDK.getInstance()
                .getTvShowDetailsAPI()
                .getSimilarTvShows(tvShowId, pageIndex, TheMovieDbConstants.APP_API_KEY, tvShowLang);
    }

    /**
     * Handler CallBack to presenter with Response /Error ..
     */
    public interface Handler {
        void onSimilarTvShowsResponse(TvShowSimilarDetailsResponse response, int uniqueId);

        void onSimilarTVShowsError(UserAPIErrorType errorType, int uniqueId);
    }
}
