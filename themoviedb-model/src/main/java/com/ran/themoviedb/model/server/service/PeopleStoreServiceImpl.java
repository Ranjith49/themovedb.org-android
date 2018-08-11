package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.PeopleStoreAPI;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.PeopleStoreResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;

/**
 * People Store Service Impl
 *
 * @author ranjithsuda
 */
public class PeopleStoreServiceImpl extends BaseRetrofitService<PeopleStoreResponse> {

    private PeopleStoreType peopleStoreType;
    private int pageIndex;
    private String language;

    public PeopleStoreServiceImpl(PeopleStoreType storeType, int page, String lang) {
        this.peopleStoreType = storeType;
        this.pageIndex = page;
        this.language = lang;
    }

    @Override
    protected Observable<Response<PeopleStoreResponse>> getDataObservable() {
        switch (peopleStoreType) {
            case PEOPLE_POPULAR:
            default:
                return NetworkSDK.getInstance()
                        .getPeopleStoreAPI()
                        .getPeopleStoreList(TheMovieDbConstants.APP_API_KEY, pageIndex, language);
        }
    }

    @Override
    protected PeopleStoreResponse transformResponseIfReq(PeopleStoreResponse sourceInput) {
        return sourceInput;
    }
}
