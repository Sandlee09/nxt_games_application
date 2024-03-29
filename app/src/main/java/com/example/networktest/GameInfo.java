package com.example.networktest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class GameInfo extends AppCompatActivity {
    ArrayList<String> imageUrls = new ArrayList<>();
    private String gameName = "hello";
    private String companyName = "";
    SliderLayout sliderShow;
    apiGameInfo allInformation;
    Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game_info);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            sliderShow = (SliderLayout) findViewById(R.id.slider);
            setTitle("NXT Games");
            setStatusBar();


            Bundle extras = getIntent().getExtras();
            gameName = extras.getString("GameID");
            companyName = extras.getString("Company Name");



        Button slider = findViewById(R.id.fullscreen);
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUrls.size() > 0) {
                    Intent fullscreen = new Intent(getApplicationContext(), FullscreenImage.class);
                    fullscreen.putStringArrayListExtra("imageUrls", imageUrls);
                    fullscreen.putExtra("slider position", sliderShow.getCurrentPosition());
                    startActivity(fullscreen);
                }

            }
        });





        //Start Background Task
        new Task1().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);












        AdLoader adLoader = new AdLoader.Builder(this, getString(R.string.game_info_ad_unit_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    ColorDrawable cd = new ColorDrawable(393939);
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(cd).build();

                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

      /* ActionBar mActionBar = getSupportActionBar();
        ScrollView scrollView = findViewById(R.id.game_info_scrollview);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float mfloat = ((ScrollView)findViewById(R.id.game_info_parent)).getScrollY();
                if (mfloat >= 20 && mActionBar.isShowing()) {
                    mActionBar.hide();
                } else if ( mfloat==0 && !mActionBar.isShowing()) {
                    mActionBar.show();
                }
            }
        });*/









        }



    ///
    /// Begining of Async Task 1
    ///
    class Task1 extends AsyncTask<String, Void,Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(GameInfo.this);
            ((ProgressDialog) dialog).setMessage("Loading Game Info...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();

        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Boolean doInBackground(String...process) {

            //Grab Full List of Games for this year and month and add Game ID and Platform ID to arrays

            try {
                allInformation = new apiGameInfo(gameName);
                if (allInformation.getImageIDs() != null) {
                    for (String i : allInformation.getImageIDs()) {
                        String url = "https://images.igdb.com/igdb/image/upload/t_original/" + i +".jpg";
                        imageUrls.add(url);
                    }
                } else {
                    String url = "EMPTY";
                    imageUrls.add(url);
                }

            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }







            return true;
        }



        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Boolean result) {
            updateUI();
            super.onPostExecute(result);
            return;
        }

    }

    private void updateUI() {



        //Add Images to Slider
        for (String i : imageUrls) {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .image(i)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);

            sliderShow.stopAutoCycle();

            sliderShow.addSlider(textSliderView);

        }
        if (imageUrls.size() > 0) {
            sliderShow.setCurrentPosition(0);
        }






        TextView titleTextView = (TextView) findViewById(R.id.game_title);
        titleTextView.setText(gameName);


        TextView companyTextView = (TextView) findViewById(R.id.gameInfo_company);
        if (companyName == "" || companyName == null) {
            companyTextView.setText("Unknown");
        } else {
            companyTextView.setText(companyName);
        }


       // Log.v("GameInfo Summary","" + allInformation.getSummary());

        TextView summaryTextView = (TextView) findViewById(R.id.gameInfo_summary);
        if (allInformation.getSummary() == null) {
            summaryTextView.setText("No Game Summary Currently Available ");
        } else {
            summaryTextView.setText(allInformation.getSummary());
        }


        TextView genreTextView = (TextView) findViewById(R.id.gameInfo_genre);
        if (allInformation.getGenres() == null) {
            genreTextView.setText("Unavailable");
        } else {
            genreTextView.setText(getGenreString(allInformation.getGenres()));
        }



        TextView platformTextView = (TextView) findViewById(R.id.gameInfo_platforms);
        platformTextView.setText(getPlatformString(allInformation.getPlatformIDs()));

        dialog.dismiss();

    }


    public String getGenreString(ArrayList<Integer> genres) {
        String allGenres = "";
        for (int i: genres) {
            switch (i) {

                case 2: allGenres = allGenres + ", Point-and-click"; break;

                case 4: allGenres = allGenres + ", Fighting"; break;

                case 5: allGenres = allGenres + ", Shooter"; break;

                case 7: allGenres = allGenres + ", Music"; break;

                case 8: allGenres = allGenres + ", Platform"; break;

                case 9: allGenres = allGenres + ", Puzzle"; break;

                case 10: allGenres = allGenres + ", Racing"; break;

                case 11: allGenres = allGenres + ", Real Time Strategy (RTS)"; break;

                case 12: allGenres = allGenres + ", Role-playing (RPG)"; break;

                case 13: allGenres = allGenres + ", Simulator"; break;

                case 14: allGenres = allGenres + ", Sport"; break;

                case 15: allGenres = allGenres + ", Strategy"; break;

                case 16: allGenres = allGenres + ", Turn-based strategy (TBS)"; break;

                case 24: allGenres = allGenres + ", Tactical"; break;

                case 25: allGenres = allGenres + ", Hack and slash/Beat 'em up"; break;

                case 26: allGenres = allGenres + ", Quiz/Trivia"; break;

                case 30: allGenres = allGenres + ", Pinball"; break;

                case 31: allGenres = allGenres + ", Adventure"; break;

                case 32: allGenres = allGenres + ", Indie"; break;

                case 33: allGenres = allGenres + ", Arcade"; break;

                case 34: allGenres = allGenres + ", Visual Novel"; break;

                default: allGenres = ",Unavailable";

            }
        }
        allGenres = allGenres.substring(1);
        return allGenres;
    }


    public String getPlatformString(ArrayList<Integer> platforms) {
        String allPlatforms = "";
        for (int i: platforms) {
            switch (i) {

                case 6: allPlatforms = allPlatforms + ", PC"; break;
                case 48: allPlatforms = allPlatforms + ", PS4"; break;
                case 49: allPlatforms = allPlatforms + ", XBox One"; break;
                case 130: allPlatforms = allPlatforms + ", Nintendo Switch"; break;


            }
        }

        if (allPlatforms != "") {
            allPlatforms = allPlatforms.substring(1);
        } else {
            allPlatforms = "Unavailable";
        }

        return allPlatforms;
    }


    // Set Status Bar Color
    public void setStatusBar() {
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(Color.parseColor("#272727"));
    }

    // Set Custom ActionBar
    public void setTitle(String title){
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#272727")));


        LinearLayout actionBar = (LinearLayout)findViewById(R.id.custom_actionbar);
        actionBar.setBackgroundColor(Color.parseColor("#272727"));


        LinearLayout options_background = (LinearLayout) findViewById(R.id.options_icon_background);
        options_background.setVisibility(View.GONE);

    }


    }






