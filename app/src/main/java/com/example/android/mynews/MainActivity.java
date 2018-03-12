package com.example.android.mynews;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ActionMenuView;
import android.widget.Spinner;

import com.example.android.mynews.Data.AndroidDatabaseManager;

public class MainActivity extends AppCompatActivity {

    //Toolbar variable
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Displayes home button in toolbar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Changes the color of the Toolbar Overflow Button to white
        setOverflowButtonColor(toolbar, Color.WHITE);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        if (viewPager != null){
            setupViewPager(viewPager);
        }

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(MainActivity.this, AndroidDatabaseManager.class);
                startActivity(intent);
                break;
            case R.id.menu_search_button:
                Intent intent1 = new Intent(MainActivity.this, SearchArticlesActivity.class);
                startActivity(intent1);
                break;
            case R.id.menu_notification_button:
                Intent intent2 = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_help_button:
                Intent intent3 = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent3);
                break;
            case R.id.menu_about_button:
                Intent intent4 = new Intent(MainActivity.this, ActivityDataLoader.class);
                startActivity(intent4);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //Uses de FragmentPageAdapter to link the PageFragmentTopStories to the ViewPager
    private void setupViewPager(ViewPager viewPager) {
        FragmentPageAdapter fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        fragmentPageAdapter.addFragment(new PageFragmentTopStories(), "TOP STORIES");
        //fragmentPageAdapter.addFragment(new PageFragmentMostPopular(), "MOST POPULAR");
        //fragmentPageAdapter.addFragment(new PageFragmentBusiness(), "BUSINESS");
        //fragmentPageAdapter.addFragment(new PageFragmentSports(), "SPORTS");
        viewPager.setAdapter(fragmentPageAdapter);
    }

    //Changes the color of the Toolbar Overflow Button to white
    public static void setOverflowButtonColor(final Toolbar toolbar, final int color) {
        Drawable drawable = toolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), color);
            toolbar.setOverflowIcon(drawable);
        }
    }

}
