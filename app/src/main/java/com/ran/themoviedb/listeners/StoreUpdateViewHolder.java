package com.ran.themoviedb.listeners;

import android.content.Context;

/**
 * Created by ranjith.suda on 1/3/2016.
 */
public interface StoreUpdateViewHolder<T> {

  /**
   * Call back from Adapter to View Holder to update its Content
   *
   * @param context -- Context
   * @param item    -- item of Type T
   */
  void updateViewItem(Context context, T item);
}
