package com.atritripathi.tealthcaresampleapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentTwo extends Fragment {

    private String IMAGE_URL;
    private static ProgressBar circularProgressBar;
    private static ImageView imageView;
    private static TextView errorMessage;

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two, container, false);
    }


    /**
     * Came up with a better way to check for internet connection, which is, to directly ping
     * the Google's servers(whose chances of going down is very less). It is better than using
     * Network Manager because, although we might be connected to the cell or wifi network but
     * it is largely possible that there might be no Internet connection.
     * @return
     */
    public boolean isConnected() {
        boolean value = false;
        try {
            String command = "ping -c 1 google.com";
            value = Runtime.getRuntime().exec(command).waitFor() == 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }



    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button loadImage = view.findViewById(R.id.button_refresh);
        imageView = view.findViewById(R.id.iv_load_image);
        errorMessage = view.findViewById(R.id.tv_error_msg);
        circularProgressBar = view.findViewById(R.id.progressBar_circle);

        errorMessage.setText("Image Placeholder");

        /**
         * List of five URL's which all point to HD images to be downloaded.
         */
        final ArrayList<String> imageUrlList = new ArrayList<>();
        imageUrlList.add("https://wallpapercave.com/wp/6K44j5E.jpg");
        imageUrlList.add("https://wallpaper-house.com/data/out/4/wallpaper2you_35911.jpg");
        imageUrlList.add("http://7-themes.com/data_images/collection/8/4493158-hd-wallpapers-1080p.jpg");
        imageUrlList.add("http://www.need1find1.com/wp-content/uploads/2017/09/programer.jpg");
        imageUrlList.add("http://www.portoalegre.travel/upload/b/2/21290_1280x720-hd-wallpapers.jpg");

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    circularProgressBar.setVisibility(View.VISIBLE);

                    // To shuffle the collection and get a random link
                    Collections.shuffle(imageUrlList);
                    IMAGE_URL = imageUrlList.get(0);

                    // Image downloading and down-scaling will done on a background thread.
                    new DownloadImageTask().execute(IMAGE_URL);
                    Toast.makeText(getContext(),"Downloading New Image", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(),"No internet connection. Please check and try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * This performs the Downloading and the manual Down-Scaling of that image into a lower resolution
     * Bitmap, on a background thread, i.e AsyncTask
     */
    private static class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... Url) {
            Bitmap resizedImage = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(Url[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);

                // This is where magic happens. We can send any value for "Scaling Factor"
                // and the image will be down-scaled that many times.
                resizedImage = getResizedImage(inputStream,2);

                inputStream.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

            return resizedImage;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            String errorText = "Error Downloading. Try again!";
            errorMessage.setText(errorText);
            if (result == null) {
                errorMessage.setVisibility(View.VISIBLE);
            } else {
                errorMessage.setVisibility(View.INVISIBLE);
            }

            imageView.setImageBitmap(result);
            circularProgressBar.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * This is where the magic happens. This method is responsible for creating a down-scaled version
     * of Bitmap image from the downloading Input Stream, by specifying its Scaling Factor.
     * @param inputStream is the incoming stream of image data.
     * @param scalingFactor is the amount of down-scaling to be applied to the image.
     * @return a Bitmap image with reduced size and very low memory footprint.
     */
    private static Bitmap getResizedImage(InputStream inputStream, int scalingFactor) {
        final BitmapFactory.Options options = new BitmapFactory.Options();

        // To make sure that bitmap is not loaded to memory immediately.
        options.inJustDecodeBounds = true;

        // To apply the resizing factor to the image stream sample
        options.inSampleSize = scalingFactor;

        // Now we can load the bitmap to memory, after applying the scaling factor
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeStream(inputStream,null,options);
    }

}
