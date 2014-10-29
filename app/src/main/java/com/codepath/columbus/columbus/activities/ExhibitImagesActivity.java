package com.codepath.columbus.columbus.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.codepath.columbus.columbus.R;
import com.codepath.columbus.columbus.adapters.ExhibitFullScreenImagesAdapater;
import com.codepath.columbus.columbus.models.Exhibit;
import com.codepath.columbus.columbus.utils.ViewPagerFixed;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;


import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnPageChange;

/**
 * Created by marc on 10/28/14.
 */
public class ExhibitImagesActivity extends SherlockActivity {

  // UI References
  @InjectView(R.id.pager) ViewPagerFixed viewPager;

  private PagerAdapter pagerAdapter;

  // Intent Data
  private String exhibitId;
  private int imagePosition;

  private Exhibit exhibit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exhibit_images);
    ButterKnife.inject(this);

    exhibitId = getIntent().getStringExtra("exhibitId");
    imagePosition = getIntent().getIntExtra("imagePosition", 0);
    fetchExhibit();
  }

  private void fetchExhibit() {
    ParseQuery<Exhibit> query = ParseQuery.getQuery(Exhibit.class);
    // First try to find from the cache and only then go to network
    query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
    query.whereEqualTo("objectId", exhibitId);
    query.getFirstInBackground(new GetCallback<Exhibit>() {
      @Override
      public void done(Exhibit result, ParseException e) {
        if (e == null) {
          exhibit = result;
          setPageAdapter();
        } else {
          e.printStackTrace();
        }
      }
    });
  }

  private void setPageAdapter() {
    pagerAdapter = new ExhibitFullScreenImagesAdapater(this, exhibit.getImageUrls());
    viewPager.setAdapter(pagerAdapter);
    viewPager.setCurrentItem(imagePosition);
  }
}
