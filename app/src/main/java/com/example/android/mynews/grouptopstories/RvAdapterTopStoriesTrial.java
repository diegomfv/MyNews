package com.example.android.mynews.grouptopstories;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.mynews.R;
import com.example.android.mynews.activities.WebViewMainActivity;
import com.example.android.mynews.data.DatabaseHelper;
import com.example.android.mynews.extras.Keys;
import com.example.android.mynews.pojo.TopStoriesAPIObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego Fajardo on 25/02/2018.
 */

public class RvAdapterTopStoriesTrial extends RecyclerView.Adapter<RvAdapterTopStoriesTrial.ViewHolder> {

    //Variable that allows to control the Adapter using "logs" (used in onBindViewHolder method)
    private static final String TAG = RvAdapterTopStoriesTrial.class.getSimpleName();

    //Loader ID
    private static final int LOADER_INSERT_ARTICLE_DATABASE = 12;

    //Array that will store TopStoriesAPIObjects after request
    private List<TopStoriesAPIObject> listOfTopStoriesAPIObjects;

    //List that stores the database urls
    private List<String> listOfArticlesReadInTheDatabase;

    //Context of the activity
    private Context mContext;

    // TODO: 22/04/2018  Activity, check if it makes the loader to work

    //Constructor of the RvAdapter
    public RvAdapterTopStoriesTrial(Context context, List<TopStoriesAPIObject> listOfObjects, List<String> listOfUrls) {
        this.mContext = context;
        this.listOfTopStoriesAPIObjects = new ArrayList<>();
        this.listOfTopStoriesAPIObjects = listOfObjects;
        this.listOfArticlesReadInTheDatabase = new ArrayList<>();
        this.listOfArticlesReadInTheDatabase = listOfUrls;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_fragment;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForListItem,
                viewGroup,
                shouldAttachToParentImmediately);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RvAdapterTopStoriesTrial.ViewHolder holder, final int position) {

        Log.d(TAG, "#" + position);

        Log.i("POSITION: " + position, "listOfArticlesInDatabase = " + listOfArticlesReadInTheDatabase.size());

        if (listOfArticlesReadInTheDatabase.contains(listOfTopStoriesAPIObjects.get(position).getArticleUrl())){
            Typeface bold = Typeface.defaultFromStyle(Typeface.BOLD);
            holder.title.setTypeface(bold);
        }

        holder.title.setText(listOfTopStoriesAPIObjects.get(position).getTitle());
        holder.section.setText("Top Stories < " + listOfTopStoriesAPIObjects.get(position).getSection());
        holder.update_date.setText(listOfTopStoriesAPIObjects.get(position).getUpdatedDate());

        if (listOfTopStoriesAPIObjects.get(position).getImageThumblarge() == null) {
            Glide.with(mContext)
                    .load(R.drawable.nyt)
                    .into(holder.imageOnLeft);
        }
        else {
            Glide.with(mContext)
                    .load(listOfTopStoriesAPIObjects.get(position).getImageThumblarge())
                    .into(holder.imageOnLeft);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ONCLICK - POSITION", "#" + " CLICKED");
                Context context = v.getContext();

                /** Since we cannot call there "getSupportLoaderManager()", we will add the url
                 * to the database in the next activity (if it is not there yet) */

                Intent intent = new Intent(context, WebViewMainActivity.class);
                intent.putExtra(Keys.PutExtras.ARTICLE_URL_SENT, listOfTopStoriesAPIObjects.get(position).getArticleUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfTopStoriesAPIObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View mView;
        private ImageView imageOnLeft;
        private TextView section;
        private TextView update_date;
        private TextView title;

        public ViewHolder(View view) {
            super(view);

            mView = view.findViewById(R.id.list_item_globalLayout);
            imageOnLeft = view.findViewById(R.id.list_item_image_news);
            section = view.findViewById(R.id.list_item_continent);
            update_date = view.findViewById(R.id.list_item_date);
            title = view.findViewById(R.id.list_item_news_text);

        }

    }

    /************************
     * ASYNC TASK LOADERS ***
     ************************/

    // TODO: 22/04/2018 Explain better
    /** Used to insert articles in the database when the user clicks but they
     * haven't been read yet */
    private static class ArticleInserter extends AsyncTaskLoader<String> {

        private DatabaseHelper dbH;
        private String url;

        public ArticleInserter (Context context, String url) {
            super(context);
            this.dbH = new DatabaseHelper(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public String loadInBackground() {
            this.url = url;
            dbH.insertDataToAlreadyReadArticlesTable(url);
            return null;
        }
    }

}
