package com.ran.themoviedb.adapters;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;
import com.ran.themoviedb.model.server.entities.PeopleKnowForData;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ranjith.suda on 3/4/2016.
 * <p/>
 * Recycler Adapter for the PeopleKnow For Fragment
 */
public class PeopleKnowForRecyclerAdapter extends RecyclerView.Adapter {

  private final String DATE_FORMAT = "yyyy-MM-dd";
  private Context context;
  private ArrayList<PeopleKnowForData> peopleKnowForData;
  private StoreClickListener storeClickListener;

  public PeopleKnowForRecyclerAdapter(Context context, ArrayList<PeopleKnowForData>
      peopleKnowForData, StoreClickListener storeClickListener) {
    this.context = context;
    this.peopleKnowForData = peopleKnowForData;
    this.storeClickListener = storeClickListener;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new PeopleKnowForHolder(
        LayoutInflater.from(context).inflate(R.layout.recycler_view_grid_peopleknow_item, parent,
            false), context);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    PeopleKnowForHolder peopleKnowForHolder = (PeopleKnowForHolder) holder;
    //Load Image here..
    loadImage(peopleKnowForData.get(position), peopleKnowForHolder.itemImage);

    //TV and MOVIE [Name , type , year differential logic]
    final int itemId = peopleKnowForData.get(position).getId();
    final DisplayStoreType storeType = DisplayStoreType.getStoreType(
        peopleKnowForData.get(position).getMedia_type());
    final String itemName;
    final String itemYear;
    switch (storeType) {
      case MOVIE_STORE:
        if (!AppUiUtils.isStringEmpty(peopleKnowForData.get(position).getTitle())) {
          itemName = peopleKnowForData.get(position).getTitle();
        } else {
          itemName = peopleKnowForData.get(position).getOriginal_title();
        }
        if (AppUiUtils.isStringEmpty(peopleKnowForData.get(position).getRelease_date())) {
          itemYear = TheMovieDbConstants.EMPTY_STRING;
        } else {
          itemYear = peopleKnowForData.get(position).getRelease_date();
        }
        peopleKnowForHolder.nameItem.setText(itemName);
        peopleKnowForHolder.typeItem.setImageResource(R.mipmap.ic_action);
        peopleKnowForHolder.typeItem.setVisibility(View.VISIBLE);
        break;
      case TV_STORE:
        if (!AppUiUtils.isStringEmpty(peopleKnowForData.get(position).getName())) {
          itemName = peopleKnowForData.get(position).getName();
        } else {
          itemName = peopleKnowForData.get(position).getOriginal_name();
        }
        if (AppUiUtils.isStringEmpty(peopleKnowForData.get(position).getFirst_air_date())) {
          itemYear = TheMovieDbConstants.EMPTY_STRING;
        } else {
          itemYear = peopleKnowForData.get(position).getFirst_air_date();
        }
        peopleKnowForHolder.nameItem.setText(itemName);
        peopleKnowForHolder.typeItem.setImageResource(R.mipmap.ic_video);
        peopleKnowForHolder.typeItem.setVisibility(View.VISIBLE);
        break;
      default:
        itemName = TheMovieDbConstants.EMPTY_STRING;
        itemYear = TheMovieDbConstants.EMPTY_STRING;
        peopleKnowForHolder.nameItem.setText(itemName);
        peopleKnowForHolder.typeItem.setVisibility(View.GONE);
        break;
    }

    //Try to Parse year , if OK [set and Visible] or else [GONE]
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    try {
      Date date = dateFormat.parse(itemYear);
      peopleKnowForHolder.yearItem.setText(dateFormat.format(date));
      peopleKnowForHolder.yearItem.setVisibility(View.VISIBLE);
    } catch (ParseException e) {
      peopleKnowForHolder.yearItem.setVisibility(View.GONE);
      Log.d(peopleKnowForHolder.TAG, "Date Format Exception  : " + e.toString());
    }

    //Click listeners ..
    peopleKnowForHolder.itemContainer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemClick(itemId, itemName, storeType);
      }
    });

    peopleKnowForHolder.itemShare.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        storeClickListener.onStoreItemShare(itemId, itemName, storeType);
      }
    });
  }

  /**
   * Helper Method to Load the Image in the View ..
   */
  private void loadImage(PeopleKnowForData item, ImageView imageView) {
    String image_pref_json = TheMovieDbAppController.getAppInstance().appSharedPreferences.getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config =
        imagesConfig.getPoster_sizes().get(TheMovieDbConstants.INDEX_POSTER_SIZE);
    String IMAGE_BASE_URL = image_url.concat(image_url_config);

    ImageLoaderUtils.loadImageWithPlaceHolder(context, imageView,
        ImageLoaderUtils.buildImageUrl(IMAGE_BASE_URL, item.getPoster_path()),
        R.drawable.image_error_placeholder);
  }

  @Override
  public int getItemCount() {
    return peopleKnowForData.size();
  }

  /**
   * Inner People Know For Holder View ..
   */
  class PeopleKnowForHolder extends RecyclerView.ViewHolder {
    private final String TAG = PeopleKnowForHolder.class.getSimpleName();
    private TextView nameItem;
    private TextView yearItem;
    private ImageView typeItem;
    private FloatingActionButton itemShare;
    private ImageView itemImage;
    private View view;
    private LinearLayout itemContainer;

    public PeopleKnowForHolder(View itemView, Context context) {
      super(itemView);
      this.view = itemView;
      itemImage = view.findViewById(R.id.recycler_item_image);
      yearItem = view.findViewById(R.id.recycler_item_year);
      nameItem = view.findViewById(R.id.recycler_item_name);
      typeItem = view.findViewById(R.id.recycler_item_type);
      itemShare = view.findViewById(R.id.recycler_item_share);
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        // get rid of margins since shadow area is now the margin
        ViewGroup.MarginLayoutParams p =
            (ViewGroup.MarginLayoutParams) itemShare.getLayoutParams();
        p.setMargins(0, 0, AppUiUtils.dpToPx(context, 8), 0);
        itemShare.setLayoutParams(p);
      }
      itemContainer = view.findViewById(R.id.recycler_item_container);
    }
  }
}
