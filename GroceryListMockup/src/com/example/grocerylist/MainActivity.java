package com.example.grocerylist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends Activity {
	
	ListView gList; // Grocery List
	ImageButton recipeBtn, sLocatorBtn, editBtn;
	ListAdapter recipeAdtr; // Recipe Adapter
	ListAdapter listAdtr; // Grocery List Adapter
	
	
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
        
        
        
        // Set up custom listView list_layout ( listtitle)
        gList = (ListView)findViewById(R.id.glist); // Grocery List
        listAdtr = new ArrayAdapter<String>(this,R.layout.list_layout,R.id.listTitle,groceryList);
        gList.setAdapter(listAdtr);
        recipeAdtr = new ArrayAdapter<String>(this,R.layout.recipe_layout,R.id.recipeTitle,groceryList);
       //gList.setAdapter(new ArrayAdapter<String>(this,R.layout.recipe_layout,R.id.recipeTitle,groceryList));
        
        
        
        recipeBtn =(ImageButton)findViewById(R.id.recipe_btn);
        sLocatorBtn =(ImageButton)findViewById(R.id.search_btn);
        editBtn = (ImageButton)findViewById(R.id.edit_btn);
        
        recipeBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
			// Changes layout of listView. It will display all recipes
			gList.setAdapter(recipeAdtr);
				
			}
        	
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			// changes the layout of the listView. It will display all grocery lists
				
				gList.setAdapter(listAdtr);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
