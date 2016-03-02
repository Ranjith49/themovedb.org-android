package com.ran.themoviedb.viewholders;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.R;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.listeners.StoreClickListener;
import com.ran.themoviedb.listeners.StoreUpdateViewHolder;
import com.ran.themoviedb.model.server.entities.PeopleStoreKnownFor;
import com.ran.themoviedb.model.server.entities.PeopleStoreResults;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/3/2016.
 * <p/>
 * View Holder for the People Store Recycler View
 */
public class PeopleStoreViewHolder extends RecyclerView.ViewHolder implements
    StoreUpdateViewHolder<PeopleStoreResults> {

  private TextView peopleName;
  private ImageView peopleImage;
  private TextView peopleRating;
  private TextView peopleMoreInfo;
  private FloatingActionButton peopleShare;
  private final int INDEX_PROFILE_SIZE = 2; //Todo [ranjith ,do better logic]
  private final int INDEX_POSTER_KNOWN_FOR_SIZE = 0; //Todo [ranjith ,do better logic]
  private final int MAX_KNOWN_FOR = 3;
  DecimalFormat df = new DecimalFormat("####0.00");

  private final Context activityContext;
  private View view;
  private final StoreClickListener storeClickListener;

  //Known For Layouts ..
  private RelativeLayout known1_Layout;
  private ImageView known1_Image;
  private TextView known1_Rating;
  private TextView known1_Name;

  private RelativeLayout known2_Layout;
  private ImageView known2_Image;
  private TextView known2_Rating;
  private TextView known2_Name;

  private RelativeLayout known3_Layout;
  private ImageView known3_Image;
  private TextView known3_Rating;
  private TextView known3_Name;


  public PeopleStoreViewHolder(View itemView, StoreClickListener storeClickListener,
                               Context context) {
    super(itemView);
    this.activityContext = context;
    this.storeClickListener = storeClickListener;
    this.view = itemView;

    peopleImage = (ImageView) view.findViewById(R.id.recycler_item_image);
    peopleName = (TextView) view.findViewById(R.id.recycler_item_name);
    peopleRating = (TextView) view.findViewById(R.id.recycler_item_rating);
    peopleMoreInfo = (TextView) view.findViewById(R.id.recycler_item_more);
    peopleShare = (FloatingActionButton) view.findViewById(R.id.recycler_item_share);

    known1_Layout = (RelativeLayout) view.findViewById(R.id.recycler_knownFor_1_container);
    known1_Image = (ImageView) view.findViewById(R.id.recycler_knownFor_1);
    known1_Name = (TextView) view.findViewById(R.id.recycler_knownFor_1_name);
    known1_Rating = (TextView) view.findViewById(R.id.recycler_knownFor_1_rating);

    known2_Layout = (RelativeLayout) view.findViewById(R.id.recycler_knownFor_2_container);
    known2_Image = (ImageView) view.findViewById(R.id.recycler_knownFor_2);
    known2_Name = (TextView) view.findViewById(R.id.recycler_knownFor_2_name);
    known2_Rating = (TextView) view.findViewById(R.id.recycler_knownFor_2_rating);

    known3_Layout = (RelativeLayout) view.findViewById(R.id.recycler_knownFor_3_container);
    known3_Image = (ImageView) view.findViewById(R.id.recycler_knownFor_3);
    known3_Name = (TextView) view.findViewById(R.id.recycler_knownFor_3_name);
    known3_Rating = (TextView) view.findViewById(R.id.recycler_knownFor_3_rating);
  }


  @Override
  public void updateViewItem(Context context, final PeopleStoreResults item) {

    /**
     * a) update the Image
     * b) update the Name
     * c) update the Rating
     * d) Click the More Info and share..
     *
     * e) Last update the Rating container ..
     */
    peopleName.setText(item.getName());
    peopleRating.setText(String.valueOf(df.format(item.getPopularity())));
    peopleMoreInfo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemClick(item.getId(), item.getName(),
            DisplayStoreType.PERSON_STORE);
      }
    });
    peopleShare.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemShare(item.getId(), item.getName(),
            DisplayStoreType.PERSON_STORE);
      }
    });
    loadProfileImage(item);

    //Views for Known for Views ..
    loadKnownForViews(item.getKnown_for());
  }

  /**
   * Helper Method to load the Known for Views
   */

  private void loadKnownForViews(final ArrayList<PeopleStoreKnownFor> items) {
    known1_Layout.setVisibility(View.GONE);
    known2_Layout.setVisibility(View.GONE);
    known3_Layout.setVisibility(View.GONE);

    if (items == null) {
      return;
    }

    String image_pref_json =
        AppSharedPreferences.getInstance(view.getContext()).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = imagesConfig.getPoster_sizes().get(INDEX_POSTER_KNOWN_FOR_SIZE);
    String IMAGE_BASE_URL = image_url.concat(image_url_config);

    for (int i = 0; i < items.size() && i < MAX_KNOWN_FOR; i++) {
      final int item_id = items.get(i).getId();
      final String name;
      final int index = i;
      if (!AppUiUtils.isStringEmpty(items.get(i).getTitle())) {
        name = items.get(i).getTitle();
      } else {
        name = items.get(i).getOriginal_title();
      }
      switch (i) {
        case 0:
          known1_Name.setText(name);
          known1_Rating.setText(String.valueOf(items.get(i).getVote_average()));
          loadPosterMovie(IMAGE_BASE_URL, items.get(i).getPoster_path(), known1_Image);
          known1_Layout.setVisibility(View.VISIBLE);
          known1_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onKnownItemClick(item_id, name, items.get(index));
            }
          });
          break;
        case 1:
          known2_Name.setText(name);
          known2_Rating.setText(String.valueOf(items.get(i).getVote_average()));
          loadPosterMovie(IMAGE_BASE_URL, items.get(i).getPoster_path(), known2_Image);
          known2_Layout.setVisibility(View.VISIBLE);
          known2_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onKnownItemClick(item_id, name, items.get(index));
            }
          });
          break;
        case 2:
          known3_Name.setText(name);
          known3_Rating.setText(String.valueOf(items.get(i).getVote_average()));
          loadPosterMovie(IMAGE_BASE_URL, items.get(i).getPoster_path(), known3_Image);
          known3_Layout.setVisibility(View.VISIBLE);
          known3_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onKnownItemClick(item_id, name, items.get(index));
            }
          });
          break;
      }
    }
  }

  private void onKnownItemClick(int item_id, String name, PeopleStoreKnownFor peopleStoreKnownFor) {
    if (peopleStoreKnownFor.getMedia_type()
        .equalsIgnoreCase(DisplayStoreType.getStoreName(DisplayStoreType.MOVIE_STORE))) {
      storeClickListener.onStoreItemClick(item_id, name, DisplayStoreType.MOVIE_STORE);
    } else if (peopleStoreKnownFor.getMedia_type()
        .equalsIgnoreCase(DisplayStoreType.getStoreName(DisplayStoreType.TV_STORE))) {
      storeClickListener.onStoreItemClick(item_id, name, DisplayStoreType.TV_STORE);
    } else if (peopleStoreKnownFor.getMedia_type()
        .equalsIgnoreCase(DisplayStoreType.getStoreName(DisplayStoreType.PERSON_STORE))) {
      storeClickListener.onStoreItemClick(item_id, name, DisplayStoreType.PERSON_STORE);
    } else {
      Toast.makeText(activityContext, R.string.people_known_for_error, Toast.LENGTH_SHORT).show();
    }
  }

  private void loadPosterMovie(String baseUrl, String url, ImageView imageView) {
    ImageLoaderUtils.loadImageWithPlaceHolder(activityContext, imageView,
        ImageLoaderUtils.getImageUrl(baseUrl, url),
        R.drawable.image_error_placeholder);
  }

  /**
   * Helper Method to Load the Image in the View ..
   */
  private void loadProfileImage(PeopleStoreResults item) {
    String image_pref_json =
        AppSharedPreferences.getInstance(view.getContext()).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = imagesConfig.getProfile_sizes().get(INDEX_PROFILE_SIZE);
    String IMAGE_BASE_URL = image_url.concat(image_url_config);

    ImageLoaderUtils.loadImageWithPlaceHolder(activityContext, peopleImage,
        ImageLoaderUtils.getImageUrl(IMAGE_BASE_URL, item.getProfile_path()),
        R.drawable.image_error_placeholder);
  }
}

