package com.ran.themoviedb.presenters;

import android.content.Context;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.exception.UserAPIErrorException;
import com.ran.themoviedb.model.server.response.ImageDetailResponse;
import com.ran.themoviedb.model.server.service.ImageServiceImpl;
import com.ran.themoviedb.view_pres_med.ImageDisplayView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Image Load Presenter
 *
 * @author ranjithsuda
 */
public class ImagePresenter extends BasePresenter {

    private final ImageServiceImpl service;
    private ImageDisplayView imageDisplayView;

    public ImagePresenter(Context context, ImageDisplayView imageDisplayView, int id, DisplayStoreType storeType) {
        super();
        this.imageDisplayView = imageDisplayView;
        service = new ImageServiceImpl(id, storeType, TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData());
    }

    @Override
    public void start() {
        imageDisplayView.showProgressBar(true);
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onImageResponse, this::onImageError));
    }

    @Override
    public void stop() {
        cancelReq();
        imageDisplayView.showProgressBar(false);
    }


    private void onImageResponse(ImageDetailResponse response) {
        imageDisplayView.showProgressBar(false);
        imageDisplayView.imageResponse(response.getBackdrops(), response.getPosters());
    }

    private void onImageError(Throwable error) {
        imageDisplayView.showProgressBar(false);
        if (error instanceof UserAPIErrorException) {
            imageDisplayView.imageError(((UserAPIErrorException) error).getApiErrorType());
        } else {
            imageDisplayView.imageError(UserAPIErrorType.UNEXPECTED_ERROR);
        }
    }
}
