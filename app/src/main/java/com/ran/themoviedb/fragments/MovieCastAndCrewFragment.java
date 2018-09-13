package com.ran.themoviedb.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.adapters.MovieGenericCastCrewAdapter;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.entities.MovieCastCrewType;
import com.ran.themoviedb.listeners.CastCrewListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.CastCrewDetailResponse;
import com.ran.themoviedb.presenters.MovieCastCrewPresenter;
import com.ran.themoviedb.utils.Navigator;
import com.ran.themoviedb.view_pres_med.MovieCastCrewView;

import java.lang.reflect.Type;

/**
 * Created by ranjith.suda on 1/12/2016.
 */
public class MovieCastAndCrewFragment extends Fragment implements GenericErrorBuilder.Handler,
        MovieCastCrewView, CastCrewListener {


    private final int CAST_CREW_PROFILE_INDEX = 1; //Todo [ranjith ,do better logic]
    private View view;
    private LinearLayout cast_crew_container;
    private LinearLayout cast_container;
    private LinearLayout crew_container;
    private RecyclerView cast_recycler;
    private RecyclerView crew_recycler;
    private ProgressBar progressBar;
    private GenericErrorBuilder genericErrorBuilder;
    private LinearLayout errorLayoutHolder;
    private int movieId;
    private MovieCastCrewPresenter castCrewPresenter;
    private MovieGenericCastCrewAdapter castAdapter;
    private MovieGenericCastCrewAdapter crewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_castcrew, container, false);
        movieId = getArguments().getInt(TheMovieDbConstants.MOVIE_ID_KEY);

        cast_crew_container = view.findViewById(R.id.movie_cast_crew_container);
        cast_container = view.findViewById(R.id.movie_cast_container);
        crew_container = view.findViewById(R.id.movie_crew_container);
        cast_recycler = view.findViewById(R.id.movie_cast_recycler);
        crew_recycler = view.findViewById(R.id.movie_crew_recycler);
        progressBar = view.findViewById(R.id.movie_cast_crew_error_screen_progress);
        errorLayoutHolder =
                view.findViewById(R.id.movie_cast_crew_error_layout_container);
        genericErrorBuilder = new GenericErrorBuilder(getActivity(), GenericUIErrorLayoutType
                .CENTER, errorLayoutHolder, this);

        initializePresenter();

        return view;
    }

    private void initializePresenter() {
        castCrewPresenter = new MovieCastCrewPresenter(getActivity(), this, movieId);
        castCrewPresenter.start();
    }

    @Override
    public void onDestroyView() {
        castCrewPresenter.stop();
        super.onDestroyView();
    }

    private String generateImageBaseUrl() {
        String image_pref_json = TheMovieDbAppController.getAppInstance().appSharedPreferences.getMovieImageConfigData();

        Gson gson = new Gson();
        Type type = new TypeToken<TheMovieDbImagesConfig>() {
        }.getType();

        TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
        String image_url = imagesConfig.getBase_url();
        String image_url_config = imagesConfig.getProfile_sizes().get(CAST_CREW_PROFILE_INDEX);

        return image_url.concat(image_url_config);
    }

    //--- Various Call Backs from different Interfaces --//
    @Override
    public void onPersonDetail(int id, String name) {
        Navigator.navigateToPeopleDetails(getActivity(), id);
    }

    @Override
    public void onRefreshClicked() {
        cast_crew_container.setVisibility(View.GONE);
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
    public void movieCastCrewData(CastCrewDetailResponse response) {
        boolean isCastDataAvailable = false;
        boolean isCrewDataAvailable = false;

        if (response.getCast() != null && response.getCast().size() > 0) {
            castAdapter =
                    new MovieGenericCastCrewAdapter(response.getCast(), null, MovieCastCrewType.CAST_TYPE,
                            getActivity(), generateImageBaseUrl(), this);
            cast_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                    .HORIZONTAL, false));
            cast_recycler.setAdapter(castAdapter);
            cast_container.setVisibility(View.VISIBLE);
            isCastDataAvailable = true;
        }

        if (response.getCrew() != null && response.getCrew().size() > 0) {
            crewAdapter = new MovieGenericCastCrewAdapter(null, response.getCrew(),
                    MovieCastCrewType.CREW_TYPE, getActivity(), generateImageBaseUrl(), this);
            crew_recycler.setLayoutManager(
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            crew_recycler.setAdapter(crewAdapter);
            crew_container.setVisibility(View.VISIBLE);
            isCrewDataAvailable = true;
        }

        //Total Response container here ..
        if (isCastDataAvailable || isCrewDataAvailable) {
            cast_crew_container.setVisibility(View.VISIBLE);
        } else {
            genericErrorBuilder.setUserAPIError(UserAPIErrorType.NOCONTENT_ERROR);
        }
    }

    @Override
    public void movieCastCrewAPIError(UserAPIErrorType errorType) {
        genericErrorBuilder.setUserAPIError(errorType);
    }
}
