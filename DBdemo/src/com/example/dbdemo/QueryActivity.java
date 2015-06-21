package com.example.dbdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.bean.Person;
import com.example.db.DatabaseUtil;
import com.example.db.MyHelper;

public class QueryActivity extends Activity {
	
	private EditText input;  //查找关键字
	private Button query;	//查询按钮
	private ListView mList; //显示查询结果
	
	private DatabaseUtil mUtil;
	
	private List<Person> list = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.query);
		//初始化
		initview();
	}

	private void initview() {
		input = (EditText)findViewById(R.id.query_input);
		query = (Button)findViewById(R.id.query_btn);
		mList = (ListView)findViewById(R.id.mlist);
		
		//获取数据库
		mUtil = new DatabaseUtil(QueryActivity.this);
		//监听查询按钮
		query.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				//每次查询时把界面清空
//				mlayout.removeAllViews();
				String name = input.getText().toString().trim();
				//查询所有数据
//				list = mUtil.queryAll();
				list = mUtil.queryByname(name);
				Log.e("listsize", list.size()+"");
				if(list.size() != 0){
						List<Map<String, Object>> templist = new ArrayList<Map<String,Object>>();
							
							for(Person person:list){
								Map<String,Object> map = new HashMap<String, Object>();
								map.put("_id", person.getId());
								map.put("name", person.getName());
								map.put("sex", person.getSex());
								templist.add(map);
							}
							//添加到ListView
							mList.setAdapter(new SimpleAdapter(QueryActivity.this, 
												templist, R.layout.item, 
												new String[]{"_id","name","sex"}, 
												new int[]{R.id.id_item,R.id.name_item,R.id.sex_item}));
							mList.setOnItemClickListener(new myOnItemClickListener());
				}else{
					Toast.makeText(getApplicationContext(), "无相关记录", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	//监听ListView，进入修改和删除界面
	private class myOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Map<String ,Object> map = (Map<String, Object>)parent.getItemAtPosition(position);
			int id_person = (Integer) map.get("_id");
//			Toast.makeText(getApplicationContext(), id_person+"", Toast.LENGTH_SHORT).show();
			Intent toDel_Update = new Intent(QueryActivity.this,DelAndUpdate.class);
			toDel_Update.putExtra("id", id_person+"");
			startActivity(toDel_Update);
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
