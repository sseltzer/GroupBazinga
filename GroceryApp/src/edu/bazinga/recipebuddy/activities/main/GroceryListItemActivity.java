package edu.bazinga.recipebuddy.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.support.AboutClass;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class GroceryListItemActivity extends Activity {

  private DataManager dm;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initDataManager();
    
    setContentView(R.layout.mylist);
    int index = getIntent().getIntExtra("index", -1);
    if (index > -1) {
      Toast.makeText(this, "Viewing List: " + dm.getAppData().getGroceryList().get(index).getListName(), Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(this, "Index could not be read.", Toast.LENGTH_LONG).show();
    }
    
  }

  
  public void addItem() {
    
  }
  
  
  ////////////////////////////////////// Data Manager Calls //////////////////////////////////////
  
  public void initDataManager() {
    try {
      dm = DataManager.getInstance();
    } catch (RecipeBuddyException e) {
      Toast.makeText(this, "Could not read user file.", Toast.LENGTH_LONG).show();
    }
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////
  
  ////////////////////////////////////// Options Menu Calls //////////////////////////////////////
  
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // Inflate the menu; this adds items to the action bar if it is present.
    inflater = getMenuInflater();
    inflater.inflate(R.menu.mylist_menu, menu);
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent i;
    switch (item.getItemId()) {
      case R.id.action_about: // shows the about screen
        i = new Intent(this, AboutClass.class);
        startActivity(i);
        return true;
      case R.id.action_add: // add new item to list
        addItem();
        return true;
      case R.id.action_directions: // takes user to the google map
        i = new Intent(this, MapsActivity.class);
        startActivity(i);
        return true;
    }
    return true;
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////
}
