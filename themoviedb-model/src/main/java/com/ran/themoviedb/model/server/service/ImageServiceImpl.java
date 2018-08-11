package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.ImageDetailResponse;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public class ImageServiceImpl extends BaseRetrofitService<ImageDetailResponse> {

    private int id;
    private String language;
    private DisplayStoreType storeType;

    public ImageServiceImpl(int id, DisplayStoreType storeType, String language) {
        this.id = id;
        this.language = language;
        this.storeType = storeType;
    }


    @Override
    protected Observable<Response<ImageDetailResponse>> getDataObservable() {
        switch (storeType) {
            case MOVIE_STORE:
                return NetworkSDK.getInstance()
                        .getMovieDetailsAPI()
                        .getMovieImageDetails(id, TheMovieDbConstants.APP_API_KEY);
            case TV_STORE:
                return NetworkSDK.getInstance()
                        .getTvShowDetailsAPI()
                        .getTvShowImageDetails(id, TheMovieDbConstants.APP_API_KEY);
            default:
                return Observable.empty();
        }
    }

    @Override
    protected ImageDetailResponse transformResponseIfReq(ImageDetailResponse sourceInput) {
        if (sourceInput == null || (sourceInput.getBackdrops().size() <= 0 && sourceInput.getPosters().size() <= 0)) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}
