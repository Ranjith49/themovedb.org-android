package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.VideoDetailResponse;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Video Service Impl
 *
 * @author ranjithsuda
 */
public class VideoServiceImpl extends BaseRetrofitService<VideoDetailResponse> {

    private int id;
    private String language;
    private DisplayStoreType storeType;

    public VideoServiceImpl(int id, DisplayStoreType storeType, String language) {
        this.id = id;
        this.language = language;
        this.storeType = storeType;
    }


    @Override
    protected Observable<Response<VideoDetailResponse>> getDataObservable() {
        switch (storeType) {
            case MOVIE_STORE:
                return NetworkSDK.getInstance()
                        .getMovieDetailsAPI()
                        .getMovieVideoDetails(id, TheMovieDbConstants.APP_API_KEY);
            case TV_STORE:
                return NetworkSDK.getInstance()
                        .getTvShowDetailsAPI()
                        .getTvShowVideoDetails(id, TheMovieDbConstants.APP_API_KEY);
            default:
                return Observable.empty();
        }
    }

    @Override
    protected VideoDetailResponse transformResponseIfReq(VideoDetailResponse sourceInput) {
        if (sourceInput == null || sourceInput.getResults() == null || sourceInput.getResults().size() <= 0) {
            throw new UserAPIErrorException(UserAPIErrorType.NOCONTENT_ERROR);
        }
        return sourceInput;
    }
}
