package edu.bazinga.recipebuddy.activities.support;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.main.MainActivity;



public class AboutClass extends Activity {
	
	  private ListView listView;
	  private ListAdapter listAdapter;
	  private String[] itemTitle = {"Recipe Buddy",
			  						"My Grocery List",
			  						"Store Search",
			  						"Recipe View",
			  						"Add Item",
			  						"Delete Item",
			  						"About page"
	  								};
	  private String[] Description = { "\nVersion 1.01. \nCreated by: Bazinga!\nCourse: CEN4021 \nTerm: Spring 2014",
			  						   "\nIn this screen the user can add items to the list",
			  						   "\nIn this screen the user can add items to the list",
			  						   "\nIn this screen the user can add items to the list",
			  						   "\nIn this screen the user can add items to the list",
			  						   "\nIn this screen the user can add items to the list",
			  						   "\nIn this screen the user can add items to the list"
	  								 };
	  private int[] images = { R.drawable.page1,
			  				   R.drawable.page1,
			  				   R.drawable.page1,
			  				   R.drawable.page1,
			  				   R.drawable.page1,
			  				   R.drawable.page1,
			  				   R.drawable.page1
	  						 };
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.about_app);
	    setTitle("About Recipe Buddy");	// Changes title of screen
	    //getActionBar().setDisplayHomeAsUpEnabled(true); // Enables back home button
	    	    
	    listView = (ListView) findViewById(R.id.about_list_view);
	    listAdapter = new MyCustomAdapter(AboutClass.this,R.layout.about_list,Description);
	    listView.setAdapter(listAdapter);
	    
	  }
	  
	  /*
	   * @author: Gus Maturan
	   * CustomAdapter will display the images and the name of the recipe
	   */
	   
	   public class MyCustomAdapter extends ArrayAdapter<String> 
	   {
	       public MyCustomAdapter(Context context, int textViewResourceId, String[] objects) 
	       {
	           super(context, textViewResourceId, objects);
	       }

	       @Override
	       public View getView(int position, View convertView, ViewGroup parent)
	       {
	           
	     	    
	           LayoutInflater inflater = AboutClass.this.getLayoutInflater();
	           View row = inflater.inflate(R.layout.about_list, parent, false);


	           TextView item = (TextView)row.findViewById(R.id.pageTitle);
	           item.setText(itemTitle[position]);


	           ImageView pageimage=(ImageView)row.findViewById(R.id.page_image);
	           pageimage.setImageResource(images[position]);
	           

	           TextView text_description = (TextView)row.findViewById(R.id.page_description);
	           text_description.setText(Description[position]);

	           return row;
	       }
	           
	   } // end MyCustomAdapter

	   @Override
	   public boolean onOptionsItemSelected(MenuItem menuItem)
	   {   
	   	switch(menuItem.getItemId()){
	   		case android.R.id.home:
	   			Intent intent = new Intent(this, MainActivity.class);
	   			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	   			startActivity(intent);
	   			break; 
	   		default:
	   			break;

	   	}
	   	return true;
	   }

}
