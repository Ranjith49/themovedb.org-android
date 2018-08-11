package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.TVShowStoreAPI;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p/>
 * Retrofit Service Impl for the TvShowStore {@link TVShowStoreAPI}
 */
public class TVShowStoreServiceImpl extends BaseRetrofitService<TVShowStoreResponse> {

    private TVShowStoreType tvShowStoreType;
    private int pageIndex;
    private String language;

    public TVShowStoreServiceImpl(TVShowStoreType storeType, int page, String language) {
        super();
        this.tvShowStoreType = storeType;
        this.pageIndex = page;
        this.language = language;
    }

    @Override
    protected Observable<Response<TVShowStoreResponse>> getDataObservable() {
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

    @Override
    protected TVShowStoreResponse transformResponseIfReq(TVShowStoreResponse sourceInput) {
        return sourceInput;
    }
}
