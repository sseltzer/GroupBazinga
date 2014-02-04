package edu.bazinga.recipebuddy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import edu.bazinga.recipebuddy.R;

public class Splash extends Activity {

  private static int INTRO_TIME_OUT = 500;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent i = new Intent(Splash.this, MainActivity.class);
        startActivity(i);
        finish();
      }
    }, INTRO_TIME_OUT);
	}
}
