package edu.bazinga.recipebuddy.activities;


import edu.bazinga.recipebuddy.R;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.content.Loader;
import android.content.CursorLoader;

public class MyListView extends ListActivity {

	private  int lItemNumber = 1;
	private GroceryList lDbHelper;
	public static final int ID_ = Menu.FIRST;
	private ListView list;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lDbHelper = new GroceryList(this);
        lDbHelper.Open();
        list = (ListView) findViewById(R.id.listView);
        fillData();
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
    	boolean result = super.onCreateOptionsMenu(menu);
        menu.add(0, ID_, 0, R.string.menu_insert);
        return result;
    }
	

	public boolean onOptionItemSelect(MenuItem item)
	{
		switch(item.getItemId())
		{
		case ID_:
			CreateItem();
			return true;
		}
	
		return super.onOptionsItemSelected(item);    
	}

	private void CreateItem() 
	{
			String itemName = "item " + lItemNumber++;
			lDbHelper.CreateItem(itemName, "");
			fillData();
	}
	
    @SuppressWarnings("deprecation")
	private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor c = lDbHelper.fetchAllItems();
       // CursorLoader.LoaderManager(c);
        startManagingCursor(c);

        String[] from = new String[] { GroceryList.TITLE };
        int[] to = new int[] { R.id.listTitle };
        
        // Now create an array adapter and set it to display using our row
       
        SimpleCursorAdapter notes =
            new SimpleCursorAdapter(this, R.layout.recipelist, c, from, to);
        //setListAdapter(notes);
        list.setAdapter(notes);
    }
	
	
}
