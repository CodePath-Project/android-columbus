package com.codepath.columbus.columbus.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.codepath.columbus.columbus.R;
import com.codepath.columbus.columbus.fragments.exhibit_list.ExhibitListFragment;
import com.codepath.columbus.columbus.fragments.exhibit_list.ExhibitListSearchFragment;
import com.codepath.columbus.columbus.utils.DrawMenuItemAvatar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ExhibitListActivity extends SherlockFragmentActivity {
    private ExhibitListFragment exhibitListFragment;
    private ExhibitListSearchFragment exhibitListSearchFragment;

    private String museumNickname;
    private String museumId;

    private MenuItem loginItem;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibit_list);
        museumId = getIntent().getStringExtra("museumId");
        String museumUUID = getIntent().getStringExtra("museumUUID");
        museumNickname = getIntent().getStringExtra("museumNickname");
        //museumUUID = "8492e75f-4fd6-469d-b132-043fe94921d8";
        Log.i("INFO", "activity museum id=" + museumId + "; uuid=" + museumUUID);

        exhibitListFragment = ExhibitListFragment.newInstance(museumId, museumUUID);
        setActionBar();
        loadFragment();
    }

    public void setActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(museumNickname + " Exhibits");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void loadFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.flListContainer, exhibitListFragment);
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.exhibit_list, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search_exhibits);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length() > 0) {
                    loadSearchFragment(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //Toast.makeText(ExhibitListActivity.this, "on close called", Toast.LENGTH_SHORT).show();
                removeSearchFragment();
                return false;
            }
        });

        loginItem = menu.findItem(R.id.action_login);

        return super.onCreateOptionsMenu(menu);
    }


    public void loadSearchFragment(String query) {
        exhibitListSearchFragment = ExhibitListSearchFragment.newInstance(museumId, query);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.flListContainer, exhibitListSearchFragment, "SearchFragment");
        ft.addToBackStack(null);
        ft.commit();
    }

    // return true to indicate search fragment was removed
    // false indicates fragment not present, or not visible
    private boolean removeSearchFragment() {
        ExhibitListSearchFragment exhibitSearchFrag = (ExhibitListSearchFragment)
                getSupportFragmentManager().findFragmentByTag("SearchFragment");
        if(exhibitSearchFrag != null && exhibitSearchFrag.isVisible()) {
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if(removeSearchFragment()) {
                    return true;
                }

                // app icon in action bar clicked; goto parent activity.
                this.finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            case R.id.action_login:
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Programmatically change default search icon since cannot do it through xml
        MenuItem searchViewMenuItem = menu.findItem(R.id.menu_search_exhibits);
        SearchView searchView = (SearchView) searchViewMenuItem.getActionView();
        int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView v = (ImageView) searchView.findViewById(searchImgId);
        v.setImageResource(R.drawable.icon_search);

        DrawMenuItemAvatar.drawAvatar(this, loginItem);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onResume() {
        super.onResume();
        DrawMenuItemAvatar.drawAvatar(this, loginItem);
    }
}
