package com.example.networktest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Main Button Creation
        TextView phrasesButton =findViewById(R.id.test_button);
        phrasesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPhrasesActivity = new Intent (getApplicationContext(), ViewPager_Activity.class);
                openPhrasesActivity.putExtra("viewpager_position", 0);
                startActivity(openPhrasesActivity);
            }
        });


        ImageView imageView = findViewById(R.id.cover_image);
        Picasso.get().load("https://images.igdb.com/igdb/image/upload/t_thumb/co1ui1.jpg").into(imageView);

    }


}
