package edu.bazinga.recipebuddy.data.packets;

import android.annotation.SuppressLint;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Iterator;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonExample
{

	@SuppressLint("UseValueOf")
	@SuppressWarnings({ "unchecked", "null" })
	public static void JSONExample() 
	{		
		Recipe r = new Recipe();
	
			//Write JSON to file
			JSONObject obj = new JSONObject();
			
			obj.put("Recipe_Name", r.getRecipeName());
			obj.put("Recipe_ID", r.getId());
			obj.put("Rating",r.getRating());
					 
			JSONArray list = new JSONArray();
			list.add(r.getIngredients());
			list.add(r.getIngredients());
			list.add(r.getIngredients());
		 
			obj.put("ingredients", list);
		 
			try 
			{		 
				FileWriter file = new FileWriter("c:\\test.json");
				file.write(obj.toJSONString());
				file.flush();
				file.close();		 
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		 
			System.out.print(obj);
			
			
			//Read JSON from file
			JSONParser parser = new JSONParser();
			 
			try 
			{
		 
				Object obj2 = parser.parse(new FileReader("c:\\test.json"));
		 
				JSONObject jsonObject = (JSONObject) obj2;
		 
				String name = (String) jsonObject.get("Recipe_Name");
				System.out.println(name);
		 
				String id = (String) jsonObject.get("Recipe_ID");
				System.out.println(id);
				
				String rating = (String) jsonObject.get("Rating");
				System.out.println(rating);
		 
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("ingredients");
				Iterator<String> iterator = msg.iterator();
				while (iterator.hasNext()) {
					System.out.println(iterator.next());
			}
		 
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (ParseException e) 
			{
				e.printStackTrace();
			}		

	}

}
