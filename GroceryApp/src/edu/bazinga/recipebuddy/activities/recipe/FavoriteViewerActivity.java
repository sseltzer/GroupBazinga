package edu.bazinga.recipebuddy.activities.recipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.MainActivity;
import edu.bazinga.recipebuddy.activities.support.AboutClass;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.Recipe;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class FavoriteViewerActivity extends Activity {

	private DataManager dm;
	int index;
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    invalidateOptionsMenu();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_viewer);
    
    try {
      dm = DataManager.getInstance();
    } catch (RecipeBuddyException e1) {
      e1.printStackTrace();
    }
    
    index = getIntent().getExtras().getInt("index");
    Recipe recipe = dm.getAppData().getFavorites().get(index);
    
    TextView recipeName = (TextView) findViewById(R.id.recipeName);
    TextView time = (TextView) findViewById(R.id.time);
    TextView ingredients = (TextView) findViewById(R.id.ingredients);
    
    recipeName.setText(recipe.getRecipeName());
    time.setText(recipe.getPrepTime());
    ingredients.setText(recipe.getIngredientsAsString());
    
    try {
      ImageView imageView = (ImageView) findViewById(R.id.recipeImage);
      imageView.setImageBitmap(recipe.getBitmap());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public boolean onPrepareOptionsMenu(Menu menu) {
    return true;
  }
  
  public void removeFromFavorites(int index) {
    try {
      dm.getAppData().removeFavorites(index);
      dm.writeFile(this);
      Toast.makeText(this, "Removed from favorites.", Toast.LENGTH_LONG).show();
    } catch (RecipeBuddyException e) {
      Toast.makeText(this, "Could not save favorite.", Toast.LENGTH_LONG).show();
    }
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.favorite_viewer, menu);
    return true;
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem)
  {   
  	switch(menuItem.getItemId()){
  	  case R.id.action_remove_favorite:
  	    removeFromFavorites(index);
  	    break;
  		case android.R.id.home:
  			Intent intent = new Intent(this, MainActivity.class);
  			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
  			startActivity(intent);
  			break; 
  		case R.id.action_about:
  			Intent i = new Intent(this, AboutClass.class);
  			startActivity(i);
  			finish();
  			return true;
  	}
  	return true;
  }
}
