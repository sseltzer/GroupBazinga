package edu.bazinga.recipebuddy.api.query;

public class YummlyQueryBuilder extends QueryBuilder {

  public static YummlyQueryBuilder qb = null;
  
  public static YummlyQueryBuilder getInstance() {
    if (qb == null) qb = new YummlyQueryBuilder();
    return qb;
  }
  
  protected YummlyQueryBuilder() {
    this("http://api.yummly.com/v1/api/recipes?_app_id=7cd13182&_app_key=c7bec9ff978723d19d5ff32d450bd1ab");
  }
  protected YummlyQueryBuilder(String baseStr) {
    super(baseStr);
  }

  public String buildQuery(String q) {
    String queryStr = super.addParam(super.getBaseStr(), "q", q);
    return super.normalize(queryStr);
  }
}
