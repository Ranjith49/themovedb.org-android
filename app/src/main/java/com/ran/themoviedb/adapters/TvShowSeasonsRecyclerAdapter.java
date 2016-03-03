package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.model.server.entities.TvShowSeasons;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 2/29/2016.
 */
public class TvShowSeasonsRecyclerAdapter extends RecyclerView.Adapter {

  private final Context context;
  private final ArrayList<TvShowSeasons> tvShowSeasons;
  private final int INDEX_POSTER_SIZE = 2; //Todo [ranjith ,do better logic]

  public TvShowSeasonsRecyclerAdapter(ArrayList<TvShowSeasons> list, Context context) {
    this.tvShowSeasons = list;
    this.context = context;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TvShowCustomViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.tv_show_season_recycler_view, parent, false));
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    TvShowCustomViewHolder tvShowCustomViewHolder = (TvShowCustomViewHolder) holder;

    //Load the Thumbnail
    loadImage(tvShowCustomViewHolder.tvSeasonPoster, tvShowSeasons.get(position).getPoster_path());

    //Load the Air Date
    if (!AppUiUtils.isStringEmpty(tvShowSeasons.get(position).getAir_date())) {
      tvShowCustomViewHolder.tvSeasonYear.setText(tvShowSeasons.get(position).getAir_date());
      tvShowCustomViewHolder.tvSeasonYear.setVisibility(View.VISIBLE);
    } else {
      tvShowCustomViewHolder.tvSeasonYear.setVisibility(View.GONE);
    }

    //Season Title ..
    String seasonTitle = context.getString(R.string.tv_season_item_title) + TheMovieDbConstants
        .SPACE_STRING + String.valueOf(position);
    tvShowCustomViewHolder.tvSeasonName.setText(seasonTitle);
    tvShowCustomViewHolder.tvSeasonName.setVisibility(View.VISIBLE);

    //Episodes Information ..
    int no_seasons = tvShowSeasons.get(position).getEpisode_count();
    String seasonString = String.format(context.getResources().getString(R.string
        .tv_episode_title), String.valueOf(no_seasons));
    tvShowCustomViewHolder.tvEpisodes.setText(seasonString);
    tvShowCustomViewHolder.tvEpisodes.setVisibility(View.VISIBLE);
  }

  @Override
  public int getItemCount() {
    return tvShowSeasons.size();
  }

  private static class TvShowCustomViewHolder extends RecyclerView.ViewHolder {
    private TextView tvSeasonName;
    private TextView tvSeasonYear;
    private TextView tvEpisodes;
    private ImageView tvSeasonPoster;

    public TvShowCustomViewHolder(View itemView) {
      super(itemView);
      tvSeasonPoster = (ImageView) itemView.findViewById(R.id.tvSeason_poster);
      tvEpisodes = (TextView) itemView.findViewById(R.id.tv_show_episodes);
      tvSeasonName = (TextView) itemView.findViewById(R.id.tv_season_name);
      tvSeasonYear = (TextView) itemView.findViewById(R.id.tv_season_date);
    }
  }

  /**
   * Load the Image Poster of the Movie
   *
   * @param url -- Url passed
   */
  private void loadImage(ImageView view, String url) {
    String image_pref_json =
        AppSharedPreferences.getInstance(view.getContext()).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = imagesConfig.getPoster_sizes().get(INDEX_POSTER_SIZE);
    String IMAGE_BASE_URL = image_url.concat(image_url_config);

    ImageLoaderUtils.loadImageWithPlaceHolder(context, view, ImageLoaderUtils
        .buildImageUrl(IMAGE_BASE_URL, url), R.drawable.image_error_placeholder);
  }
}
