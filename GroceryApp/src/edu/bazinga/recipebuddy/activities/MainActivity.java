package edu.bazinga.recipebuddy.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.retrievers.ImageRetriever;
import edu.bazinga.recipebuddy.api.services.YummlyManager;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.GroceryItem;
import edu.bazinga.recipebuddy.data.packets.Recipe;



public class MainActivity extends FragmentActivity {

   // FragmentCollectionPackage FCPackage;
    private ViewPager viewPager;
    Intent i;
    private TabAdapter tabAdapter;
    
    private int icon_tabs[] = {	R.drawable.ic_action_list,
    							R.drawable.ic_action_recipe,
    							R.drawable.ic_action_favorite
    							};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
       /* FCPackage =
                new FragmentCollectionPackage(
                        getSupportFragmentManager());
        
        viewPager.setAdapter(FCPackage);*/
        final ActionBar actionBar = getActionBar();
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        
        
        // Sets title of main application
        actionBar.setTitle("RECIPE BUDDY");

        // tabs will be displayed in the action bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            
        	@Override
			public void onTabReselected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
        		viewPager.setCurrentItem(tab.getPosition());
		        switch (tab.getPosition())
		        {
		            case 0:
		                // My List
		            	actionBar.setSubtitle("MY LIST");
		                break;

		            case 1:
		                // Recipes
		            	actionBar.setSubtitle("RECIPES");
		                break;
		                
		            case 2:
		            	// Favorites
		            	actionBar.setSubtitle("FAVORITES");
		            	break;

		            default:
		                break;
		        }
			}

			@Override
			public void onTabSelected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
				viewPager.setCurrentItem(tab.getPosition());
		        switch (tab.getPosition())
		        {
		            case 0:
		                // My List
		            	actionBar.setSubtitle("MY LIST");
		    	       
		                break;

		            case 1:
		                // Recipes
		            	actionBar.setSubtitle("RECIPES");
		                break;
		                
		            case 2:
		            	// Favorites
		            	actionBar.setSubtitle("FAVORITES");
		            	break;

		            default:
		                break;
		        }
				
			}

			@Override
			public void onTabUnselected(Tab tab,
					android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
        };
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        // Add 3 tabs, specifying the tab's icons and TabListener
        for(int icons : icon_tabs)
        {
        	actionBar.addTab(actionBar.newTab().setIcon(icons).setTabListener(tabListener));
        }
        /*
        //My List Tab
        Tab my_list_tab = actionBar.newTab();
        //my_list_tab.setText("My List");
        my_list_tab.setIcon(R.drawable.ic_action_list);			// Places Icon on Tab
        my_list_tab.setTabListener(tabListener);
        actionBar.addTab(my_list_tab);
        
        // Recipes Tab
        Tab recipes_tab = actionBar.newTab();
        //recipes_tab.setText("Recipes");
        recipes_tab.setIcon(R.drawable.ic_action_recipe);		// Places Icon on Tab
        recipes_tab.setTabListener(tabListener);
        actionBar.addTab(recipes_tab);
        
        // Favorites Tab
        Tab favorites_tab = actionBar.newTab();
        //favorites_tab.setText("Favorites");
        favorites_tab.setIcon(R.drawable.ic_action_favorite);	// Places Icon on Tab
        favorites_tab.setTabListener(tabListener);
        actionBar.addTab(favorites_tab);
        */
        
    }
    /*
    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            // When the tab is selected, switch to the
            // corresponding page in the ViewPager.
            viewPager.setCurrentItem(tab.getPosition());
        }

		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
    }; */

  public String Time (String n)
  {
	  String result = "";
	  if (n.equals("null"))
	  {
		  return result = "Preparation Time is not available";
	  }
	  else
	  {
		  double number = Double.parseDouble(n);;
	  
		  int num = (int) (number);
	  
		  int hours = 0;
		  int min = 0;
	  
		  min = num/60;
	  
		  if (min > 60)
		  {
			  hours = min/60;
			  min = min%60;
			  result = "Preparation Time: "+ hours +"hr " + min +"min."; 
		  }
		  if (min == 60)
		  {
			  result = "Preparation Time: "+ min + "min.";
		  }
		  else
		  {
			  result = "Preparation Time: "+ min + "min.";
		  }
	  
		  return result;
	  }
	  
	  
  } 
  /*
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  } */
}
/*

  // Handels item selections
  @Override
	public boolean onOptionsItemSelected (MenuItem item){
    Intent i;
    
  	switch(item.getItemId()){
  		case R.id.action_settings:
  			return true;
  		case R.id.action_about:
  		{
  			i = new Intent(MainActivity.this, AboutClass.class);
  			startActivity(i);
  			finish();
  			return true;
  		}
  		case R.id.action_directions:
  		{
  			i = new Intent(MainActivity.this, MapsActivity.class);
  			startActivity(i);
  			return true;
  		}
  		case R.id.action_list:
  		{
  			i = new Intent(MainActivity.this, MyListView.class);
  			startActivity(i);
  			return true;
  		}
  	}
	return false;

  }
  
 /*
  * Added on March 8, 2014
  * @author: Gus Maturana
  * CustomAdapter will display the images and the name of the recipe
  */
  /*
  public class MyCustomAdapter extends ArrayAdapter<String> 
  {
      public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) 
      {
          super(context, textViewResourceId, objects);
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent)
      {
          
    	  ArrayList<String> listNames = new ArrayList<String>();
    	  for (Recipe recipe : recipes) listNames.add(recipe.getRecipeName());
    	  ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    	  for (Recipe recipe : recipes) bitmapArray.add(getBitmap(recipe.getBigUrl()));
    	  ArrayList<String> cookTime = new ArrayList<String>();
    	  for (Recipe recipe : recipes) cookTime.add(Time(recipe.getTotalTimeInSeconds()));
    	    
    	  // Inflate the layout, mainlvitem.xml, in each row.
          LayoutInflater inflater = MainActivity.this.getLayoutInflater();
          View row = inflater.inflate(R.layout.recipe_list, parent, false);

          // Declare and define the TextView, "item." This is where
          // the name of each recipe will appear.
          TextView item = (TextView)row.findViewById(R.id.recipeTitle);
          item.setText(listNames.get(position));

          // Declare and define the ImageView, "food." This is where
          // the food image in each row will appear.
          ImageView food=(ImageView)row.findViewById(R.id.foodImage);
          food.setImageBitmap(bitmapArray.get(position));
          
          // Declare and define the TextView, "cTime." this is where
          // the cook time of each recipe will appear.
          TextView cTime = (TextView)row.findViewById(R.id.authorName);
          cTime.setText(cookTime.get(position));

          return row;
      }
      
      
  } // end MyCustomAdapter


} */
