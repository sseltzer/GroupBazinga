package edu.bazinga.recipebuddy.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.FavoriteRecipe;
import edu.bazinga.recipebuddy.data.packets.GroceryItem;
import edu.bazinga.recipebuddy.data.packets.GroceryList;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;


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
    
    try {
      dataManager = DataManager.getInstance(this);
    } catch (RecipeBuddyException e) {
      Log.d("recipe", e.getMessage());
      e.printStackTrace();
    }
    
    ArrayList<GroceryItem> items = new ArrayList<GroceryItem>();
    items.add(new GroceryItem("item0", "0"));
    items.add(new GroceryItem("item1", "1"));
    items.add(new GroceryItem("item2", "2"));
    GroceryList list = new GroceryList("Our Grocery List", items);
    dataManager.getAppData().addGroceryList(list);
    
    FavoriteRecipe favorite = new FavoriteRecipe("123", "Everyone Loves Onions", "http://someURL.com");
    dataManager.getAppData().addFavorite(favorite);
    
    try {
      dataManager.writeFile(this);
    } catch (RecipeBuddyException e) {
      Log.d("recipe", e.getMessage());
      e.printStackTrace();
    }
	}
}
