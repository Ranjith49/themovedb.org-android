package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.server.entities.TvShowSearchResults;
import com.ran.themoviedb.model.server.response.TvShowSearchResponse;
import com.ran.themoviedb.model.utils.UniqueIdCreator;
import com.ran.themoviedb.presenters.TvSearchDataPresenter;
import com.ran.themoviedb.view_pres_med.TvSearchView;
import com.ran.themoviedb.viewholders.SearchViewHolders;
import com.ran.themoviedb.viewholders.TvSearchViewHolder;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class TvSearchRecyclerAdapter extends CustomRecyclerView.Adapter<TvShowSearchResponse> {

  private ArrayList<TvShowSearchResults> tvShowSearchResults = new ArrayList<>();
  private final TvSearchView tvSearchView;
  private final String query;
  private final int INVALID_NEXT_PAGE_INDEX = -1;
  private TvSearchDataPresenter tvSearchDataPresenter;
  private Context context;
  private StoreClickListener storeClickListener;

  //Update the Current Page after we get from Server
  private int currentPage;
  private int totalPages;

  public TvSearchRecyclerAdapter(Context context, int firstPageIndex,
                                 String query, TvSearchView tvSearchView,
                                 StoreClickListener storeClickListener) {
    super(context, firstPageIndex);
    this.context = context;
    this.query = query;
    this.tvSearchView = tvSearchView;
    this.storeClickListener = storeClickListener;
  }

  public void addTvSearchResults(ArrayList<TvShowSearchResults> storeResults, int currentPage,
                                 int totalPages) {
    this.currentPage = currentPage;
    this.totalPages = totalPages;
    if (currentPage < totalPages) {
      setNextPageIndex(++currentPage);
    } else {
      setNextPageIndex(INVALID_NEXT_PAGE_INDEX);
    }
    tvShowSearchResults.addAll(storeResults);
    notifyDataSetChanged();
  }

  public void onDestroyView() {
    //Make any final Calls to be stopped here ..
    if (tvSearchDataPresenter != null) {
      tvSearchDataPresenter.stop();
    }
  }

  @Override
  public void loadFirstPageIndex(int firstPageIndex) {
    tvShowSearchResults.clear();
    notifyDataSetChanged();

    //Start the Presenter , for First Page
    tvSearchDataPresenter = new TvSearchDataPresenter(context, firstPageIndex,
        query, tvSearchView, UniqueIdCreator.getInstance().generateUniqueId());
    tvSearchDataPresenter.start();
  }

  @Override
  public void loadNextPageIndex(int nextPageIndex) {
    //Start the Presenter , for Next Page
    tvSearchDataPresenter = new TvSearchDataPresenter(context, nextPageIndex,
        query, tvSearchView, UniqueIdCreator.getInstance().generateUniqueId());
    tvSearchDataPresenter.start();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return SearchViewHolders.getSearchViewHolder(context, parent, DisplayStoreType.TV_STORE,
        storeClickListener);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    TvShowSearchResults tvShowSearchResultsItem = tvShowSearchResults.get(position);
    TvSearchViewHolder tvSearchViewHolder = (TvSearchViewHolder) holder;
    tvSearchViewHolder.updateViewItem(context, tvShowSearchResultsItem);
  }

  @Override
  public int getItemCount() {
    return tvShowSearchResults.size();
  }
}
