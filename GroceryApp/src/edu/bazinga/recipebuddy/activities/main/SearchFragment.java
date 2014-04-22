package edu.bazinga.recipebuddy.activities.main;

import android.content.Intent;
import android.os.Bundle;
//Use this one
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.listviews.RecipeListView;
import edu.bazinga.recipebuddy.activities.support.AboutClass;
import edu.bazinga.recipebuddy.api.services.YummlyManager;
import edu.bazinga.recipebuddy.data.collections.DataManager;
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
    setHasOptionsMenu(true);
    
    rootView = inflater.inflate(R.layout.search, container, false);

    // initialize
    ym = new YummlyManager();

    search_text = (EditText) rootView.findViewById(R.id.search_bar_text);

    // Get the layout elements
    search_icon = (ImageView) rootView.findViewById(R.id.search_bar_icon);
    search_icon.setImageResource(R.drawable.srch);
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
    search_results = (ListView) rootView.findViewById(R.id.search_results);
    registerForContextMenu(search_results);
    displayList();
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
  
  //////////////////////////////////////Context Menu Handlers //////////////////////////////////////
  
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    getActivity().getMenuInflater().inflate(R.menu.search_floatingmenu, menu);
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    int position = info.position;
    
    switch (item.getItemId()) {
      case R.id.action_add_favorite_search:
        addToFavorites(position);
        return true;
    }
    return super.onContextItemSelected(item);
  }
  
  ////////////////////////////////////////////////////////////////////////////////////////////////
  
  //////////////////////////////////////Data Manager Calls //////////////////////////////////////
    
  public void initDataManager() {
    try {
      dm = DataManager.getInstance();
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not read user file.", Toast.LENGTH_LONG).show();
    }
  }
  public void addToFavorites(int index) {
    try {
      dm.getAppData().addFavorite(dm.getAppData().getQueries().get(index));
      dm.writeFile(getActivity());
      Toast.makeText(getActivity(), "Added to favorites.", Toast.LENGTH_LONG).show();
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not save favorite.", Toast.LENGTH_LONG).show();
    }
  }
  public void displayList() {
    try {
      search_results.setAdapter(new RecipeListView(getActivity(), inflater));
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
