package edu.bazinga.recipebuddy.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.api.recipe.Recipe;
import edu.bazinga.recipebuddy.api.recipe.RecipeAPI;

public class MainActivity extends Activity {

  private ListView listView;
  private ListAdapter listAdapter;
  private ArrayList<Recipe> recipes;
  
  private AdapterView.OnItemClickListener getOnItemClickListener() {
    return new AdapterView.OnItemClickListener() {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
        String viewText = (String) ((TextView)view.findViewById(R.id.listTitle)).getText();
        System.out.println(viewText);
        Recipe selected = null;
        for (Recipe recipe : recipes) if (recipe.getRecipeName().equals(viewText)) selected = recipe;
        Intent i = new Intent(MainActivity.this, RecipeViewer.class);
        i.putExtra("selected", selected);
        startActivity(i);
      }
    };
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecipeAPI recipeAPI = new RecipeAPI();
    recipes = recipeAPI.getRecipes(null);

    ArrayList<String> listNames = new ArrayList<String>();
    for (Recipe recipe : recipes) listNames.add(recipe.getRecipeName());

    
    listView = (ListView) findViewById(R.id.listView);
    listAdapter = new ArrayAdapter<String>(this, R.layout.recipelist, R.id.listTitle, listNames);
    listView.setAdapter(listAdapter);
    listView.setOnItemClickListener(getOnItemClickListener());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

}
