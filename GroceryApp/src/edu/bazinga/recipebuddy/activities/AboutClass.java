package edu.bazinga.recipebuddy.activities;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import edu.bazinga.recipebuddy.R;



public class AboutClass extends Activity {
	
	  private ListView listView;
	  private ListAdapter listAdapter;
	  private String[] itemTitle = {"Recipe Buddy",
			  						"My Grocery List"
	  								};
	  private String[] Description = { "Version 1.01. \n Created by: Bazinga!\n Course: CEN4021",
			  						 "This is just an example of description"
			  						
	  								};
	  private int[] images = { R.drawable.page1,
			  				   	  R.drawable.gbicon
	  						  };
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.about_app);
	    setTitle("ABOUT");	// Changes title of screen
	    
	    	    
	    listView = (ListView) findViewById(R.id.listView);
	    listAdapter = new MyCustomAdapter(this,R.layout.about_list,Description);
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

}
