package com.example.profileviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.*;

public class ProfileDetails extends AppCompatActivity {
    private JSONObject profileInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_details);

        // We ge the profile information
        if (getIntent().hasExtra("profile")) {
            String stringProfileInfo = getIntent().getStringExtra("profile");
            try {
                profileInformation = new JSONObject(stringProfileInfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Set the image to display and the profile text
        TextView titleTextView = findViewById(R.id.profileText);
        ImageView imageView = findViewById(R.id.imageToDisplay);

        try {

            // In case we have some problems during the information retrieving,
            // we set a default placeholder.
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            // Load the images in the holder
            Glide
                    .with(this)
                    .load(profileInformation.getString("profile"))
                    .apply(options)
                    .into(imageView);

            // Set the profile information
            titleTextView.append("Name: " + profileInformation.getString("name") +  "\n \n");
            titleTextView.append("Email: " + profileInformation.getString("email") + "\n \n");
            titleTextView.append("Address: " + profileInformation.getString("address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // We have a button to go back
        Button goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(v -> finish());
    }


}
