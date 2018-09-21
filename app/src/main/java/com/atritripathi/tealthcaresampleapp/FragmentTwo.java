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

    private static final String TAG = "FragmentTwo";

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

                    // To pick random link
                    Collections.shuffle(imageUrlList);
                    IMAGE_URL = imageUrlList.get(0);

                    new DownloadImageTask().execute(IMAGE_URL);
                    Toast.makeText(getContext(),"Downloading New Image", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(),"No internet connection. Please check and try again.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


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
                Log.d(TAG, "doInBackground: inputStream stream = " + inputStream);

                resizedImage = getResizedImage(inputStream,2);
                Log.d(TAG, "doInBackground: inputStream here = " + resizedImage);

                inputStream.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } finally {
                //connection.disconnect();
                Log.d(TAG, "doInBackground: connection is closed");
            }
            connection.disconnect();
            Log.d(TAG, "doInBackground: input there = " + resizedImage);
            return resizedImage;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            errorMessage.setText("Error Downloading. Try again!");
            if (result == null) {
                errorMessage.setVisibility(View.VISIBLE);
            } else {
                errorMessage.setVisibility(View.INVISIBLE);
            }

            imageView.setImageBitmap(result);
            circularProgressBar.setVisibility(View.INVISIBLE);
        }
    }


    private static Bitmap getResizedImage(InputStream inputStream, int scalingFactor) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        options.inSampleSize = scalingFactor;
        
        Log.d(TAG, "getResizedImage: inSampleSize = " + options.inSampleSize);

        options.inJustDecodeBounds = false;
        
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);

        Log.d(TAG, "getResizedImage: Initial bitmap size = " + bitmap.getByteCount());
        Log.d(TAG, "getResizedImage: Options outwidth = " + options.outWidth);
        Log.d(TAG, "getResizedImage: Options outheight = " + options.outHeight);

        return bitmap;
    }

}
