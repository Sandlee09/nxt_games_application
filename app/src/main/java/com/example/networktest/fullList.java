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


public class fullList {
    String thisMonth, thisYear;
    ArrayList<Long> gameID = new ArrayList<>();
    ArrayList<Integer> platform = new ArrayList<>();
    ArrayList<String> releaseDate = new ArrayList<String>();



    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void fullList() throws UnirestException, JSONException, IOException {



        //Get month number and year
        Calendar calendar = new Calendar();
        thisMonth = Integer.toString(calendar.getMonthNumber());
        thisYear = Integer.toString(calendar.getY());


        //Start Async Task
        try {

            Unirest.setTimeouts(10000, 10000);
            HttpResponse<String> response = Unirest.post("https://api-v3.igdb.com/release_dates")
                .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                .header("Content-Type", "text/plain")
                .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                .body("fields *;\nwhere platform = (6,48,49,130) & y =" + thisYear + "& m = " + thisMonth + ";\nsort date desc;\nlimit 500;")
                .asString();


        JSONArray gamesArray = new JSONArray(response.getBody());



        for (int i = 0; i < gamesArray.length(); i++) {
            JSONObject game1 = gamesArray.getJSONObject(i);
            long vGameID = game1.getLong("game");
            int vPlatform = game1.getInt("platform");
            String vReleaseDate = game1.getString("human");


            gameID.add(vGameID);
            platform.add(vPlatform);
            releaseDate.add(vReleaseDate);


        }



    } catch (UnirestException | JSONException e) {
        Log.v("Exception called", "");
    }  Unirest.shutdown();

    }





    //Get Array Items
    public ArrayList<Long> getGameID() {
        return gameID;
    }

    public ArrayList<Integer> getPlatform() {
        return platform;
    }



}
