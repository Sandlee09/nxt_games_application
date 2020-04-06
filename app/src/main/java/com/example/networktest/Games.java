package com.example.networktest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Games extends ArrayList<Games>{
    String Name;
    int Cover;
    int Company;
    int releaseDate;

    public Games(String gameName, int gameCover, int gameCompany, int gameRelease) throws UnirestException, JSONException {

        Name = gameName;
        Cover = gameCover;
        Company = gameCompany;
        releaseDate = gameRelease;
    }


    public String getName() {
        return Name;
    }

    public int getCover() {
        return Cover;
    }

    public int getCompany() {
        return Company;
    }

    public int getReleaseDate() {
        return releaseDate;
    }
}
