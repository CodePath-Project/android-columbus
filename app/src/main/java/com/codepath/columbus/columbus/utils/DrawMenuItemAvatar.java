package com.codepath.columbus.columbus.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.view.View;

import com.actionbarsherlock.view.MenuItem;
import com.codepath.columbus.columbus.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DrawMenuItemAvatar {
    public static void drawAvatar(Activity activity, final MenuItem loginItem){
        // set the profile image if signed in
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String imageURL = sharedPreferences.getString("imageURL",null);

        if (loginItem != null) {
            if (imageURL != null) {
                // by default the profile url gives 50x50 px image only
                imageURL = imageURL.substring(0, imageURL.length() - 2) + 80;

                // set the profile image
                ImageLoader.getInstance().loadImage(imageURL, new ImageLoadingListener() {
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    }

                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        loginItem.setIcon(new RoundedAvatarDrawable(loadedImage));
                    }

                    public void onLoadingCancelled(String imageUri, View view) {
                    }
                });
            } else {
                // if not signed in
                loginItem.setIcon(activity.getResources().getDrawable(R.drawable.ic_login));
            }
        }
    }
}
