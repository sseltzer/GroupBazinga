package edu.bazinga.recipebuddy.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabAdapter extends FragmentPagerAdapter {
 
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // My List
            return new TopRatedFragment();
     /*   case 1:
            // Recipes
           return new MyListClass();
        case 2:
            // Favorites
            return new RecipeViewer(); */
        }
 		
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}