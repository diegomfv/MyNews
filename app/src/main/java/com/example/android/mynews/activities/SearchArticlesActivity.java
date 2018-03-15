package com.example.android.mynews.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.mynews.R;
import com.example.android.mynews.extras.Keys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego Fajardo on 25/02/2018.
 */

public class SearchArticlesActivity extends AppCompatActivity {

    //List to trial
    private List<String> listOfStrings;

    //Button Search
    private Button button_search;

    //Checkboxes
    private CheckBox cb_arts;
    private CheckBox cb_business;
    private CheckBox cb_entrepreneurs;
    private CheckBox cb_politics;
    private CheckBox cb_sports;
    private CheckBox cb_travel;

    //Textviews to check if the value of the variables is the correct one according to checkboxes
    private TextView tv_arts;
    private TextView tv_business;
    private TextView tv_entrepreneurs;
    private TextView tv_politics;
    private TextView tv_sports;
    private TextView tv_travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_articles_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listOfStrings = new ArrayList<>();

        //Checkboxes
        cb_arts = (CheckBox) findViewById(R.id.search_checkBox_arts);
        cb_business = (CheckBox) findViewById(R.id.search_checkBox_business);
        cb_entrepreneurs = (CheckBox) findViewById(R.id.search_checkBox_entrepeneurs);
        cb_politics = (CheckBox) findViewById(R.id.search_checkBox_politics);
        cb_sports = (CheckBox) findViewById(R.id.search_checkBox_sports);
        cb_travel = (CheckBox) findViewById(R.id.search_checkBox_travel);

        //TextViews
        tv_arts = (TextView) findViewById(R.id.tv_arts);
        tv_business = (TextView) findViewById(R.id.tv_business);
        tv_entrepreneurs = (TextView) findViewById(R.id.tv_entrepeneurs);
        tv_politics = (TextView) findViewById(R.id.tv_politics);
        tv_sports = (TextView) findViewById(R.id.tv_sports);
        tv_travel = (TextView) findViewById(R.id.tv_travel);

        /**

         */

        button_search = (Button) findViewById(R.id.search_button);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the checkbox is checked, check that there is an elements (String) with that name in the list
                //if there is any, set that name to the text view

                //ARTS
                if (cb_arts.isChecked()){
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_ARTS)) {
                        tv_arts.setText(listOfStrings.get((listOfStrings.indexOf(Keys.CheckboxFields.CB_ARTS))));
                    }
                }
                else tv_arts.setText("false");

                //BUSINESS
                if (cb_business.isChecked()){
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_BUSINESS)) {
                        tv_business.setText(listOfStrings.get((listOfStrings.indexOf(Keys.CheckboxFields.CB_BUSINESS))));
                    }
                }
                else tv_business.setText("false");

                //CB_ENTREPRENEURS
                if (cb_entrepreneurs.isChecked()){
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_ENTREPRENEURS)) {
                        tv_entrepreneurs.setText(listOfStrings.get((listOfStrings.indexOf(Keys.CheckboxFields.CB_ENTREPRENEURS))));
                    }
                }
                else tv_entrepreneurs.setText("false");

                //POLITICS
                if (cb_politics.isChecked()){
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_POLITICS)) {
                        tv_politics.setText(listOfStrings.get((listOfStrings.indexOf(Keys.CheckboxFields.CB_POLITICS))));
                    }
                }
                else tv_politics.setText("false");

                //SPORTS
                if (cb_sports.isChecked()){
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_SPORTS)) {
                        tv_sports.setText(listOfStrings.get((listOfStrings.indexOf(Keys.CheckboxFields.CB_SPORTS))));
                    }
                }
                else tv_sports.setText("false");

                //TRAVEL
                if (cb_travel.isChecked()){
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_TRAVEL)) {
                        tv_travel.setText(listOfStrings.get((listOfStrings.indexOf(Keys.CheckboxFields.CB_TRAVEL))));
                    }
                }
                else tv_travel.setText("false");


                // TODO: 15/03/2018 Add Search Query

                // TODO: 15/03/2018 Add Dates

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SearchArticlesActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCheckboxClicked(View view) {

        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.search_checkBox_arts:
                ///When checked, add a String with the name of the checkbox to the list
                if (checked) { listOfStrings.add(Keys.CheckboxFields.CB_ARTS); }
                //When unchecked, check if there is an element with the name of the Checkbox in the array
                //if there is one, remove it from the list
                else {
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_ARTS))
                        listOfStrings.remove(listOfStrings.indexOf(Keys.CheckboxFields.CB_ARTS));
                }
                break;

            case R.id.search_checkBox_business:
                if (checked) { listOfStrings.add(Keys.CheckboxFields.CB_BUSINESS); }
                else {
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_BUSINESS))
                        listOfStrings.remove(listOfStrings.indexOf(Keys.CheckboxFields.CB_BUSINESS));
                }
                break;

            case R.id.search_checkBox_entrepeneurs:
                if (checked) { listOfStrings.add(Keys.CheckboxFields.CB_ENTREPRENEURS); }
                else {
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_ENTREPRENEURS))
                        listOfStrings.remove(listOfStrings.indexOf(Keys.CheckboxFields.CB_ENTREPRENEURS));
                }
                break;

            case R.id.search_checkBox_politics:
                if (checked) { listOfStrings.add(Keys.CheckboxFields.CB_POLITICS); }
                else {
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_POLITICS))
                        listOfStrings.remove(listOfStrings.indexOf(Keys.CheckboxFields.CB_POLITICS));
                }
                break;

            case R.id.search_checkBox_sports:
                if (checked) { listOfStrings.add(Keys.CheckboxFields.CB_SPORTS); }
                else {
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_SPORTS))
                        listOfStrings.remove(listOfStrings.indexOf(Keys.CheckboxFields.CB_SPORTS));
                }
                break;

            case R.id.search_checkBox_travel:
                if (checked) { listOfStrings.add(Keys.CheckboxFields.CB_TRAVEL); }
                else {
                    if (listOfStrings.contains(Keys.CheckboxFields.CB_TRAVEL))
                        listOfStrings.remove(listOfStrings.indexOf(Keys.CheckboxFields.CB_TRAVEL));
                }
                break;
        }
    }

    /**
     * This method is used when the SEARCH BUTTON is clicked.
     * It starts the process of searching for the articles according to the information
     * needed from the user.
     * */
    private void addToSearch (List<String> listOfStrings) {

            //Execute queries, etc.
        }
}

