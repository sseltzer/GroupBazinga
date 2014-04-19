
package edu.bazinga.recipebuddy.activities;

import java.util.ArrayList;

import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.RecipeBookActivity.MyCustomAdapter;
import edu.bazinga.recipebuddy.data.collections.ApplicationData;
import edu.bazinga.recipebuddy.data.packets.GroceryList;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.bazinga.recipebuddy.data.packets.GroceryItem;
import edu.bazinga.recipebuddy.data.packets.GroceryList;
import edu.bazinga.recipebuddy.data.packets.Recipe;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.widget.Button;

public class MyListClass extends Fragment {
	 
	  private ListView listView;
	  private ListAdapter listAdapter;
	  private static GroceryList grocerylist = new GroceryList("");
	  private static ApplicationData applicationList = new ApplicationData();
	  private static ArrayList<GroceryList> listNames = new ArrayList<GroceryList>();
	  
	 // final Context context = MainActivity.class;
	  private String inputString = "";
	  private EditText inputText;
	  private ListView list;
	  private String TAG;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
	  
	  View myListView = super.onCreateView(inflater, container, savedInstanceState);
	  // Creates option menu
	  setHasOptionsMenu(true);	// The onCreateOptionsMenu must not return a boolean in order to work
	  
	  myListView = inflater.inflate(R.layout.mylist, container, false);
      
      // Setup
       listView = (ListView)myListView.findViewById(R.id.shoppingListView);
       listView.setVisibility(View.VISIBLE);
       registerForContextMenu(listView);
       
      return myListView;
  }
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
  	// Inflate the menu; this adds items to the action bar if it is present.
  	inflater = getActivity().getMenuInflater();
  	inflater.inflate(R.menu.mylist_menu, menu);
  	  
  	}
  @Override
	public boolean onOptionsItemSelected (MenuItem item){
  	Intent i;
	switch(item.getItemId()){
	
		case R.id.action_settings: // shows the settings screen
		{
			// TODO
			return true;
		}
		case R.id.action_about: // shows the about screen
		{
			i = new Intent(getActivity(),AboutClass.class); // calls and activity from a fragment
			getActivity().startActivity(i);
			getActivity().finish();
			return true;
		}
		case R.id.action_add: // add new item to list
		{	
			AskForInput();
			return true;
		}
		case R.id.action_directions: // takes user to the google map
		{
			i = new Intent(getActivity(),MapsActivity.class);
			getActivity().startActivity(i);
			getActivity().finish();
			return true;
		}
		case R.id.sample: 	// takes you back to the recipes screen
		{
			/*i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish(); */
			return true;
		}
		case R.id.action_favorite:
		{
			// TODO
			return true;
		}
	}
	return true;
	//
	}
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
   getActivity().getMenuInflater().inflate(R.menu.mylist_floatingmenu, menu);
  }
  @Override
  public boolean onContextItemSelected(MenuItem item) {
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
	  int position = info.position;
	  //CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
	  //Crime crime = adapter.getItem(position);
	  switch (item.getItemId()) {
	  		case R.id.action_add_item_to_list:
	  				//CrimeLab.get(getActivity()).deleteCrime(crime);
	  				//adapter.notifyDataSetChanged();
	  				return true;
	  		case R.id.action_delete:
  				//CrimeLab.get(getActivity()).deleteCrime(crime);
  				//adapter.notifyDataSetChanged();
	  			
  				return true;
	  		case R.id.action_go_list_contents:
  				//CrimeLab.get(getActivity()).deleteCrime(crime);
  				//adapter.notifyDataSetChanged();
  				return true;
	  		case R.id.action_edit_list_name:
	  			EditGroceryListName(position);
  				return true;
	  }
	  return super.onContextItemSelected(item);
  }

  
  public AlertDialog AskForInput()
  {
  	// gets prompt from xml view
	// We use get activity instead of Context
  	LayoutInflater layoutinflater = LayoutInflater.from(getActivity());
  	View inputView = layoutinflater.inflate(R.layout.input_prompt_dialog, null);
  	
  	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
  	
  	alertDialogBuilder.setView(inputView);
  	inputText = (EditText)inputView.findViewById(R.id.input);
  	
  	alertDialogBuilder
  		.setCancelable(false)
  		.setPositiveButton("OK", new DialogInterface.OnClickListener(){
  			public void onClick(DialogInterface dialog, int id)
  			{
  				inputString = inputText.getText().toString();
  				Log.d("str",inputString);
  				
  				//grocerylist = new GroceryList(inputString);
  				grocerylist.setListName(inputString);
  				applicationList.addGroceryList(grocerylist);
  				
  				
  				//listNames.addAll(grocerylist.getListName());
  				//listNames.add(inputString);
  				listNames.addAll(applicationList.getGroceryList());
  				
  				//listNames.add(grocerylist.getListName());
  			
  		       // listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.mylist_adapter, R.id.shoppinglist, applicationList.getGroceryList());
  		       listAdapter = new MyAdapter(R.layout.recipe_list,listNames);
  		       listView.setAdapter(listAdapter);
  		      
  			}
  		})
  		.setNegativeButton("Cancel",
  				new DialogInterface.OnClickListener() {
  			public void onClick(DialogInterface dialog, int id) {
  				dialog.cancel();
  			}
  		});

  		AlertDialog alertD = alertDialogBuilder.create();
  		alertD.show();
		return alertD;

  }
  // Need to be implemented
  public AlertDialog EditGroceryListName(int position)
  {
	// gets prompt from xml view
		// We use get activity instead of Context
	  	LayoutInflater layoutinflater = LayoutInflater.from(getActivity());
	  	View inputView = layoutinflater.inflate(R.layout.input_prompt_dialog, null);
	  	
	  	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
	  	
	  	alertDialogBuilder.setView(inputView);
	  	inputText = (EditText)inputView.findViewById(R.id.input);
	  	String tmp = " " + position;
	  	inputText.setText(tmp);
	  	
	  	
	  	alertDialogBuilder
	  		.setCancelable(false)
	  		.setPositiveButton("OK", new DialogInterface.OnClickListener(){
	  			public void onClick(DialogInterface dialog, int id)
	  			{
	  				inputString = inputText.getText().toString();
	  				Log.d("str",inputString);
	  				
	  				//grocerylist = new GroceryList(inputString);
	  				grocerylist.setListName(inputString);
	  				applicationList.addGroceryList(grocerylist);
	  				
	  				
	  				//listNames.addAll(grocerylist.getListName());
	  				//listNames.add(inputString);
	  				listNames.addAll(applicationList.getGroceryList());
	  				
	  				//listNames.add(grocerylist.getListName());
	  			
	  		       // listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.mylist_adapter, R.id.shoppinglist, applicationList.getGroceryList());
	  		       listAdapter = new MyAdapter(R.layout.recipe_list,listNames);
	  		       listView.setAdapter(listAdapter);
	  		      
	  			}
	  		})
	  		.setNegativeButton("Cancel",
	  				new DialogInterface.OnClickListener() {
	  			public void onClick(DialogInterface dialog, int id) {
	  				dialog.cancel();
	  			}
	  		});

	  		AlertDialog alertD = alertDialogBuilder.create();
	  		alertD.show();
			return alertD;
  }
  

  public void setCustomTag(String tag)
  {
      this.TAG = "MY_LIST_CLASS_TAG";
  }

  public String getCustomTag()
  {
      return TAG;
  }
  
  /*
   * Added on March 8, 2014
   * @author: Gus Maturana
   * CustomAdapter will display the images and the name of the recipe
   */
  
   public class MyAdapter extends ArrayAdapter<GroceryList> 
   {
       public MyAdapter (int textViewResourceId, ArrayList<GroceryList> objects) 
       {
           super(getActivity(), textViewResourceId, objects);
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent)
       {
           
     	  ArrayList<String> List = new ArrayList<String>();
     	 for (GroceryList grocery: listNames)
    	  List.add(grocery.getListName());
     	    
     	  // Inflate the layout, mainlvitem.xml, in each row.
           LayoutInflater inflater = getActivity().getLayoutInflater();
           View row = inflater.inflate(R.layout.mylist_adapter, parent, false);

           // Declare and define the TextView, "item." This is where
           // the name of each recipe will appear.
           TextView item = (TextView)row.findViewById(R.id.shoppinglist);
           item.setText(List.get(position));

           return row;
       }
       
       
   } // end MyCustomAdapter 
}