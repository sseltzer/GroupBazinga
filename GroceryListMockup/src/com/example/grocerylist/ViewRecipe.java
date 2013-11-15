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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
 
public class ViewRecipe extends Activity {
 
	ImageButton mylistBtn;
	ListView recipeList;
	ArrayAdapter<String> recipeAdptr;
	TextView ingridients, prep;
	TextView dummy;
	
	// The following string array is just for testing purposes
	private static final String[] groceryList={"Friday's Dinner", "Holloween Candy", "My next recipe",
								"Mom's Birthday", "Next Week", "Chicken Salad", "Soup Ingridients",
								"Cake", "Salmon Dinner", "Can goods", "Tacos","Today's meal", "Things I need to get",
								"Fruits I like", "Sweet Tomatoes", "Cheese Hamburgers", "Don't forget",
								"I want that", "Hot stuff", "Sundsay, cake", "What to get after work",
								"missing items", "This is just an example", "End of the list"};
 
	private static final String ingrideints= "1. Apple\n" +
											 "2.Table Spoons of salt\n" +
											 "Half cup of Milk\n" +
											 "2. Tomatoes\n";
	
	private static final String preparation = "This is how you prepare\n" +
											  "how can I dod this thing\n" +
											  "This is not the best way\n";
	private static final String recipe = "Chicken Marsala";
											  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recipe_page_layout);
        
        /* ActionBar seetings */
        ActionBar actionBar = getActionBar();
        actionBar.setSubtitle((CharSequence)recipe);
        actionBar.setDisplayHomeAsUpEnabled(true);

        
    }
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     * Returns to parent activity. Parent Activity RecipeClass.class
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {   
    	switch(menuItem.getItemId()){
    	case android.R.id.home:
    	  Intent intent = new Intent(ViewRecipe.this, RecipeClass.class);
    	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	  startActivity(intent);
    	  break; 
    	}
    	return true;
    }
 
}