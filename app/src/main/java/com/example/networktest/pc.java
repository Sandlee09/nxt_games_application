package com.example.networktest;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class pc extends Fragment{
    ArrayList<Long> vGameID ;
    ArrayList<Integer> vPlatforms;
    ArrayList<String> coverURL ;
    ArrayList<Games> games = new ArrayList<Games>();
    ArrayList<Games> modifiedGames = new ArrayList<Games>();
    ArrayList<String> developerName = new ArrayList<>();
    View view;
    ProgressDialog dialog;
    boolean tasksDone = false;
    CountDownTimer timer;




    //Platform ID
    // PC = 6
    // PS4 = 48
    // Xbox One = 49
    // Switch = 130
    private final int platformID = 6;


    public pc() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_list, container, false);

        //Set BackGround
        view.setBackgroundResource(R.drawable.midnight_city);



        ((ViewGroup) getActivity().findViewById(R.id.genre_menu)).getLayoutTransition()
                .enableTransitionType(LayoutTransition.CHANGING);

        ((ViewGroup) getActivity().findViewById(R.id.genre_menu)).getLayoutTransition()
                .enableTransitionType(LayoutTransition.CHANGING);





        //Countdown Timer for bad Internet Connection
         timer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.v("Timer","" + millisUntilFinished);
            }

            public void onFinish() {
               if (tasksDone == false) {
                   //Hide loading circle
                   dialog.dismiss();

                   LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View dialog_layout = inflater.inflate(R.layout.dialog_layout,(ViewGroup) getActivity().findViewById(R.id.dialog_root));
                    AlertDialog.Builder db = new AlertDialog.Builder(getContext());
                    db.setCancelable(false);
                    db.setView(dialog_layout);
                    Button button = (Button) dialog_layout.findViewById(R.id.noInternet_button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent mainActivity = new Intent (getContext(), MainActivity.class);
                            startActivity(mainActivity);
                        }
                    });
                    AlertDialog dialog = db.show();
                       }

            }
        }.start();


        //Start Tasks
        new pc.Task1().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        new pc.Task2().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        new pc.Task3().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);











        return view;
    }



    //Called when menu Genre Confirm Button is clicked to update the list
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void chosenUI(ArrayList<Integer> chosenGenres) {
        modifiedGames.clear();
        updateUI(modifiedGames);
        if (chosenGenres.size() != 0) {
            for (int i = 0; i < games.size(); i++) {
                ArrayList<Integer> currentGamesGenre = games.get(i).getGenres();
                outer: for (int x = 0; x < currentGamesGenre.size(); x++) {
                    for (int y = 0; y < chosenGenres.size(); y++) {
                        if (currentGamesGenre.get(x) == chosenGenres.get(y) ) {
                            modifiedGames.add(games.get(i));
                            break outer;
                        }
                    }

                }
            }

            updateUI(modifiedGames);

        }
    }




    ///
    /// Begining of Async Task 1
    ///
    class Task1 extends AsyncTask <String, Void,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading Upcoming Games...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();




        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Boolean doInBackground(String...process) {

            //Grab Full List of Games for this year and month and add Game ID and Platform ID to arrays
            fullList listOfGames = new fullList();
            try {
                listOfGames.fullList();
            } catch (UnirestException | IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            vGameID = listOfGames.getGameID();
            vPlatforms = listOfGames.getPlatform();


            //Create a string of all gameID for PS4 Platform to search Database only once
            String gameIdString = "";
            for (int i = vGameID.size() - 1; i >= 0; i--) {

                if (vPlatforms.get(i) == platformID) {
                    gameIdString += "," + Long.toString(vGameID.get(i));
                }
            }

            if (gameIdString.length() != 0) {
                gameIdString = gameIdString.substring(1);
            }




            //Empty Out Unused Array's for memory
            for (int i = vGameID.size() - 1; i >= 0; i--) {
                vGameID.remove(i);
                vPlatforms.remove(i);
            }



            //Make a Game Array with all the PS4 Games Information for the Month including, name, gamecoverID, release date, and companyID
            try {
                makeGameArray makeGameArray = new makeGameArray(gameIdString);
                games = makeGameArray.getGames();
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }



        //Once onBackground is done onPostExecute runs and calles updateUI()
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Boolean result) {


            if (games.size() != 0) {
                timer.cancel();
            }


            super.onPostExecute(result);
        }

    }




    ///
    /// Begining of Async Task 2
    ///
     class Task2 extends AsyncTask <String, Void,Boolean> {


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Boolean doInBackground(String... process) {


            //Create String of companyID's to search database Once
            String companyIdString = "";
            for (int i = 0; i<games.size(); i++) {
                companyIdString += "," + Integer.toString(games.get(i).getCompany());
            }

            if (companyIdString.length() != 0) {
                companyIdString = companyIdString.substring(1);
            }



            //Run HTTP request to get all of the companyNames
            getCompanyCode companyNamesArray = null;
            try {
                companyNamesArray = new getCompanyCode(companyIdString);
                developerName = companyNamesArray.getCompanyName();
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            //Search and Match Company Names to correct Game Object
            for (int i = 0; i<developerName.size(); i++) {
                int companyID = companyNamesArray.getGameIDs().get(i);

                for (int x = 0; x < games.size(); x++) {
                    if (games.get(x).getCompany() == companyID) {
                        games.get(x).setCompanyName(developerName.get(i));
                    }
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }





    ///
    /// Begining of Async Task 3
    ///
     class Task3 extends AsyncTask <String, Void,Boolean> {


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Boolean doInBackground(String... process) {

            //Create String of CoverID's to search database Once
            String coverIdString = "";
            for (int i = 0; i<games.size(); i++) {
                coverIdString += "," + Integer.toString(games.get(i).getCover());
            }

            if (coverIdString.length() != 0) {
                coverIdString = coverIdString.substring(1);
            }


            //Run HTTP request to get all of the covers
            imageArray imageArray = null;
            try {
                imageArray = new imageArray(coverIdString);
                coverURL = imageArray.getCoverUrls();
            } catch (UnirestException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            //Search and Match Cover URL to correct Game Object
            for (int i = 0; i<coverURL.size(); i++) {
                int coverID = imageArray.getCoverID().get(i);

                for (int x = 0; x < games.size(); x++) {
                    if (games.get(x).getCover() == coverID) {
                        games.get(x).setCoverURL(coverURL.get(i));
                    }
                }
            }

            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            updateUI(games);
        }
    }





    //Method to Update UI, called on final async task's onPostExecute
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateUI(ArrayList list) {


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


            //Hide loading circle
            dialog.dismiss();
            tasksDone = true;

        }


        //Create a Layout Adapter instance
        final Layout_Adapter adapter = new Layout_Adapter(getContext(), 0, list);

        //Create a new listView set it to id of the rootView in activity_numbers
        ListView listView = (ListView) view.findViewById(R.id.list);

        //setAdapter to listView
        listView.setAdapter(adapter);


        //Empty Out Unused Array's for memory
        for (int i = coverURL.size() - 1; i >= 0; i--) {
            coverURL.remove(i);
        }

        for (int i = developerName.size() - 1; i >= 0; i--) {
            developerName.remove(i);
        }


    }




}
