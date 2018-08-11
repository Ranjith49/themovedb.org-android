package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.PeopleDetailsAPI;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleKnownForResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

/**
 * People Known For Service
 *
 * @author ranjithsuda
 */
public class PeopleKnownForServiceImpl extends BaseRetrofitService<PeopleKnownForResponse> {

    private int peopleId;
    private String language;

    public PeopleKnownForServiceImpl(int tvShowId, String language) {
        this.peopleId = tvShowId;
        this.language = language;
    }


    @Override
    protected Observable<Response<PeopleKnownForResponse>> getDataObservable() {
        return NetworkSDK.getInstance()
                .getPeopleDetailsAPI()
                .getPeopleKnownForResponse(peopleId, TheMovieDbConstants.APP_API_KEY, language);
    }

    @Override
    protected PeopleKnownForResponse transformResponseIfReq(PeopleKnownForResponse sourceInput) {
        return sourceInput;
    }

}
