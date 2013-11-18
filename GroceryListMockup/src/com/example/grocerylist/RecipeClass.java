/*
 * Group: BAZINGA!
 * @author: Gustavo Maturana
 * @date: 11/06/12
 */

package com.example.grocerylist;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
 
public class RecipeClass extends Activity {
 
	Button mylistBtn;
	ListView recipeList;
	ArrayAdapter<String> recipeAdptr;
	
	// The following string array is just for testing purposes
	private static final String[] groceryList={"Friday's Dinner", "Holloween Candy", "My next recipe",
								"Mom's Birthday", "Next Week", "Chicken Salad", "Soup Ingridients",
								"Cake", "Salmon Dinner", "Can goods", "Tacos","Today's meal", "Things I need to get",
								"Fruits I like", "Sweet Tomatoes", "Cheese Hamburgers", "Don't forget",
								"I want that", "Hot stuff", "Sundsay, cake", "What to get after work",
								"missing items", "This is just an example", "End of the list"};
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_layout);
        setTitle("Grocery List");
        
        
        ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle("Recipe");
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        
        /* ListView */
        recipeList = (ListView)findViewById(R.id.recipelist);
        /* ArrayAdapter */
        recipeAdptr = new ArrayAdapter<String>(this,R.layout.recipe_layout,R.id.recipeTitle,groceryList);
        /* Set Adapter */
        recipeList.setAdapter(recipeAdptr);
        
        mylistBtn = (Button)findViewById(R.id.mylist_btn);
        
        mylistBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/* Intent */
				Intent i = new Intent(RecipeClass.this, MainActivity.class);
				startActivity(i);
				finish();
				
			}
		});
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        	public void onItemClick(AdapterView<?>parent, View view, int position, long id)
        	{
				Intent i = new Intent(RecipeClass.this, ViewRecipe.class);
				startActivity(i);
				// Closing Grocery List
				finish();
        	}
		});

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {   
    	switch(menuItem.getItemId()){
    	case android.R.id.home:
    	  Intent intent = new Intent(RecipeClass.this, MainActivity.class);
    	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	  startActivity(intent);
    	  break; 
    	}
    	return true;
    }
 
}