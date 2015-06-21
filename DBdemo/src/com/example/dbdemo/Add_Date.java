package com.example.dbdemo;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.bean.Person;
import com.example.db.DatabaseUtil;
import com.example.db.MyHelper;

public class Add_Date extends Activity {
	/*界面组件*/
	private Button add_enter;
	private Button add_cancel;
	private EditText name_edt;
	private RadioButton radio_male;
	private RadioButton radio_female;
	private RadioGroup radioGroup;
	
	private DatabaseUtil mDBUtil;
	
	private int sex = 1; //判断性别

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_data);
		//初始化
		initview();
		Log.e("init", "init");
		this.setListener();
	}
	
	//性别监听
	public void setListener(){
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Log.e("radio", "radio");
				if(checkedId == R.id.radio_male){
					sex = 1;
				}else if(checkedId == R.id.radio_female){
					sex = 0;
				}
			}
		});
	}

	/*初始化界面*/
	private void initview() {
		add_enter = (Button)findViewById(R.id.add_enter_btn);
		add_cancel = (Button)findViewById(R.id.add_cancel_btn);
		name_edt = (EditText)findViewById(R.id.name_edt);
		radioGroup = (RadioGroup)findViewById(R.id.radioGroup_selectSex);
		radio_male = (RadioButton)findViewById(R.id.radio_male);
		radio_female = (RadioButton)findViewById(R.id.radio_female);
		//获取数据库
		mDBUtil = new DatabaseUtil(Add_Date.this);
		//监听添加
		add_enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("add_enter", "add_enter");
				Person person = new Person();
				if(null == name_edt.getText().toString().trim() || name_edt.getText().toString().length() == 0){
					Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
					return;
				}
				person.setName(name_edt.getText().toString());
				if(sex == 1){
					person.setSex(radio_male.getText().toString());
				}else{
					person.setSex(radio_female.getText().toString());
				}
				if(mDBUtil.Insert(person)){
					Toast.makeText(getApplicationContext(), "添加数据成功", Toast.LENGTH_SHORT).show();
					clear();
				}else{
					Toast.makeText(getApplicationContext(), "添加数据失败", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		add_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
			

	
	private void clear(){
		name_edt.setText("");
	}
}
