package edu.bazinga.recipebuddy.activities.main;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.listviews.AddRecipeToGroceryListView;
import edu.bazinga.recipebuddy.activities.main.listviews.GroceryListView;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.GroceryItem;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class Add_to_grocerylist extends Activity {

  private DataManager dm;
  int index;
  ListAdapter listAdapter;
  ListView listView;

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
    index = getIntent().getExtras().getInt("index");

    listView = (ListView) findViewById(R.id.grocery_list_view);
    displayList();
    listView.setOnItemClickListener(getOnItemClickListener());
    listView.setVisibility(View.VISIBLE);

  }

  private AdapterView.OnItemClickListener getOnItemClickListener() {
    return new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AddItemsToList(position, index);
      }
    };
  }

  public void AddItemsToList(int position, int index) {
    try {
      for (String ingridient : dm.getAppData().getFavorites().get(index).getIngredients()) {
        dm.getAppData().getGroceryList().get(position).addGroceryItem(new GroceryItem(ingridient, "1", false));
        dm.writeFile(this);
      }
      Toast.makeText(this, "Items added.", Toast.LENGTH_LONG).show();
    } catch (RecipeBuddyException e) {
      Toast.makeText(this, "Could not write user file.", Toast.LENGTH_LONG).show();
    }
    finish();
  }

  public void displayList() {
    try {
      listAdapter = new GroceryListView(this, R.layout.mylist_adapter, dm.getAppData().getGroceryList());
      listView.setAdapter(listAdapter);
    } catch (RecipeBuddyException e) {
      Toast.makeText(Add_to_grocerylist.this, "Could find lists.", Toast.LENGTH_LONG).show();
    }
  }
}
