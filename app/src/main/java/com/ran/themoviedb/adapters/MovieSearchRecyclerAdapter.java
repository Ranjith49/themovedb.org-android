package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.server.entities.MovieSearchResults;
import com.ran.themoviedb.model.server.response.MovieSearchResponse;
import com.ran.themoviedb.model.utils.UniqueIdCreator;
import com.ran.themoviedb.presenters.MovieSearchDataPresenter;
import com.ran.themoviedb.view_pres_med.MovieSearchView;
import com.ran.themoviedb.viewholders.MovieSearchViewHolder;
import com.ran.themoviedb.viewholders.SearchViewHolders;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class MovieSearchRecyclerAdapter extends CustomRecyclerView.Adapter<MovieSearchResponse> {

  private ArrayList<MovieSearchResults> movieSearchResults = new ArrayList<>();
  private final MovieSearchView movieSearchView;
  private final String query;
  private final int INVALID_NEXT_PAGE_INDEX = -1;
  private MovieSearchDataPresenter movieSearchDataPresenter;
  private Context context;
  private StoreClickListener storeClickListener;

  //Update the Current Page after we get from Server
  private int currentPage;
  private int totalPages;

  public MovieSearchRecyclerAdapter(Context context, int firstPageIndex,
                                    String query, MovieSearchView movieSearchView,
                                    StoreClickListener storeClickListener) {
    super(context, firstPageIndex);
    this.context = context;
    this.query = query;
    this.movieSearchView = movieSearchView;
    this.storeClickListener = storeClickListener;
  }

  public void addMovieSearchResultsData(ArrayList<MovieSearchResults> storeResults, int currentPage,
                                        int totalPages) {
    this.currentPage = currentPage;
    this.totalPages = totalPages;
    if (currentPage < totalPages) {
      setNextPageIndex(++currentPage);
    } else {
      setNextPageIndex(INVALID_NEXT_PAGE_INDEX);
    }
    movieSearchResults.addAll(storeResults);
    notifyDataSetChanged();
  }

  public void onDestroyView() {
    //Make any final Calls to be stopped here ..
    if (movieSearchDataPresenter != null) {
      movieSearchDataPresenter.stop();
    }
  }

  @Override
  public void loadFirstPageIndex(int firstPageIndex) {
    movieSearchResults.clear();
    notifyDataSetChanged();

    //Start the Presenter , for First Page
    movieSearchDataPresenter = new MovieSearchDataPresenter(context, firstPageIndex,
        query, movieSearchView, UniqueIdCreator.getInstance().generateUniqueId());
    movieSearchDataPresenter.start();
  }

  @Override
  public void loadNextPageIndex(int nextPageIndex) {
    //Start the Presenter , for First Page
    movieSearchDataPresenter = new MovieSearchDataPresenter(context, nextPageIndex,
        query, movieSearchView, UniqueIdCreator.getInstance().generateUniqueId());
    movieSearchDataPresenter.start();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return SearchViewHolders.getSearchViewHolder(context, parent, DisplayStoreType.MOVIE_STORE,
        storeClickListener);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    MovieSearchResults movieSearchResultItem = movieSearchResults.get(position);
    MovieSearchViewHolder movieStoreViewHolder = (MovieSearchViewHolder) holder;
    movieStoreViewHolder.updateViewItem(context, movieSearchResultItem);
  }

  @Override
  public int getItemCount() {
    return movieSearchResults.size();
  }
}
