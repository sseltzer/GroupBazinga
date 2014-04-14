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
import android.text.Html;
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
    
	private int icon_tabs[] = {	
			R.drawable.ic_action_list,
			R.drawable.ic_action_recipe,
			R.drawable.ic_action_favorite,
			android.R.drawable.ic_menu_search
	};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabs();
        
        // Formats main title
        getActionBar().setTitle(Html.fromHtml("<font style =\"bold\" color=\"#0174DF\">" + "RECIPE" + "</font>"+
        		"<font color=\"#DF7401\">"+ " BUDDY" + "</font>"));


    }

    private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		// My list tab
		Tab tab1 = actionBar
				.newTab()
				.setIcon(icon_tabs[0])
				.setTag("my list")
				.setTabListener(
						new FragmentTabListener<MyListClass>(R.id.main_fragment, this, "first",
								MyListClass.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);
		
		// Recipes Tab 
		Tab tab2 = actionBar
				.newTab()
				.setIcon(icon_tabs[1])
				.setTag("recipes")
				.setTabListener(
						new FragmentTabListener<RecipeBookActivity>(R.id.main_fragment, this, "second",
								RecipeBookActivity.class));

		actionBar.addTab(tab2);
		
		// Favorites tab
		Tab tab3 = actionBar
				.newTab()
				.setIcon(icon_tabs[2])
				.setTag("favorites")
				.setTabListener(
						new FragmentTabListener<RecipeBookActivity>(R.id.main_fragment, this, "third",
								RecipeBookActivity.class));

		actionBar.addTab(tab3);		
		
		// Search tab
		Tab tab4 = actionBar
				.newTab()
				.setText("SEARCH")
				.setIcon(icon_tabs[3])
				.setTag("search")
				.setTabListener(
						new FragmentTabListener<SearchFragment>(R.id.main_fragment, this, "fourth",
								SearchFragment.class));

		actionBar.addTab(tab4);

	}
    
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
