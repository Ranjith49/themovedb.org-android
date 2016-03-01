package com.ran.themoviedb.model.share;

import android.content.Context;
import android.content.Intent;

import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.DisplayStoreType;

/**
 * Created by ranjith.suda on 3/1/2016.
 * <p>
 * Helper Class required to build the Share Text based on the ShareContent
 */
public class ShareContentHelper {

  /**
   * Utility method to generate the Share Url
   *
   * @param id        -- id of the Item
   * @param storeType -- store type
   * @return -- String url
   */
  public static String generateShareUrl(int id, DisplayStoreType storeType) {
    switch (storeType) {
      case MOVIE_STORE:
        return TheMovieDbConstants.WAP_MOVIE_URL + String.valueOf(id);
      case TV_STORE:
        return TheMovieDbConstants.WAP_TV_URL + String.valueOf(id);
      case PERSON_STORE:
        return TheMovieDbConstants.WAP_PERSON_URL + String.valueOf(id);
      default:
        return TheMovieDbConstants.EMPTY_STRING;
    }
  }

  /**
   * Helper Method to build the Url of the Share with help of {@link ShareContent}
   *
   * @param shareContent -- share content
   * @return -- Extra text
   */
  private static String generateShareText(ShareContent shareContent) {
    String shareTextUrl = String.valueOf(android.text.Html.fromHtml(
        shareContent.getShareDescription() + TheMovieDbConstants.ANCHOR_OPEN_TAG +
            shareContent.getShareUrl() + TheMovieDbConstants.CLOSE_TAG +
            shareContent.getShareUrl() + TheMovieDbConstants.ANCHOR_CLOSE_TAG));

    return shareTextUrl;
  }

  /**
   * Utility to build the Share Dialog
   *
   * @param context          -- Context
   * @param shareContent     -- Share content
   * @param shareDialogTitle -- Dialog for the Share Dialog
   */
  public static void buildShareIntent(Context context, ShareContent shareContent,
                                      String shareDialogTitle) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_SUBJECT, shareContent.getShareTitle());
    intent.putExtra(Intent.EXTRA_TEXT, generateShareText(shareContent));
    context.startActivity(Intent.createChooser(intent, shareDialogTitle));
  }
}
