package edu.bazinga.recipebuddy.activities.main;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import edu.bazinga.recipebuddy.R;
import edu.bazinga.recipebuddy.activities.fragments.FragmentTabListener;

public class MainActivity extends FragmentActivity {
  
  public static int tab = 0;

  // FragmentCollectionPackage FCPackage;
  private int icon_tabs[] = {
      R.drawable.ic_action_list,
      R.drawable.ic_action_favorite,
      android.R.drawable.ic_menu_search,
      R.drawable.ic_action_directions
  };

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupTabs();
    
    // Formats main title
    getActionBar().setTitle(Html.fromHtml("<font face =\"Arial\" color=\"#0174DF\">" + "RECIPE" + "</font><font color=\"#DF7401\">" + " BUDDY" + "</font>"));
  }

  private void setupTabs() {
    ActionBar actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    actionBar.setDisplayShowTitleEnabled(true);

    // My list tab
    Tab tab1 = actionBar.newTab().setIcon(icon_tabs[0]).setTag("my list").setTabListener(new FragmentTabListener<GroceryListFragment>(R.id.main_fragment, this, "first", GroceryListFragment.class));
    actionBar.addTab(tab1);
    actionBar.selectTab(tab1);

    // Favorites tab
    Tab tab2 = actionBar.newTab().setIcon(icon_tabs[1]).setTag("favorites").setTabListener(new FragmentTabListener<RecipeBookFragment>(R.id.main_fragment, this, "second", RecipeBookFragment.class));
    actionBar.addTab(tab2);

    // Search tab
    Tab tab3 = actionBar.newTab().setIcon(icon_tabs[2]).setTag("search").setTabListener(new FragmentTabListener<SearchFragment>(R.id.main_fragment, this, "third", SearchFragment.class));
    actionBar.addTab(tab3);
  }
  
  @Override
  protected void onResume() {
    getActionBar().selectTab(getActionBar().getTabAt(tab));
    super.onResume();
  }
}
