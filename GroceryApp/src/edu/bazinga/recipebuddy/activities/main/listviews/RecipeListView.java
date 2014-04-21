package edu.bazinga.recipebuddy.activities.main.listviews;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.recipe.RecipeViewerActivity;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.Recipe;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class RecipeListView extends BaseAdapter {

  private DataManager dm;
  private Activity activity;
  private LayoutInflater inflater;
  
  public RecipeListView(Activity activity, LayoutInflater inflater) throws RecipeBuddyException {
    this.activity = activity;
    this.inflater = inflater;
    dm = DataManager.getInstance();
   }
  
  @Override
  public int getCount() {
    return dm.getAppData().getQueries().size();
  }

  @Override
  public Object getItem(int position) {
    return null;
    // return recipes.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    if (convertView != null) return (View) convertView;
    View row = inflater.inflate(R.layout.search_item, parent, false);

    Recipe recipe = dm.getAppData().getQueries().get(position);
    
    // set the title
    TextView item = (TextView) row.findViewById(R.id.recipeTitle);
    item.setText(recipe.getRecipeName());

    // Set the image
    ImageView food = (ImageView) row.findViewById(R.id.foodImage);
    food.setImageBitmap(recipe.getBitmap());

    // Set the author
    TextView cTime = (TextView) row.findViewById(R.id.authorName);
    cTime.setText(recipe.getPrepTime());

    // Set the source name
    TextView source = (TextView) row.findViewById(R.id.sourceName);
    source.setText("From: " + recipe.getSourceDisplayName());

    // Set the rating
    RatingBar rating = (RatingBar) row.findViewById(R.id.favorate_check);
    rating.setRating(Float.valueOf(recipe.getRating()));
    
    row.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View target) {
        Intent i = new Intent(activity, RecipeViewerActivity.class);
        i.putExtra("index", position);
        activity.startActivity(i);
      }
    });

    // return the view
    return row;
  }

}