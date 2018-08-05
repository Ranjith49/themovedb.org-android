package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.TVShowStoreAPI;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p/>
 * Retrofit Service Impl for the TvShowStore {@link TVShowStoreAPI}
 */
public class TVShowStoreServiceImpl extends BaseRetrofitService<TVShowStoreResponse> {

    private Handler handler;
    private TVShowStoreType tvShowStoreType;
    private int pageIndex;
    private String language;

    public TVShowStoreServiceImpl(Handler handler, TVShowStoreType storeType, int page,
                                  String language) {
        super();
        this.handler = handler;
        this.tvShowStoreType = storeType;
        this.pageIndex = page;
        this.language = language;
    }

    @Override
    protected void handleApiResponse(TVShowStoreResponse response, int uniqueId) {
        handler.onTvStoreResponseRetrieved(response, uniqueId);
    }

    @Override
    protected void handleError(UserAPIErrorType errorType, int uniqueId) {
        handler.onTvStoreAPIError(errorType, uniqueId);
    }

    @Override
    protected Call<TVShowStoreResponse> getRetrofitCall() {
        switch (tvShowStoreType) {
            case TV_AIR:
                return NetworkSDK.getInstance()
                        .getTvShowStoreAPI()
                        .getOnAirTVShows(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
            case TV_POPULAR:
                return NetworkSDK.getInstance()
                        .getTvShowStoreAPI()
                        .getPopularTVShows(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
            case TV_TOP_RATED:
                return NetworkSDK.getInstance()
                        .getTvShowStoreAPI()
                        .getTopRatedTVShows(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
            default:
                return NetworkSDK.getInstance()
                        .getTvShowStoreAPI()
                        .getPopularTVShows(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
        }
    }

    /**
     * Handler call Back for the Presenters
     */
    public interface Handler {
        void onTvStoreResponseRetrieved(TVShowStoreResponse response, int uniqueId);

        void onTvStoreAPIError(UserAPIErrorType errorType, int uniqueId);
    }
}
