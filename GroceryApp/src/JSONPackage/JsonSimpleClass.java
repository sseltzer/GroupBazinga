package JSONPackage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Iterator;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonSimpleClass
{
			public static void main(String[] args) 
			{
				//Write JSON to file
				JSONObject obj = new JSONObject();
				obj.put("name", "mkyong.com");
				obj.put("age", new Integer(100));
			 
				JSONArray list = new JSONArray();
				list.add("msg 1");
				list.add("msg 2");
				list.add("msg 3");
			 
				obj.put("messages", list);
			 
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
			 
					String name = (String) jsonObject.get("name");
					System.out.println(name);
			 
					long age = (Long) jsonObject.get("age");
					System.out.println(age);
			 
					// loop array
					JSONArray msg = (JSONArray) jsonObject.get("messages");
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
