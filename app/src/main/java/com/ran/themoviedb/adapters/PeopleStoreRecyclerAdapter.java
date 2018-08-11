package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ran.themoviedb.customviews.CustomRecyclerView;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.model.server.entities.PeopleStoreResults;
import com.ran.themoviedb.model.server.entities.PeopleStoreType;
import com.ran.themoviedb.model.server.response.PeopleStoreResponse;
import com.ran.themoviedb.model.utils.UniqueIdCreator;
import com.ran.themoviedb.presenters.PeopleStoreDataPresenter;
import com.ran.themoviedb.view_pres_med.PeopleStoreView;
import com.ran.themoviedb.viewholders.PeopleStoreViewHolder;
import com.ran.themoviedb.viewholders.StoreViewHolders;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 * <p>
 * Recycler View Adapter responsible for showing the People Store {@link PeopleStoreResponse}
 */
public class PeopleStoreRecyclerAdapter extends CustomRecyclerView.Adapter<PeopleStoreResponse> {

    private ArrayList<PeopleStoreResults> peopleStoreResults = new ArrayList<>();
    private final PeopleStoreView peopleStoreView;
    private final PeopleStoreType peopleStoreType;
    private final int INVALID_NEXT_PAGE_INDEX = -1;
    private PeopleStoreDataPresenter peopleStoreDataPresenter;
    private Context context;
    private StoreClickListener storeClickListener;

    //Update the Current Page after we get from Server
    private int currentPage;
    private int totalPages;

    public PeopleStoreRecyclerAdapter(Context context, int firstPageIndex,
                                      PeopleStoreType peopleStoreType,
                                      PeopleStoreView peopleStoreView,
                                      StoreClickListener storeClickListener) {
        super(context, firstPageIndex);
        this.context = context;
        this.peopleStoreType = peopleStoreType;
        this.peopleStoreView = peopleStoreView;
        this.storeClickListener = storeClickListener;
    }

    public void addPeopleStoreData(ArrayList<PeopleStoreResults> storeResults, int currentPage,
                                   int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        if (currentPage < totalPages) {
            setNextPageIndex(++currentPage);
        } else {
            setNextPageIndex(INVALID_NEXT_PAGE_INDEX);
        }
        peopleStoreResults.addAll(storeResults);
        notifyDataSetChanged();
    }

    public void onDestroyView() {
        //Make any final Calls to be stopped here ..
        if (peopleStoreDataPresenter != null) {
            peopleStoreDataPresenter.stop();
        }
    }

    @Override
    public void loadFirstPageIndex(int firstPageIndex) {
        peopleStoreResults.clear();
        notifyDataSetChanged();

        //Start the Presenter , for First Page
        peopleStoreDataPresenter =
                new PeopleStoreDataPresenter(context, peopleStoreType, firstPageIndex, peopleStoreView);
        peopleStoreDataPresenter.start();
    }

    @Override
    public void loadNextPageIndex(int nextPageIndex) {
        //Start the Presenter for the Next pages ..
        peopleStoreDataPresenter = new PeopleStoreDataPresenter(context, peopleStoreType, nextPageIndex, peopleStoreView);
        peopleStoreDataPresenter.start();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return StoreViewHolders.getStoreViewHolder(context, parent, DisplayStoreType.PERSON_STORE,
                storeClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PeopleStoreResults peopleStoreResultItem = peopleStoreResults.get(position);
        PeopleStoreViewHolder peopleStoreViewHolder = (PeopleStoreViewHolder) holder;
        peopleStoreViewHolder.updateViewItem(context, peopleStoreResultItem);
    }

    @Override
    public int getItemCount() {
        return peopleStoreResults.size();
    }
}
