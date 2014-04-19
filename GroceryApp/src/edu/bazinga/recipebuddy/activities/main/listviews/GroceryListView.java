package edu.bazinga.recipebuddy.activities.main.listviews;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.data.collections.DataManager;
import edu.bazinga.recipebuddy.data.packets.GroceryList;
import edu.bazinga.recipebuddy.error.RecipeBuddyException;

public class GroceryListView extends ArrayAdapter<GroceryList> {
  private DataManager dm;
  private Activity activity;
 
  public GroceryListView(Activity activity, int textViewResourceId, ArrayList<GroceryList> objects) throws RecipeBuddyException {
   super(activity, textViewResourceId, objects);
   this.activity = activity;
   dm = DataManager.getInstance();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    ArrayList<String> List = new ArrayList<String>();
    for (GroceryList grocery : dm.getAppData().getGroceryList()) List.add(grocery.getListName());

    // Inflate the layout, mainlvitem.xml, in each row.
    LayoutInflater inflater = activity.getLayoutInflater();
    View row = inflater.inflate(R.layout.mylist_adapter, parent, false);

    // Declare and define the TextView, "item." This is where
    // the name of each recipe will appear.
    TextView item = (TextView) row.findViewById(R.id.shoppinglist);
    item.setText(List.get(position));

    return row;
  }
}