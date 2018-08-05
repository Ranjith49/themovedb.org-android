package com.ran.themoviedb.model.server.service;

import com.ran.themoviedb.model.NetworkSDK;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.api.MovieDetailsAPI;
import com.ran.themoviedb.model.server.api.TvShowDetailsAPI;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.ImageDetailResponse;

import retrofit2.Call;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public class ImageServiceImpl extends BaseRetrofitService<ImageDetailResponse> {

    private int id;
    private String language;
    private DisplayStoreType storeType;
    private Handler handler;

    public ImageServiceImpl(int id, DisplayStoreType storeType, Handler handler, String language) {
        this.id = id;
        this.language = language;
        this.storeType = storeType;
        this.handler = handler;
    }

    @Override
    protected void handleApiResponse(ImageDetailResponse response, int uniqueId) {
        if (response == null ||
                (response.getBackdrops().size() <= 0 && response.getPosters().size() <= 0)) {
            handler.onImageError(UserAPIErrorType.NOCONTENT_ERROR, uniqueId);
        } else {
            handler.onImageResponse(response, uniqueId);
        }
    }

    @Override
    protected void handleError(UserAPIErrorType errorType, int uniqueId) {
        handler.onImageError(errorType, uniqueId);
    }

    @Override
    protected Call<ImageDetailResponse> getRetrofitCall() {
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
                return null;
        }
    }

    public interface Handler {

        void onImageResponse(ImageDetailResponse response, int uniqueId);

        void onImageError(UserAPIErrorType errorType, int uniqueId);

    }
}
