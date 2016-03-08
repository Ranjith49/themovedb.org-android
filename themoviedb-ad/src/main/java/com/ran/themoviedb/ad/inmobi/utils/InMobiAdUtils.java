package com.ran.themoviedb.ad.inmobi.utils;

import android.content.Context;
import android.widget.RelativeLayout;

import com.ran.themoviedb.ad.inmobi.InMobiAdTypes;
import com.ran.themoviedb.model.utils.ApplicationUtils;

/**
 * Created by ranjith.suda on 3/7/2016.
 * <p>
 * Common Utils for the InMobi SDK Ad's
 */
public class InMobiAdUtils {

  public static final long BANNER_PLACEMENT_ID = 1456480209860L;
  public static final long INTERSTITIAL_PLACEMENT_ID = 1455764375720L;
  public static final int BANNER_REFRESH_INTERVAL = 60; // 1 min

  //Counter for the InMobi Interstitial AD ..
  public static int INTERSTITIAL_AD_COUNT = 0;
  public static final int INTERSTITIAL_AD_RESET_COUNTER = 5;

  /**
   * Utility Method to build the Relative Layout Params for the In mobi AD's
   *
   * @param adTypes -- AD type
   * @return -- RelativeLayout Params
   */
  public static RelativeLayout.LayoutParams bannerRelativeLayoutParams(InMobiAdTypes adTypes) {
    int width_banner;
    int height_banner;
    switch (adTypes) {
      case BANNER_AD_320_50:
        width_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 320);
        height_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 50);
        break;
      case BANNER_AD_320_250:
        width_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 320);
        height_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 250);
        break;
      case BANNER_AD_728_90:
        width_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 728);
        height_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 90);
        break;
      case BANNER_AD_468_60:
        width_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 468);
        height_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 60);
        break;
      default:
        width_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 320);
        height_banner = InMobiAdUtils.dpToPx(ApplicationUtils.getApplication(), 50);
        break;
    }

    RelativeLayout.LayoutParams layoutParams =
        new RelativeLayout.LayoutParams(width_banner, height_banner);
    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    return layoutParams;
  }

  /**
   * Utility for dp to px conversion
   *
   * @param context -- context
   * @param dp      -- dp
   * @return -- pixels
   */
  private static int dpToPx(Context context, float dp) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) ((dp * scale) + 0.5f);
  }
}
