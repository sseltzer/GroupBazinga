package com.bazinga.groceryapppoc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class Retriever extends AsyncTask<String, Void, String> {
  StringBuilder responseBuilder = new StringBuilder();

  @Override
  protected String doInBackground(String... urls) {
    for (String url : urls) {
      HttpClient placesClient = new DefaultHttpClient();
      try {
        HttpGet placesGet = new HttpGet(url);
        HttpResponse placesResponse = placesClient.execute(placesGet);
        StatusLine placeSearchStatus = placesResponse.getStatusLine();
        if (placeSearchStatus.getStatusCode() == 200) {
          HttpEntity placesEntity = placesResponse.getEntity();
          InputStream placesContent = placesEntity.getContent();
          InputStreamReader placesInput = new InputStreamReader(placesContent);
          BufferedReader placesReader = new BufferedReader(placesInput);
          String lineIn;
          while ((lineIn = placesReader.readLine()) != null)
            responseBuilder.append(lineIn);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return responseBuilder.toString();
  }

}