package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bean.Person;

public class DatabaseUtil {
	private MyHelper helper;

	public DatabaseUtil(Context context) {
		super();
		helper = new MyHelper(context);
	}
	
	/**插入数据
	 * @param String
	 * */
	public boolean Insert(Person person){
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "insert into "+MyHelper.TABLE_NAME
					+"(name,sex) values (" 
					+ "'"+person.getName() 
					+ "' ," + "'"+ person.getSex() + "'" + ")";    
		try {            
			db.execSQL(sql); 
			return true;
			} catch (SQLException e){  
				Log.e("err", "insert failed"); 
				return false;
				}finally{
					db.close();
				}
		
	}
	
	
	/**更新数据
	 * @param Person person , int id
	 * */
	
	public void Update(Person person ,int id){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", person.getName());
		values.put("sex", person.getSex());
		int rows = db.update(MyHelper.TABLE_NAME, values, "_id=?", new String[] { id + "" });
		
		db.close();
	}
	
	/**删除数据
	 * @param int id
	 * */
	
	public void Delete(int id){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		int raw = db.delete(MyHelper.TABLE_NAME, "_id=?", new String[]{id+""});
		db.close();
	}
	
	/**查询所有数据
	 * 
	 * */
	public List<Person> queryAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<Person> list = new ArrayList<Person>();
		Cursor cursor = db.query(MyHelper.TABLE_NAME, null, null,null, null, null, null);
		
		while(cursor.moveToNext()){
			Person person = new Person();
			 person.setId(cursor.getInt(cursor.getColumnIndex("_id")));   
			 person.setName(cursor.getString(cursor.getColumnIndex("name"))); 
			 person.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			 list.add(person);
		}
		db.close();
		return list;
	}
	
	
	/**按姓名进行查找并排序
	 * 
	 * */
	public List<Person> queryByname(String name){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<Person> list = new ArrayList<Person>();
		Cursor cursor = db.query(MyHelper.TABLE_NAME, new String[]{"_id","name","sex"}, "name like ? " ,new String[]{"%" +name + "%" }, null, null, "name asc");
//		Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
		while(cursor.moveToNext()){
			Person person = new Person();
			 person.setId(cursor.getInt(cursor.getColumnIndex("_id")));   
			 person.setName(cursor.getString(cursor.getColumnIndex("name"))); 
			 person.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			 list.add(person);
		}
		db.close();
		return list;
	} 
	
	
	/**按id查询
	 * 
	 * */
	public Person queryByid(int id){
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Person person = new Person();
		Cursor cursor = db.query(MyHelper.TABLE_NAME, new String[]{"name","sex"}, "_id=?",new String[]{ id + ""}, null, null, null);
//		db.delete(table, whereClause, whereArgs)
		while(cursor.moveToNext()){
			 person.setId(id);   
			 person.setName(cursor.getString(cursor.getColumnIndex("name"))); 
			 person.setSex(cursor.getString(cursor.getColumnIndex("sex")));
		}
		db.close();
		return person;
	}
	

}
