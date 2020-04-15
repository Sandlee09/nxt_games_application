package com.example.networktest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ViewPager_Activity extends AppCompatActivity {

        ViewPager vp;
        int position;
        TabLayout tabLayout;
        LinearLayout menu;
        Activity activity;
        pc mypc;
        playstation myps4;
        xbox myxbox;
        nintendo mynintendo;

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.view_pager);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setTitle("NXT Games");
            setStatusBar();

            activity = this;
            mypc = new pc();
            myps4 = new playstation();
            myxbox = new xbox();
            mynintendo = new nintendo();

            //Initiate Genre Menu
            menu = this.findViewById(R.id.genre_menu);


             //Initiate GenreMenuSwitches class
             GenreMenuSwitches genresOptions = new GenreMenuSwitches(this);



        // Make Genre Button open menu on click
        ImageView genre_button = (ImageView) this.findViewById(R.id.genre_options);
        genre_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                menu.setVisibility(View.VISIBLE);

                menu.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                menu.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;

            }
        });




        // Set Menu's Confirm Button to hide menu and update UI
      Button confirm_button = this.findViewById(R.id.menu_confirm);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {



                menu.getLayoutParams().height= 100;
                menu.getLayoutParams().width= 200;
                menu.setVisibility(View.GONE);

                ArrayList<Integer> chosenGenres = genresOptions.genreChosen;

                mypc.chosenUI(chosenGenres);
                myps4.chosenUI(chosenGenres);
                myxbox.chosenUI(chosenGenres);
                mynintendo.chosenUI(chosenGenres);
            }
        });


        Button reset_button = this.findViewById(R.id.menu_reset);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                menu.getLayoutParams().height= 100;
                menu.getLayoutParams().width= 200;
                menu.setVisibility(View.GONE);

                mypc.updateUI(mypc.games);
                myps4.updateUI(myps4.games);
                myxbox.updateUI(myxbox.games);
                mynintendo.updateUI(mynintendo.games);
            }
        });













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

            adapter.addFragment(mypc);
            adapter.addFragment(myps4);
            adapter.addFragment(myxbox);
            adapter.addFragment(mynintendo);






            //initiate viewpager
            vp = (ViewPager) findViewById(R.id.pager);

            Log.v("View Pager Status: " , ""+ vp);

            //set adapter
            vp.setAdapter(adapter);

            vp.setOffscreenPageLimit(4);

            if(position == 0) {
                tabLayout.setBackgroundColor(Color.parseColor("#414345"));

            }

            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                // This method will be invoked when a new page becomes selected. Animation is not necessarily complete.
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onPageSelected(int position) {

                    switch (position) {
                        case 0: tabLayout.setBackgroundColor(Color.parseColor("#414345"));
                            Log.v("Games array size","" + mypc.games.size());
                            break;




                        case 1: tabLayout.setBackgroundColor(Color.parseColor("#4286F4"));

                            break;


                        case 2: tabLayout.setBackgroundColor(Color.parseColor("#017E01"));
                            break;


                        case 3: tabLayout.setBackgroundColor(Color.parseColor("#ED213A"));
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

