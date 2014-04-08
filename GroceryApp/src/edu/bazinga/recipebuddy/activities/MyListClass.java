package edu.bazinga.recipebuddy.activities;


import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.data.packets.GroceryItem;
import edu.bazinga.recipebuddy.data.packets.GroceryList;

public class MyListClass extends Fragment {
	 
	  private ListView listView;
	  private ListAdapter listAdapter;
	  private static ArrayList<GroceryList> groceryList;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {

      View rootView = inflater.inflate(R.layout.recipe_list, container, false);
       
      return rootView;
  }
  public boolean onCreateOptionsMenu(Menu menu) {
  	// Inflate the menu; this adds items to the action bar if it is present.
  	MenuInflater inflater = getActivity().getMenuInflater();
  	inflater.inflate(R.menu.mylist_menu, menu);
  	    return true;
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
			// TODO
			return true;
		}
		case R.id.action_directions: // takes user to the google map
		{
			i = new Intent(getActivity(),MapsActivity.class);
			getActivity().startActivity(i);
			getActivity().finish();
			// TODO
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
}