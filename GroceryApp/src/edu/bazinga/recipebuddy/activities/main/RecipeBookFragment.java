package edu.bazinga.recipebuddy.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
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
    
    rootView = inflater.inflate(R.layout.favorites, container, false);
    listView = (ListView) rootView.findViewById(R.id.favorites_list);
    listView.setVisibility(View.VISIBLE);

    registerForContextMenu(listView);
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
  public void removeFromFavorites(int index) {
    try {
      dm.getAppData().removeFavorites(index);
      dm.writeFile(getActivity());
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not save favorite.", Toast.LENGTH_LONG).show();
    }
  }
  public void displayList() {
    try {
      listView.setAdapter(new FavoriteListView(getActivity(), inflater));
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not display recipe.", Toast.LENGTH_LONG).show();
    }
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////
  
  //////////////////////////////////////Context Menu Handlers //////////////////////////////////////
  
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    getActivity().getMenuInflater().inflate(R.menu.favorites_floatingmenu, menu);
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    int position = info.position;
    
    switch (item.getItemId()) {
      case R.id.action_add_favorite_list:
        return true;
      case R.id.action_remove_favorite:
        removeFromFavorites(position);
        displayList();
        return true;
    }
    return super.onContextItemSelected(item);
  }
  
  ////////////////////////////////////////////////////////////////////////////////////////////////
  
  ////////////////////////////////////// Options Menu Calls //////////////////////////////////////
  
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // Inflate the menu; this adds items to the action bar if it is present.
    inflater = getActivity().getMenuInflater();
    inflater.inflate(R.menu.mylist_menu, menu);
    menu.getItem(1).setVisible(false);
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
