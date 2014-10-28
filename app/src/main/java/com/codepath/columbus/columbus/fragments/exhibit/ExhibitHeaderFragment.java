package com.codepath.columbus.columbus.fragments.exhibit;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.columbus.columbus.R;
import com.codepath.columbus.columbus.adapters.ImageSlideAdapter;
import com.codepath.columbus.columbus.models.Exhibit;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ExhibitHeaderFragment extends ExhibitFragment {

  // UI References
  @InjectView(R.id.view_pager) ViewPager viewPager;
  @InjectView(R.id.tvExhibitName) TextView tvExhibitName;
  @InjectView(R.id.btnAddComment) ImageButton btnAddComment;

  private OnButtonClicked listener;
  // Define the events that the fragment will use to communicate
  public interface OnButtonClicked {
    public void onComposeClicked();
  }

  public static ExhibitHeaderFragment newInstance(Exhibit exhibit) {
    ExhibitHeaderFragment fragment = new ExhibitHeaderFragment();
    fragment.init(exhibit);
    return fragment;
  }

  public ExhibitHeaderFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_exhibit_header, container, false);
    ButterKnife.inject(this, view);
    setHeaderTitle();
    setImageCarousel();
    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof OnButtonClicked) {
      listener = (OnButtonClicked) activity;
    } else {
      throw new ClassCastException(activity.toString()
                                       + " must implement ExhibitHeaderFragment.OnButtonClicked");
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }

  public void setHeaderTitle() {
    tvExhibitName.setText(exhibit.getName());
  }

  public void setImageCarousel() {
    List<String> images = exhibit.getImageUrls();

    if (images != null && images.size() > 0) {
      viewPager.setAdapter(new ImageSlideAdapter(activity, images));
    }
  }

  @OnClick (R.id.btnAddComment)
  public void onComposeClicked() {
    listener.onComposeClicked();
  }
}
