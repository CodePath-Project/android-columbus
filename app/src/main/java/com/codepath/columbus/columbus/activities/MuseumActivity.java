package com.codepath.columbus.columbus.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.columbus.columbus.R;
import com.codepath.columbus.columbus.adapters.MuseumPagerAdapter;
import com.codepath.columbus.columbus.utils.DrawMenuItemAvatar;
import com.viewpagerindicator.TabPageIndicator;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MuseumActivity extends SherlockFragmentActivity {

    MuseumPagerAdapter pagerAdapter;
    MenuItem loginItem;

    @Override
    protected void attachBaseContext(Context newBase) {
      super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.museum, menu);
        loginItem = menu.findItem(R.id.action_login);

     return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        DrawMenuItemAvatar.drawAvatar(this,loginItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_login:
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        pagerAdapter = new MuseumPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(pagerAdapter);

        TabPageIndicator titleIndicator = (TabPageIndicator)findViewById(R.id.titles);
        titleIndicator.setViewPager(vpPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DrawMenuItemAvatar.drawAvatar(this,loginItem);
    }
}
