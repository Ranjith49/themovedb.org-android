package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.MovieStoreResults;
import com.ran.themoviedb.model.server.response.MovieStoreResponse;
import com.ran.themoviedb.presenters.MovieSimilarDataPresenter;
import com.ran.themoviedb.view_pres_med.MovieSimilarView;
import com.ran.themoviedb.viewholders.MovieStoreViewHolder;
import com.ran.themoviedb.viewholders.StoreViewHolders;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Recycler View Adapter responsible for showing the Movie Store {@Link MovieStoreResponse}
 */
public class MovieSimilarRecyclerAdapter extends CustomRecyclerView.Adapter<MovieStoreResponse> {


    private ArrayList<MovieStoreResults> movieStoreResults = new ArrayList<>();
    private final MovieSimilarView movieSimilarView;
    private final int INVALID_NEXT_PAGE_INDEX = -1;
    private MovieSimilarDataPresenter movieStoreDataPresenter;
    private Context context;
    private StoreClickListener storeClickListener;
    private final int movieId;


    public MovieSimilarRecyclerAdapter(Context context, MovieSimilarView movieSimilarView,
                                       int firstPath,
                                       StoreClickListener storeClickListener, int movieId) {
        super(context, firstPath);
        this.context = context;
        this.movieSimilarView = movieSimilarView;
        this.movieId = movieId;
        this.storeClickListener = storeClickListener;
    }


    public void addMovieResultsData(ArrayList<MovieStoreResults> storeResults, int currentPage,
                                    int totalPages) {

        if (currentPage < totalPages) {
            setNextPageIndex(++currentPage);
        } else {
            setNextPageIndex(INVALID_NEXT_PAGE_INDEX);
        }
        movieStoreResults.addAll(storeResults);
        notifyDataSetChanged();
    }

    public void onDestroyView() {
        //Make any final Calls to be stopped here ..
        if (movieStoreDataPresenter != null) {
            movieStoreDataPresenter.stop();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return StoreViewHolders.getStoreViewHolder(context, parent, DisplayStoreType.MOVIE_STORE,
                storeClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieStoreResults movieStoreResultItem = movieStoreResults.get(position);
        MovieStoreViewHolder movieStoreViewHolder = (MovieStoreViewHolder) holder;
        movieStoreViewHolder.updateViewItem(context, movieStoreResultItem);
    }

    @Override
    public int getItemCount() {
        return movieStoreResults.size();
    }

    @Override
    public void loadFirstPageIndex(int firstPageIndex) {
        movieStoreResults.clear();
        notifyDataSetChanged();

        //Start the Presenter , for First Page
        movieStoreDataPresenter = new MovieSimilarDataPresenter(context, movieSimilarView, firstPageIndex, movieId);
        movieStoreDataPresenter.start();
    }

    @Override
    public void loadNextPageIndex(int nextPageIndex) {

        //Start the Presenter for the Next pages ..
        movieStoreDataPresenter = new MovieSimilarDataPresenter(context, movieSimilarView, nextPageIndex, movieId);
        movieStoreDataPresenter.start();
    }
}
