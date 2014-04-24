package edu.bazinga.recipebuddy.activities.main.listviews;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.GroceryItem;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class GroceryItemListView extends ArrayAdapter<GroceryItem> {
  private DataManager dm;
  private Activity activity;
  private int i;
 
  public GroceryItemListView(Activity activity, int textViewResourceId, ArrayList<GroceryItem> objects, int index) throws RecipeBuddyException {
   super(activity, textViewResourceId, objects);
   this.activity = activity;
   dm = DataManager.getInstance();
   i = index;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	  
    GroceryItem item = dm.getAppData().getGroceryList().get(i).getGroceryItems().get(position);
    // Inflate the layout, mainlvitem.xml, in each row.
    LayoutInflater inflater = activity.getLayoutInflater();
    View row = inflater.inflate(R.layout.itemlist_adapter, parent, false);

    // Declare and define the TextView, "item." This is where
    // the name of each recipe will appear.
    TextView name = (TextView) row.findViewById(R.id.list_item);
    name.setText(item.getItemName());
    TextView qty = (TextView)row.findViewById(R.id.item_quantity);
    qty.setText(item.getQuantity());

    if (item.getStrikeAsBoolean()) {
      name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
      qty.setPaintFlags(qty.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
    return row;
  }
  
  
}