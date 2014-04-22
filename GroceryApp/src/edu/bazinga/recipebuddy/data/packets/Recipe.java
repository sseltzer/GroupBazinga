package edu.bazinga.recipebuddy.data.packets;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import edu.bazinga.recipebuddy.api.retrievers.ImageRetriever;

public class Recipe {
  
  private String id = "";
  private String recipeName = "";
  private String totalTimeInSeconds = "";
  private ArrayList<String> ingredients = new ArrayList<String>();
  private String smallImageUrls = "";
  private String sourceDisplayName = "";
  private Flavors flavors = new Flavors();
  private ArrayList<String> course = new ArrayList<String>();
  private ArrayList<String> cuisine = new ArrayList<String>();
  private ArrayList<String> holiday = new ArrayList<String>();
  private String rating = "";
  private String bigUrl = "";
  private Bitmap bitmap;
  
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
    if (ingredientStr.equals("")) return "";
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
    JSONArray jsonArray;
    JSONObject ret = new JSONObject();
    ret.put("id", id);
    ret.put("recipeName", recipeName);
    ret.put("totalTimeInSeconds", totalTimeInSeconds);
    jsonArray = new JSONArray();
    for (int i = 0; i < ingredients.size(); ++i) jsonArray.put(ingredients.get(i));
    ret.put("ingredients", jsonArray);
    ret.put("smallImageUrls", smallImageUrls);
    ret.put("sourceDisplayName", sourceDisplayName);
    ret.put("flavors", flavors.toJSON());
    jsonArray = new JSONArray();
    for (int i = 0; i < course.size(); ++i) jsonArray.put(course.get(i));
    ret.put("course", jsonArray);
    jsonArray = new JSONArray();
    for (int i = 0; i < cuisine.size(); ++i) jsonArray.put(cuisine.get(i));
    ret.put("cuisine", jsonArray);
    jsonArray = new JSONArray();
    for (int i = 0; i < holiday.size(); ++i) jsonArray.put(holiday.get(i));
    ret.put("holiday", jsonArray);
    ret.put("rating", rating);
    ret.put("bigUrl", bigUrl);
    Log.d("recipe", ret.toString());
    return ret;
  }
  public static Recipe fromJSON(JSONObject jsonObject) throws JSONException {
    JSONArray jsonArray;
    Recipe recipe = new Recipe();
    recipe.setId(jsonObject.optString("id"));
    recipe.setRecipeName(jsonObject.optString("recipeName"));
    recipe.setTotalTimeInSeconds(jsonObject.optString("totalTimeInSeconds"));
    
    ArrayList<String> ingredients = new ArrayList<String>();
    jsonArray = jsonObject.getJSONArray("ingredients");
    for (int i = 0; i < jsonArray.length(); ++i) ingredients.add(jsonArray.optString(i));
    recipe.setIngredients(ingredients);
    
    recipe.setSmallImageUrls(jsonObject.optString("smallImageUrls"));
    recipe.setSourceDisplayName(jsonObject.optString("sourceDisplayName"));
    recipe.setFlavors(Flavors.fromJSON(jsonObject.getJSONObject("flavors")));
    
    ArrayList<String> course = new ArrayList<String>();
    jsonArray = jsonObject.getJSONArray("course");
    for (int i = 0; i < jsonArray.length(); ++i) course.add(jsonArray.optString(i));
    recipe.setCourse(course);
    
    ArrayList<String> cuisine = new ArrayList<String>();
    jsonArray = jsonObject.getJSONArray("cuisine");
    for (int i = 0; i < jsonArray.length(); ++i) cuisine.add(jsonArray.optString(i));
    recipe.setCuisine(cuisine);
    
    ArrayList<String> holiday = new ArrayList<String>();
    jsonArray = jsonObject.getJSONArray("holiday");
    for (int i = 0; i < jsonArray.length(); ++i) holiday.add(jsonArray.optString(i));
    recipe.setHoliday(holiday);
    
    recipe.setRating(jsonObject.optString("rating"));
    recipe.setBigUrl(jsonObject.optString("bigUrl"));
    recipe.fetchBitmap();
    return recipe;
  }
}

