package edu.bazinga.recipebuddy.activities.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
//Use this one
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.recipe.RecipeUtils;
import edu.bazinga.recipebuddy.activities.recipe.RecipeViewerActivity;
import edu.bazinga.recipebuddy.activities.support.AboutClass;
import edu.bazinga.recipebuddy.api.services.YummlyManager;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.Recipe;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

//Don't use this one
//import android.app.Fragment;

public class SearchFragment extends Fragment {

  private DataManager dm;
  private YummlyManager ym;

  private ImageView search_icon;
  private EditText search_text;
  private ListView search_results;

  private View rootView;
  private LayoutInflater inflater;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    initDataManager();
    this.inflater = inflater;
    rootView = inflater.inflate(R.layout.search, container, false);

    // initialize
    ym = new YummlyManager();

    search_text = (EditText) rootView.findViewById(R.id.search_bar_text);

    // Get the layout elements
    search_icon = (ImageView) rootView.findViewById(R.id.search_bar_icon);
    // search_icon.setImageResource(android.R.drawable.ic_menu_search);
    search_icon.setImageResource(R.drawable.srch);

    /* Set the onClickListener */
    // Search and populate the recipes ArrayList
    search_icon.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View target) {
        doSearch();
      }
    });
    search_text.setOnEditorActionListener(new OnEditorActionListener() {
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
          doSearch();
        }
        return false;
      }
    });
    
    Log.d("returning", rootView.toString());
    displayList();
    setHasOptionsMenu(true);
    return rootView;
  }
  
  public void doSearch() {
    // Get the query
    String str = search_text.getText().toString();
    
    try {
      ym.getRecipes(str);
      displayList();
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not reach database.", Toast.LENGTH_LONG).show();
    }
  }

  public class RecipeAdapter extends BaseAdapter {

    public RecipeAdapter() {
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

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      Recipe recipe = dm.getAppData().getQueries().get(position);
      View row;

      // Creating a new View
      if (convertView == null) {
        row = inflater.inflate(R.layout.search_item, parent, false);
      }
      // Recyling a View
      else {
        row = (View) convertView;
        return row;
      }

      // set the title
      TextView item = (TextView) row.findViewById(R.id.recipeTitle);
      item.setText(recipe.getRecipeName());

      // set the image
      ImageView food = (ImageView) row.findViewById(R.id.foodImage);

      // if there is > 1 URL (delimited by ',')
      // then get the last URL in the string
      String url = recipe.getBigUrl();
      if (url.contains(",")) {
        Log.d("Fixing", url);
        // url = url.substring(0,url.indexOf(",") - 1); (1st URL in the string)
        url = url.substring(url.indexOf(",") + 1, url.length()); // (Last URL in
                                                                 // the string)

      }

      // set the image
      food.setImageBitmap(recipe.getBitmap());

      // set the prep time
      TextView cTime = (TextView) row.findViewById(R.id.authorName);
      cTime.setText(RecipeUtils.getPrepTime(recipe.getTotalTimeInSeconds()));

      // Set the source name
      TextView source = (TextView) row.findViewById(R.id.sourceName);
      source.setText("From: " + recipe.getSourceDisplayName());

      // Set the rating
      RatingBar rating = (RatingBar) row.findViewById(R.id.favorate_check);
      rating.setRating(Float.valueOf(recipe.getRating()));

      // ["lime juice","sugar","water","mint leaves"]
      Log.d("ing", recipe.getIngredients());

      final Recipe eventRecipe = recipe; 
      
      // set the onclick listener
      row.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View target) {
          Intent i = new Intent(getActivity(), RecipeViewerActivity.class);
          i.putExtra("selected", eventRecipe);
          startActivity(i);
        }

      });

      // return the view
      return row;
    }

  }
  
  //////////////////////////////////////Data Manager Calls //////////////////////////////////////
    
  public void initDataManager() {
    try {
      dm = DataManager.getInstance();
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not read user file.", Toast.LENGTH_LONG).show();
    }
  }
  public void displayList() {
    search_results = (ListView) rootView.findViewById(R.id.search_results);
    search_results.setAdapter(new RecipeAdapter());
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////

  
  ////////////////////////////////////// Options Menu Calls //////////////////////////////////////
  
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // Inflate the menu; this adds items to the action bar if it is present.
    inflater = getActivity().getMenuInflater();
    inflater.inflate(R.menu.mylist_menu, menu);
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent i;
    switch (item.getItemId()) {
      case R.id.action_about: // shows the about screen
        i = new Intent(getActivity(), AboutClass.class);
        getActivity().startActivity(i);
        return true;
      case R.id.action_add: // add new item to list
        return true;
      case R.id.action_directions: // takes user to the google map
        i = new Intent(getActivity(), MapsActivity.class);
        getActivity().startActivity(i);
        return true;
    }
    return true;
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////
}
