package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.TvShowStoreResults;
import com.ran.themoviedb.model.server.response.TvShowSimilarDetailsResponse;
import com.ran.themoviedb.presenters.TvShowSimilarDataPresenter;
import com.ran.themoviedb.view_pres_med.TvShowSimilarView;
import com.ran.themoviedb.viewholders.StoreViewHolders;
import com.ran.themoviedb.viewholders.TVStoreViewHolder;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * Recycler View Adapter responsible for showing the Movie Store {@Link TvShowSimilarDetailsResponse}
 */
public class TvShowSimilarRecyclerAdapter
        extends CustomRecyclerView.Adapter<TvShowSimilarDetailsResponse> {


    private ArrayList<TvShowStoreResults> tvShowStoreResults = new ArrayList<>();
    private final TvShowSimilarView tvShowSimilarView;
    private final int INVALID_NEXT_PAGE_INDEX = -1;
    private TvShowSimilarDataPresenter tvShowSimilarDataPresenter;
    private Context context;
    private StoreClickListener storeClickListener;
    private final int tvShowId;


    public TvShowSimilarRecyclerAdapter(Context context, TvShowSimilarView tvShowSimilarView,
                                        int firstPath,
                                        StoreClickListener storeClickListener, int tvShowId) {
        super(context, firstPath);
        this.context = context;
        this.tvShowSimilarView = tvShowSimilarView;
        this.tvShowId = tvShowId;
        this.storeClickListener = storeClickListener;
    }


    public void addTvShowsResultsData(ArrayList<TvShowStoreResults> storeResults, int currentPage,
                                      int totalPages) {

        if (currentPage < totalPages) {
            setNextPageIndex(++currentPage);
        } else {
            setNextPageIndex(INVALID_NEXT_PAGE_INDEX);
        }
        tvShowStoreResults.addAll(storeResults);
        notifyDataSetChanged();
    }

    public void onDestroyView() {
        //Make any final Calls to be stopped here ..
        if (tvShowSimilarDataPresenter != null) {
            tvShowSimilarDataPresenter.stop();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return StoreViewHolders.getStoreViewHolder(context, parent, DisplayStoreType.TV_STORE,
                storeClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TvShowStoreResults tvShowStoreResultsItem = tvShowStoreResults.get(position);
        TVStoreViewHolder tvStoreViewHolder = (TVStoreViewHolder) holder;
        tvStoreViewHolder.updateViewItem(context, tvShowStoreResultsItem);
    }

    @Override
    public int getItemCount() {
        return tvShowStoreResults.size();
    }

    @Override
    public void loadFirstPageIndex(int firstPageIndex) {
        tvShowStoreResults.clear();
        notifyDataSetChanged();

        //Start the Presenter , for First Page
        tvShowSimilarDataPresenter = new TvShowSimilarDataPresenter(context, tvShowSimilarView, firstPageIndex, tvShowId);
        tvShowSimilarDataPresenter.start();
    }

    @Override
    public void loadNextPageIndex(int nextPageIndex) {

        //Start the Presenter for the Next pages ..
        tvShowSimilarDataPresenter = new TvShowSimilarDataPresenter(context, tvShowSimilarView, nextPageIndex, tvShowId);
        tvShowSimilarDataPresenter.start();
    }
}
