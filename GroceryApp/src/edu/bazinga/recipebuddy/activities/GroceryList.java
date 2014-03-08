package edu.bazinga.recipebuddy.activities;


import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;



public class GroceryList {
	
	public static final String TITLE = "_title";
	public static final String BODY = "_body";
	public static final String IDROW = "_id";
	
	private static final String TAG = "GroceryList";
	private DatabaseHelper listDbHelper;
	private SQLiteDatabase lDb;
	
	
	/**
	 * This is how the database gets to be created
	 */
	
	private final Context lCntx;
	private static final String NAME_OF_DATABASE = "_data";
	private static final int VERSION_DATABASE = 2;
	private static final String TABLE = "_list";
	
	private static final String CREATE_DATABASE = "create table list (_id integer primary key autoincrement, "+
						"_title text not null, _body text not null)";
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
	
		DatabaseHelper(Context cntx){
			super(cntx, NAME_OF_DATABASE, null, VERSION_DATABASE);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(CREATE_DATABASE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion)
		{
			Log.w(TAG,"Upgrading Database from version "+ OldVersion + " to " + NewVersion);
			db.execSQL("Drop table if exists list");
			onCreate(db);
			
		}
	}
	
	// Constructor  allows the database to be open/created
	public GroceryList(Context cntx)
	{
		this.lCntx = cntx;
	}
	
	// Open the GroceryList Database
	public GroceryList Open() throws SQLException
	{
		listDbHelper = new DatabaseHelper(lCntx);
		lDb = listDbHelper.getWritableDatabase();
		return this;
	}
	
	// Closes Database
	public void Close()
	{
		listDbHelper.close();
	}
	
	// Create a new item on list
	public long CreateItem(String title, String body)
	{
		ContentValues startValue =  new ContentValues();
		startValue.put(TITLE, title);
		startValue.put(BODY, body);
		
		return lDb.insert(TABLE, null, startValue);
	}
	
	// Deletes the item
	public boolean DeleteItem(long id)
	{
		return lDb.delete(TABLE, IDROW + "=" + id, null) > 0;
	}
	
	// Return a cursor over the list of all items
	public Cursor fetchAllItems()
	{
		return lDb.query(TABLE, new String[] {IDROW, TITLE,BODY}, null, null, null, null, null);
	}
	
	// return a cursor positioned a the note that matches to the give idrow
	
	public Cursor fetchItem(long id) throws SQLException
	{
		Cursor lCursor = lDb.query(true, TABLE, new String[] {IDROW, TITLE, BODY}, IDROW + "=" + id, null,
				null, null, null, null);
		if (lCursor != null)
		{
			lCursor.moveToFirst();
		}
		return lCursor;
	}
	
	public boolean updateList(long id, String title, String body)
	{
		ContentValues values = new ContentValues();
		values.put(TITLE, title);
		values.put(BODY, body);
		
		return lDb.update(TABLE, values, IDROW + "=" + id, null) > 0;
	}
}
