package edu.bazinga.recipebuddy.api.query;

public class PlacesQueryBuilder extends QueryBuilder {

  protected PlacesQueryBuilder(String baseStr) {
    super(baseStr);
  }

  @Override
  public String buildQuery(String q) {
    return q;
  }

}
