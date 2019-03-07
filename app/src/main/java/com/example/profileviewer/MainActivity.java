package com.example.profileviewer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private Toolbar mTopToolbar;
    private JSONObject profilesInformation = new JSONObject();
    private ArrayList<JSONObject> profiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        try {
            profilesInformation = new getProfilesInformationFromUrl().execute("https://randomuser.me/api/?results=50").get();
            profiles = getProfilesInformation(profilesInformation);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new ThumbnailAdapter(MainActivity.this, profiles));

        gridView.setOnItemClickListener((parent, v, position, id) -> {

            // Create intent
            Intent intent = new Intent(MainActivity.this, ProfileDetails.class);

            // Pass the JSON with the profile that we can see the profile details
            intent.putExtra("profile", profiles.get(position).toString());

            //Start details activity
            startActivity(intent);
        });

    }


    /**
     * Builds the URL as string
     * @param urlString string to build URL
     * @return image's URL to be loaded into gridview
     * @throws Exception
     */
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    /**
     * Will get the images in semi-asynchronous form
     */
    private class getProfilesInformationFromUrl extends AsyncTask<String, Void, JSONObject> {
        private JSONObject profilesInfo_ = new JSONObject();
        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                profilesInfo_ = new JSONObject(readUrl(strings[0]));
                return profilesInfo_;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Builds all the information to be displayed on profiles
     * @param json JSON retrieved from URL that contains the raw data
     * @return Array with all the information formatted in a proper way
     * @throws JSONException
     */
    private ArrayList<JSONObject> getProfilesInformation(JSONObject json) throws JSONException {
        JSONArray yield;
        JSONObject name ;
        String email;
        JSONObject location;
        JSONObject picture;

        ArrayList<JSONObject> profiles = new ArrayList<>();
        yield = new JSONArray(json.getString("results"));
        for(int i=0; i < yield.length(); i++) {
            JSONObject profileInformation = new JSONObject();


            name = yield.getJSONObject(i).getJSONObject("name");
            email = yield.getJSONObject(i).getString("email");
            location = yield.getJSONObject(i).getJSONObject("location");
            picture = yield.getJSONObject(i).getJSONObject("picture");

            profileInformation.put("name", name.getString("first") + " " +
                                                        name.getString("last"));
            profileInformation.put("email", email);
            profileInformation.put("address", location.getString("street") +
                    ", " + location.getString("city") +
                    ", " + location.getString("state"));

            profileInformation.put("thumbnail", picture.getString("medium"));
            profileInformation.put("profile", picture.getString("large"));
            profiles.add(profileInformation);
        }
        return profiles;
    }



}
