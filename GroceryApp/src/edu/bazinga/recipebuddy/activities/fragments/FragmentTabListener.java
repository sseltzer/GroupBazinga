package edu.bazinga.recipebuddy.activities.fragments;

import edu.bazinga.recipebuddy.activities.main.MainActivity;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;

public class FragmentTabListener<T extends Fragment> implements TabListener {
  private Fragment mFragment;
  private final FragmentActivity mActivity;
  private final String mTag;
  private final Class<T> mClass;
  private final int mfragmentContainerId;

  // This version defaults to replacing the entire activity content area
  // new FragmentTabListener<SomeFragment>(this, "first", SomeFragment.class))
  public FragmentTabListener(FragmentActivity activity, String tag, Class<T> clz) {
    mActivity = activity;
    mTag = tag;
    mClass = clz;
    mfragmentContainerId = android.R.id.content;
  }

  // This version supports specifying the container to replace with fragment
  // content
  // new FragmentTabListener<SomeFragment>(R.id.flContent, this, "first",
  // SomeFragment.class))
  public FragmentTabListener(int fragmentContainerId, FragmentActivity activity, String tag, Class<T> clz) {
    mActivity = activity;
    mTag = tag;
    mClass = clz;
    mfragmentContainerId = fragmentContainerId;
  }

  /* The following are each of the ActionBar.TabListener callbacks */

  public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
    FragmentTransaction sft = mActivity.getSupportFragmentManager().beginTransaction();
    // Check if the fragment is already initialized
    MainActivity.tab = tab.getPosition();
    switch (tab.getPosition()) {
    case 0:
      // My List
      // access the actionbar of the MainActivity.java and changes the subtitle
      mActivity.getActionBar().setSubtitle(Html.fromHtml("<font color=\"#848484\">" + "My List" + "</font>"));
      break;
      
    case 1:
      // Favorites
      // access the actionbar of the MainActivity.java and changes the subtitle
      mActivity.getActionBar().setSubtitle(Html.fromHtml("<font color=\"#848484\">" + "Favorites " + "</font>"));
      break;
      
    case 2:
      // Search
      // access the actionbar of the MainActivity.java and changes the subtitle
      mActivity.getActionBar().setSubtitle(Html.fromHtml("<font color=\"#848484\">" + "Search" + "</font>"));
      break;

    case 3:
      // Recipes
      // access the actionbar of the MainActivity.java and changes the subtitle
      mActivity.getActionBar().setSubtitle(Html.fromHtml("<font color=\"#848484\">" + "Recipes" + "</font>"));
      break;

    default:
      break;
    }
    if (mFragment == null) {
      // If not, instantiate and add it to the activity
      mFragment = Fragment.instantiate(mActivity, mClass.getName());
      sft.add(mfragmentContainerId, mFragment, mTag);
    } else {
      // If it exists, simply attach it in order to show it
      sft.attach(mFragment);
    }
    sft.commit();
  }

  public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
    FragmentTransaction sft = mActivity.getSupportFragmentManager()
        .beginTransaction();
    if (mFragment != null) {
      // Detach the fragment, because another one is being attached
      sft.detach(mFragment);
    }
    sft.commit();
  }

  public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
    // User selected the already selected tab. Usually do nothing.
  }
}
