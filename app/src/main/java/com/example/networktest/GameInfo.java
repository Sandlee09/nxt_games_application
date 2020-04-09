package com.example.networktest;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class GameInfo extends AppCompatActivity {
    ArrayList<String> imageUrls = new ArrayList<>();
    private String gameName = "hello";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game_info);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            SliderLayout sliderShow = (SliderLayout) findViewById(R.id.slider);


            Bundle extras = getIntent().getExtras();
            String Value = extras.getSerializable("GameID").toString();
            Log.v("Game Name","" + Value);


            imageUrls.add("https://images.igdb.com/igdb/image/upload/t_cover_big/co20m9.jpg");
            imageUrls.add("https://images.igdb.com/igdb/image/upload/t_original/hcfpcjiout0cmtttfj5z.jpg");


            //Add Images to Slider
            for (String i : imageUrls) {
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                textSliderView
                        .description("Game of Thrones")
                        .image(i)
                        .setScaleType(BaseSliderView.ScaleType.CenterInside);

                sliderShow.stopAutoCycle();

                sliderShow.addSlider(textSliderView);
            }






        }





    }
