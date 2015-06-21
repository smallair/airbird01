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
	
	private EditText input;  //���ҹؼ���
	private Button query;	//��ѯ��ť
	private ListView mList; //��ʾ��ѯ���
	
	private DatabaseUtil mUtil;
	
	private List<Person> list = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.query);
		//��ʼ��
		initview();
	}

	private void initview() {
		input = (EditText)findViewById(R.id.query_input);
		query = (Button)findViewById(R.id.query_btn);
		mList = (ListView)findViewById(R.id.mlist);
		
		//��ȡ���ݿ�
		mUtil = new DatabaseUtil(QueryActivity.this);
		//������ѯ��ť
		query.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				//ÿ�β�ѯʱ�ѽ������
//				mlayout.removeAllViews();
				String name = input.getText().toString().trim();
				//��ѯ��������
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
							//��ӵ�ListView
							mList.setAdapter(new SimpleAdapter(QueryActivity.this, 
												templist, R.layout.item, 
												new String[]{"_id","name","sex"}, 
												new int[]{R.id.id_item,R.id.name_item,R.id.sex_item}));
							mList.setOnItemClickListener(new myOnItemClickListener());
				}else{
					Toast.makeText(getApplicationContext(), "����ؼ�¼", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	//����ListView�������޸ĺ�ɾ������
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
