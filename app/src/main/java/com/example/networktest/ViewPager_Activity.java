package com.example.networktest;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ViewPager_Activity extends AppCompatActivity {

        ViewPager vp;
        int position;
        TabLayout tabLayout;

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.view_pager);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setTitle("NXT Games");
            setStatusBar();





            //Tab Text Color
            tabLayout = findViewById(R.id.tab_layout);
            tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"));

            //Get Tab Position
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                position = extras.getInt("viewpager_position");
            }

            setupViewPager();


             vp.setCurrentItem(position);


            }

        // Set Status Bar Color
        public void setStatusBar() {
            Window window = this.getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(Color.parseColor("#393939"));
        }

        // Set Custom ActionBar
        public void setTitle(String title){
            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_actionbar);
            getSupportActionBar().setElevation(0);
            View view = getSupportActionBar().getCustomView();
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#393939")));

            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);

        }


        private void setupViewPager () {


            //adapter for the page view
            final SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

            //add fragments to the adapter
            adapter.addFragment(new pc());
            //adapter.addFragment(new playstation());



            //initiate viewpager
            vp = (ViewPager) findViewById(R.id.pager);

            Log.v("View Pager Status: " , ""+ vp);

            //set adapter
            vp.setAdapter(adapter);


            if(position == 0) {
                tabLayout.setBackgroundColor(Color.parseColor("#414345"));
                Log.v("Position Value: ", "" + position);
            }

            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                // This method will be invoked when a new page becomes selected. Animation is not necessarily complete.
                @Override
                public void onPageSelected(int position) {

                    switch (position) {
                        case 0: tabLayout.setBackgroundColor(Color.parseColor("#414345"));
                            Log.v("Position Value: ", "" + position);break;



                        case 1: //vp.setCurrentItem(position);
                            tabLayout.setBackgroundColor(Color.parseColor("#4286F4"));
                            break;


                        case 2: tabLayout.setBackgroundColor(Color.parseColor("#017E01"));
                            break;


                        case 3: tabLayout.setBackgroundColor(Color.parseColor("#4F017E"));
                            break;
                    }



                }

                // This method will be invoked when the current page is scrolled, either as part of
                // a programmatically initiated smooth scroll or a user initiated touch scroll.
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                // Called when the scroll state changes. Useful for discovering when the user begins
                // dragging, when the pager is automatically settling to the current page,
                // or when it is fully stopped/idle.
                @Override
                public void onPageScrollStateChanged(int state) {
                    // Your code
                }
            });

        }




}

