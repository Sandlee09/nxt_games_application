
package com.example.networktest;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class getCompanyCode {
    ArrayList<Integer> companyNumber = new ArrayList<>();
    ArrayList<Integer> gameIDs = new ArrayList<>();
    ArrayList<String> companyName = new ArrayList<>();
    String companyNumbersString = "";

    public ArrayList<String> getCompanyName() {
        return companyName;
    }

    public ArrayList<Integer> getCompanyNumber() {
        return companyNumber;
    }

    public ArrayList<Integer> getGameIDs() {
        return gameIDs;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected getCompanyCode(String companyIDs) throws UnirestException, JSONException, IOException {

        ////
        //// Begining Task 1
        ////

        HttpResponse<String> response = null;
        try {

            Unirest.setTimeouts(10000, 10000);
            response = Unirest.post("https://api-v3.igdb.com/involved_companies")
                    .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                    .header("Content-Type", "text/plain")
                    .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                    .body("fields company;\nwhere id = ("+ companyIDs  +");\nlimit 500;")
                    .asString();


            JSONArray coverArray = new JSONArray(response.getBody());

            //Get all necessary info from JSON array
            for (int i = 0; i < coverArray.length(); i++) {
                JSONObject jsonObject = coverArray.getJSONObject(i);
                Integer company = jsonObject.getInt("company");
                companyNumber.add(company);
                companyNumbersString += "," + company;


                Integer jsonID = jsonObject.getInt("id");
                gameIDs.add(jsonID);
            }

            companyNumbersString = companyNumbersString.substring(1);


        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        } Unirest.shutdown();


        ///
        ///
        /// Beginning Task 2
        ///
        ///
        String[] temporary = new String[companyNumber.size()];


        try {
            Unirest.setTimeouts(10000, 10000);
            HttpResponse<String> response2 = Unirest.post("https://api-v3.igdb.com/companies")
                    .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                    .header("Content-Type", "text/plain")
                    .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                    .body("fields name;\nwhere id = (" + companyNumbersString  + ");\nlimit 500;")
                    .asString();



            JSONArray coverArray = new JSONArray(response2.getBody());


            for (int i = 0; i < coverArray.length(); i++) {
                JSONObject jsonObject = coverArray.getJSONObject(i);

                String company = jsonObject.getString("name");

                int companyNum = jsonObject.getInt("id");


                //Cycle through companyNumber Array to see if it matches current objects companyNum
                //In order to make sure temporary array is arranged the same
                inner: for (int y = 0; y < companyNumber.size(); y++) {

                    if (companyNum == companyNumber.get(y)) {
                        temporary[y] = company;
                        break inner;
                    }

                }

            }




        } catch (UnirestException | JSONException e) {
            e.printStackTrace();
        }
        Unirest.shutdown();



        //Add placeholder info to temporary array for missing developer names and then add that information into Array List
        for (int i = 0; i< temporary.length; i++) {
            if (temporary[i] == null) {
                temporary[i] = "Unknown";
            }

            companyName.add(temporary[i]);

        }

    }



}