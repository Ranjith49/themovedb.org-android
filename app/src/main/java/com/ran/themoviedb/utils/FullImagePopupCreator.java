package com.ran.themoviedb.utils;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;

import com.ran.themoviedb.R;
import com.ran.themoviedb.listeners.VideoPopupClickListener;
import com.ran.themoviedb.model.server.entities.VideoDetails;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/17/2016.
 * <p/>
 * Helper class for creating the Full Image Details Popup ,
 * -- Popup Window to be shown on the Details click of the Full Image
 */
public class FullImagePopupCreator {

  private static ListPopupWindow listPopupWindow;

  /**
   * Method to show the Popup Window
   *
   * @param context      -- Context
   * @param popupDetails -- list of the Popup String
   * @param anchorView   -- Anchor View
   */
  public static void showPopupWindow(Context context, final String[] popupDetails,
                                     View anchorView) {
    listPopupWindow = new ListPopupWindow(context);
    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, popupDetails);

    listPopupWindow.setAdapter(adapter);
    listPopupWindow.setWidth(
        context.getResources().getDimensionPixelSize(R.dimen.full_popup_width));
    listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
    listPopupWindow.setAnchorView(anchorView);
    listPopupWindow.setModal(true);
    listPopupWindow.show();
  }

  /**
   * Method to dismiss the Hide Popup Window
   */
  public static void hidePopupWindow() {
    if (listPopupWindow != null) {
      listPopupWindow.dismiss();
    }
  }
}
