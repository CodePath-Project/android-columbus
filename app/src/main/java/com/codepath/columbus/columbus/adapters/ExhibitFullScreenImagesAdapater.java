package com.codepath.columbus.columbus.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.codepath.columbus.columbus.R;
import com.codepath.columbus.columbus.utils.ViewPagerFixed;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by marc on 10/28/14.
 */
public class ExhibitFullScreenImagesAdapater extends PagerAdapter {

  private Activity activity;
  private List<String> imagePaths;
  private LayoutInflater inflater;
  ImageLoader imageLoader = ImageLoader.getInstance();

  // UI References
  @InjectView(R.id.ivImage) ImageView imageView;
  @InjectView(R.id.spinner) ProgressBar spinner;

  // constructor
  public ExhibitFullScreenImagesAdapater(Activity activity, List<String> imagePaths) {
    this.activity = activity;
    this.imagePaths = imagePaths;
  }

  @Override
  public int getCount() {
    return this.imagePaths.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {

    inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View viewLayout = inflater.inflate(R.layout.item_fullscreen_image, container,
                                          false);
    ButterKnife.inject(this, viewLayout);

    DisplayImageOptions options = new DisplayImageOptions.Builder()
                                      .showImageOnLoading(null).build();

    imageLoader.displayImage(imagePaths.get(position), imageView, options, new SimpleImageLoadingListener() {
      @Override
      public void onLoadingStarted(String imageUri, View view) {
        spinner.setVisibility(View.VISIBLE);
      }

      @Override
      public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        spinner.setVisibility(View.GONE);
      }

      @Override
      public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        spinner.setVisibility(View.GONE);
      }
    });
    container.addView(viewLayout);
    return viewLayout;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
     container.removeView((RelativeLayout)object);
  }
}
