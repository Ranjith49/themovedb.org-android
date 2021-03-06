package com.ran.themoviedb.viewholders;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.listeners.StoreUpdateViewHolder;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.TvShowSearchResults;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ranjith.suda on 1/5/2016.
 */
public class TvSearchViewHolder extends RecyclerView.ViewHolder implements
    StoreUpdateViewHolder<TvShowSearchResults> {

  private TextView tvNameView;
  private TextView tvRating;
  private TextView tvYear;
  private FloatingActionButton tvShare;
  private ImageView tvImage;
  private View view;
  private StoreClickListener storeClickListener;
  private LinearLayout tvItemContainer;
  private String DATE_FORMAT = "yyyy-MM-dd";
  private final String TAG = TvSearchViewHolder.class.getSimpleName();
  private final Context activityContext;

  public TvSearchViewHolder(View itemView, StoreClickListener storeClickListener, Context context) {
    super(itemView);

    this.activityContext = context;
    this.view = itemView;
    this.storeClickListener = storeClickListener;
    tvImage = (ImageView) view.findViewById(R.id.recycler_item_image);
    tvRating = (TextView) view.findViewById(R.id.recycler_item_rating);
    tvYear = (TextView) view.findViewById(R.id.recycler_item_year);
    tvNameView = (TextView) view.findViewById(R.id.recycler_item_name);
    tvShare = (FloatingActionButton) view.findViewById(R.id.recycler_item_share);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      // get rid of margins since shadow area is now the margin
      ViewGroup.MarginLayoutParams p =
          (ViewGroup.MarginLayoutParams) tvShare.getLayoutParams();
      p.setMargins(0, 0, AppUiUtils.dpToPx(context, 8), 0);
      tvShare.setLayoutParams(p);
    }
    tvItemContainer = (LinearLayout) view.findViewById(R.id.recycler_search_container);
  }

  @Override
  public void updateViewItem(Context context, final TvShowSearchResults item) {
/**
 * a) Name
 * b) Rating
 * c) Year
 * d) Click for More details
 * e) Share Click
 * f) Image for the tv
 */
    tvNameView.setText(item.getName());
    tvRating.setText(String.valueOf(item.getVote_average()));
    tvRating.setVisibility(View.VISIBLE);

    if (AppUiUtils.isStringEmpty(item.getFirst_air_date())) {
      tvYear.setVisibility(View.GONE);
    } else {
      SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
      try {
        Date date = dateFormat.parse(item.getFirst_air_date());
        tvYear.setText(dateFormat.format(date));
      } catch (ParseException e) {
        tvYear.setVisibility(View.GONE);
        Log.d(TAG, "Date Format Exception  : " + e.toString());
      }
    }
    tvItemContainer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemClick(item.getId(), item.getName(),
            DisplayStoreType.TV_STORE);
      }
    });

    tvShare.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemShare(item.getId(), item.getName(),
            DisplayStoreType.TV_STORE);
      }
    });

    loadImage(item);
  }

  /**
   * Helper Method to Load the Image in the View ..
   */
  private void loadImage(TvShowSearchResults item) {
    String image_pref_json =
        AppSharedPreferences.getInstance(view.getContext()).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config =
        imagesConfig.getBackdrop_sizes().get(TheMovieDbConstants.INDEX_BANNER_SIZE);
    String IMAGE_BASE_URL = image_url.concat(image_url_config);

    ImageLoaderUtils.loadImageWithPlaceHolder(activityContext, tvImage,
        ImageLoaderUtils.buildImageUrl(IMAGE_BASE_URL, item.getBackdrop_path()),
        R.drawable.image_error_placeholder);
  }
}
