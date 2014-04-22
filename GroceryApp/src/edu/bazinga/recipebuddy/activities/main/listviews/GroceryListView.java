package edu.bazinga.recipebuddy.activities.main.listviews;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.GroceryItem;
import edu.bazinga.recipebuddy.data.packets.GroceryList;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class GroceryListView extends ArrayAdapter<GroceryList> {
  private DataManager dm;
  private Activity activity;
 // private LinearLayout layout;
 
  public GroceryListView(Activity activity, int textViewResourceId, ArrayList<GroceryList> objects) throws RecipeBuddyException {
   super(activity, textViewResourceId, objects);
   this.activity = activity;
   dm = DataManager.getInstance();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    
    GroceryList list = dm.getAppData().getGroceryList().get(position);
    String listName = list.getListName();
    String itemStr =  "Grocery List is Empty";
    if (list.getGroceryItems().size() > 0) {
      GroceryItem item = list.getGroceryItems().get(0);
      itemStr = StringTest(item.getItemName(), item.getQuantity());
    }
    String letter = "";
    if (listName != null && !listName.equals("")) letter = listName.substring(0, 1).toUpperCase();
    int color = Color.parseColor(list.getColor());
    
    
    // Inflate the layout, mainlvitem.xml, in each row.
    LayoutInflater inflater = activity.getLayoutInflater();
    RelativeLayout row = (RelativeLayout)inflater.inflate(R.layout.mylist_adapter, parent, false);
    LinearLayout layout = (LinearLayout) row.findViewById(R.id.groceryListRowLayout);
    layout.setBackgroundColor(color);
    // Declare and define the TextView, "item." This is where
    // the name of each recipe will appear.
    TextView item = (TextView) row.findViewById(R.id.shoppinglist);
    item.setText(listName);
    TextView item2 = (TextView)row.findViewById(R.id.letter_index);
    item2.setText(letter);
    TextView item3 = (TextView)row.findViewById(R.id.first_item);
    item3.setText(itemStr);
    
    return row;
  }
  
  public String StringTest(String item, String qty)
  {
	  String result = "";
	  
	  if (item == null || item.isEmpty())
	  {
		  return result = "Grocery List is Empty";
	  }
	  else
	  {
		  if (qty == null || qty.isEmpty())
		  {
			  return result = "\t" + item + "\t\tQty: 0";
		  }
		  return result = "\t" + item + "\t\tQTY: " + qty;
	  }
	  
	
  }
  
}
