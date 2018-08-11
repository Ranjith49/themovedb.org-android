package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.PeopleDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleDetailResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

/**
 * People Detail Service Impl
 *
 * @author ranjithsuda
 */
public class PeopleDetailServiceImpl extends BaseRetrofitService<PeopleDetailResponse> {

    private int peopleId;
    private String language;

    public PeopleDetailServiceImpl(int tvShowId, String language) {
        this.peopleId = tvShowId;
        this.language = language;
    }


    @Override
    protected Observable<Response<PeopleDetailResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getPeopleDetailsAPI()
                .getPeopleDetailResponse(peopleId, TheMovieDbConstants.APP_API_KEY, language);
    }

    @Override
    protected PeopleDetailResponse transformResponseIfReq(PeopleDetailResponse sourceInput) {
        return sourceInput;
    }
}
