package edu.bazinga.recipebuddy.activities.recipe;

public class RecipeUtils {
  
  public static String getPrepTime(String n) {
    if (n == null || n.equals("") || n.equals("null")) {
      return "Preparation Time is not available";
    } else {
      double number = Double.parseDouble(n);
      int min = (int)number / 60;

      if (min > 60) {
        int hours = min / 60;
        min = min % 60;
        return "Preparation Time: " + hours + "hr " + min + "min.";
      }
      return "Preparation Time: " + min + "min.";
    }
  }
}
