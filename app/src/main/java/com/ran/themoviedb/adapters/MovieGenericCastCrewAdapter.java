package com.ran.themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ran.themoviedb.R;
import com.ran.themoviedb.entities.MovieCastCrewType;
import com.ran.themoviedb.listeners.CastCrewListener;
import com.ran.themoviedb.model.TheMovieDbConstants;
import com.ran.themoviedb.model.server.entities.CastDetails;
import com.ran.themoviedb.model.server.entities.CrewDetails;
import com.ran.themoviedb.utils.AppUiUtils;
import com.ran.themoviedb.utils.ImageLoaderUtils;

import java.util.ArrayList;

/**
 * Created by ranjith.suda on 1/12/2016.
 */
public class MovieGenericCastCrewAdapter extends RecyclerView.Adapter {

  private ArrayList<CastDetails> castDetails;
  private ArrayList<CrewDetails> crewDetails;
  private MovieCastCrewType castCrewType;
  private Context context;
  private String baseUrl;
  private CastCrewListener castCrewListener;

  public MovieGenericCastCrewAdapter(ArrayList<CastDetails> castDetails,
                                     ArrayList<CrewDetails> crewDetails,
                                     MovieCastCrewType castCrewType, Context context, String baseUrl,
                                     CastCrewListener listener) {

    this.castCrewType = castCrewType;
    this.castDetails = castDetails;
    this.crewDetails = crewDetails;
    this.context = context;
    this.baseUrl = baseUrl;
    this.castCrewListener = listener;

  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (castCrewType) {
      case CAST_TYPE:
        return new CastHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.recycler_cast_item, parent, false));
      case CREW_TYPE:
        return new CrewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.recycler_crew_item, parent, false));
      default:
        return null;
    }

  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

    //Crew Type Binding
    if (castCrewType == MovieCastCrewType.CREW_TYPE) {
      CrewHolder crewHolder = (CrewHolder) holder;
      crewHolder.crew_name.setText(TheMovieDbConstants.COLON_STRING +
          TheMovieDbConstants.SPACE_STRING + TheMovieDbConstants.SPACE_STRING +
          crewDetails.get(position).getName());
      crewHolder.crew_department.setText(crewDetails.get(position).getDepartment());
      crewHolder.crew_holder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          castCrewListener.onPersonDetail(crewDetails.get(position).getId(),
              crewDetails.get(position).getName());
        }
      });
    }

    //Cast Type binding
    if (castCrewType == MovieCastCrewType.CAST_TYPE) {
      CastHolder holder_custom = (CastHolder) holder;
      holder_custom.image_name.setText(castDetails.get(position).getName());
      String image_endPoint = castDetails.get(position).getProfile_path();
      if (!AppUiUtils.isStringEmpty(image_endPoint)) {
        ImageLoaderUtils.loadImageWithPlaceHolder(context, holder_custom.image_person,
            ImageLoaderUtils.getImageUrl(baseUrl, image_endPoint),
            R.drawable.image_error_placeholder);
      } else {
        holder_custom.image_person.setImageResource(R.drawable.image_error_placeholder);
      }
      holder_custom.image_person.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          castCrewListener.onPersonDetail(castDetails.get(position).getId(),
              castDetails.get(position).getName());
        }
      });
    }
  }

  @Override
  public int getItemCount() {

    switch (castCrewType) {
      case CAST_TYPE:
        return castDetails.size();
      case CREW_TYPE:
        return crewDetails.size();
      default:
        return 0;
    }
  }

  private class CastHolder extends RecyclerView.ViewHolder {

    private ImageView image_person;
    private TextView image_name;

    public CastHolder(View itemView) {
      super(itemView);
      image_name = (TextView) itemView.findViewById(R.id.cast_person);
      image_person = (ImageView) itemView.findViewById(R.id.cast_image);
    }
  }

  private class CrewHolder extends RecyclerView.ViewHolder {

    private TextView crew_name;
    private TextView crew_department;
    private LinearLayout crew_holder;

    public CrewHolder(View itemView) {
      super(itemView);
      crew_holder = (LinearLayout) itemView.findViewById(R.id.crew_holder);
      crew_name = (TextView) itemView.findViewById(R.id.crew_name);
      crew_department = (TextView) itemView.findViewById(R.id.crew_department);
    }
  }
}
