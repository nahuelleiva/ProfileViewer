package com.example.profileviewer;

import android.widget.*;
import android.view.*;
import android.content.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * ThumbnailAdapter: controller that works as mediator to hold thumbnails
 */
public class ThumbnailAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<JSONObject> images;

    /**
     * Thumbnail constructor
     * @param context application context
     * @param images_ list of images to load into the gridview.
     */
    public ThumbnailAdapter(Context context, ArrayList images_) {
        super(context, R.layout.grid_item_layout, images_);
        this.context = context;
        this.images = images_;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // In case some image is not loaded correctly, we take care of it putting a default
        // placeholder
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_layout, parent, false);
            ImageView profileThumbnail = convertView.findViewById(R.id.imageToDisplay);

            try {
                Glide
                        .with(context)
                        .load(images.get(position).getString("thumbnail"))
                        .apply(options)
                        .into(profileThumbnail);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }
}
