package edu.bazinga.recipebuddy.data.packets;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
  
  private String id = null;
  private String recipeName = null;
  private String totalTimeInSeconds = null;
  private String ingredients = null;
  private String smallImageUrls = null;
  private String sourceDisplayName = null;
  private String flavors = null;
  private String rating = null;
  private String bigUrl = null; 
  
  
  public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
    public Recipe createFromParcel(Parcel in) {
        return new Recipe(in); 
    }

    public Recipe[] newArray(int size) {
      return new Recipe[size];
    }
  };
  
  public Recipe(Parcel in){
    this.id                 = in.readString();
    this.recipeName         = in.readString();
    this.totalTimeInSeconds = in.readString();
    this.ingredients        = in.readString();
    this.smallImageUrls     = in.readString();
    this.sourceDisplayName  = in.readString();
    this.flavors            = in.readString();
    this.rating             = in.readString();
    this.bigUrl             = in.readString();
  }
  
  @Override
  public int describeContents() {
    return 0;
  }
  
  @Override
  public void writeToParcel(Parcel out, int flags) {
    out.writeString(id);
    out.writeString(recipeName);
    out.writeString(totalTimeInSeconds);
    out.writeString(ingredients);
    out.writeString(smallImageUrls);
    out.writeString(sourceDisplayName);
    out.writeString(flavors);
    out.writeString(rating);
    out.writeString(bigUrl);
  }
  
  public Recipe() {
    
  }
  
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

  public String getIngredients() {
    return ingredients;
  }
  public void setIngredients(String ingredients) {
    this.ingredients = ingredients;
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

  public String getFlavors() {
    return flavors;
  }
  public void setFlavors(String flavors) {
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
  
  public String toString() {
    String ret = "";
    ret += id + "\n";
    ret += recipeName + "\n";
    ret += totalTimeInSeconds + "\n";
    ret += ingredients + "\n";
    ret += smallImageUrls + "\n";
    ret += sourceDisplayName + "\n";
    ret += flavors + "\n";
    ret += rating + "\n";
    return ret;
  }
}
