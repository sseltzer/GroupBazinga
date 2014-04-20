package edu.bazinga.recipebuddy.activities.main.listviews;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.GroceryItem;
import edu.bazinga.recipebuddy.data.packets.GroceryList;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class GroceryListView extends ArrayAdapter<GroceryList> {
  private DataManager dm;
  private Activity activity;
  private LinearLayout layout;
 
  public GroceryListView(Activity activity, int textViewResourceId, ArrayList<GroceryList> objects) throws RecipeBuddyException {
   super(activity, textViewResourceId, objects);
   this.activity = activity;
   dm = DataManager.getInstance();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  
    ArrayList<String> List = new ArrayList<String>();
    ArrayList<String> Ld = new ArrayList<String>();
    ArrayList<String> fItems = new ArrayList<String>();
    for (GroceryList grocery : dm.getAppData().getGroceryList()) 
    	{
        ArrayList<GroceryItem> items = dm.getAppData().getGroceryList().get(position).getGroceryItems();
        String listName = grocery.getListName();
    		if (listName != null && !listName.equals("")) Ld.add(grocery.getListName().substring(0,1));
    		else Ld.add("");
    		if (items.size() > 0) fItems.add(StringTest(items.get(0).getItemName(), items.get(0).getQuantity()));
    		else fItems.add("");
    		List.add(grocery.getListName());
    		
    	}

    // Inflate the layout, mainlvitem.xml, in each row.
    LayoutInflater inflater = activity.getLayoutInflater();
    View row = inflater.inflate(R.layout.mylist_adapter, parent, false);

    // Declare and define the TextView, "item." This is where
    // the name of each recipe will appear.
    TextView item = (TextView) row.findViewById(R.id.shoppinglist);
    item.setText(List.get(position));
    TextView item2 = (TextView)row.findViewById(R.id.letter_index);
    item2.setText(Ld.get(position));
    TextView item3 = (TextView)row.findViewById(R.id.first_item);
    item3.setText(fItems.get(position));
    

    return row;
  }
  
  public String StringTest(String item, String qty)
  {
	  String result = "";
	  
	  if (item == null || item.isEmpty())
	  {
		 result = "Grocery List is Empty";
	  }
	  else
	  {
		  result = "\t" + item + "\t\tQTY: " + qty;
	  }
	  
	  return result;
  }
  
}