package edu.bazinga.recipebuddy.data.packets;

import java.util.ArrayList;
import java.util.Iterator;

public class RecipeBook implements Iterable<Recipe>
{
	
	private ArrayList<Recipe> recipeList;
	
	public RecipeBook (ArrayList<Recipe> recipeList){
		this.recipeList = recipeList;
	}
	
	
	
	
	
	/* Basic methods */
	
	public ArrayList<Recipe> getRecipeList(){
		return recipeList;
	}
	
	public void setRecipeList(ArrayList<Recipe> recipeList){
		this.recipeList = recipeList;
	}
	
	public boolean addRecipe(Recipe recipe){
		return recipeList.add(recipe);
	}
	
	public Recipe removeRecipe(int index){
		return recipeList.remove(index);
	}
	////

	
	//Get a recipe by its ID
	public Recipe findRecipeByID(String id)
	{
		for(Recipe recipe : recipeList)
			if (id.equalsIgnoreCase(recipe.getId()))
				return recipe;

		return null;
	}
	
	public void sortByName(String order)
	{
		//default to asc (A-z)
		if     (order.equalsIgnoreCase("desc")) order = "<";
		else   order =  ">";
		 
		Recipe t0;
		for (int i = 0; i < recipeList.size() - 1; i++)
		{
			for (int j = i + 1; j < recipeList.size(); j++)
			{
				if (strcmp(
						order,
						recipeList.get(j).getRecipeName(),
						recipeList.get(j - 1).getRecipeName()))
				{
					t0 = recipeList.get(j);
					recipeList.set(j,     recipeList.get(j - 1));
					recipeList.set(j - 1, recipeList.get(j));
				}
			}
	
		}	
		
	}

	
	/*compare two strings based on order argument */
	private boolean strcmp(String order, String str1, String str2)
	{
		if      (order.equalsIgnoreCase("<"))  return str1.compareTo(str2) < 0;
		else if (order.equalsIgnoreCase(">"))  return str1.compareTo(str2) > 0;
		else if (order.equalsIgnoreCase("==")) return str1.compareTo(str2) == 0;
		else if (order.equalsIgnoreCase("<=")) return str1.compareTo(str2) <= 0;
		else if (order.equalsIgnoreCase(">=")) return str1.compareTo(str2) >= 0;
		else	return false;
	}
	
	
	/* Iterator support */
	
	@Override
	public Iterator<Recipe> iterator() {
		return new RecipeBookIterator(recipeList);
	}
	
	public class RecipeBookIterator implements Iterator<Recipe>
	{
		private ArrayList<Recipe> recipeList;
		private Iterator<Recipe> itr;
		
		public RecipeBookIterator(ArrayList<Recipe> recipeList)
		{
			setRecipeList(recipeList);
			itr = recipeList.iterator();
		}
		
		public ArrayList<Recipe> getRecipeList() {
			return recipeList;
		}

		public void setRecipeList(ArrayList<Recipe> recipeList) {
			this.recipeList = recipeList;
		}

		@Override
		public boolean hasNext() {
			return itr.hasNext();
		}

		@Override
		public Recipe next() {
			return itr.next();
		}

		@Override
		public void remove() {
			itr.remove();
			
		}
		
	}
	////

}
