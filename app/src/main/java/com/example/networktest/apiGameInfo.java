
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


public class apiGameInfo {

    ArrayList<Integer> screenshotIDs = new ArrayList<>();
    ArrayList<Integer> platformIDs = new ArrayList<>();
    ArrayList<Integer> genres = new ArrayList<>();
    ArrayList<String> imageIDs = new ArrayList<>();
    String summary = "";


    public ArrayList<Integer> getScreenshotIDs() {
        return screenshotIDs;
    }

    public ArrayList<Integer> getPlatformIDs() {
        return platformIDs;
    }

    public ArrayList<Integer> getGenres() {
        return genres;
    }

    public String getSummary() {
        return summary;
    }

    public ArrayList<String> getImageIDs() {
        return imageIDs;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected apiGameInfo(String gameName) throws UnirestException, JSONException, IOException {

        ////
        //// Begining Task 1
        ////

        try {

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post("https://api-v3.igdb.com/games")
                    .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                    .header("Content-Type", "text/plain")
                    .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                    .body("fields genres, platforms, screenshots, summary; \nwhere name = \""+ gameName +"\";")
                    .asString();




            JSONArray jsonArray = new JSONArray(response.getBody());
            JSONObject gameObject = jsonArray.getJSONObject(0);


            boolean hasSummary = gameObject.has("summary");
            if (hasSummary == true) {
                String gameSummary = gameObject.getString("summary");
                summary = gameSummary;
            }



            boolean hasScreens = gameObject.has("screenshots");
            if (hasScreens == true) {
                JSONArray screenshotArray = gameObject.getJSONArray("screenshots");
                if (screenshotArray != null) {
                    for (int i = 0; i < screenshotArray.length(); i++) {
                        Integer screenshot = screenshotArray.getInt(i);
                        screenshotIDs.add(screenshot);
                    }
                }
            }



            boolean hasGenre = gameObject.has("genres");
            if (hasGenre == true) {
                JSONArray genreArray = gameObject.getJSONArray("genres");
                for (int i = 0; i < genreArray.length(); i++) {
                    Integer genre = genreArray.getInt(i);
                    genres.add(genre);
                }
            } else genres.add(0);


            boolean hasPlatform = gameObject.has("platforms");
            if (hasPlatform == true) {
                JSONArray consoleArray = gameObject.getJSONArray("platforms");
                if (consoleArray != null) {
                    for (int i = 0; i < consoleArray.length(); i++) {
                        Integer console = consoleArray.getInt(i);
                        platformIDs.add(console);
                    }
                }
            }






        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        } Unirest.shutdown();


        ///
        ///
        /// Beginning Task 2
        ///
        ///

        //Create a string of all gameID for PS4 Platform to search Database only once
      if (screenshotIDs.size() != 0 ) {
          String screenshotString = "";
          for (int i = screenshotIDs.size() - 1; i >= 0; i--) {

              screenshotString += "," + Long.toString(screenshotIDs.get(i));
          }

          if (screenshotString.length() != 0) {
              screenshotString = screenshotString.substring(1);
          }

          Unirest.setTimeouts(0, 0);
          HttpResponse<String> response = Unirest.post("https://api-v3.igdb.com/screenshots")
                  .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                  .header("Content-Type", "text/plain")
                  .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                  .body("fields image_id;\nwhere id =  (" + screenshotString +");")
                  .asString();

          JSONArray jsonArray = new JSONArray(response.getBody());

          for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);
              String image = jsonObject.getString("image_id");
              imageIDs.add(image);
          }

      }




    }



}