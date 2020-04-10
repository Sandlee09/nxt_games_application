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
    ArrayList<String> coverUrls = new ArrayList<>();
    ArrayList<Integer> coverID = new ArrayList<>();

    public ArrayList<String> getCoverUrls() {
        return coverUrls;
    }

    public ArrayList<Integer> getCoverID() {
        return coverID;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected imageArray(String coverIdList) throws UnirestException, JSONException, IOException {


        Log.v("Async Task 2 Running","");
        try {
            Unirest.setTimeouts(10000, 10000);
            HttpResponse<String> response = Unirest.post("https://api-v3.igdb.com/covers")
                    .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                    .header("Content-Type", "text/plain")
                    .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                    .body("fields *; \nwhere id = (" + coverIdList +");\nlimit 500;")
                    .asString();




            JSONArray coverArray = new JSONArray(response.getBody());

            //get all necessary info from json array
            for (int i = 0; i < coverArray.length(); i++) {
                JSONObject jsonObject = coverArray.getJSONObject(i);
                String url = jsonObject.getString("image_id");
                coverUrls.add(url);
                Integer jsonID = jsonObject.getInt("id");
                coverID.add(jsonID);
            }




        } catch (UnirestException | JSONException e) {
            Log.v("Network Exception", "");
        }


        Unirest.shutdown();





    }
}