package com.example.arun.inclass07group14v1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Arun on 10/23/2017.
 */

public class ITuneSearchAsync extends AsyncTask {


    IMusicData musicData;
    ArrayList<ItuneApp> musicResults= new ArrayList<ItuneApp>();

    public ITuneSearchAsync(IMusicData musicData)
    {
        this.musicData = musicData;
    }

    @Override
    protected void onPostExecute(Object o) {
       musicData.setMusicSearchResult(musicResults);
    }

    @Override
    protected void onPreExecute() {
        musicData.startProgressDialog();;
    }



    @Override
    protected Object doInBackground(Object[] objects) {
        RequestParams requestParams = new RequestParams("GET","https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");

        HttpURLConnection connection = (HttpURLConnection) requestParams.setupConnection();
        try {
            connection.connect();
            StringBuilder sb= new StringBuilder();
            String line="";
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                BufferedReader br= new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line=br.readLine())!=null)
                {
                    sb.append(line);
                }

                //  Log.d("Demo",sb.toString());
                musicResults=ITuneUtil.JSONApps(sb.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Demo",musicResults.toString());

        return musicResults;








    }


    public  interface IMusicData
    {
        void setMusicSearchResult(ArrayList<ItuneApp> tracks);
       void startProgressDialog();


        // void startProgressbar();
        //void stopProgressbar();
    }

}
