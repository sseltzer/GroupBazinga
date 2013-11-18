/*
 * Group: BAZINGA!
 * @author: Gustavo Maturana
 * date: 10/31/13
 */

package com.example.grocerylist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.TextView;
import android.graphics.Color;



public class MainActivity extends Activity {
	
	ListView gList; // Grocery List
	Button recipeBtn, sLocatorBtn, editBtn; // Main Menu buttons
	ListAdapter recipeAdtr; // Recipe Adapter
	ListAdapter listAdtr; // Grocery List Adapter
	Button aboutBtn, searchBtn, settingsBtn;
	
	
	// The following string array is just for testing purposes
	private static final String[] groceryList={"Friday's Dinner", "Holloween Candy", "My next recipe",
								"Mom's Birthday", "Next Week", "Chicken Salad", "Soup Ingridients",
								"Cake", "Salmon Dinner", "Can goods", "Tacos","Today's meal", "Things I need to get",
								"Fruits I like", "Sweet Tomatoes", "Cheese Hamburgers", "Don't forget",
								"I want that", "Hot stuff", "Sundsay, cake", "What to get after work",
								"missing items", "This is just an example", "End of the list"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        // Set up custom listView list_layout ( listTitle)
        // GroceryList
        gList = (ListView)findViewById(R.id.glist); 
        listAdtr = new ArrayAdapter<String>(this,R.layout.list_layout,R.id.listTitle,groceryList);
        gList.setAdapter(listAdtr);
        recipeAdtr = new ArrayAdapter<String>(this,R.layout.recipe_layout,R.id.recipeTitle,groceryList);
       //gList.setAdapter(new ArrayAdapter<String>(this,R.layout.recipe_layout,R.id.recipeTitle,groceryList));
        
        // Click on ListView item to go to next page
        gList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        	public void onItemClick(AdapterView<?>parent, View view, int position, long id)
        	{
				Intent i = new Intent(MainActivity.this, GroceryList.class);
				startActivity(i);
				// Closing Grocery List
				finish();
        	}
		});
        
        
        recipeBtn =(Button)findViewById(R.id.recipe_btn);
        sLocatorBtn =(Button)findViewById(R.id.search_btn);
        editBtn = (Button)findViewById(R.id.edit_btn);
        
        // Android Menu Buttons
        aboutBtn = (Button)findViewById(R.id.about);
        
        recipeBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				
			recipeBtn.setBackgroundColor(R.drawable.button_pressed);
			/* Changes Activity.
			 * Intent will send you to a new screen (recipe_list_layout)
			 */
			Intent i = new Intent(MainActivity.this, RecipeClass.class);
				startActivity(i);
				finish();
				
			}
			});
        editBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			editBtn.setBackgroundColor(R.drawable.button_pressed);
			// changes the layout of the listView. It will display all grocery lists
				
				gList.setAdapter(listAdtr);
			}
		});
        
        sLocatorBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sLocatorBtn.setBackgroundColor(R.drawable.button_pressed);
				sLocatorBtn.setBackgroundColor(R.drawable.button_pressed);
				/*
				 * Intent
				 */
				
			}
		});
        


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    // Handels item selections
    @Override
	public boolean onOptionsItemSelected (MenuItem item){
    	switch(item.getItemId()){
    	
    	case R.id.menu_settings:
    		return true;
    	case R.id.about:
    	{
    		Intent i = new Intent(MainActivity.this, AboutClass.class);
			startActivity(i);
			finish();
			return true;
    	}
    	case R.id.search_Store:
    	{
    		
    	}
    	
    		
    	}
		return false;
    	
    }
    
    // General Header
    private View Header(String arg)
	{
		TextView text = new TextView(this);
		text.setText(arg);
		text.setBackgroundColor(Color.GREEN);
		text.setTextColor(Color.RED);
		return (text);
	}
    
    //
}
