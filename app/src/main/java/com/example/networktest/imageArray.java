package com.example.networktest;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class imageArray {
    String image;

    public String getImage() {
        return image;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected imageArray() throws UnirestException, JSONException {


        Log.v("Async Task 2 Running","");
        try {
            HttpResponse<String> response = Unirest.post("https://api-v3.igdb.com/covers")
                    .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                    .header("Content-Type", "text/plain")
                    .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                    .body("fields *;")
                    .asString();



            JSONArray coverArray = new JSONArray(response.getBody());
            JSONObject jsonObject = coverArray.getJSONObject(0);


            String url = jsonObject.getString("url");

            image = url;


        } catch (UnirestException | JSONException e) {
            Log.v("Network Exception", "");
        }








    }
}