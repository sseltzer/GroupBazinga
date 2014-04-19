package edu.bazinga.recipebuddy.activities.main;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.listviews.RecipeListView;
import edu.bazinga.recipebuddy.activities.recipe.RecipeViewerActivity;
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
        Intent i = new Intent(getActivity(), RecipeViewerActivity.class);
        i.putExtra("index", position);
        getActivity().startActivity(i);
      }
    };
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    setHasOptionsMenu(true);
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

    try {
      listAdapter = new RecipeListView(getActivity(), R.layout.recipe_list, listNames);
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could create list adapter.", Toast.LENGTH_LONG).show();
    }
    // listAdapter = new ArrayAdapter<String>(getActivity(),
    // R.layout.recipelist, R.id.listTitle, listNames);
    listView.setAdapter(listAdapter);
    listView.setOnItemClickListener(getOnItemClickListener());

    return myListView;
  }
}
