package com.ran.themoviedb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;
import com.ran.themoviedb.presenters.TvShowDetailPresenter;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;
import com.ran.themoviedb.view_pres_med.TvShowDetailView;

import java.lang.reflect.Type;

/**
 * Created by ranjith.suda on 2/29/2016.
 * <p/>
 * Fragment for Showing the Tv Show Description Details.
 */
public class TvShowDetailFragment extends Fragment implements GenericErrorBuilder.Handler,
        TvShowDetailView {

    private View view;
    private ImageView tvPoster;
    private TextView tvTitle;
    private TextView tvRating;
    private TextView tvInitialAirDate;
    private TextView tvRunningTime;

    private TextView tvDescription;
    private TextView tvSeasons;
    private TextView tvEpisodes;
    private TextView tvExternalUrl;

    private ScrollView tvContainer;
    private LinearLayout tvErrorLayout;
    private ProgressBar tvProgressBar;

    private GenericErrorBuilder genericErrorBuilder;
    private TvShowDetailPresenter presenter;
    private int tvShowId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tv_show_detail, container, false);
        tvShowId = getArguments().getInt(TheMovieDbConstants.TV_SHOW_ID_KEY);
        initView();
        initializePresenter();
        return view;
    }

    private void initView() {
        tvContainer = view.findViewById(R.id.tv_scroll_view);
        tvErrorLayout = view.findViewById(R.id.genre_error_layout_container);
        tvProgressBar = view.findViewById(R.id.tv_description_progress);

        tvPoster = view.findViewById(R.id.tvShow_poster);
        tvTitle = view.findViewById(R.id.tv_show_name);
        tvRating = view.findViewById(R.id.tv_show_rating);
        tvInitialAirDate = view.findViewById(R.id.tv_show_date);
        tvRunningTime = view.findViewById(R.id.tv_show_runtime);

        tvDescription = view.findViewById(R.id.overview_description);
        tvSeasons = view.findViewById(R.id.overview_no_seasons);
        tvEpisodes = view.findViewById(R.id.overview_no_episodes);
        tvExternalUrl = view.findViewById(R.id.overview_url);
    }

    private void initializePresenter() {
        genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
                .CENTER, tvErrorLayout, this);
        presenter = new TvShowDetailPresenter(getActivity(), this, tvShowId);
        presenter.start();
    }

    @Override
    public void onDestroyView() {
        presenter.stop();
        super.onDestroyView();
    }

    private void processResponse(TvShowDetailResponse response) {
        //Top View  ..
        if (!AppUiUtils.isStringEmpty(response.getName())) {
            tvTitle.setText(response.getName());
            tvTitle.setVisibility(View.VISIBLE);
        }
        tvRating.setText(String.valueOf(response.getVote_average()));
        tvRating.setVisibility(View.VISIBLE);

        String runningTimeFormatted =
                AppUiUtils.generateCommaString(response.getEpisode_running_time());
        String runningTime = String.format(getActivity().getResources().
                getString(R.string.tv_running_time), String.valueOf(runningTimeFormatted));
        tvRunningTime.setText(runningTime);
        tvRunningTime.setVisibility(View.VISIBLE);
        if (!AppUiUtils.isStringEmpty(response.getFirst_air_date())) {
            tvInitialAirDate.setText(response.getFirst_air_date());
            tvInitialAirDate.setVisibility(View.VISIBLE);
        }
        loadImage(response.getPoster_path());

        //Over View Container
        if (!AppUiUtils.isStringEmpty(response.getOverview())) {
            tvDescription.setText(response.getOverview());
            tvDescription.setVisibility(View.VISIBLE);
        }

        int no_seasons = response.getNumber_of_seasons();
        String seasonString = String.format(getActivity().getResources().getString(R.string
                .tv_season_title), String.valueOf(no_seasons));
        tvSeasons.setText(seasonString);
        tvSeasons.setVisibility(View.VISIBLE);

        int no_episodes = response.getNumber_of_episodes();
        String episodeString = String.format(getActivity().getResources().getString(R.string
                .tv_episode_title), String.valueOf(no_episodes));
        tvEpisodes.setText(episodeString);
        tvEpisodes.setVisibility(View.VISIBLE);

        if (!AppUiUtils.isStringEmpty(response.getHomepage())) {
            tvExternalUrl.setText(response.getHomepage());
            tvExternalUrl.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Load the Image Poster of the Movie
     *
     * @param url -- Url passed
     */
    private void loadImage(String url) {
        String image_pref_json = TheMovieDbAppController.getAppInstance().appSharedPreferences.getMovieImageConfigData();

        Gson gson = new Gson();
        Type type = new TypeToken<TheMovieDbImagesConfig>() {
        }.getType();

        TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
        String image_url = imagesConfig.getBase_url();
        String image_url_config =
                imagesConfig.getPoster_sizes().get(TheMovieDbConstants.INDEX_POSTER_SIZE);
        String IMAGE_BASE_URL = image_url.concat(image_url_config);

        ImageLoaderUtils.loadImageWithPlaceHolder(getActivity(), tvPoster, ImageLoaderUtils
                .buildImageUrl(IMAGE_BASE_URL, url), R.drawable.image_error_placeholder);
    }
    // -- Call Backs from various Interfaces .. //

    @Override
    public void onRefreshClicked() {
        initializePresenter();
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            tvProgressBar.setVisibility(View.VISIBLE);
        } else {
            tvProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void tvShowResponse(TvShowDetailResponse response) {
        processResponse(response);
        tvContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void tvShowError(UserAPIErrorType errorType) {
        genericErrorBuilder.setUserAPIError(errorType);
    }
}
