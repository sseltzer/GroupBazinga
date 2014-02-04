package edu.bazinga.recipebuddy.api.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import edu.bazinga.recipebuddy.api.retrievers.JSONRetriever;
import edu.bazinga.recipebuddy.data.packets.Recipe;

public class YummlyAPIManager {
  
  public ArrayList<Recipe> getRecipes(String query) {
    return parseResponse(getJSONResponse(buildQuery(query)));
  }
  
  private String buildQuery(String query) {
    return "http://api.yummly.com/v1/api/recipes?_app_id=7cd13182&_app_key=c7bec9ff978723d19d5ff32d450bd1ab&q=onion+soup";
  }
  private String getJSONResponse(String query) {
    String jsonResponse = null;
    try {
      JSONRetriever ret = new JSONRetriever();
      AsyncTask<String, Void, String> task = ret.execute(query);
      jsonResponse = task.get();
    } catch (Exception e) {
      // Ignore this one. This should never go wrong. Stack trace if it does.
      e.printStackTrace();
    }
    return jsonResponse;
  }
  
  private ArrayList<Recipe> parseResponse(String jsonResponse) {
    Log.d("asdf", "Parsing");
    ArrayList<Recipe> list = new ArrayList<Recipe>();
    try {
      JSONObject entries = new JSONObject(jsonResponse);
      JSONArray attribution = entries.getJSONArray("matches");
      for (int i = 0; i < attribution.length(); ++i) {
        JSONObject match = attribution.getJSONObject(i);
        Recipe item = new Recipe();
        
        item.setId(match.getString("id"));
        item.setIngredients(match.getString("ingredients"));
        item.setTotalTimeInSeconds(match.getString("totalTimeInSeconds"));
        item.setRecipeName(match.getString("recipeName"));
        item.setSmallImageUrls(match.getString("smallImageUrls"));
        item.setSourceDisplayName(match.getString("sourceDisplayName"));
        item.setFlavors(match.getString("flavors"));
        item.setRating(match.getString("rating"));
        JSONObject bigUrl = match.getJSONObject("imageUrlsBySize");
        item.setBigUrl(bigUrl.getString("90"));
        list.add(item);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return list;
  }
}
/*
{
  "attribution":
    {
      "html":"Recipe search powered by <a href='http://www.yummly.com/recipes'><img alt='Yummly' src='http://static.yummly.com/api-logo.png'/></a>",
      "url":"http://www.yummly.com/recipes/",
      "text":"Recipe search powered by Yummly",
      "logo":"http://static.yummly.com/api-logo.png"
    },
  "totalMatchCount":43361,
  "facetCounts":{},
  "matches":[
    {
      "smallImageUrls":["http://yummly-recipeimages-compressed.s3.amazonaws.com/Spring-Onion-Soup-Martha-Stewart-112371.s.png"],
      "ingredients":["coarse salt","chicken stock","water","extra-virgin olive oil","crisps","spring onions"],
      "flavors":
        {
          "sour":0.8333333333333334,
          "salty":1.0,
          "sweet":0.5,
          "meaty":0.16666666666666666,
          "bitter":0.8333333333333334
        },
      "imageUrlsBySize":
        {
          "90":"http://lh6.ggpht.com/mCBbVSgp_cWWDxlazrIhN8eDXsqyfhfUJ0yLvdBD-h7tu66VooTboXH_jZkJSkC1BiMgheyM0yhmcdIiY3SnzA=s90-c"
        },
      "attributes":
        {
          "course":["Soups"],
          "holiday":["spring"]
        },
      "totalTimeInSeconds":3600.0,
      "rating":5,
      "recipeName":"Spring Onion Soup",
      "sourceDisplayName":"Martha Stewart",
      "id":"Spring-Onion-Soup-Martha-Stewart"
    },
    {
      "smallImageUrls":["http://lh5.ggpht.com/MYuO7kwuQ0DPyXNpJeMR6vzmeNPDMbIsfErPhhL_0u2m3-jU6rjiOW0KQoW8gZJbTAUpLBgtnyVL6NtQeiIXWMk=s90"],
      "ingredients":["white wine","butter","onion","french loaf","hand","vegetable stock"],
      "flavors":
        {"sour":0.5,"salty":0.16666666666666666,"sweet":0.6666666666666666,"meaty":0.16666666666666666,"bitter":0.16666666666666666},"imageUrlsBySize":{"90":"http://lh5.ggpht.com/whdDwPHCqcj9cSO-BHtjpOB1GlTQ__tUOVUYnOe64cqeQt8AyXuC2Toqz0V4QQa8oOGj_3xww-ywqL0JyVy3=s90-c"},"attributes":{"course":["Soups"]},"totalTimeInSeconds":null,"rating":5,"recipeName":"Onion Soup without Tears","sourceDisplayName":"101 Cookbooks","id":"Onion-Soup-without-Tears-443154"},{"smallImageUrls":["http://yummly-recipeimages-compressed.s3.amazonaws.com/Caramelized-Balsamic-Red-Onion-Soup-with-Cheese-Topped-Croutons-Serious-Eats-200310-40102.s.png"],"ingredients":["butter","black pepper","soy sauce","prepared mustard","olive oil","swiss cheese","french bread","salt","water","balsamic vinegar","red onion"],"flavors":{"sour":0.6666666666666666,"salty":1.0,"sweet":0.6666666666666666,"piquant":0.6666666666666666,"meaty":0.6666666666666666,"bitter":0.6666666666666666},"imageUrlsBySize":{"90":"http://lh3.ggpht.com/LjMeaewmnzLf4Wtl-QuLmJdu1OFF137hVqNjXzsEXK0UwFUypVOIcx7LaLvWFAEwo9PBc-_qk3U-gL1eyf0hkK8=s90-c"},"attributes":{"course":["Soups"]},"totalTimeInSeconds":3600.0,"rating":5,"recipeName":"Caramelized Balsamic-Red Onion Soup with Cheese-Topped Croutons","sourceDisplayName":"Serious Eats","id":"Caramelized-Balsamic-Red-Onion-Soup-with-Cheese-Topped-Croutons-Serious-Eats-200310"},{"smallImageUrls":["http://yummly-recipeimages-compressed.s3.amazonaws.com/Seven-Onion-Soup-Martha-Stewart-190680-101872.s.png"],"ingredients":["chives","shallots","pearl onions","marsala wine","fresh thyme","dried porcini","white onion","leeks","extra-virgin olive oil","red onion","yellow onion"],"flavors":{"sour":0.5,"salty":0.16666666666666666,"sweet":0.6666666666666666,"meaty":0.16666666666666666,"bitter":0.16666666666666666},"imageUrlsBySize":{"90":"http://lh3.ggpht.com/aBhTJ04LuCReIagADquhwaTIkU46sbcuySkbwbjtCGDSpw44uZ7e-_8DScFooD6wdTvNnBTHTKkj-BYd24VW-e8=s90-c"},"attributes":{"course":["Soups"]},"totalTimeInSeconds":6300.0,"rating":4,"recipeName":"Seven-Onion Soup","sourceDisplayName":"Martha Stewart","id":"Seven-Onion-Soup-Martha-Stewart-190680"},{"smallImageUrls":["http://yummly-recipeimages-compressed.s3.amazonaws.com/Onion-soup-351380-342158.s.jpg"],"ingredients":["butter","shallots","black pepper","onion","sage","hot","garlic","leeks","extra-virgin olive oil","bread","red onion","pecorino cheese"],"flavors":null,"imageUrlsBySize":{"90":"http://lh3.ggpht.com/genDwKkcMpyz9I6b0z12avCyHKBwhzsKzVD45MmHkonLdd_bllwD8D1tDSuXsye_5B0nQlvtVTBf-eOJ17joCyk=s90-c"},"attributes":{"course":["Soups"]},"totalTimeInSeconds":6600.0,"rating":5,"recipeName":"Onion soup","sourceDisplayName":"Juls' Kitchen","id":"Onion-soup-351380"},{"smallImageUrls":["http://yummly-recipeimages-compressed.s3.amazonaws.com/French-onion-soup-348995-313707.s.jpg"],"ingredients":["dry red wine","swiss cheese","croutons","beef broth","sweet onion"],"flavors":{"sour":0.3333333333333333,"salty":0.6666666666666666,"sweet":0.6666666666666666,"meaty":0.3333333333333333,"bitter":0.3333333333333333},"imageUrlsBySize":{"90":"http://lh5.ggpht.com/abuZrYFauWn7rNXzI_sXgkumAPPnlhuVYopQv6suHDgavrHO5c4DbRw5DMXXP6ZFngqYTseqr0TW3K1JwzzZEQ=s90-c"},"attributes":{"course":["Soups"],"cuisine":["french"]},"totalTimeInSeconds":2100.0,"rating":5,"recipeName":"French Onion Soup","sourceDisplayName":"EveryDay with Rachael Ray","id":"French-onion-soup-348995"},{"smallImageUrls":["http://yummly-recipeimages-2.s3.amazonaws.com/French-Onion-Soup-Epicurious.s.png"],"ingredients":["butter","can beef broth","parmesan","onion","low salt chicken broth","sourdough bread","dijon mustard","garlic cloves","swiss cheese","dry white wine"],"flavors":{"sour":1.0,"salty":1.0,"sweet":0.6666666666666666,"meaty":0.8333333333333334,"bitter":1.0},"imageUrlsBySize":{"90":"http://lh6.ggpht.com/BeBf2EHjvMGl82hSd5491gB6xoDEhKA4olZLaQajPIEX2Wxful_5Wj0sHFRFGtLkfq5qiO1MUj6Y-vUYkK81=s90-c"},"attributes":{"course":["Appetizers","Soups"],"cuisine":["french","american"],"holiday":["christmas"]},"totalTimeInSeconds":5100.0,"rating":5,"recipeName":"French Onion Soup","sourceDisplayName":"Epicurious","id":"French-Onion-Soup-Epicurious"},{"smallImageUrls":["http://lh4.ggpht.com/Q4Wmqnt7d0aUbeZ_1iAZI5CMLsVmfl69Oja52EKlhkjJNWVDXffBFsSimXmaRBtcNXLyrOxvFKCL3SBSoC2Qhw=s90","http://yummly-recipeimages-compressed.s3.amazonaws.com/Crockpot-french-onion-soup-333495-350173.s.jpg","http://yummly-recipeimages-compressed.s3.amazonaws.com/Crockpot-french-onion-soup-333495-295415.s.jpg"],"ingredients":["butter","black pepper","flour","fresh thyme","brown sugar","gruyere cheese","beer","garlic cloves","french bread","low sodium beef stock","salt","sweet onion","balsamic vinegar"],"flavors":null,"imageUrlsBySize":{"90":"http://lh6.ggpht.com/2IZvz8-dGxoA52eTL1IHloebQbHbE27Nu-uAyIhyg8P24GLnli_9vVTZ7gb_3-tucCXhYi2g1ELDCZPEGK2Zmv4=s90-c"},"attributes":{"course":["Soups"],"cuisine":["french"]},"totalTimeInSeconds":null,"rating":5,"recipeName":"Crockpot French Onion Soup","sourceDisplayName":"How Sweet It Is","id":"Crockpot-french-onion-soup-333495"},{"smallImageUrls":["http://yummly-recipeimages-compressed.s3.amazonaws.com/French-Onion-Soup-The-Pioneer-Woman-Cooks-_-Ree-Drummond-41364-1512.s.jpg","http://yummly-recipeimages-compressed.s3.amazonaws.com/French-Onion-Soup-The-Pioneer-Woman-Cooks-_-Ree-Drummond-41364-1199.s.jpg","http://yummly-recipeimages-compressed.s3.amazonaws.com/French-Onion-Soup-The-Pioneer-Woman-Cooks-_-Ree-Drummond-41364-220.s.jpg"],"ingredients":["worcestershire sauce","low sodium chicken broth","gruyere cheese","stick butter","french bread","dry white wine","beef broth","yellow onion","minced garlic"],"flavors":{"sour":0.16666666666666666,"salty":0.16666666666666666,"sweet":0.16666666666666666,"meaty":0.16666666666666666,"bitter":0.16666666666666666},"imageUrlsBySize":{"90":"http://lh4.ggpht.com/3IU0ec076OSQxMkUv-RoVZ24dMf6NJ9cKZHzqUYMvywwtO3litKEvBml6b-ryr1BuIn9y9HHX1LwD5TKAT7R=s90-c"},"attributes":{"course":["Appetizers","Soups"],"cuisine":["french"],"holiday":["thanksgiving"]},"totalTimeInSeconds":null,"rating":5,"recipeName":"French Onion Soup","sourceDisplayName":"The Pioneer Woman","id":"French-Onion-Soup-The-Pioneer-Woman-Cooks-_-Ree-Drummond-41364"},{"smallImageUrls":["http://yummly-recipeimages-compressed.s3.amazonaws.com/Fast-French-Onion-Soup-Martha-Stewart-103955.s.png"],"ingredients":["coarse salt","onion","gruyere cheese","country bread","low sodium beef broth","ground pepper"],"flavors":{"sour":0.3333333333333333,"salty":0.16666666666666666,"sweet":0.5,"meaty":0.16666666666666666},"imageUrlsBySize":{"90":"http://lh3.ggpht.com/bFrVOYDBVkxpuiwYgNd9-bj5Ao6MjoDwsYFAg_fUt2HNAmyu79OllbIm2LoqWK3oZw1XpmH00Ik5EQOnryhEUA=s90-c"},"attributes":{"course":["Soups"],"cuisine":["french"]},"totalTimeInSeconds":2100.0,"rating":4,"recipeName":"Fast French Onion Soup","sourceDisplayName":"Martha Stewart","id":"Fast-French-Onion-Soup-Martha-Stewart"}],"criteria":{"excludedIngredients":null,"allowedIngredients":null,"terms":null}}
 */