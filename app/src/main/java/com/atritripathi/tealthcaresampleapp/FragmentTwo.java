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
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentTwo extends Fragment {

    private static final String TAG = "FragmentTwo";

    private String MY_URL_STRING;
    private static ProgressBar circularProgressBar;
    private static ImageView imageView;
    private Button loadImage;

    public FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.iv_load_image);
        loadImage = view.findViewById(R.id.button_refresh);
        circularProgressBar = view.findViewById(R.id.progressBar_circle);

        final ArrayList<String> imageUrlList = new ArrayList<>();
        imageUrlList.add("https://wallpapercave.com/wp/6K44j5E.jpg");
        imageUrlList.add("https://wallpaper-house.com/data/out/4/wallpaper2you_35911.jpg");
        imageUrlList.add("http://7-themes.com/data_images/collection/8/4493158-hd-wallpapers-1080p.jpg");
        imageUrlList.add("http://www.need1find1.com/wp-content/uploads/2017/09/programer.jpg");
        imageUrlList.add("http://www.portoalegre.travel/upload/b/2/21290_1280x720-hd-wallpapers.jpg");

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularProgressBar.setVisibility(View.VISIBLE);
                Collections.shuffle(imageUrlList);
                MY_URL_STRING = imageUrlList.get(0);
                new DownloadImageTask().execute(MY_URL_STRING);
                Toast.makeText(getContext(),"Downloading Image", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {

//        private DownloadImageTask(ImageView imageView) {
//            this.imageView = imageView;
//        }

        @Override
        protected Bitmap doInBackground(String... Url) {
            Bitmap image = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(Url[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream input = new BufferedInputStream(url.openStream(),8192);
                input.reset();
                image = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } finally {
                connection.disconnect();
                Log.d(TAG, "doInBackground: connection is closed");
            }
            return image;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
            circularProgressBar.setVisibility(View.GONE);
        }
    }
}
