package edu.bazinga.recipebuddy.activities.main;

import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.recipe.RecipeViewerActivity;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.GroceryList;
import edu.bazinga.recipebuddy.data.packets.Recipe;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Add_to_grocerylist extends Activity {
	
	private DataManager dm;
	int index;
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    invalidateOptionsMenu();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.adding_recipe_to_gorcerylist);
    
    try {
      dm = DataManager.getInstance();
    } catch (RecipeBuddyException e1) {
      e1.printStackTrace();
    }
    
    getActionBar().setTitle(Html.fromHtml("<font face =\"Arial\" color=\"#0174DF\">" + "ADD RECIPE" + "</font><font color=\"#DF7401\">" + " TO GROCERY LIST" + "</font>"));
    //index = getIntent().getExtras().getInt("index");
    
    
    ListView listview = (ListView)findViewById(R.id.grocery_list_view);
    listview.setVisibility(View.VISIBLE);
    
    

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
}
