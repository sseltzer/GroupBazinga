package edu.bazinga.recipebuddy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import edu.bazinga.recipebuddy.R;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;


public class Splash extends Activity {

  private static int INTRO_TIME_OUT = 3600;
  
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
        Intent i = new Intent(Splash.this, MainActivity.class);
        startActivity(i);
        finish();
      }
    }, INTRO_TIME_OUT);
	}
}
