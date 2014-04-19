package edu.bazinga.recipebuddy.activities.recipe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.MainActivity;
import edu.bazinga.recipebuddy.activities.support.AboutClass;
import edu.bazinga.recipebuddy.api.retrievers.ImageRetriever;
import edu.bazinga.recipebuddy.data.packets.Recipe;

public class RecipeViewerActivity extends Activity {

	
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_viewer);
    
    getActionBar().setDisplayHomeAsUpEnabled(true);
    Recipe recipe = getIntent().getExtras().getParcelable("selected");
    
    TextView recipeName = (TextView) findViewById(R.id.recipeName);
    TextView time = (TextView) findViewById(R.id.time);
    TextView ingredients = (TextView) findViewById(R.id.ingredients);
    
    recipeName.setText(recipe.getRecipeName());
    time.setText(RecipeUtils.getPrepTime(recipe.getTotalTimeInSeconds()));
    ingredients.setText(recipe.getIngredients());
    
    try {
      ImageView imageView = (ImageView) findViewById(R.id.recipeImage);
      imageView.setImageBitmap(recipe.getBitmap());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.recipe_viewer, menu);
    return true;
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem menuItem)
  {   
  	switch(menuItem.getItemId()){
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
