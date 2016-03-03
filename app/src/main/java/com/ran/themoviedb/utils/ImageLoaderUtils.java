package com.ran.themoviedb.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.TheMovieDbImagesConfig;

import java.lang.reflect.Type;

/**
 * Created by ranjith.suda on 1/1/2016.
 * <p/>
 * Utils class related to Image Loading Stuff
 */
public class ImageLoaderUtils {

  private static final String TAG = ImageLoaderUtils.class.getSimpleName();

  /**
   * Return the URL with BaseURL + end Point , if valid or Empty String
   *
   * @param baseURl  -- base Url
   * @param endPoint -- end point
   * @return -- baseUrl + endPointUrl
   */
  public static String buildImageUrl(String baseURl, String endPoint) {
    Log.d(TAG, "Base url : " + baseURl + " end point : " + endPoint);
    if (baseURl != null && endPoint != null) {
      return baseURl.concat(endPoint);
    } else {
      return TheMovieDbConstants.EMPTY_STRING;
    }
  }

  /**
   * Utility Method to get the Original Image Base Url
   *
   * @param context   -- Context
   * @param imageType -- Image Type [Banner / poster]
   * @return -- Full Original Image Url
   */
  public static String generateOrgImageBaseUrl(Context context, String imageType) {
    String image_pref_json = AppSharedPreferences.getInstance(context).getMovieImageConfigData();

    Gson gson = new Gson();
    Type type = new TypeToken<TheMovieDbImagesConfig>() {
    }.getType();

    TheMovieDbImagesConfig imagesConfig = gson.fromJson(image_pref_json, type);
    String image_url = imagesConfig.getBase_url();
    String image_url_config = TheMovieDbConstants.EMPTY_STRING;
    if (imageType.equalsIgnoreCase(TheMovieDbConstants.IMAGE_BANNER_TYPE)) {
      image_url_config =
          imagesConfig.getBackdrop_sizes().get(imagesConfig.getBackdrop_sizes().size() - 1);
    } else {
      image_url_config =
          imagesConfig.getBackdrop_sizes().get(imagesConfig.getPoster_sizes().size() - 1);
    }
    return image_url.concat(image_url_config);
  }

  /**
   * Load an Image URL to the ImageView with Picasso
   *
   * @param context     - Context
   * @param imageView   -- ImageView
   * @param imageURL    -- Url of the image
   * @param errorHolder -- View to be shown till load happened
   */
  public static void loadImageWithPlaceHolder(Context context, ImageView imageView, String imageURL,
                                              int errorHolder) {
    if (!AppUiUtils.isStringEmpty(imageURL)) {
      Log.d(TAG, imageURL);
      Glide.with(context)
          .load(imageURL).asBitmap()
          .centerCrop()
          .animate(android.R.anim.fade_in) //Smooth Transition
          .error(errorHolder)
          .placeholder(errorHolder)
          .into(imageView);

    }
  }

  /**
   * Load Image with Resize Option
   *
   * @param context     -- Context
   * @param imageView   -- ImageView
   * @param imageURL    -- ImageUrl
   * @param errorHolder -- place Holder
   * @param dimens      -- width and height
   */
  public static void loadImageWithResize(Context context, ImageView imageView, String imageURL,
                                         int errorHolder, int[] dimens) {
    if (!AppUiUtils.isStringEmpty(imageURL)) {
      Log.d(TAG, imageURL);
      Glide.with(context)
          .load(imageURL).asBitmap()
          .override(dimens[0], dimens[1])
          .centerCrop()
          .animate(android.R.anim.fade_in) //Smooth Transition
          .error(errorHolder)
          .placeholder(errorHolder)
          .into(imageView);
    }
  }
}
