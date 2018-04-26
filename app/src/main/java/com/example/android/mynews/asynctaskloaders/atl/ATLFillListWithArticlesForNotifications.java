package com.example.android.mynews.asynctaskloaders.atl;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.mynews.data.DatabaseContract;
import com.example.android.mynews.data.DatabaseHelper;
import com.example.android.mynews.pojo.ArticlesSearchAPIObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego Fajardo on 25/04/2018.
 */

/** This ATL fills a list with all the information from the table
 * Articles for Search Articles
 * */
public class ATLFillListWithArticlesForNotifications extends AsyncTaskLoader<List<ArticlesSearchAPIObject>> {

    DatabaseHelper dbH;
    Cursor mCursor;

    public ATLFillListWithArticlesForNotifications(
            Context context) {
        super(context);
        dbH = new DatabaseHelper(context);
        if (!dbH.isTableEmpty(DatabaseContract.Database.ARTICLES_FOR_NOTIFICATION_TABLE_NAME)) {
            mCursor = dbH.getAllDataFromTableName(DatabaseContract.Database.ARTICLES_FOR_NOTIFICATION_TABLE_NAME);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<ArticlesSearchAPIObject> loadInBackground() {

        List<ArticlesSearchAPIObject> listOfObjects = new ArrayList<>();

        if (!dbH.isTableEmpty(DatabaseContract.Database.ARTICLES_FOR_NOTIFICATION_TABLE_NAME)){

            mCursor.moveToFirst();
            for (int i = 0; i < mCursor.getCount(); i++) {

                ArticlesSearchAPIObject object = new ArticlesSearchAPIObject();

                object.setWebUrl(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.Database.SA_WEB_URL)));
                object.setSnippet(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.Database.SA_SNIPPET)));
                object.setImageUrl(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.Database.SA_IMAGE_URL)));
                object.setNewDesk(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.Database.SA_NEW_DESK)));
                object.setPubDate(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.Database.SA_PUB_DATE)));

                listOfObjects.add(object);

                if (i != mCursor.getCount()) {
                    mCursor.moveToNext();
                }
            }
            return listOfObjects;
        } else {
            return null;
        }
    }
}
