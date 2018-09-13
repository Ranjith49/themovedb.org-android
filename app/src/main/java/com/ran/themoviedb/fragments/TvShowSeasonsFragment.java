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

import com.ran.themoviedb.R;
import com.ran.themoviedb.adapters.TvShowSeasonsRecyclerAdapter;
import com.ran.themoviedb.customviews.GenericErrorBuilder;
import com.ran.themoviedb.entities.GenericUIErrorLayoutType;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.TvShowSeasons;
import com.ran.themoviedb.model.server.entities.UserAPIErrorType;
import com.ran.themoviedb.model.server.response.TvShowDetailResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class TvShowSeasonsFragment extends Fragment
        implements GenericErrorBuilder.Handler, TvShowDetailView {

    private RecyclerView tvRecyclerView;
    private LinearLayout tvErrorLayout;
    private ProgressBar tvProgressBar;
    private View view;
    private GenericErrorBuilder genericErrorBuilder;
    private TvShowDetailPresenter presenter;
    private int tvShowId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tv_seasons, container, false);
        tvShowId = getArguments().getInt(TheMovieDbConstants.TV_SHOW_ID_KEY);
        initView();
        initializePresenter();
        return view;
    }

    private void initView() {
        tvRecyclerView = (RecyclerView) view.findViewById(R.id.tv_seasons_recycler);
        tvErrorLayout = (LinearLayout) view.findViewById(R.id.genre_error_layout_container);
        tvProgressBar = (ProgressBar) view.findViewById(R.id.tv_description_progress);
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

    private void processResponse(ArrayList<TvShowSeasons> tvShowSeasons) {
        //Sort for the Order of the Seasons..
        Collections.sort(tvShowSeasons, new Comparator<TvShowSeasons>() {
            @Override
            public int compare(TvShowSeasons lhs, TvShowSeasons rhs) {
                return lhs.getSeason_number() - rhs.getSeason_number();
            }
        });
        TvShowSeasonsRecyclerAdapter adapter = new TvShowSeasonsRecyclerAdapter(tvShowSeasons,
                getActivity());
        tvRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvRecyclerView.setAdapter(adapter);
        tvRecyclerView.setVisibility(View.VISIBLE);
    }

    //Call Backs from Different Interface Impl .. //

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
        if (response.getSeasons() != null && response.getSeasons().size() > 0) {
            processResponse(response.getSeasons());
        } else {
            genericErrorBuilder.setUserAPIError(UserAPIErrorType.NOCONTENT_ERROR);
        }
    }

    @Override
    public void tvShowError(UserAPIErrorType errorType) {
        genericErrorBuilder.setUserAPIError(errorType);
    }
}
