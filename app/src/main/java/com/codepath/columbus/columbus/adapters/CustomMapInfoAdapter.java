package com.codepath.columbus.columbus.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.codepath.columbus.columbus.R;
import com.codepath.columbus.columbus.activities.ExhibitListActivity;
import com.codepath.columbus.columbus.models.Museum;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class CustomMapInfoAdapter implements GoogleMap.InfoWindowAdapter{
    LayoutInflater mInflater;
    Activity activity;
    Museum museum;
    public CustomMapInfoAdapter(Activity activity, Museum museum , LayoutInflater i){
        mInflater = i;
        this.activity = activity;
        this.museum = museum;
    }

    // This defines the contents within the info window based on the marker
    @Override
    public View getInfoContents(Marker marker) {
        // Getting view from the layout file
        View v = mInflater.inflate(R.layout.custom_info_map, null);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ExhibitListActivity.class);
                i.putExtra("museumId",museum.getObjectId());
                i.putExtra("museumUUID",museum.getBeaconUUID());
                i.putExtra("museumNickname",museum.getNickname());
                activity.startActivity(i);
            }
        });

        // Populate fields
        TextView title = (TextView) v.findViewById(R.id.tvMuseumName);
        title.setText(marker.getTitle());

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ExhibitListActivity.class);
                i.putExtra("museumId",museum.getObjectId());
                i.putExtra("museumUUID",museum.getBeaconUUID());
                i.putExtra("museumNickname",museum.getNickname());
                activity.startActivity(i);
            }
        });

        // Return info window contents
        return v;
    }

    // This changes the frame of the info window; returning null uses the default frame.
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}
