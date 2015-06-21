package com.example.dbdemo;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.db.DatabaseUtil;
import com.example.db.MyHelper;
import com.example.tools.JxlUtil;

/**
 * ������
 * */
public class Main extends Activity {
	
	private Button add_btn = null;  
	private Button query_btn = null;
	private Button export_data = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		add_btn = (Button)findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new OnClickMyListener());
		
		
		query_btn = (Button)findViewById(R.id.query_delete_btn);
		query_btn.setOnClickListener(new OnClickMyListener());
		
		export_data = (Button)findViewById(R.id.export_excel);
		export_data.setOnClickListener(new OnClickMyListener());
	}
	
	private class OnClickMyListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add_btn:  //add data  ��ת��������ݵĽ���
				Intent ToAddactivity = new Intent(Main.this,Add_Date.class);
				startActivity(ToAddactivity);
				
				break;
			case R.id.query_delete_btn: //query data  ��ת����ѯ��ɾ�����޸Ľ���
				Intent Toqueryctivity = new Intent(Main.this,QueryActivity.class);
				startActivity(Toqueryctivity);
				break;
			case R.id.export_excel:
				DatabaseUtil mUtil = new DatabaseUtil(Main.this);
				JxlUtil jxl = new JxlUtil(mUtil.queryAll());
				if(jxl.toExcel()){
					Toast.makeText(getApplicationContext(), "���ݵ����ɹ�", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getApplicationContext(), "���ݵ���ʧ�ܣ�����SD�������ݿ�", Toast.LENGTH_SHORT).show();
		
				}
				break;
			default:
				break;
			}
		
			}
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
