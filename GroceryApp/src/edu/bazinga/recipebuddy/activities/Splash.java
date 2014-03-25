package edu.bazinga.recipebuddy.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.data.collections.DataManager;


public class Splash extends Activity {

  private static int INTRO_TIME_OUT = 3600;
  private DataManager dataManager;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		
	final ImageView splashImageView = (ImageView)findViewById(R.id.splashImageView);
	splashImageView.setBackgroundResource(R.drawable.splashanimation);
	
	final AnimationDrawable animation = (AnimationDrawable)splashImageView.getBackground();
	
	// Displays the animation on the splash screen
	
	splashImageView.post(new Runnable(){
        @Override
        public void run() {
            animation.start();                
        }            
    }); 
		
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
    	// This method will be executed once the timer is over
        // Start your app main activity
        Intent i = new Intent(Splash.this, MainActivity.class);
        startActivity(i);
        finish();
      }
    }, INTRO_TIME_OUT);
    
    dataManager = DataManager.getInstance(this); 
	}
}
