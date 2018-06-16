package com.example.navoki.khanakhazana.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.widget.ImageView;

import com.example.navoki.khanakhazana.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BitmapCacheAsyncTask extends AsyncTask<File, File, File> {

    private String videoPath;
    private final ThreadLocal<ImageView> imageView = new ThreadLocal<>();

    public synchronized static void retriveVideoFrameFromVideo(String videoPath, ImageView imageView) {
        BitmapCacheAsyncTask asyncTask = new BitmapCacheAsyncTask();
        asyncTask.videoPath = videoPath;
        asyncTask.imageView.set(imageView);
        asyncTask.execute();
    }


    @Override
    protected File doInBackground(File... files) {
        MediaMetadataRetriever mediaMetadataRetriever = null;
        Bitmap bitmap;
        File file = null;

        try {
            // for ImageCache Key on basis of url
            if (!videoPath.equals(AppConstants.NOT_AVAILABLE)) {
                byte[] byteArray = videoPath.getBytes("UTF-8");
                String base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                String directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + AppConstants.APP_FOLDER;
                File directory = new File(directoryPath);
                if (!directory.isDirectory())
                    directory.mkdirs();

                file = new File(directoryPath + File.separator + base64 + ".jpeg");
                if (!file.exists()) {
                    file.createNewFile();

                    mediaMetadataRetriever = new MediaMetadataRetriever();
                    mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
                    bitmap = mediaMetadataRetriever.getFrameAtTime();
                    Utils.scaleDown(bitmap, file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return file;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (file != null) {
            Picasso.get().load(file).placeholder(R.drawable.placeholder_video).into(imageView.get());
        }
    }
}
