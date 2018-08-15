package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.preloaddb.BaseSnappyDBService;
import com.ran.themoviedb.model.server.response.TheMovieDbConfigResponse;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Created by ranjith.suda on 12/30/2015.
 * Retrofit Service Implementation for {@see TheMovieDbConfigAPI}
 */
public class TheMovieDbConfigServiceImpl extends BaseSnappyDBService<TheMovieDbConfigResponse> {

    public TheMovieDbConfigServiceImpl() {
        super();
    }

    @Override
    protected Observable<Response<TheMovieDbConfigResponse>> getResponseOfType() {
        return NetworkSDK.getInstance()
                .getTheMovieDbConfigAPI()
                .getAppConfig(TheMovieDbConstants.APP_API_KEY);
    }
}
