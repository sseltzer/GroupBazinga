package edu.bazinga.recipebuddy.error;

public class RecipeBuddyException extends Exception {
  private static final long serialVersionUID = -869916506912703303L;

  public RecipeBuddyException() {
    super();
  }
  public RecipeBuddyException(String message) {
    super(message);
  }
  public RecipeBuddyException(String message, Throwable cause) {
    super(message, cause);
  }
  public RecipeBuddyException(Throwable cause) {
    super(cause);
  }
}
