package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.server.entities.TVShowStoreType;
import com.ran.themoviedb.model.server.entities.TvShowStoreResults;
import com.ran.themoviedb.model.server.response.TVShowStoreResponse;
import com.ran.themoviedb.model.utils.UniqueIdCreator;
import com.ran.themoviedb.presenters.TvStoreDataPresenter;
import com.ran.themoviedb.view_pres_med.TvStoreView;
import com.ran.themoviedb.viewholders.StoreViewHolders;
import com.ran.themoviedb.viewholders.TVStoreViewHolder;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 *
 * RecyclerView Adapter for showing the Tv Store Response {@link TVShowStoreResponse}
 */
public class TvStoreRecyclerAdapter extends CustomRecyclerView.Adapter<TVShowStoreResponse> {

  private ArrayList<TvShowStoreResults> tvStoreResults = new ArrayList<>();
  private final TvStoreView tvStoreView;
  private final TVShowStoreType tvStoreType;
  private final int INVALID_NEXT_PAGE_INDEX = -1;
  private TvStoreDataPresenter tvStoreDataPresenter;
  private Context context;
  private StoreClickListener storeClickListener;

  //Update the Current Page after we get from Server
  private int currentPage;
  private int totalPages;

  public TvStoreRecyclerAdapter(Context context, int firstPageIndex,
                                TVShowStoreType tvShowStoreType, TvStoreView tvStoreView,
                                StoreClickListener storeClickListener) {
    super(context, firstPageIndex);
    this.context = context;
    this.tvStoreType = tvShowStoreType;
    this.tvStoreView = tvStoreView;
    this.storeClickListener = storeClickListener;
  }

  public void addTvStoreResultsData(ArrayList<TvShowStoreResults> storeResults, int currentPage,
                                    int totalPages) {
    this.currentPage = currentPage;
    this.totalPages = totalPages;
    if (currentPage < totalPages) {
      setNextPageIndex(++currentPage);
    } else {
      setNextPageIndex(INVALID_NEXT_PAGE_INDEX);
    }
    tvStoreResults.addAll(storeResults);
    notifyDataSetChanged();
  }

  public void onDestroyView() {
    //Make any final Calls to be stopped here ..
    if (tvStoreDataPresenter != null) {
      tvStoreDataPresenter.stop();
    }
  }

  @Override
  public void loadFirstPageIndex(int firstPageIndex) {
    tvStoreResults.clear();
    notifyDataSetChanged();

    //Start the Presenter , for First Page
    tvStoreDataPresenter = new TvStoreDataPresenter(context, tvStoreType, firstPageIndex,
        tvStoreView, UniqueIdCreator.getInstance().generateUniqueId());
    tvStoreDataPresenter.start();
  }

  @Override
  public void loadNextPageIndex(int nextPageIndex) {
    //Start the Presenter for the Next pages ..
    tvStoreDataPresenter = new TvStoreDataPresenter(context, tvStoreType, nextPageIndex,
        tvStoreView, UniqueIdCreator.getInstance().generateUniqueId());
    tvStoreDataPresenter.start();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return StoreViewHolders.getStoreViewHolder(context, parent, DisplayStoreType.TV_STORE,
        storeClickListener);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    TvShowStoreResults tvShowStoreResultItem = tvStoreResults.get(position);
    TVStoreViewHolder tvStoreViewHolder = (TVStoreViewHolder) holder;
    tvStoreViewHolder.updateViewItem(context, tvShowStoreResultItem);
  }

  @Override
  public int getItemCount() {
    return tvStoreResults.size();
  }
}
