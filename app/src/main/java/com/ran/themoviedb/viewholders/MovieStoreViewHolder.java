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
import com.ran.themoviedb.TheMovieDbAppController;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.listeners.StoreUpdateViewHolder;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.MovieStoreResults;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * View Holder for the Movie Store Recycler View
 */
public class MovieStoreViewHolder extends RecyclerView.ViewHolder
    implements StoreUpdateViewHolder<MovieStoreResults> {

  private final String TAG = MovieStoreViewHolder.class.getSimpleName();
  private final Context activityContext;
  private TextView movieNameView;
  private TextView movieRating;
  private TextView movieYear;
  private FloatingActionButton movieShare;
  private ImageView movieImage;
  private View view;
  private StoreClickListener storeClickListener;
  private LinearLayout movieItemContainer;
  private String DATE_FORMAT = "yyyy-MM-dd";

  public MovieStoreViewHolder(View itemView, StoreClickListener storeClickListener, Context
      context) {
    super(itemView);
    this.activityContext = context;
    this.view = itemView;
    this.storeClickListener = storeClickListener;
    movieImage = view.findViewById(R.id.recycler_item_image);
    movieRating = view.findViewById(R.id.recycler_item_rating);
    movieYear = view.findViewById(R.id.recycler_item_year);
    movieNameView = view.findViewById(R.id.recycler_item_name);
    movieShare = view.findViewById(R.id.recycler_item_share);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      // get rid of margins since shadow area is now the margin
      ViewGroup.MarginLayoutParams p =
          (ViewGroup.MarginLayoutParams) movieShare.getLayoutParams();
      p.setMargins(0, 0, AppUiUtils.dpToPx(context, 8), 0);
      movieShare.setLayoutParams(p);
    }
    movieItemContainer = view.findViewById(R.id.recycler_item_container);
  }

  @Override
  public void updateViewItem(Context context, final MovieStoreResults item) {

    /**
     * a) Name
     * b) Rating
     * c) Year
     * d) Click for More details
     * e) Share Click
     * f) Image for the movie
     */
    final String movieName;
    if (!AppUiUtils.isStringEmpty(item.getTitle())) {
      movieName = item.getTitle();
    } else {
      movieName = item.getOriginal_title();
    }
    movieNameView.setText(movieName);
    movieRating.setText(String.valueOf(item.getVote_average()));
    movieRating.setVisibility(View.VISIBLE);

    if (AppUiUtils.isStringEmpty(item.getRelease_date())) {
      movieYear.setVisibility(View.GONE);
    } else {
      SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
      try {
        Date date = dateFormat.parse(item.getRelease_date());
        movieYear.setText(dateFormat.format(date));
      } catch (ParseException e) {
        movieYear.setVisibility(View.GONE);
        Log.d(TAG, "Date Format Exception  : " + e.toString());
      }
    }
    movieItemContainer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemClick(item.getId(), movieName, DisplayStoreType.MOVIE_STORE);
      }
    });

    movieShare.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemShare(item.getId(), movieName, DisplayStoreType.MOVIE_STORE);
      }
    });

    loadImage(item);
  }

  /**
   * Helper Method to Load the Image in the View ..
   */
  private void loadImage(MovieStoreResults item) {
    String image_pref_json =
            TheMovieDbAppController.getAppInstance().appSharedPreferences.getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config =
        imagesConfig.getPoster_sizes().get(TheMovieDbConstants.INDEX_POSTER_SIZE);
    String IMAGE_BASE_URL = image_url.concat(image_url_config);

    ImageLoaderUtils.loadImageWithPlaceHolder(activityContext, movieImage,
        ImageLoaderUtils.buildImageUrl(IMAGE_BASE_URL, item.getPoster_path()),
        R.drawable.image_error_placeholder);
  }
}
