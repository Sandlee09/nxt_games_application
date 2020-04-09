


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


public class makeGameArray {
    final ArrayList<Games> games = new ArrayList<Games>();


    public ArrayList<Games> getGames() {
        return games;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected makeGameArray(String gameID) throws UnirestException, JSONException, IOException {


        //Search Database for all gameID's once and add all necessary information to Games Object Array
        try {
            Unirest.setTimeouts(10000, 10000);
            HttpResponse<String> response = Unirest.post("https://api-v3.igdb.com/games")
                    .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                    .header("Content-Type", "text/plain")
                    .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                    .body("fields name, cover, first_release_date, involved_companies; \nwhere id = (" + gameID + ");\nlimit 500;\n sort first_release_date asc;")
                    .asString();


            //Turn Into JSON and add to Games Array
            JSONArray gameArray = new JSONArray(response.getBody());
            int arrayLength = gameArray.length();

            for (int i = 0; i < arrayLength; i++) {
                JSONObject gameObject = gameArray.getJSONObject(i);

                String gameName = (String) gameObject.get("name");

                boolean hasCover = gameObject.has("cover");
                int gameCover = 0;
                if (hasCover == true) {
                    gameCover = (int) gameObject.get("cover");
                }


                boolean hasCompanyArray = gameObject.has("involved_companies");
                int gameCompany = 0;
                if (hasCompanyArray == true) {
                    JSONArray companyArray = gameObject.getJSONArray("involved_companies");
                    gameCompany = (int) companyArray.get(0);

                }

                int gameRelease = (int) gameObject.get("first_release_date");


                games.add(new Games(gameName, gameCover, gameCompany, gameRelease));
            }




        } catch (UnirestException | JSONException e) {
            Log.v("Error Occurred", " Line 125");
        }  Unirest.shutdown();

    }





}