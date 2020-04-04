package com.example.networktest;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ViewPager_Activity extends AppCompatActivity {

        ViewPager vp;
        int position;
        CharSequence actionBarTitle;
        ActionBar actionBar;
        TabLayout tabLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.view_pager);

            //Set ActionBar Up button
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

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


        private void setupViewPager () {


            //adapter for the page view
            final SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

            //add fragments to the adapter
            adapter.addFragment(new playstation());

            //initiate viewpager
            vp = (ViewPager) findViewById(R.id.pager);

            Log.v("View Pager Status: " , ""+ vp);

            //set adapter
            vp.setAdapter(adapter);


            if(position == 0) {
                tabLayout.setBackgroundColor(Color.parseColor("#1DACEF"));
                actionBarTitle = adapter.getPageTitle(position);
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1DACEF")));
                actionBar.setTitle(actionBarTitle);
                Log.v("Position Value: ", "" + position);
            }

            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                // This method will be invoked when a new page becomes selected. Animation is not necessarily complete.
                @Override
                public void onPageSelected(int position) {

                    switch (position) {
                        case 0: tabLayout.setBackgroundColor(Color.parseColor("#1DACEF"));
                            actionBarTitle = adapter.getPageTitle(position);
                            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1DACEF")));
                            actionBar.setTitle(actionBarTitle);
                            Log.v("Position Value: ", "" + position);break;



                        case 1: vp.setCurrentItem(position);
                            tabLayout.setBackgroundColor(Color.parseColor("#EFA21D"));
                            actionBarTitle = adapter.getPageTitle(position);
                            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EFA21D")));
                            actionBar.setTitle(actionBarTitle);
                            break;


                        case 2: tabLayout.setBackgroundColor(Color.parseColor("#017E01"));
                            actionBarTitle = adapter.getPageTitle(position);
                            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#017E01")));
                            actionBar.setTitle(actionBarTitle);
                            break;


                        case 3: tabLayout.setBackgroundColor(Color.parseColor("#4F017E"));
                            actionBarTitle = adapter.getPageTitle(position);
                            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4F017E")));
                            actionBar.setTitle(actionBarTitle);
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

