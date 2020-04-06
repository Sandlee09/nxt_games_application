package com.example.networktest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class playstation extends Fragment{
    ArrayList<Long> vGameID ;
    ArrayList<Integer> vPlatforms;
    ArrayList<Games> games = new ArrayList<Games>();
    View view;
    ProgressDialog dialog;


    String image;



    public playstation() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_list, container, false);




            new Task2().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            new backgroundTasks().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);


        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateUI() {

        //Create Calendar Object for dates
        Calendar calendar = new Calendar();
        int monthNumber = calendar.getMonthNumber();
        int yearNumber = calendar.getY();



        //Final Check to remove all games with incorrect release dates that are not equal to this month's
        for (int i = games.size()-1; i >= 0 ; i--) {

                String date = calendar.getUnixToDate(games.get(i).releaseDate);

                int releaseMonth = Integer.parseInt(date.substring(0,2));
                int releaseYear = Integer.parseInt(date.substring(6));


                boolean sameMonth = monthNumber != releaseMonth;
                boolean sameYear = yearNumber != releaseYear;


                if (sameMonth || sameYear) {
                    games.remove(i);
                }

            dialog.hide();

        }




        //Create a Layout Adapter instance
        final Layout_Adapter adapter = new Layout_Adapter(getContext(), 0, games, "#EFA21D");

        //Create a new listView set it to id of the rootView in activity_numbers
        ListView listView = (ListView) view.findViewById(R.id.list);

        //setAdapter to listView
        listView.setAdapter(adapter);
    }






    class backgroundTasks extends AsyncTask <String, Void,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Processing...");
            dialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Boolean doInBackground(String...process) {


                //Grab Full List of Games for this year and month and add Game ID and Platform ID to arrays
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


                //Create a string of all gameID for PS4 Platform to search Database only once
                String gameIdString = "";
                for (int i = vGameID.size() - 1; i >= 0; i--) {

                    if (vPlatforms.get(i) == 48) {
                        gameIdString += "," + Long.toString(vGameID.get(i));
                    }
                }

                gameIdString = gameIdString.substring(2);
                Log.v("gameID list ", "" + gameIdString);


                //Empty Out Unused Array's for memory
                for (int i = vGameID.size() - 1; i >= 0; i--) {
                    vGameID.remove(i);
                    vPlatforms.remove(i);
                }




            //Search Database for all gameID's once and add all necessary information to Games Object Array
               /* try {

                    HttpResponse<String> response = Unirest.post("https://api-v3.igdb.com/games")
                            .header("user-key", "ae905519b935239aa1d5ddd574f0563a")
                            .header("Content-Type", "text/plain")
                            .header("Cookie", "__cfduid=df1974e12093c47d3cf52f64eb47233c31585872828")
                            .body("fields name, cover, first_release_date, involved_companies; \nwhere id = (" + gameIdString + ");\nlimit 500;\n sort first_release_date asc;")
                            .asString();


                    //Turn Into JSON and add to Games Array
                    JSONArray gameArray = new JSONArray(response.getBody());
                    int arrayLength = gameArray.length();

                    for (int i = 0; i < arrayLength; i++) {
                        JSONObject gameObject = gameArray.getJSONObject(i);
                        Log.v("Object", "" + gameObject);

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


                    Unirest.shutdown();

                } catch (UnirestException | JSONException | IOException e) {
                    Log.v("Error Occurred", " Line 125");
                }
*/

                    //
            //  Everything was working up to here
            //Everything was working up to here
            //Everything was working up to here
            //Everything was working up to here
            //Everything was working up to here
            //Everything was working up to here




            try {
                makeGameArray makeGameArray = new makeGameArray(gameIdString);
                games = makeGameArray.getGames();
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }



        //Once onBackground is done onPostExecute runs and calles updateUI()
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Boolean result) {

            if (result == true) {
                Log.v("onPost" , "is being executed");

                updateUI();

            }

            super.onPostExecute(result);
            return;
        }

    }







    class Task2 extends AsyncTask <String, Void,Boolean> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Boolean doInBackground(String... strings) {
            imageArray imageArray = null;
            try {
                imageArray = new imageArray();
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v("Image", "" + imageArray.getImage());

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
                Log.v("Task 2 Done", " is done");
                Thread.interrupted();
        }
    }




}
