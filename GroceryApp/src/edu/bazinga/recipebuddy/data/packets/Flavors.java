package edu.bazinga.recipebuddy.data.packets;

public class Flavors {
  private String sour;
  private String salty;
  private String sweet;
  private String piquant;
  private String meaty;
  private String bitter;
  
  public int getSour() {
    return Integer.parseInt(sour);
  }
  public void setSour(String sour) {
    this.sour = sour;
  }
  public int getSalty() {
    return Integer.parseInt(salty);
  }
  public void setSalty(String salty) {
    this.salty = salty;
  }
  public int getSweet() {
    return Integer.parseInt(sweet);
  }
  public void setSweet(String sweet) {
    this.sweet = sweet;
  }
  public int getPiquant() {
    return Integer.parseInt(piquant);
  }
  public void setPiquant(String piquant) {
    this.piquant = piquant;
  }
  public int getMeaty() {
    return Integer.parseInt(meaty);
  }
  public void setMeaty(String meaty) {
    this.meaty = meaty;
  }
  public int getBitter() {
    return Integer.parseInt(bitter);
  }
  public void setBitter(String bitter) {
    this.bitter = bitter;
  }
}
