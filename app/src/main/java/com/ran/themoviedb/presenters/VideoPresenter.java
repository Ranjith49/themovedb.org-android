package com.ran.themoviedb.presenters;

import android.content.Context;
import android.util.Log;

import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.VideoDetails;
import com.ran.themoviedb.model.server.response.VideoDetailResponse;
import com.ran.themoviedb.model.server.service.VideoServiceImpl;
import com.ran.themoviedb.view_pres_med.VideoDisplayView;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public class VideoPresenter extends BasePresenter {

    private final VideoServiceImpl service;
    private final String KEY_FILTER_YOUTUBE = "YouTube";
    private VideoDisplayView videoDisplayView;

    public VideoPresenter(Context context, VideoDisplayView videoDisplayView, int id, DisplayStoreType storeType) {
        super();
        this.videoDisplayView = videoDisplayView;
        service = new VideoServiceImpl(id, storeType, AppSharedPreferences.getInstance(context).getAppLanguageData());
    }

    @Override
    public void start() {
        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onVideoResponse, this::onVideoError));
    }

    @Override
    public void stop() {
        cancelReq();
    }

    private void onVideoResponse(VideoDetailResponse response) {
        ArrayList<VideoDetails> filteredResults = new ArrayList<>();
        for (VideoDetails videoDetails : response.getResults()) {
            if (videoDetails.getSite().equalsIgnoreCase(KEY_FILTER_YOUTUBE)) {
                filteredResults.add(videoDetails);
            }
        }
        videoDisplayView.onVideoResponse(filteredResults);
    }

    private void onVideoError(Throwable throwable) {
        //Nothing to do
        Log.d("Video", "Response is null /Empty");
    }
}
