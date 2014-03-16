package edu.bazinga.recipebuddy.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.services.YummlyManager;
import edu.bazinga.recipebuddy.api.retrievers.ImageRetriever;
import edu.bazinga.recipebuddy.data.packets.Recipe;



public class MainActivity extends Activity {

  private ListView listView;
  private ListAdapter listAdapter;
  private static ArrayList<Recipe> recipes;
  
  private AdapterView.OnItemClickListener getOnItemClickListener() {
    return new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
      //  String viewText = (String) ((TextView)view.findViewById(R.id.listTitle)).getText();
        String viewText = (String) ((TextView)view.findViewById(R.id.recipeTitle)).getText();
        System.out.println(viewText);
        Recipe selected = null;
        for (Recipe recipe : recipes) if (recipe.getRecipeName().equals(viewText)) selected = recipe;
        Intent i = new Intent(MainActivity.this, RecipeViewer.class);
        i.putExtra("selected", selected);
        startActivity(i);
      }
    };
  }
  
  private Bitmap getBitmap(String url) {
	    Bitmap bitmap = null;
	    try {
	      ImageRetriever ret = new ImageRetriever();
	      AsyncTask<String, Void, Bitmap> task = ret.execute(url);
	      bitmap = task.get();
	    } catch (Exception e) {
	      // Ignore this one. This should never go wrong. Stack trace if it does.
	      e.printStackTrace();
	    }
	    return bitmap;
	  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle("MY RECIPES");	// Changes title of screen
    @SuppressWarnings("unused")
	YummlyManager recipeAPI = new YummlyManager();
    recipes = new ArrayList<Recipe>();
    //if (recipes == null) recipes = recipeAPI.getRecipes("beef stew");
    Log.d("asdf", "recipes length: " + recipes.size());
    
    ArrayList<String> listNames = new ArrayList<String>();
    for (Recipe recipe : recipes) listNames.add(recipe.getRecipeName());
    
    listView = (ListView) findViewById(R.id.listView);
    //listAdapter = new MyCustomAdapter(this,R.layout.recipe_list,listNames);
    listAdapter = new ArrayAdapter<String>(this, R.layout.recipelist, R.id.listTitle, listNames);
    listView.setAdapter(listAdapter);
    listView.setOnItemClickListener(getOnItemClickListener());
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
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


}
