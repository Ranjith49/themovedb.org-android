package com.ran.themoviedb.viewholders;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.listeners.StoreUpdateViewHolder;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.TvShowStoreResults;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * View Holder for the Tv Store Recycler View
 */
public class TVStoreViewHolder extends RecyclerView.ViewHolder
    implements StoreUpdateViewHolder<TvShowStoreResults> {
  private TextView tvNameView;
  private TextView tvRating;
  private TextView tvYear;
  private FloatingActionButton tvShare;
  private ImageView tvImage;
  private View view;
  private StoreClickListener storeClickListener;
  private LinearLayout tvItemContainer;
  private String DATE_FORMAT = "yyyy-MM-dd";
  private final String TAG = MovieStoreViewHolder.class.getSimpleName();
  private final int INDEX_POSTER_SIZE = 2; //Todo [ranjith ,do better logic]
  private final Context activityContext;

  public TVStoreViewHolder(View itemView, StoreClickListener storeClickListener, Context
      context) {
    super(itemView);
    this.activityContext = context;
    this.view = itemView;
    this.storeClickListener = storeClickListener;
    tvImage = (ImageView) view.findViewById(R.id.recycler_item_image);
    tvRating = (TextView) view.findViewById(R.id.recycler_item_rating);
    tvYear = (TextView) view.findViewById(R.id.recycler_item_year);
    tvNameView = (TextView) view.findViewById(R.id.recycler_item_name);
    tvShare = (FloatingActionButton) view.findViewById(R.id.recycler_item_share);
    tvItemContainer = (LinearLayout) view.findViewById(R.id.recycler_item_container);
  }

  @Override
  public void updateViewItem(Context context, final TvShowStoreResults item) {

    /**
     * a) Name
     * b) Rating
     * c) Year
     * d) Click for More details
     * e) Share Click
     * f) Image for the movie
     */
    final String tvName;
    if (!AppUiUtils.isStringEmpty(item.getName())) {
      tvName = item.getName();
    } else {
      tvName = item.getOriginal_name();
    }
    tvNameView.setText(tvName);
    tvRating.setText(String.valueOf(item.getVote_average()));

    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    try {
      Date date = dateFormat.parse(item.getFirst_air_date());
      tvYear.setText(dateFormat.format(date));
    } catch (ParseException e) {
      tvYear.setVisibility(View.GONE);
      Log.d(TAG, "Date Format Exception  : " + e.toString());
    }
    tvItemContainer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemClick(item.getId(), tvName, DisplayStoreType.TV_STORE);
      }
    });

    tvShare.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemShare(item.getId(), tvName, DisplayStoreType.TV_STORE);
      }
    });

    loadImage(item);
  }

  /**
   * Helper Method to Load the Image in the View ..
   */
  private void loadImage(TvShowStoreResults item) {
    String image_pref_json =
        AppSharedPreferences.getInstance(view.getContext()).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = imagesConfig.getPoster_sizes().get(INDEX_POSTER_SIZE);
    String IMAGE_BASE_URL = image_url.concat(image_url_config);

    ImageLoaderUtils.loadImageWithPlaceHolder(activityContext, tvImage,
        ImageLoaderUtils.getImageUrl(IMAGE_BASE_URL, item.getPoster_path()),
        R.drawable.image_error_placeholder);
  }
}
