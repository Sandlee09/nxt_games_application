package com.example.networktest;

import java.util.ArrayList;

public class Games extends ArrayList<Games>{
    String Name;
    int Cover;
    int Company;
    int releaseDate;

    public Games(String gameName, int gameCover, int gameCompany, int gameRelease) {

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
