package com.example.android.mynews.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.mynews.R;
import com.example.android.mynews.asynctaskloaders.atldatabase.ATLDeleteHistory;
import com.example.android.mynews.asynctaskloaders.atldatabase.ATLMainActCreateDatabase;
import com.example.android.mynews.extras.helperclasses.ToastHelper;
import com.example.android.mynews.fragmentadapters.FragmentPageAdapter;
import com.example.android.mynews.fragments.PageFragmentBusiness;
import com.example.android.mynews.fragments.PageFragmentMostPopular;
import com.example.android.mynews.fragments.PageFragmentSports;
import com.example.android.mynews.fragments.PageFragmentTopStories;

// TODO: 26/04/2018 Some images (SearchArticles) load too slow
// TODO: 30/04/2018 See what to do when there is no information and the text views show things grey (change in layout)
// TODO: 01/05/2018 Progress Bar (test SearchArticlesActivity)
// TODO: 03/05/2018 Problems with webViewsTests

/** First activity displayed when the app is launched.
 * It shows a tab on the top link to a viewPager. The user
 * can navigate between for different RecyclerViews to see
 * different articles ordered by Topic.
 * */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int LOADER_DATABASE_CREATION = 1;
    private static final int LOADER_DELETE_HISTORY = 2;

    //Toolbar variable
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Changes the color of the Toolbar Overflow Button
        //Listener to white
        setOverflowButtonColor(toolbar, Color.WHITE);

        //Checking if the tables of the database exist. If they don't,
        //we create them
        loadLoaderCreateDatabase(LOADER_DATABASE_CREATION);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        if (viewPager != null){
            setupViewPager(viewPager);
        }

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    /*********************
     * MENU OPTIONS ******
     ********************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_search_button:
                Intent intent1 = new Intent(MainActivity.this, SearchArticlesActivity.class);
                startActivity(intent1);
                break;
            case R.id.menu_notifications_button:
                Intent intent2 = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_delete_database:
                alertDialogDeleteHistory();
                break;
            case R.id.menu_help_button:
                startActivity (new Intent(MainActivity.this, HelpActivity.class));
                break;
            case R.id.menu_about_button:
                startActivity (new Intent(MainActivity.this, AboutActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /** Method used to link the viewPager with the Adapters via the PageFragmentAdapter
     */
    private void setupViewPager(ViewPager viewPager) {
        FragmentPageAdapter fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        fragmentPageAdapter.addFragment(new PageFragmentTopStories(), getResources().getString(R.string.top_stories_tag));
        fragmentPageAdapter.addFragment(new PageFragmentMostPopular(), getResources().getString(R.string.most_popular_tag));
        fragmentPageAdapter.addFragment(new PageFragmentBusiness(), getResources().getString(R.string.business_tag));
        fragmentPageAdapter.addFragment(new PageFragmentSports(), getResources().getString(R.string.sports_tag));

        viewPager.setAdapter(fragmentPageAdapter);
    }

    /** Changes the color of the Toolbar Overflow ButtonListener to white
     * */
    public static void setOverflowButtonColor(final Toolbar toolbar, final int color) {
        Drawable drawable = toolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), color);
            toolbar.setOverflowIcon(drawable);
        }
    }

    /** Method that creates an alert dialog that
     * can be used to delete the Read Articles History
     * */
    private void alertDialogDeleteHistory () {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getResources().getString(R.string.delete_history_message))
                .setTitle(getResources().getString(R.string.delete_history_title))
                .setPositiveButton(getResources().getString(R.string.delete_history_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        loadLoaderDeleteHistory(LOADER_DELETE_HISTORY);

                    }
                })
                .setNegativeButton(getResources().getString(R.string.delete_history_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing happens
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /*****************************/
    /** METHODS TO INIT LOADERS **/
    /*****************************/

    /** The loaders use the LoaderCallbacks to call AsyncTaskLoaders
     * and check if the tables of the database exist
     * */
    private void loadLoaderCreateDatabase(int id) {

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Boolean> loader = loaderManager.getLoader(id);

        if (loader == null) {
            Log.i(TAG, "loadLoaderUpdateSwitchTable: ");
            loaderManager.initLoader(id, null, loaderCreateDatabase);
        } else {
            Log.i(TAG, "loadLoaderUpdateSwitchTable: ");
            loaderManager.restartLoader(id, null, loaderCreateDatabase);
        }
    }

    private void loadLoaderDeleteHistory (int id) {

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Void> loader = loaderManager.getLoader(id);

        if (loader == null) {
            Log.i(TAG, "loadLoaderUpdateSwitchTable: ");
            loaderManager.initLoader(id, null, loaderDeleteHistory);
        } else {
            Log.i(TAG, "loadLoaderUpdateSwitchTable: ");
            loaderManager.restartLoader(id, null, loaderDeleteHistory);
        }
    }


    /**********************/
    /** LOADER CALLBACKS **/
    /**********************/

    /** This LoaderCallback
     * allows to check if the tables in the database
     * exist and, if they don't, it fills them
     * with the necessary information
     * */
    private LoaderManager.LoaderCallbacks<Boolean> loaderCreateDatabase =
            new LoaderManager.LoaderCallbacks<Boolean>() {

                @Override
                public Loader<Boolean> onCreateLoader(int id, Bundle args) {
                    return new ATLMainActCreateDatabase(MainActivity.this);
                }

                @Override
                public void onLoadFinished(Loader<Boolean> loader, Boolean data) {

                }

                @Override
                public void onLoaderReset(Loader<Boolean> loader) {

                }
            };

    private LoaderManager.LoaderCallbacks<Void> loaderDeleteHistory =
            new LoaderManager.LoaderCallbacks<Void>() {
                @NonNull
                @Override
                public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
                    return new ATLDeleteHistory(MainActivity.this);
                }

                @Override
                public void onLoadFinished(@NonNull Loader<Void> loader, Void data) {

                    ToastHelper.toastShort(MainActivity.this, getResources().getString(R.string.delete_history_toast));

                    //Code used to restart the activity
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }

                @Override
                public void onLoaderReset(@NonNull Loader<Void> loader) {

                }
            };
}