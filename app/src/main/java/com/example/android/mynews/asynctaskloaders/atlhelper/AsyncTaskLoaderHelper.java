package com.example.android.mynews.asynctaskloaders.atlhelper;

import android.content.Context;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.android.mynews.asynctaskloaders.atl.ATLMainActCreateDatabase;
import com.example.android.mynews.asynctaskloaders.atl.ATLNotifUpdateDatabase;
import com.example.android.mynews.asynctaskloaders.atl.ATLNotifUpdateList;
import com.example.android.mynews.asynctaskloaders.atl.ATLNotifUpdateSwitchDatabase;
import com.example.android.mynews.asynctaskloaders.atl.ATLNotifUpdateSwitchVariable;

import java.util.List;

/**
 * Created by Diego Fajardo on 21/04/2018.
 */

/** This class has static methods
 * that create AsyncTaskLoaders for specific reasons */

public class AsyncTaskLoaderHelper {

    private static final String TAG = "AsyncTaskLoaderHelper";

    /** Used in MainActivity to create the databases and fill the tables */
    public static Loader<Boolean> createDatabaseIfDoesntExist(Context context) {
        return new ATLMainActCreateDatabase(context);
    }

    /** Used in "onPause" and "onDestroy" in Notifications Activity
     * to update the database with the information of NotificationsActivity */
    public static Loader<Boolean> updateQueryAndSectionsTable(Context context, List<String> list) {
        Log.i(TAG, "updateQueryAndSectionsTable: +++");
        return new ATLNotifUpdateDatabase(context, list);
    }

    public static Loader<Boolean> updateSwitchTable (Context context, boolean switchState) {
        Log.i(TAG, "updateSwitchTable: +++");
        return new ATLNotifUpdateSwitchDatabase(context, switchState);
    }

    /** Used in "onResume" in Notifications Activity
     * to update the listOfQueryAndSections with the information from the Database */
    public static Loader<List<String>> updateListOfQueryAndSections(Context context) {
        Log.i(TAG, "updateListOfQueryAndSections: +++");
        return new ATLNotifUpdateList(context);
    }

    public static Loader<Boolean> updateSwitchVariable(Context context) {
        Log.i(TAG, "updateSwitchVariable: +++");
        return new ATLNotifUpdateSwitchVariable(context);
    }

}

