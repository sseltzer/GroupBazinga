package edu.bazinga.recipebuddy.activities.main;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.recipe.RecipeUtils;
import edu.bazinga.recipebuddy.activities.recipe.RecipeViewer;
import edu.bazinga.recipebuddy.api.retrievers.ImageRetriever;
import edu.bazinga.recipebuddy.api.services.YummlyManager;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.Recipe;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class RecipeBookFragment extends Fragment {

  public ListView listView;
  private ListAdapter listAdapter;
  private static ArrayList<Recipe> recipes;

  private AdapterView.OnItemClickListener getOnItemClickListener() {
    return new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // String viewText = (String)
        // ((TextView)view.findViewById(R.id.listTitle)).getText();
        String viewText = (String) ((TextView) view.findViewById(R.id.recipeTitle)).getText();
        System.out.println(viewText);
        Recipe selected = null;
        for (Recipe recipe : recipes)
          if (recipe.getRecipeName().equals(viewText))
            selected = recipe;
        Intent i = new Intent(getActivity(), RecipeViewer.class);
        i.putExtra("selected", selected);
        getActivity().startActivity(i);

      }
    };
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
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    // Creates option menu
    setHasOptionsMenu(true); // The onCreateOptionsMenu must not return a
                             // boolean in order to work
    View myListView = inflater.inflate(R.layout.recipelist, container, false);

    // Setup
    listView = (ListView) myListView.findViewById(R.id.listTitle);

    YummlyManager ym = new YummlyManager();
    recipes = new ArrayList<Recipe>();

    try {
      ym.getRecipes("soup");
      recipes = DataManager.getInstance().getAppData().getQueries();
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not reach database.", Toast.LENGTH_LONG).show();
    }

    ArrayList<String> listNames = new ArrayList<String>();
    for (Recipe recipe : recipes)
      listNames.add(recipe.getRecipeName());

    listAdapter = new MyCustomAdapter(R.layout.recipe_list, listNames);
    // listAdapter = new ArrayAdapter<String>(getActivity(),
    // R.layout.recipelist, R.id.listTitle, listNames);
    listView.setAdapter(listAdapter);
    listView.setOnItemClickListener(getOnItemClickListener());

    return myListView;
  }

  /*
   * Added on March 8, 2014
   * 
   * @author: Gus Maturana CustomAdapter will display the images and the name of
   * the recipe
   */

  public class MyCustomAdapter extends ArrayAdapter<String> {
    public MyCustomAdapter(int textViewResourceId, ArrayList<String> objects) {
      super(getActivity(), textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      ArrayList<String> listNames = new ArrayList<String>();
      for (Recipe recipe : recipes)
        listNames.add(recipe.getRecipeName());
      ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
      for (Recipe recipe : recipes)
        bitmapArray.add(getBitmap(recipe.getBigUrl()));
      ArrayList<String> cookTime = new ArrayList<String>();
      for (Recipe recipe : recipes) cookTime.add(RecipeUtils.getPrepTime(recipe.getTotalTimeInSeconds()));
      // Inflate the layout, mainlvitem.xml, in each row.
      LayoutInflater inflater = getActivity().getLayoutInflater();
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

  } // end MyCustomAdapter
}