package com.ran.themoviedb.viemodels;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.util.Pair;

import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.VideoDetails;
import com.ran.themoviedb.model.server.response.VideoDetailResponse;
import com.ran.themoviedb.model.server.service.VideoServiceImpl;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Video View Model for ImageFragment
 *
 * @author ranjithsuda
 */

public class VideoViewModel extends BaseViewModel<Pair<Integer, DisplayStoreType>> {

    private static final String KEY_FILTER_YOUTUBE = "YouTube";
    private VideoServiceImpl service;
    private MutableLiveData<ArrayList<VideoDetails>> videoDetailsList;

    public VideoViewModel() {
        super();
        initialiseViewModel();
    }

    @Override
    public void initialiseViewModel() {
        this.videoDetailsList = new MutableLiveData<>();
    }

    @Override
    public void startExecution(Pair<Integer, DisplayStoreType> storeTypePair) {
        service = new VideoServiceImpl(storeTypePair.first, storeTypePair.second,
                TheMovieDbAppController.getAppInstance().appSharedPreferences.getAppLanguageData());

        disposable.add(service.requestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onVideoResponse, this::onVideoError));
    }

    private void onVideoResponse(VideoDetailResponse response) {
        ArrayList<VideoDetails> filteredResults = new ArrayList<>();
        for (VideoDetails videoDetails : response.getResults()) {
            if (videoDetails.getSite().equalsIgnoreCase(KEY_FILTER_YOUTUBE)) {
                filteredResults.add(videoDetails);
            }
        }
        videoDetailsList.setValue(filteredResults);
    }

    private void onVideoError(Throwable throwable) {
        //Nothing to do
        Log.d("Video", "Response is null /Empty");
    }

    public MutableLiveData<ArrayList<VideoDetails>> getVideoDetailsList() {
        return videoDetailsList;
    }
}
