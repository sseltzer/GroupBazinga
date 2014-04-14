package edu.bazinga.recipebuddy.api.query;

public abstract class QueryBuilder {

  private String baseStr;
  public String getBaseStr() {
    return baseStr;
  }
  protected QueryBuilder(String baseStr) {
    this.baseStr = baseStr;
  }
  public abstract String buildQuery(String q);
  protected String normalize(String normalizeStr) {
    return normalizeStr.replaceAll(" ", "+");
  }
  protected String addParam(String query, String param, String value) {
    return query + "&" + param + "=" + value; 
  }
}
