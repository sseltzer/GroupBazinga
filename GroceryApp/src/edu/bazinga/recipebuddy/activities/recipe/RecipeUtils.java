package edu.bazinga.recipebuddy.activities.recipe;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import edu.bazinga.recipebuddy.api.retrievers.ImageRetriever;

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
  
  public static Bitmap getBitmap(String url) {
    Bitmap bitmap = null;
    try {
      ImageRetriever ret = new ImageRetriever();
      AsyncTask<String, Void, Bitmap> task = ret.execute(url);
      bitmap = task.get();
    } catch (Exception e) {
      // Ignore this one. This should never go wrong. Stack trace if it does.
      e.printStackTrace();
    }
    return bitmap;
  }
}
