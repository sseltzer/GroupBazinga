package edu.bazinga.recipebuddy.api.retrievers;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ImageRetriever extends AsyncTask<String, Void, Bitmap> {
  StringBuilder responseBuilder = new StringBuilder();

  @Override
  protected Bitmap doInBackground(String... urls) {
    Bitmap bitmap = null;
    if (urls == null) return bitmap;
    for (String url : urls) {
      if (url == null || url.equals("")) continue;
      Log.d("recipeURL: ", url);
      if (!url.startsWith("http://")) url = "http://" + url;
      try {
        HttpGet httpRequest = null;
        httpRequest = new HttpGet(url);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
        HttpEntity entity = response.getEntity();
        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
        InputStream input = b_entity.getContent();
        bitmap = BitmapFactory.decodeStream(input);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return bitmap;
  }
}