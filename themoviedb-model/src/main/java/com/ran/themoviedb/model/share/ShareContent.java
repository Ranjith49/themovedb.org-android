package com.ran.themoviedb.model.share;

import com.ran.themoviedb.model.server.entities.DisplayStoreType;

/**
 * Created by ranjith.suda on 3/1/2016.
 * <p>
 * Bean Responsible for Creating the Share Data.
 */
public class ShareContent {

  private String shareTitle;
  private String shareDescription;
  private String shareUrl;

  public ShareContent(String tile, String shareDescription, int id,DisplayStoreType storeType) {
    this.shareTitle = tile;
    this.shareDescription = shareDescription;
    this.shareUrl = ShareContentHelper.generateShareUrl(id,storeType);
  }

  public String getShareTitle() {
    return shareTitle;
  }

  public String getShareUrl() {
    return shareUrl;
  }

  public String getShareDescription() {
    return shareDescription;
  }
}
