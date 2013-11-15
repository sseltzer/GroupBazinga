/*
 * Group: BAZINGA!
 * @author: Gustavo Maturana
 * @date: 11/06/12
 */

package com.example.grocerylist;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
 
public class GroceryList extends Activity {
 
/*
 * When the use click in any of the items that are shown in the list in the main screen
 * the user will be rerouted to this class 
 */
	ImageButton backBtn;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        

        ActionBar actionBar = getActionBar();
        actionBar.setSubtitle("My List");
        actionBar.setDisplayHomeAsUpEnabled(true);
        

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {   
    	switch(menuItem.getItemId()){
    	case android.R.id.home:
    	  Intent intent = new Intent(GroceryList.this, MainActivity.class);
    	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	  startActivity(intent);
    	  break; 
    	}
    	return true;
    }

 
}