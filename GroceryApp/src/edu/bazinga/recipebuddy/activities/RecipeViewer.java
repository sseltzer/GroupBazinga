package edu.bazinga.recipebuddy.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.retrievers.ImageRetriever;
import edu.bazinga.recipebuddy.data.packets.Recipe;

public class RecipeViewer extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_viewer);
    
    Recipe recipe = getIntent().getExtras().getParcelable("selected");
    
    TextView recipeName = (TextView) findViewById(R.id.recipeName);
    TextView time = (TextView) findViewById(R.id.time);
    TextView ingredients = (TextView) findViewById(R.id.ingredients);
    
    recipeName.setText(recipe.getRecipeName());
    time.setText(recipe.getTotalTimeInSeconds());
    ingredients.setText(recipe.getIngredients());
    
    try {
      ImageView imageView = (ImageView) findViewById(R.id.recipeImage);
      imageView.setImageBitmap(getBitmap(recipe.getBigUrl()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Bitmap getBitmap(String url) {
    Bitmap bitmap = null;
    try {
      ImageRetriever ret = new ImageRetriever();
      AsyncTask<String, Void, Bitmap> task = ret.execute(url);
      bitmap = task.get();
    } catch (Exception e) {
      // Ignore this one. This should never go wrong. Stack trace if it does.
      e.printStackTrace();
    }
    return bitmap;
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.recipe_viewer, menu);
    return true;
  }

}
