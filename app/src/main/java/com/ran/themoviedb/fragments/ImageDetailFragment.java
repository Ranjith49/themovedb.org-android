package com.ran.themoviedb.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.adapters.ImageDetailAdapter;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.listeners.VideoPopupClickListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.ImageDetails;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.entities.VideoDetails;
import com.ran.themoviedb.presenters.ImagePresenter;
import com.ran.themoviedb.presenters.VideoPresenter;
import com.ran.themoviedb.utils.VideoPopupCreator;
import com.ran.themoviedb.view_pres_med.ImageDisplayView;
import com.ran.themoviedb.view_pres_med.VideoDisplayView;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/17/2016.
 */
public class ImageDetailFragment extends Fragment
        implements ImageDisplayView, VideoDisplayView, GenericErrorBuilder.Handler,
        VideoPopupClickListener {

    private final String TAG = ImageDetailFragment.class.getSimpleName();
    private View view;
    private LinearLayout image_container;
    private LinearLayout image_poster_container;
    private LinearLayout image_banner_container;
    private RecyclerView image_poster_recycler;
    private RecyclerView image_banner_recycler;
    private TextView image_poster_count;
    private TextView image_banner_count;
    private ProgressBar progressBar;
    private GenericErrorBuilder genericErrorBuilder;
    private LinearLayout errorLayoutHolder;
    private int id;

    private ImagePresenter imagePresenter;
    private VideoPresenter videoPresenter;
    private ImageDetailAdapter imagePosterAdapter;
    private ImageDetailAdapter imageBannerAdapter;
    private DisplayStoreType displayStoreType;

    private ArrayList<VideoDetails> videoDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image_details, container, false);

        Bundle bundle = getArguments();
        if (bundle.containsKey(TheMovieDbConstants.MOVIE_ID_KEY)) {
            id = bundle.getInt(TheMovieDbConstants.MOVIE_ID_KEY);
        }
        if (bundle.containsKey(TheMovieDbConstants.TV_SHOW_ID_KEY)) {
            id = bundle.getInt(TheMovieDbConstants.TV_SHOW_ID_KEY);
        }
        if (bundle.containsKey(TheMovieDbConstants.STORE_TYPE_KEY)) {
            displayStoreType = DisplayStoreType.getStoreType(
                    bundle.getString(TheMovieDbConstants.STORE_TYPE_KEY,
                            DisplayStoreType.getStoreName(DisplayStoreType.MOVIE_STORE)));
        }

        image_container = view.findViewById(R.id.image_container);
        image_poster_count = view.findViewById(R.id.image_poster_title);
        image_banner_count = view.findViewById(R.id.image_banner_title);
        image_poster_container = view.findViewById(R.id.image_poster_container);
        image_banner_container = view.findViewById(R.id.image_banner_container);
        image_poster_recycler = view.findViewById(R.id.image_poster_recycler);
        image_banner_recycler = view.findViewById(R.id.image_banner_recycler);
        progressBar = view.findViewById(R.id.image_screen_progress);
        errorLayoutHolder =
                view.findViewById(R.id.image_error_layout_container);
        genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
                .CENTER, errorLayoutHolder, this);

        initializePresenter();
        return view;
    }


    private void initializePresenter() {
        imagePresenter = new ImagePresenter(getActivity(), this, id, displayStoreType);
        imagePresenter.start();
        videoPresenter = new VideoPresenter(getActivity(), this, id, displayStoreType);
        videoPresenter.start();
    }

    @Override
    public void onDestroyView() {
        videoPresenter.stop();
        imagePresenter.stop();
        super.onDestroyView();
    }

    private String generateImagePosterBaseUrl() {
        String image_pref_json = TheMovieDbAppController.getAppInstance().appSharedPreferences.getMovieImageConfigData();

        Gson gson = new Gson();
        Type type = new TypeToken<TheMovieDbImagesConfig>() {
        }.getType();

        TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
        String image_url = imagesConfig.getBase_url();
        String image_url_config =
                imagesConfig.getPoster_sizes().get(TheMovieDbConstants.INDEX_BANNER_SIZE);

        return image_url.concat(image_url_config);
    }

    private String generateImageBannerBaseUrl() {
        String image_pref_json = TheMovieDbAppController.getAppInstance().appSharedPreferences.getMovieImageConfigData();

        Gson gson = new Gson();
        Type type = new TypeToken<TheMovieDbImagesConfig>() {
        }.getType();

        TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
        String image_url = imagesConfig.getBase_url();
        String image_url_config =
                imagesConfig.getBackdrop_sizes().get(TheMovieDbConstants.INDEX_BANNER_SIZE);

        return image_url.concat(image_url_config);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.image_detail_screen_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_video:
                View view = getActivity().findViewById(R.id.item_video);
                VideoPopupCreator.createListPopupWindow(getActivity(), videoDetails, this, view);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //-- CallBacks from different Views -- //
    @Override
    public void onRefreshClicked() {
        image_container.setVisibility(View.GONE);
        initializePresenter();
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void imageResponse(ArrayList<ImageDetails> backdrops, ArrayList<ImageDetails> posters) {
        if (backdrops.size() > 0) {
            imageBannerAdapter = new ImageDetailAdapter(false, backdrops, getActivity(),
                    generateImageBannerBaseUrl());
            image_banner_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false));
            String count = String.format(getActivity().getResources().
                    getString(R.string.image_banner_title), String.valueOf(backdrops.size()));
            image_banner_count.setText(count);
            image_banner_recycler.setAdapter(imageBannerAdapter);
            image_banner_container.setVisibility(View.VISIBLE);
        }

        if (posters.size() > 0) {
            imagePosterAdapter = new ImageDetailAdapter(true, posters, getActivity(),
                    generateImagePosterBaseUrl());
            image_poster_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false));
            String count = String.format(getActivity().getResources().
                    getString(R.string.image_poster_title), String.valueOf(posters.size()));
            image_poster_count.setText(count);
            image_poster_recycler.setAdapter(imagePosterAdapter);
            image_poster_container.setVisibility(View.VISIBLE);
        }
        //Total Response container here ..
        image_container.setVisibility(View.VISIBLE);
    }

    @Override
    public void imageError(UserAPIErrorType errorType) {
        genericErrorBuilder.setUserAPIError(errorType);
    }

    @Override
    public void onVideoResponse(ArrayList<VideoDetails> videoDetails) {
        this.videoDetails = videoDetails;
        setHasOptionsMenu(true);
    }

    @Override
    public void onVideoClickItem(String videoId, String videoKey) {
        try {
            String youtubeUrl = TheMovieDbConstants.YOUTUBE_BASE_URL.concat(videoKey);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
            startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}
