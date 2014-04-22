package edu.bazinga.recipebuddy.activities.recipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.MainActivity;
import edu.bazinga.recipebuddy.activities.support.AboutClass;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.Recipe;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class RecipeViewerActivity extends Activity {

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
    
    getActionBar().setTitle(Html.fromHtml("<font face =\"Arial\" color=\"#0174DF\">" + "RECIPE" + "</font><font color=\"#DF7401\">" + " BUDDY" + "</font>"));
    getActionBar().setSubtitle(Html.fromHtml("<font color=\"#848484\">" + "Recipe Viewer" + "</font>"));
    getActionBar().setDisplayHomeAsUpEnabled(true);
    index = getIntent().getExtras().getInt("index");
    Recipe recipe = dm.getAppData().getQueries().get(index);
    
    
    RatingBar ratingbar = (RatingBar)findViewById(R.id.ratings);
    ratingbar.setRating(Float.valueOf(recipe.getRating()));
    TextView recipeSource = (TextView)findViewById(R.id.source_text);
    recipeSource.setText("Source: " + recipe.getSourceDisplayName());
    TextView recipeName = (TextView) findViewById(R.id.recipeName);
    TextView time = (TextView) findViewById(R.id.time);
    TextView ingredients = (TextView) findViewById(R.id.ingredients);
    TextView cuisine = (TextView)findViewById(R.id.cuisine_text);
    cuisine.setText(FormatCuisine());
    recipeName.setText(recipe.getRecipeName());
    time.setText(recipe.getPrepTime());
    ingredients.setText(FormatIngridients());
   //ingredients.setText(recipe.getIngredientsAsString());
    
    
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
  
  public void addToFavorites() {
    try {
      dm.getAppData().addFavorite(dm.getAppData().getQueries().get(index));
      dm.writeFile(this);
      Toast.makeText(this, "Added to favorites.", Toast.LENGTH_LONG).show();
    } catch (RecipeBuddyException e) {
      Toast.makeText(this, "Could not save favorite.", Toast.LENGTH_LONG).show();
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
  	  case R.id.action_add_favorite:
  	    addToFavorites();
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
  public String FormatIngridients()
  {
	  String result = "";
	  for(String ingridient : dm.getAppData().getQueries().get(index).getIngredients())
	  {
		  
		 result = result + (Html.fromHtml("<font face =\"Arial\" color=\"#0080FF\">" + "&#149" +" "+ "</font><font color=\"#DF7401\">" + ingridient + "</font>") + "\n");
		  
	  }
	  return result;
  }
  public String FormatCuisine()
  {
	  String result = "";
	  for(String cuisine : dm.getAppData().getQueries().get(index).getCuisine())
	  {
		  
		 result = result + (Html.fromHtml("<font face =\"Arial\" color=\"#0080FF\">" + "&#149" +" "+ "</font><font color=\"#DF7401\">" + cuisine+ "</font>") + "\n");
		  
	  }
	  return result;
  }
}

