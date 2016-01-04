package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ran.themoviedb.R;
import com.ran.themoviedb.model.server.entities.MovieGenre;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/4/2016.
 */
public class StoreGenreAdapter extends RecyclerView.Adapter<StoreGenreAdapter.CustomViewHolder> {

  private Context context;
  private ArrayList<MovieGenre> list;

  public StoreGenreAdapter(Context context, ArrayList<MovieGenre> genres) {
    this.context = context;
    this.list = genres;
  }

  @Override
  public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new CustomViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_genre,
            parent, false));
  }

  @Override
  public void onBindViewHolder(CustomViewHolder holder, int position) {
    holder.name_genre.setText(list.get(position).getName());
    holder.name_genre.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //ToDO [ranjith] add the Click here..
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public static class CustomViewHolder extends RecyclerView.ViewHolder {

    private Button name_genre;

    public CustomViewHolder(View itemView) {
      super(itemView);
      name_genre = (Button) itemView.findViewById(R.id.recycler_genre_button);
    }
  }

}
