package edu.bazinga.recipebuddy.activities.main.listviews;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.recipe.RecipeUtils;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.Recipe;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class RecipeListView extends ArrayAdapter<String> {
  
  private DataManager dm;
  private Activity activity;
  
  public RecipeListView(Activity activity, int textViewResourceId, ArrayList<String> objects) throws RecipeBuddyException {
    super(activity, textViewResourceId, objects);
    this.activity = activity;
    dm = DataManager.getInstance();
   }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    ArrayList<String> listNames = new ArrayList<String>();
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    ArrayList<String> cookTime = new ArrayList<String>();
    
    for (Recipe recipe : dm.getAppData().getQueries()) {
      listNames.add(recipe.getRecipeName());
      bitmapArray.add(recipe.getBitmap());
      cookTime.add(RecipeUtils.getPrepTime(recipe.getTotalTimeInSeconds()));
    }
    
    LayoutInflater inflater = activity.getLayoutInflater();
    View row = inflater.inflate(R.layout.recipe_list, parent, false);

    // Declare and define the TextView, "item." This is where
    // the name of each recipe will appear.
    TextView item = (TextView) row.findViewById(R.id.recipeTitle);
    item.setText(listNames.get(position));

    // Declare and define the ImageView, "food." This is where
    // the food image in each row will appear.
    ImageView food = (ImageView) row.findViewById(R.id.foodImage);
    food.setImageBitmap(bitmapArray.get(position));

    // Declare and define the TextView, "cTime." this is where
    // the cook time of each recipe will appear.
    TextView cTime = (TextView) row.findViewById(R.id.authorName);
    cTime.setText(cookTime.get(position));

    return row;
  }
}
