package com.example.networktest;

import android.util.Log;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

public class getAPI {

    //REMOVE THIS IF YOU USE IT, IF NOT DELETE ENTIRE GETAPI JAVA FILE


   public HttpsURLConnection getConnection(String vUrl) throws IOException, UnirestException {
       URL url = new URL(vUrl);
       HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

       connection.setRequestMethod("POST");
       connection.setRequestProperty("user-key", "ae905519b935239aa1d5ddd574f0563a");
       connection.setRequestProperty("Content-Type", "application/json; utf-8");
       connection.setRequestProperty("Accept", "application/json");





       return connection;
   }

   public String getJSON(HttpsURLConnection connection) throws IOException {
       InputStream inputStream = connection.getInputStream();
       StringBuilder output = new StringBuilder();
       if (inputStream != null) {
           InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
           BufferedReader reader = new BufferedReader(inputStreamReader);
           String line = reader.readLine();
           while (line != null) {
               output.append(line);
               line = reader.readLine();
           }
           reader.close();
       }

       return output.toString();
   }

}
