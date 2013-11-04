package com.bazinga.groceryapppoc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
  
  private Button button = null;

  
  private void initViewAction(View view) {
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        funnelActionHandler(v);
      }
    });
  }
  
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    button = (Button) findViewById(R.id.button1);
    initViewAction(button);
  }
  
  // Take all button actions in here and funnel them to the appropriate handlers.
  private void funnelActionHandler(View view) {
    try {
      String query;
      query = buildQuery();
      Retriever ret = new Retriever();
      AsyncTask<String, Void, String> task = ret.execute(query);
      String retStr;
      retStr = task.get();
      Log.d("asdf", retStr);
    } catch (Exception e) {
      // Ignore this one. This should never go wrong. Stack trace if it does.
      e.printStackTrace();
    }
  }
  
  private String buildQuery() {
    return "http://api.yummly.com/v1/api/recipes?_app_id=7cd13182&_app_key=c7bec9ff978723d19d5ff32d450bd1ab&q=onion+soup";
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

}
