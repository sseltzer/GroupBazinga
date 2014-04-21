package edu.bazinga.recipebuddy.activities.main;

import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.listviews.GroceryListView;
import edu.bazinga.recipebuddy.activities.recipe.GroceryListViewerActivity;
import edu.bazinga.recipebuddy.activities.support.AboutClass;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.GroceryList;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class GroceryListFragment extends Fragment {

  private DataManager dm;
  
  private ListView listView;
  private ListAdapter listAdapter;
  private EditText inputText;
  boolean firstLoad = true;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    initDataManager();

    View myListView = inflater.inflate(R.layout.mylist, container, false);
    listView = (ListView) myListView.findViewById(R.id.shoppingListView);
    listView.setVisibility(View.VISIBLE);
    listView.setOnItemClickListener(getOnItemClickListener());
    registerForContextMenu(listView);
    
    displayList();
    
    setHasOptionsMenu(true);
    return myListView;
  }
  
  private AdapterView.OnItemClickListener getOnItemClickListener() {
    return new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getActivity(), GroceryListViewerActivity.class);
        i.putExtra("index", position);
        getActivity().startActivity(i);
      }
    };
  }
  
  ////////////////////////////////////// Floating Menu Menu Handlers //////////////////////////////////////
  
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    getActivity().getMenuInflater().inflate(R.menu.mylist_floatingmenu, menu);
  }
  
  @Override
  public boolean onContextItemSelected(MenuItem item) {
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    int position = info.position;
    
    switch (item.getItemId()) {
      case R.id.action_delete:
        deleteList(position);
        displayList();
        return true;
      case R.id.action_edit_list_name:
        renameGroceryList(position);
        return true;
    }
    return super.onContextItemSelected(item);
  }
  
  ////////////////////////////////////////////////////////////////////////////////////////////////
  
  ////////////////////////////////////// Context Menu Handlers //////////////////////////////////////

  public void getNewListName() {
    View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.input_prompt_dialog, null);
    inputText = (EditText) inputView.findViewById(R.id.input);
    
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
    alertDialogBuilder.setView(inputView);
    alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        addNewList(inputText.getText().toString());
        displayList();
      }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });
    alertDialogBuilder.create().show();
  }

  public void renameGroceryList(final int position) {
    View inputView = LayoutInflater.from(getActivity()).inflate(R.layout.input_prompt_dialog, null);
    inputText = (EditText) inputView.findViewById(R.id.input);
    inputText.setText(dm.getAppData().getGroceryList().get(position).getListName());
    
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
    alertDialogBuilder.setView(inputView);
    alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        updateList(position, inputText.getText().toString());
        displayList();
      }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();
      }
    });
    alertDialogBuilder.create().show();;
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////
  
  ////////////////////////////////////// Data Manager Calls //////////////////////////////////////
  
  public void initDataManager() {
    try {
      dm = DataManager.getInstance();
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not read user file.", Toast.LENGTH_LONG).show();
    }
  }
  public void addNewList(String name) {
    try {
      Random rnd = new Random(); 
      int lim = 90;
      int r = rnd.nextInt(lim);
      int g = rnd.nextInt(lim);
      int b = rnd.nextInt(lim);
      String colorStr = getHex(r, g, b); 
      dm.getAppData().addGroceryList(new GroceryList(name, colorStr));
      dm.writeFile(getActivity());
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not write user file.", Toast.LENGTH_LONG).show();
    }
  }
  public String getHex(int... hex) {
    String ret = "#";
    for (int i : hex) {
      if (i < 16) ret += "0";
      ret += Integer.toHexString(i);
    }
    return ret;
  }
  public void updateList(int i, String name) {
    try {
      dm.getAppData().getGroceryList().get(i).setListName(name);
      dm.writeFile(getActivity());
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not write user file.", Toast.LENGTH_LONG).show();
    }
  }
  public void deleteList(int i) {
    String listName = dm.getAppData().getGroceryList().get(i).getListName();
    try {
      dm.getAppData().getGroceryList().remove(i);
      dm.writeFile(getActivity());
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could not write user file.", Toast.LENGTH_LONG).show();
      return;
    }
    Toast.makeText(getActivity(), listName + " deleted.", Toast.LENGTH_LONG).show();
  }
  public void displayList() {
    if (firstLoad) {
      firstLoad = false;
      return;
    }
    try {
      listAdapter = new GroceryListView(getActivity(), R.layout.mylist_adapter, dm.getAppData().getGroceryList());
      listView.setAdapter(listAdapter);
    } catch (RecipeBuddyException e) {
      Toast.makeText(getActivity(), "Could find lists.", Toast.LENGTH_LONG).show();
    }
  }
  ////////////////////////////////////////////////////////////////////////////////////////////////
  
  @Override
  public void onResume() {
    displayList();
    super.onResume();
  }
  
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
        getNewListName();
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