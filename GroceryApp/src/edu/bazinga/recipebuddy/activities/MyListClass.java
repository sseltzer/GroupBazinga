
package edu.bazinga.recipebuddy.activities;

import java.util.ArrayList;

import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.RecipeBookActivity.MyCustomAdapter;
import edu.bazinga.recipebuddy.data.packets.GroceryList;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
	  private static GroceryList grocerylist;
	  
	 // final Context context = MainActivity.class;
	  private String inputString = "";
	  private EditText inputText;
	  private ListView list;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
	  
	  // Creates option menu
	  setHasOptionsMenu(true);	// The onCreateOptionsMenu must not return a boolean in order to work
      View myListView = inflater.inflate(R.layout.mylist, container, false);
      
      // Setup
       listView = (ListView)myListView.findViewById(R.id.shoppingListView);
       
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
  				int size = 1;
  				inputString = inputText.getText().toString();
  				Log.d("str",inputString);
  				
  				grocerylist = new GroceryList(inputString);
  				//grocerylist.setListName(inputString);
  				ArrayList<String> listNames = new ArrayList<String>();
  				//for (int i = 0; i < size; i++)
  				//{
  					listNames.add(inputString);
  					//listNames.add(inputString);
  					size++;
  				//} 
  		        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.mylist_adapter, R.id.shoppinglist, listNames);
  		       // listAdapter = new MyAdapter(R.layout.recipe_list,listNames);
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
  
  /*
   * Added on March 8, 2014
   * @author: Gus Maturana
   * CustomAdapter will display the images and the name of the recipe
   */
  /* 
   public class MyAdapter extends ArrayAdapter<String> 
   {
       public MyAdapter (int textViewResourceId, ArrayList<String> objects) 
       {
           super(getActivity(), textViewResourceId, objects);
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent)
       {
           
     	  ArrayList<String> listNames = new ArrayList<String>();
    	  listNames.add(grocerylist.getListName());
     	    
     	  // Inflate the layout, mainlvitem.xml, in each row.
           LayoutInflater inflater = getActivity().getLayoutInflater();
           View row = inflater.inflate(R.layout.recipe_list, parent, false);

           // Declare and define the TextView, "item." This is where
           // the name of each recipe will appear.
           TextView item = (TextView)row.findViewById(R.id.recipeTitle);
           item.setText(listNames.get(position));

           return row;
       }
       
       
   } // end MyCustomAdapter */
}