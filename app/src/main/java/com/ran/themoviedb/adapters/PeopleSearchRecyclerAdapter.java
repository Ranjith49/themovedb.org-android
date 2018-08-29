package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.PeopleSearchResults;
import com.ran.themoviedb.model.server.response.PeopleSearchResponse;
import com.ran.themoviedb.presenters.PeopleSearchDataPresenter;
import com.ran.themoviedb.view_pres_med.PeopleSearchView;
import com.ran.themoviedb.viewholders.PeopleSearchViewHolder;
import com.ran.themoviedb.viewholders.SearchViewHolders;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class PeopleSearchRecyclerAdapter extends CustomRecyclerView.Adapter<PeopleSearchResponse> {

    private final PeopleSearchView peopleSearchView;
    private final String query;
    private final int INVALID_NEXT_PAGE_INDEX = -1;
    private ArrayList<PeopleSearchResults> peopleSearchResults = new ArrayList<>();
    private PeopleSearchDataPresenter peopleSearchDataPresenter;
    private Context context;
    private StoreClickListener storeClickListener;

    //Update the Current Page after we get from Server
    private int currentPage;
    private int totalPages;

    public PeopleSearchRecyclerAdapter(Context context, int firstPageIndex,
                                       String query, PeopleSearchView peopleSearchView,
                                       StoreClickListener storeClickListener) {
        super(context, firstPageIndex);
        this.context = context;
        this.query = query;
        this.peopleSearchView = peopleSearchView;
        this.storeClickListener = storeClickListener;
    }

    public void addPeopleSearchData(ArrayList<PeopleSearchResults> storeResults, int currentPage,
                                    int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        if (currentPage < totalPages) {
            setNextPageIndex(++currentPage);
        } else {
            setNextPageIndex(INVALID_NEXT_PAGE_INDEX);
        }
        peopleSearchResults.addAll(storeResults);
        notifyDataSetChanged();
    }

    public void onDestroyView() {
        //Make any final Calls to be stopped here ..
        if (peopleSearchDataPresenter != null) {
            peopleSearchDataPresenter.stop();
        }
    }

    @Override
    public void loadFirstPageIndex(int firstPageIndex) {
        peopleSearchResults.clear();
        notifyDataSetChanged();

        //Start the Presenter , for First Page
        peopleSearchDataPresenter = new PeopleSearchDataPresenter(firstPageIndex, query, peopleSearchView);
        peopleSearchDataPresenter.start();
    }

    @Override
    public void loadNextPageIndex(int nextPageIndex) {
        //Start the Presenter , for Next Page
        peopleSearchDataPresenter = new PeopleSearchDataPresenter(nextPageIndex, query, peopleSearchView);
        peopleSearchDataPresenter.start();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SearchViewHolders.getSearchViewHolder(context, parent, DisplayStoreType.PERSON_STORE,
                storeClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PeopleSearchResults peopleSearchResultsItem = peopleSearchResults.get(position);
        PeopleSearchViewHolder peopleSearchViewHolder = (PeopleSearchViewHolder) holder;
        peopleSearchViewHolder.updateViewItem(context, peopleSearchResultsItem);
    }

    @Override
    public int getItemCount() {
        return peopleSearchResults.size();
    }
}
