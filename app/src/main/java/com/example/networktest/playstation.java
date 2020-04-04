package com.example.networktest;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class playstation extends Fragment{
    ArrayList<Long> vGameID ;
    ArrayList<Integer> vPlatforms;
    final ArrayList<Games> games = new ArrayList<Games>();

    public playstation() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_list, container, false);

        new backgroundTasks().execute();






        //Create a Layout Adapter instance
        final Layout_Adapter adapter = new Layout_Adapter(getContext(), 0, games, "#EFA21D");

        //Create a new listView set it to id of the rootView in activity_numbers
        ListView listView = (ListView) view.findViewById(R.id.list);

        //setAdapter to listView
        listView.setAdapter(adapter);


        return view;
    }


    public void updateUI() {
        TextView textView = (TextView) getActivity().findViewById(R.id.text_view);
        textView.setText(vGameID.get(5).toString());
    }

    class backgroundTasks extends AsyncTask {

        private Object fullList;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Games doInBackground(Object[] objects) {




            fullList listOfGames = new fullList();
            try {
                listOfGames.fullList();
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            vGameID = listOfGames.getGameID();
            vPlatforms = listOfGames.getPlatform();



            for (int i = 0; i < vGameID.size(); i++) {
                if (vPlatforms.get(i) == 48) {
                   String gameIdString =  Long.toString(vGameID.get(i));

                    try {
                        HttpResponse<String> response = Unirest.post("https://api-v3.igdb.com/games")
                                .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                                .header("Content-Type", "text/plain")
                                .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                                .body("fields name, cover, first_release_date, involved_companies; \nwhere id = " + gameIdString +";")
                                .asString();


                        //Turn Into JSON and add to Games Array
                        JSONArray gameArray = new JSONArray(response.getBody());
                        JSONObject gameObject = gameArray.getJSONObject(0);
                        Log.v("Object", "" + gameObject);

                        String gameName = (String) gameObject.get("name");
                        int gameCover = (int) gameObject.get("cover");

                        JSONArray companyArray = gameObject.getJSONArray("involved_companies");
                        int gameCompany = (int)companyArray.get(0);
                        int gameRelease = (int) gameObject.get("first_release_date");

                        games.add(new Games(gameName, gameCover, gameCompany, gameRelease));

                    } catch (UnirestException | JSONException e) {
                        e.printStackTrace();
                    }

                }
            }



            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            updateUI();
        }

    }


}