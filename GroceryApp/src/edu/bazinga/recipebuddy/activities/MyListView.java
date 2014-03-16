package edu.bazinga.recipebuddy.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import edu.bazinga.recipebuddy.R;

public class MyListView extends Activity {
///ListActivity {

	// private  int lItemNumber = 1;
	// private GroceryList lDbHelper;
	// public static final int ID_ = Menu.FIRST;
	// private ListView list;
	//Button recipeBtn, storeSearchBtn, addNewListBtn;
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylist);
        
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.mylist_menu, menu);
	    return true;
	}
    @Override
	public boolean onOptionsItemSelected (MenuItem item){
    	Intent i;
  	switch(item.getItemId()){
  	
  		case R.id.action_settings:
  			return true;
  		case R.id.action_about:
  		{
  			i = new Intent(this, AboutClass.class);
			startActivity(i);
			finish();
			return true;
  		}
  		case R.id.sample:
  		{
  			i = new Intent(this, MainActivity.class);
  			startActivity(i);
  			finish();
  			return true;
  		}
  	}
  	return true;
  	//
  	}
	
}
