package edu.bazinga.recipebuddy.data.packets;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import edu.bazinga.recipebuddy.api.retrievers.ImageRetriever;

public class Recipe {
  
  private String id = null;
  private String recipeName = null;
  private String totalTimeInSeconds = null;
  private ArrayList<String> ingredients = null;
  private String smallImageUrls = null;
  private String sourceDisplayName = null;
  private Flavors flavors = null;
  private ArrayList<String> course = null;
  private ArrayList<String> cuisine = null;
  private ArrayList<String> holiday = null;
  private String rating = null;
  private String bigUrl = null;
  private Bitmap bitmap = null;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getRecipeName() {
    return recipeName;
  }
  public void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }
  
  public String getTotalTimeInSeconds() {
    return totalTimeInSeconds;
  }
  public void setTotalTimeInSeconds(String totalTimeInSeconds) {
    this.totalTimeInSeconds = totalTimeInSeconds;
  }

  public ArrayList<String> getIngredients() {
    return ingredients;
  }
  public void setIngredients(ArrayList<String> ingredients) {
    this.ingredients = ingredients;
  }
  public String getIngredientsAsString() {
    StringBuilder builder = new StringBuilder();
    for (String s : ingredients) builder.append(s + ", ");
    String ingredientStr = builder.toString(); 
    return ingredientStr.substring(0, ingredientStr.lastIndexOf(","));
  }

  public String getSmallURL() {
    return smallImageUrls;
  }
  public void setSmallImageUrls(String smallImageUrls) {
    this.smallImageUrls = smallImageUrls.replace("\\/", "/").replace("[\"", "").replace("\"]", "");;
  }

  public String getSourceDisplayName() {
    return sourceDisplayName;
  }
  public void setSourceDisplayName(String sourceDisplayName) {
    this.sourceDisplayName = sourceDisplayName;
  }

  public Flavors getFlavors() {
    return flavors;
  }
  public void setFlavors(Flavors flavors) {
    this.flavors = flavors;
  }

  public String getRating() {
    return rating;
  }
  public void setRating(String rating) {
    this.rating = rating;
  }
  
  public String getBigUrl() {
    return bigUrl;
  }
  public void setBigUrl(String bigUrl) {
    this.bigUrl = bigUrl.replace("\\/", "/").replace("[\"", "").replace("\"]", "");
  }
  
  public ArrayList<String> getCourse() {
    if (course == null) course = new ArrayList<String>();
    return course;
  }
  public void setCourse(ArrayList<String> course) {
    this.course = course;
  }
  public ArrayList<String> getCuisine() {
    if (cuisine == null) cuisine = new ArrayList<String>();
    return cuisine;
  }
  public void setCuisine(ArrayList<String> cuisine) {
    this.cuisine = cuisine;
  }
  public ArrayList<String> getHoliday() {
    if (holiday == null) holiday = new ArrayList<String>();
    return holiday;
  }
  public void setHoliday(ArrayList<String> holiday) {
    this.holiday = holiday;
  }
  
  public String getPrepTime() {
    if (totalTimeInSeconds == null || totalTimeInSeconds.equals("") || totalTimeInSeconds.equals("null")) {
      return "Preparation Time is not available";
    } else {
      double number = Double.parseDouble(totalTimeInSeconds);
      int min = (int)number / 60;

      if (min > 60) {
        int hours = min / 60;
        min = min % 60;
        return "Preparation Time: " + hours + "hr " + min + "min.";
      }
      return "Preparation Time: " + min + "min.";
    }
  }
  
  public Bitmap getBitmap() {
    if (bitmap == null) fetchBitmap(); 
    return bitmap;
  }

  private void fetchBitmap() {
    if (bigUrl != null) {
      try {
        ImageRetriever ret = new ImageRetriever();
        AsyncTask<String, Void, Bitmap> task = ret.execute(bigUrl);
        bitmap = task.get();
      } catch (Exception e) {
        // Ignore this one. This should never go wrong. Stack trace if it does.
        e.printStackTrace();
      }
    }
  }
  
  public JSONObject toJSON() throws JSONException {
    JSONObject ret = new JSONObject();
    ret.put("id", id);
    ret.put("recipeName", recipeName);
    ret.put("bigUrl", bigUrl);   
    return ret;
  }
  public static Recipe fromJSON(JSONObject jsonObject) throws JSONException {
    Recipe recipe = new Recipe();
    recipe.setId(jsonObject.getString("id"));
    recipe.setRecipeName(jsonObject.getString("recipeName"));
    recipe.setBigUrl(jsonObject.getString("bigUrl"));
    recipe.fetchBitmap();
    return recipe;
  }
}

