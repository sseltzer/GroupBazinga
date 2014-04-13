package edu.bazinga.recipebuddy.activities;

import java.util.ArrayList;

import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.retrievers.ImageRetriever;
import edu.bazinga.recipebuddy.api.services.YummlyManager;
import edu.bazinga.recipebuddy.data.collections.ApplicationData;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.Recipe;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


//Use this one
import android.support.v4.app.Fragment;

//Don't use this one
//import android.app.Fragment;

public class SearchFragment extends Fragment
{

	ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	Recipe recipe;
	ApplicationData AD;
	DataManager DM;
	YummlyManager YM;

	ImageView search_icon;
	EditText search_text;
	ListView search_results;

	View rootView;
	LayoutInflater inflater;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		
		this.inflater = inflater;
		rootView = inflater.inflate(R.layout.search, container, false);

		//initialize 
		YM = new YummlyManager();
		//AD = new ApplicationData();
		//DM = DataManager.getInstance(this);


		search_text = (EditText) rootView.findViewById(R.id.search_bar_text);

		//Get the layout elements
		search_icon = (ImageView) rootView.findViewById(R.id.search_bar_icon);
		//search_icon.setImageResource(android.R.drawable.ic_menu_search);
		search_icon.setImageResource(R.drawable.srch);


		/* Set the onClickListener */
		//Search and populate the recipes ArrayList
		search_icon.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View target) {

				//Get the query
				String str = search_text.getText().toString();

				//Query the API and load into recipes
				recipes = YM.getRecipes(str);

				//TESTING
				Log.d("recipeList",recipes.toString());

				//load the recipes into the ListView if results exist
				if (recipes.size() != 0)
				{
					search_results = (ListView) rootView.findViewById(R.id.search_results);
					search_results.setAdapter(new RecipeAdapter(recipes));
				}
				//else Toast the user - no results were found
				else 
					Toast.makeText(getActivity(), "No results found for "+str, Toast.LENGTH_LONG).show();

			}
		});	

		Log.d("returning",rootView.toString());
		return rootView;
	}

	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.mylist_menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		Intent i;
		switch(item.getItemId()){

		case R.id.action_settings: // shows the settings screen
		{
			// TODO
			return true;
		}
		case R.id.action_about: // shows the about screen
		{
			i = new Intent(getActivity(),AboutClass.class); // calls and activity from a fragment
			getActivity().startActivity(i);
			getActivity().finish();
			return true;
		}
		case R.id.action_add: // add new item to list
		{	
			// TODO
			return true;
		}
		case R.id.action_directions: // takes user to the google map
		{
			i = new Intent(getActivity(),MapsActivity.class);
			getActivity().startActivity(i);
			getActivity().finish();
			// TODO
			return true;
		}
		case R.id.sample: 	// takes you back to the recipes screen
		{
			/*i = new Intent(this, MainActivity.class);
					startActivity(i);
					finish(); */
			return true;
		}
		case R.id.action_favorite:
		{
			// TODO
			return true;
		}
		}
		return true;
		//
	}

	public class RecipeAdapter extends BaseAdapter
	{
		ArrayList<Recipe> recipes;

		public RecipeAdapter(ArrayList<Recipe> recipeList)
		{
			recipes = recipeList;
		}

		@Override
		public int getCount() {
			return recipes.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
			//return recipes.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{

			recipe = recipes.get(position);
			View row;

			//Creating a new View
			if (convertView == null)
			{		
				row = inflater.inflate(R.layout.search_item, parent, false);
			}
			//Recyling a View
			else
			{
				row = (View) convertView;
				return row;
			}

			//set the title
			TextView item = (TextView)row.findViewById(R.id.recipeTitle);
			item.setText(recipe.getRecipeName());

			//set the image
			ImageView food=(ImageView)row.findViewById(R.id.foodImage);

			//if there is > 1 URL (delimited by ',')
			//then get the last URL in the string
			String url = recipe.getBigUrl();
			if(url.contains(","))
			{
				Log.d("Fixing", url);
				//url = url.substring(0,url.indexOf(",") - 1); (1st URL in the string)
				url = url.substring(url.indexOf(",") + 1, url.length()); //(Last URL in the string)

			}

			//set the image
			food.setImageBitmap(getBitmap(url));

			// set the prep time
			TextView cTime = (TextView)row.findViewById(R.id.authorName);
			cTime.setText(Time(recipe.getTotalTimeInSeconds()));

			//Set the source name
			TextView source = (TextView)row.findViewById(R.id.sourceName);
			source.setText("From: "+recipe.getSourceDisplayName());

			//Set the rating
			RatingBar rating = (RatingBar)row.findViewById(R.id.favorate_check);
			rating.setRating(Float.valueOf(recipe.getRating()));

			// ["lime juice","sugar","water","mint leaves"]
			Log.d("ing",recipe.getIngredients());


			//set the onclick listener 
			row.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View target) 
				{
					Intent i = new Intent(getActivity(), RecipeViewer.class);
					i.putExtra("selected", recipe);
					startActivity(i);
				}


			});


			//return the view
			return row;
		}

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

	public String Time (String n)
	{
		String result = "";
		if (n.equals("null"))
		{
			return result = "Preparation Time not available";
		}
		else
		{
			double number = Double.parseDouble(n);

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

}
