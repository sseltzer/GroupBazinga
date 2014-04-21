package edu.bazinga.recipebuddy.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.listviews.FavoriteListView;
import edu.bazinga.recipebuddy.activities.support.AboutClass;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class RecipeBookFragment extends Fragment {

  private DataManager dm;
  private ListView listView;
  
  private View rootView;
  private LayoutInflater inflater;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    initDataManager();
    this.inflater = inflater;
    setHasOptionsMenu(true);
    
    rootView = inflater.inflate(R.layout.search, container, false);
    
    
    listView = (ListView) rootView.findViewById(R.id.search_results);
    displayList();
    return rootView;
  }
  
  
  ////////////////////////////////////// Data Manager Calls //////////////////////////////////////
  
  public void initDataManager() {
    try {
      dm = DataManager.getInstance();
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not read user file.", Toast.LENGTH_LONG).show();
    }
  }
  public void addToFavorites(int index) {
    dm.getAppData().addFavorite(dm.getAppData().getQueries().get(index));
  }
  public void displayList() {
    try {
      listView.setAdapter(new FavoriteListView(getActivity(), inflater));
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not display recipe.", Toast.LENGTH_LONG).show();
    }
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////

  
  ////////////////////////////////////// Options Menu Calls //////////////////////////////////////
  
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // Inflate the menu; this adds items to the action bar if it is present.
    inflater = getActivity().getMenuInflater();
    inflater.inflate(R.menu.mylist_menu, menu);
    menu.getItem(0).setVisible(false);
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
