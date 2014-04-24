package edu.bazinga.recipebuddy.activities.support;

import android.util.Log;

public class NameFormatter {
  
  public static String format(String listName) {
    if ("".equals(listName)) return "";
    listName = listName.trim();
    listName = getCapital(listName, 0) + listName.substring(1);
    for (int i = 1; i < listName.length(); ++i) {
      if (listName.charAt(i) == ' ') listName = splice(listName, i);
    }
    return listName;
  }
  private static String getCapital(String listName, int i) {
    return String.valueOf(listName.charAt(i)).toUpperCase();
  }
  private static String splice(String listName, int i) {
    Log.d("recipe", "" + listName.substring(0, i) + getCapital(listName, i + 1) + listName.substring(i + 2));
    return listName.substring(0, i + 1) + getCapital(listName, i + 1) + listName.substring(i + 2);
  }
}
