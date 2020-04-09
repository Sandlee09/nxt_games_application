package com.example.networktest;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;

import java.util.ArrayList;

public class Games extends ArrayList<Games>{
    String Name;
    int Cover;
    int Company;
    String companyName;



    int releaseDate;
    String coverURL;



    public Games(String gameName, int gameCover, int gameCompany, int gameRelease) throws UnirestException, JSONException {

        Name = gameName;
        Cover = gameCover;
        Company = gameCompany;
        releaseDate = gameRelease;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
