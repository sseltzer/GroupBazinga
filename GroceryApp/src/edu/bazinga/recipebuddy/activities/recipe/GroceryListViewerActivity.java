package edu.bazinga.recipebuddy.activities.recipe;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.MapsActivity;
import edu.bazinga.recipebuddy.activities.main.listviews.GroceryItemListView;
import edu.bazinga.recipebuddy.activities.support.AboutClass;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.GroceryItem;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class GroceryListViewerActivity extends Activity {

  private DataManager dm;

  private int listIndex;
  private ListAdapter listAdapter;
  private ListView listview;
  private EditText inputTextItem;
  private EditText inputTextQty;
  
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
    
    listIndex = index;
    getActionBar().setDisplayHomeAsUpEnabled(true); // Goes back to main activity
    getActionBar().setSubtitle(Html.fromHtml("<font color=\"#848484\">" + dm.getAppData().getGroceryList().get(index).getListName() + "</font>"));
    getActionBar().setTitle(Html.fromHtml("<font face =\"Arial\" color=\"#0174DF\">" + "GROCERY" + "</font><font color=\"#DF7401\">" + " LIST VIEW" + "</font>"));
    listview = (ListView)findViewById(R.id.shoppingListView);
    registerForContextMenu(listview);
    displayItems();
    
    
  }

  
  public void addItem(String name, String qty) {
	try {
		  dm.getAppData().getGroceryList().get(listIndex).addGroceryItem(new GroceryItem(name,qty));
		  dm.writeFile(this);
	  } catch (RecipeBuddyException e) {
		  Toast.makeText(this, "Could not write user file.", Toast.LENGTH_LONG).show();
	  } 
  }
  public void updateItemList(int i, String name, String qty)
  {
	  try{
		  dm.getAppData().getGroceryList().get(listIndex).getGroceryItems().get(i).setItemName(name);
		  dm.getAppData().getGroceryList().get(listIndex).getGroceryItems().get(i).setQuantity(qty);
		  dm.writeFile(this);
	  }catch (RecipeBuddyException e) {
		  Toast.makeText(this, "Could not write user file.", Toast.LENGTH_LONG).show();
	  }
  }
  
  public void deleteItem(int i)
  {
	  String itemName = dm.getAppData().getGroceryList().get(listIndex).getGroceryItems().get(i).getItemName();
	  try{
		  dm.getAppData().getGroceryList().get(listIndex).removeGroceryItem(i);
		  dm.writeFile(this);
	  }catch (RecipeBuddyException e) {
		  Toast.makeText(this, "Could not write user file.", Toast.LENGTH_LONG).show();
		  return;
	  }
	  Toast.makeText(this, itemName + " deleted", Toast.LENGTH_LONG).show();
  }
  
  public void displayItems()
  {
	  try{
		  listAdapter = new GroceryItemListView(this, R.layout.itemlist_adapter, dm.getAppData().getGroceryList().get(listIndex).getGroceryItems(), listIndex);
		  listview.setAdapter(listAdapter);
	  }catch(RecipeBuddyException e){
		Toast.makeText(this, "Could not find List.", Toast.LENGTH_LONG).show();  
	  }
  }
  public void getNewItemName() {
	    View inputView = LayoutInflater.from(this).inflate(R.layout.input_item_prompt_dialog, null);
	    inputTextItem = (EditText) inputView.findViewById(R.id.input_item);
	    inputTextQty = (EditText) inputView.findViewById(R.id.input_qty);
	    
	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	    alertDialogBuilder.setView(inputView);
	    alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int id) {
	        addItem(inputTextItem.getText().toString(), inputTextQty.getText().toString());
	        displayItems();
	      }
	    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int id) {
	        dialog.cancel();
	      }
	    });
	    alertDialogBuilder.create().show();;
	  }
  
  public void renameGroceryItem(final int position) {
	    View inputView = LayoutInflater.from(this).inflate(R.layout.input_item_prompt_dialog, null);
	    inputTextItem = (EditText) inputView.findViewById(R.id.input_item);
	    inputTextItem.setText(dm.getAppData().getGroceryList().get(listIndex).getGroceryItems().get(position).getItemName());
	    inputTextQty = (EditText) inputView.findViewById(R.id.input_qty);
	    inputTextQty.setText(dm.getAppData().getGroceryList().get(listIndex).getGroceryItems().get(position).getQuantity());
	    
	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	    alertDialogBuilder.setView(inputView);
	    alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int id) {
	        updateItems(position, inputTextItem.getText().toString(), inputTextQty.getText().toString());
	        displayItems();
	      }
	    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialog, int id) {
	        dialog.cancel();
	      }
	    });
	    alertDialogBuilder.create().show();;
	  }
  
  ////////////////////////////////////// Data Manager Calls //////////////////////////////////////
  
  protected void updateItems(int position, String item, String qty) {
	// TODO Auto-generated method stub
	  try{
		  dm.getAppData().getGroceryList().get(listIndex).getGroceryItems().get(position).setItemName(item);
		  dm.getAppData().getGroceryList().get(listIndex).getGroceryItems().get(position).setQuantity(qty);
		  dm.writeFile(this);
	  }catch(RecipeBuddyException e){
		  Toast.makeText(this, "Could not write user file.", Toast.LENGTH_LONG).show();
	  }
	
}

public void initDataManager() {
    try {
      dm = DataManager.getInstance();
    } catch (RecipeBuddyException e) {
      Toast.makeText(this, "Could not read user file.", Toast.LENGTH_LONG).show();
    }
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////
  
  ////////////////////////////////////// Options Menu Calls //////////////////////////////////////
  
@Override
public boolean onCreateOptionsMenu(Menu menu) {
  // Inflate the menu; this adds items to the action bar if it is present.
  getMenuInflater().inflate(R.menu.mylist_menu, menu);
  return true;
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
    	  getNewItemName();
        return true;
      case R.id.action_directions: // takes user to the google map
        i = new Intent(this, MapsActivity.class);
        startActivity(i);
        return true;
    }
    return true;
  }
  
  /////////////////////////////////////// Context Menu Calls ///////////////////////////////////////////
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    this.getMenuInflater().inflate(R.menu.mylist_floatingmenu, menu);
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    int position = info.position;
    
    switch (item.getItemId()) {
      case R.id.action_delete:
        deleteItem(position);
        displayItems();
        return true;
      case R.id.action_edit_list_name:
    	  renameGroceryItem(position);
    	  displayItems();
        return true;
    }
    return super.onContextItemSelected(item);
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////
}
