package com.ran.themoviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ran.themoviedb.R;
import com.ran.themoviedb.db.AppSharedPreferences;
import com.ran.themoviedb.entities.LanguageType;

/**
 * Created by ranjith.suda on 12/31/2015.
 * <p>
 * Language Adapter for showing List of the Languages in the Language List View
 */
public class LanguageBaseAdapter extends BaseAdapter {

  private String langDisplayNames[];
  private String langCodes[];
  private Context context;
  private LayoutInflater layoutInflater;

  public LanguageBaseAdapter(Context context, String[] langCodes) {
    this.context = context;
    this.langCodes = langCodes;
    langDisplayNames = context.getResources().getStringArray(R.array.language_display_titles);
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return langDisplayNames.length;
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    TextView langText;
    ImageView langImage;
    ImageView langStatusImage;
    if (convertView == null) {
      convertView = layoutInflater.inflate(R.layout.list_language_item, null);
    }
    langText = (TextView) convertView.findViewById(R.id.language_list_text);
    langText.setText(langDisplayNames[position]);
    langImage = (ImageView) convertView.findViewById(R.id.language_list_item_image);
    langImage.setImageResource(getFlagIcon(langCodes[position]));
    langStatusImage = (ImageView) convertView.findViewById(R.id.language_list_item_choose);
    if (AppSharedPreferences.getInstance(context).getAppLanguageData().equalsIgnoreCase
        (langCodes[position])) {
      langStatusImage.setVisibility(View.VISIBLE);
    } else {
      langStatusImage.setVisibility(View.GONE);
    }
    return convertView;
  }

  private int getFlagIcon(String code) {
    switch (LanguageType.getLangType(code)) {
      case ENGLISH:
        return R.mipmap.usa;
      case GERMAN:
        return R.mipmap.germany;
      case SPANISH:
        return R.mipmap.spain;
      case FRENCH:
        return R.mipmap.france;
      case ITALIAN:
        return R.mipmap.italy;
      case RUSSIAN:
        return R.mipmap.russianfederation;
      case PORTUGUESE:
        return R.mipmap.portugal;
      case HUNGARIAN:
        return R.mipmap.hungary;
      case CHINESE:
        return R.mipmap.china;
      case CZECH:
        return R.mipmap.europeanunion;
      default:
        return R.mipmap.usa;
    }
  }
}
