package com.ran.themoviedb.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.listeners.StoreClickListener;

/**
 * Created by ranjith.suda on 1/3/2016.
 */
public class StoreViewHolders {

  private static final String TAG = StoreViewHolders.class.getSimpleName();

  public static RecyclerView.ViewHolder getStoreViewHolder(Context context, ViewGroup parent,
                                                           DisplayStoreType displayStoreType,
                                                           StoreClickListener storeClickListener) {
    switch (displayStoreType) {
      case MOVIE_STORE:
        return new MovieStoreViewHolder(createView(parent, displayStoreType), storeClickListener,
            context);
      case TV_STORE:
        return new TVStoreViewHolder(createView(parent, displayStoreType), storeClickListener,
            context);
      case PERSON_STORE:
        return new PeopleStoreViewHolder(createView(parent, displayStoreType), storeClickListener,
            context);

      default:
        return null;
    }
  }

  private static View createView(ViewGroup parent, DisplayStoreType storeType) {

    switch (storeType) {
      case MOVIE_STORE:
        return LayoutInflater.from(parent.getContext()).inflate(R.layout
            .recycler_view_grid_store_item, parent, false);
      case TV_STORE:
        return LayoutInflater.from(parent.getContext()).inflate(R.layout
            .recycler_view_grid_store_item, parent, false);
      case PERSON_STORE:
        return LayoutInflater.from(parent.getContext()).inflate(R.layout
            .recycler_view_linear_store_item, parent, false);
      default:
        return null;
    }
  }
}
